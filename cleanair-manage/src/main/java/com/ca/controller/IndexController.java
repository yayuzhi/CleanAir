package com.ca.controller;

import com.ca.pojo.Order;
import com.ca.pojo.OrderItem;
import com.ca.service.ItemService;
import com.ca.service.OrderItemService;
import com.ca.service.OrderService;
import com.ca.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yayuzhi
 */
@Controller
public class IndexController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;

    @RequestMapping("/index/show")
    @ResponseBody
    public JsonResult IndexShow(){
        Integer itemNum = 0;
        Integer itemsNum = itemService.count();
        Integer orderNum = orderService.countBystatus(1).size();
        for (Order order : orderService.countBystatus(2)){
            Long orderId =order.getOrderId();
            List<OrderItem> orderItems =orderItemService.findOrderItemByOrderId(orderId);
            for (OrderItem orderItem : orderItems){
                itemNum += orderItem.getNum();
            }
        }
        Map map =new HashMap();
        map.put("itemNum", itemNum);
        map.put("itemsNum", itemsNum);
        map.put("orderNum", orderNum);
        return JsonResult.success(map);
    }


}
