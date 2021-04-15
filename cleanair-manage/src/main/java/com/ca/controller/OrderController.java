package com.ca.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ca.pojo.Item;
import com.ca.pojo.Order;
import com.ca.pojo.OrderItem;
import com.ca.service.DubboItemService;
import com.ca.service.DubboOrderService;
import com.ca.service.OrderService;
import com.ca.vo.JsonResult;
import com.ca.vo.LayUITbale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/order-list")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    @Reference(check = false)
    private DubboItemService dubboItemService;


    @Reference(check = false)
    private DubboOrderService dubboOrderService;

    /**
     * 拿到分页订单信息
     * @param orderId
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/findOrderByPage")
    public String findOrderByPage(String orderId, int page, int limit){
       if (orderId ==null){
           int page1 = (page - 1) * limit;
           List<Order> orders = orderService.findAllPage(page1, limit);
           int count = orderService.count();
           return new LayUITbale().LayUIResponseByOrder(count, orders);
       }else {
           Integer id = Integer.parseInt(orderId);
           int page1 = (page - 1) * limit;
           List<Order> orders = orderService.findOrderById(id,page1, limit);
           int count = orderService.countbyid(id);
           return new LayUITbale().LayUIResponseByOrder(count, orders);
       }
    }

    /**
     * 修改订单状态
     * @param id
     * @return
     */
    @RequestMapping("/updateOrderStatusTo4ById")
    public JsonResult updateOrderStatusTo4ById(Integer id){
        orderService.updateOrderStatusTo4ById(id);
        Long orderId = orderService.findOrderById(id);
        List<OrderItem> orderItems = orderService.getorderItemsByOrderId(orderId);
        for (OrderItem orderItem : orderItems){
            Long itemid = orderItem.getItemId();
            int num = orderItem.getNum();
            dubboItemService.updateItemAddNum(itemid,num);
        }
        return JsonResult.success("update ok!");
    }


}
