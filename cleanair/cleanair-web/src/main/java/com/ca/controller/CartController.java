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

@Controller
@RequestMapping("/cart")
public class CartController {

    @Reference(check = false)
    private DubboCartService cartService;
    /**
     *  业务描述: 展现购物车列表页面,同时查询购物车数据
     *  url: http://www.ca.com/cart/show.html
     *  参数: userId=7L
     *  返回值:  页面逻辑名称  cart.jsp
     *  页面取值:  ${cartList}
     */
    @RequestMapping("/show")
    public String show(Model model, HttpServletRequest request){
        User user = (User) request.getAttribute("QK_USER");
        Long userId = user.getId();   //暂时写死
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        model.addAttribute("cartList",cartList);
        return "shoppingcart";
    }

    /**
     * 业务描述:
     *  完成购物车数量的修改操作
     *  url地址:  http://www.ca.com/cart/update/
     *  参数:     restFul风格
     *  返回值:   void
     */
     @RequestMapping("/update/num/")
     @ResponseBody  //让ajax程序结束
     public void updateNum(Cart cart){//springmvc 针对restFul提供的功能 名称和属性一致

         Long userId = UserThreadLocal.get().getId();
         cart.setUserId(userId);
         cartService.updateCartNum(cart);
     }

    /**
     * 实现购物车删除操作
     * 1.url地址: http://www.ca.com/cart/delete/
     * 2.参数:    1474392004 itemId
     * 3.返回值:  String   重定向到列表页面
     */
     @RequestMapping("/delete/")
     public String deleteCart(Cart cart){
         Long userId = UserThreadLocal.get().getId();
         cart.setUserId(userId);
         cartService.deleteCart(cart);
         return "redirect:/cart/show";
     }

    /**
     * 购物车新增操作
     * url地址:http://www.qk.com/cart/add/
     * url参数: 购物车属性数据
     * 返回值:  重定向到购物车列表页面
     */
    @RequestMapping("/add")
    public String addCart(Cart cart){
        System.out.println(cart);
        Long userId = UserThreadLocal.get().getId();
        cart.setUserId(userId);
        cartService.addCart(cart);
//        return "redirect:/cart/show.html";
        return "redirect:/cart/show";

    }

}
