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
public class OrderServiceImpl implements DubboOrderService {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderItemMapper orderItemMapper;



	/**
	 * 1、存入顶订单
     * 2、存入order——item
     * 3、删除购物车
	 * @param order
	 * @return
	 */
	@Override
	@Transactional	//控制事务
	public String saveOrder(Order order, List<Cart> cartList) {
        //1、存入订单
		Long orderId =  System.currentTimeMillis();
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
		String id =  orderId.toString();
		System.out.println(id);
		return id;	//入库成功之后,返回orderId
	}

	//需要通过order对象 返回3部分数据
	@Override
	public Order findOrderId(String id) {
		Order order = orderMapper.selectById(id);
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("order_id", id);
		List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
		return order;
	}
}
