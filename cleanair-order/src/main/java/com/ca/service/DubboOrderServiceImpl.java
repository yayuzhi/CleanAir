package com.ca.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ca.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ca.mapper.OrderItemMapper;
import com.ca.mapper.OrderMapper;
//import com.ca.mapper.OrderShippingMapper;
import com.ca.pojo.Order;
import com.ca.pojo.OrderItem;
//import com.ca.pojo.OrderShipping;

@Service(timeout = 3000)
public class DubboOrderServiceImpl implements DubboOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;


    /**
     * 1、存入顶订单
     * 2、存入order——item
     * 3、删除购物车
     *
     * @param order
     * @return
     */
    @Override
    @Transactional    //控制事务
    public String saveOrder(Order order, List<Cart> cartList) {
        //1、存入订单
        Long orderId = System.currentTimeMillis();
        System.out.println(orderId);
        order.setOrderId(orderId).setStatus(1);
        System.out.println(order);
        orderMapper.insert(order);
        //2、存入order_item
        for (Cart cart : cartList) {
            System.out.println(cart);
            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(cart.getItemId());
            orderItem.setOrderId(orderId);
            orderItem.setImage(cart.getItemImage());
            orderItem.setNum(cart.getNum());
            orderItem.setPrice(cart.getItemPrice());
            orderItem.setTitle(cart.getItemTitle());
            orderItemMapper.insert(orderItem);
        }
        String id = orderId.toString();
        System.out.println(id);
        return id;    //入库成功之后,返回orderId
    }

    //需要通过order对象 返回3部分数据
    @Override
    public List<Order> findOrderUserId(Long userId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Order> orderList = orderMapper.selectList(queryWrapper);
        for (Order order : orderList) {
            QueryWrapper<OrderItem> queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("order_id", order.getOrderId());
            List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper2);
            order.setOrderItems(orderItems);
        }
        return orderList;
    }

    @Override
    public void updateStatus(Order order) {

        Order orderTemp = new Order();
        orderTemp.setStatus(order.getStatus() + 1);
        if (order.getStatus() == 1) {
            //确定收货
            orderTemp.setStart(new Date());
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_id", order.getOrderId());
            orderMapper.update(orderTemp, queryWrapper);
        } else if (order.getStatus() == 2) {
            //租用结束
            orderTemp.setEnd(new Date());
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_id", order.getOrderId());
            Order oldorder = orderMapper.selectOne(queryWrapper);

            QueryWrapper<OrderItem> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("order_id", order.getOrderId());
            List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper1);
            //单日一个订单 总价
            Long price = 0L;
            for (OrderItem orderItem : orderItems){
                System.out.println(orderItem);
                price += orderItem.getPrice()*orderItem.getNum();
            }
            //总共租用了多少天
            System.out.println(price);
            Double day =Math.floor((orderTemp.getEnd().getTime() - oldorder.getStart().getTime())/86400000) ;
            System.out.println(day);
            Long payment = day.longValue()*price;
            orderTemp.setPayment(payment);


            orderMapper.update(orderTemp, queryWrapper);
        }



    }

    @Override
    public boolean checkTime(Order order) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", order.getOrderId());
        Order oldorder = orderMapper.selectOne(queryWrapper);
        Double day =Math.floor((new Date().getTime() - oldorder.getStart().getTime())/86400000) ;
        if (day>0){
            return true;
        }else {
            return false;
        }



    }
}
