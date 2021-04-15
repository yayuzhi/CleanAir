package com.ca.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ca.pojo.Cart;
import com.ca.pojo.User;
import com.ca.service.DubboCartService;
import com.ca.util.UserThreadLocal;
import com.ca.vo.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Reference(check = false)
    private DubboCartService cartService;


    @RequestMapping("/show")
    @ResponseBody
    public Map<String,Object> show(Model model, HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) request.getAttribute("QK_USER");
        Long userId = user.getId();
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        map.put("code", 0);
        map.put("msg", "操作成功");
        map.put("data", cartList);

        return map;
    }


     @RequestMapping("/update/num")
     @ResponseBody  //让ajax程序结束
     public JsonResult updateNum(Cart cart){
        //SpringMVC 针对restFul提供的功能 名称和属性一致

         Long userId = UserThreadLocal.get().getId();
         if (userId.equals(cart.getUserId())){

         }else {
             cart.setUserId(userId);
         }
         cartService.updateCartNum(cart);
         return JsonResult.success("update ok!");
     }


     @RequestMapping("/delete")
     @ResponseBody
     public JsonResult deleteCart(Cart cart){
         Long userId = UserThreadLocal.get().getId();
         cart.setUserId(userId);
         cartService.deleteCart(cart);
         return JsonResult.success("delete ok!");
     }


    @RequestMapping("/add")
    @ResponseBody
    public JsonResult addCart(Cart cart){
        Long userId = UserThreadLocal.get().getId();
        cart.setUserId(userId);
        cartService.addCart(cart);
        return  JsonResult.success("add ok!");
    }

}
