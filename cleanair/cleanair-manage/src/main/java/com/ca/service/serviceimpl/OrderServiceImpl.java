package com.ca.service.serviceimpl;

import com.ca.annotation.RequiredLog;
import com.ca.mapper.OrderMapper;
import com.ca.pojo.Order;
import com.ca.service.OrderService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public List<Order> findAllPage(int page, int limit) {
        List<Order> orders = orderMapper.findAllPage(page, limit);
        return orders;
    }

    @Override
    public int count() {
        return orderMapper.count();
    }

    @Override
    public int countbyid(int id) {
        return orderMapper.countbyid(id);
    }

    @Override
    public List<Order> findOrderById(int id, int page, int limit) {
        List<Order> orders = orderMapper.findOrderById(id,page,limit);
        return orders;
    }


    @RequiresPermissions("qk:order:status")
    @RequiredLog("订单状态变更")
    @Override
    public void updateOrderStatusTo4ById(Integer id) {
        Order order =  orderMapper.selectById(id);
        order.setStatus(4);
        orderMapper.updateById(order);
    }

}
