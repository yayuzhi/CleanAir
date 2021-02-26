package com.ca.service;

import com.ca.pojo.Item;
import com.ca.pojo.Order;

import java.util.List;

public interface OrderService {

    //查询所有order数据
    List<Order> findAllPage(int page, int  limit);

    int count();


    int countbyid(int id);

    //查询所有order  by id数据
    List<Order> findOrderById(int id,int page, int  limit);


    //status 3 -> 4
    void updateOrderStatusTo4ById(Integer id);
}
