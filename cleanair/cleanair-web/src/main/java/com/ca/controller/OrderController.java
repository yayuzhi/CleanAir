package com.ca.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ca.pojo.Cart;
import com.ca.pojo.Order;
import com.ca.service.DubboCartService;
import com.ca.service.DubboOrderService;
import com.ca.util.UserThreadLocal;
import com.ca.vo.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Reference(check = false)
    private DubboCartService cartService;

    @Reference(check = false)
    private DubboOrderService orderService;


    /**
     * 跳转订单确认页面
     * url:http://www.qk.com/order/create
     * 参数: 暂时没有
     * 返回值:
     * 页面取值:
     */
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


    /**
     * 完成订单入库操作
     * url地址:   http://www.qk.com/order/submit
     * 参数:      整个表单对象  用order对象接收
     * 返回值:    SysResult对象(orderId)
     */
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
            for (Cart cart : cartList) {
                Cart delcart = new  Cart();
                delcart.setUserId(cart.getUserId());
                delcart.setItemId(cart.getItemId());
                cartService.deleteCart(delcart);
            }
            return JsonResult.success(orderId);
        }
    }


    /**
     * 完成订单查询
     * url地址: http://www.qk.com/order/success.html?id=71605862296712
     * 参数:    orderId
     * 返回值:  订单成功页面
     * 页面取值: ${order.orderId}
     */
    @RequestMapping("/success")
    public String success(String id, Model model) {

        Order order = orderService.findOrderId(id);
        model.addAttribute("order", order);
        return "success";
    }


}
