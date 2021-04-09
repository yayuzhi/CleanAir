package com.ca.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ca.pojo.Order;
import com.ca.pojo.User;
import com.ca.service.DubboCartService;
import com.ca.service.DubboUserService;
import com.ca.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/personal")
public class PersonalController {

    @Autowired
    private JedisCluster jedisCluster;

    @Reference(check = false)
    private DubboUserService dubboUserService;

    //用户个人中心 数据展示
    @RequestMapping("/show")
    @ResponseBody
    public Map<String, Object> showUser(Model model, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) request.getAttribute("QK_USER");
//        Long userId = user.getId();
//        List<Cart> cartList = cartService.findCartListByUserId(userId);
//        model.addAttribute("cartList",cartList);
        map.put("code", 0);
        map.put("msg", "操作成功");
        map.put("data", user);

        return map;
    }
    @RequestMapping("/update")
    public JsonResult updateUser(User user, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        User olduser = (User) request.getAttribute("QK_USER");
        User olduserhavepassword = dubboUserService.findUserByUserId(olduser.getId());
        System.out.println(user);
        if (user.getPassword().length()>0){
            user.setId(olduser.getId());
            String password = user.getPassword();
            String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
            user.setPassword(md5Pass);
        }else {
            user.setId(olduser.getId());
            user.setPassword(olduserhavepassword.getPassword());
        }
        System.out.println(user);
        dubboUserService.updateUser(user);


        return JsonResult.success("update ok!");
    }



    //用户退出
    @RequestMapping("/logout")
    public JsonResult logout(HttpServletRequest request, HttpServletResponse response) {
        //1.获取Cookie中的JT_TICKET值
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("QK_TICKET")) {
                    String ticket = cookie.getValue();
                    //redis删除ticket信息
                    jedisCluster.del(ticket);
                    cookie.setMaxAge(0);    //0表示立即删除
                    //规则cookie如果需要操作,必须严格定义
                    cookie.setPath("/");
                    cookie.setDomain("qk.com");
                    response.addCookie(cookie);
                }
            }
        }
        return JsonResult.success();
    }
}
