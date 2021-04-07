package com.ca.service;

import com.ca.pojo.Cart;
import com.ca.pojo.Order;

import java.util.List;

public interface DubboOrderService {
    String saveOrder(Order order, List<Cart> cartList);

    Order findOrderId(String id);
}
