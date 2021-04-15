package com.ca.service;

import com.ca.pojo.OrderItem;

import java.util.List;

/**
 * @author yayzuhi
 */
public interface OrderItemService {

    List<OrderItem> findOrderItemByOrderId(Long orderId);
}
