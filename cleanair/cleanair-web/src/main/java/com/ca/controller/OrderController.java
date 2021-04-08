package com.ca.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ca.pojo.Cart;
import com.ca.pojo.Order;
import com.ca.pojo.OrderItem;
import com.ca.pojo.User;
import com.ca.service.DubboCartService;
import com.ca.service.DubboOrderService;
import com.ca.util.UserThreadLocal;
import com.ca.vo.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference(check = false)
    private DubboCartService cartService;

    @Reference(check = false)
    private DubboOrderService orderService;



    @RequestMapping("/create")
    @ResponseBody
    public Map<String, Object> create(Model model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Long userId = UserThreadLocal.get().getId();
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        model.addAttribute("cartList", cartList);
        map.put("code", 0);
        map.put("msg", "操作成功");
        map.put("data", cartList);
        return map;
    }


    @RequestMapping("/submit")
    @ResponseBody
    public JsonResult saveOrder(Order order) {
        Long userId = UserThreadLocal.get().getId();
        order.setUserId(userId);
        //拿到购物车所有的商品
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        String orderId = orderService.saveOrder(order, cartList);
        if (StringUtils.isEmpty(orderId)) {
            return JsonResult.fail();
        } else {
            //清空购物车
            for (Cart cart : cartList) {
                Cart delcart = new  Cart();
                delcart.setUserId(cart.getUserId());
                delcart.setItemId(cart.getItemId());
                cartService.deleteCart(delcart);
            }
            return JsonResult.success(orderId);
        }
    }


    @RequestMapping("/show")
    @ResponseBody
    public Map<String, Object> show(Model model, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) request.getAttribute("QK_USER");
        Long userId = user.getId();

        List<Order> orderList = orderService.findOrderUserId(userId);

//        model.addAttribute("oderList",orderList);
        map.put("code", 0);
        map.put("msg", "操作成功");
        map.put("data", orderList);
        return map;
    }


    @RequestMapping("/update")
    public JsonResult updateStatus(Order order){
        System.out.println(order);
        orderService.updateStatus(order);

        return JsonResult.success("update ok!");
    }

}
