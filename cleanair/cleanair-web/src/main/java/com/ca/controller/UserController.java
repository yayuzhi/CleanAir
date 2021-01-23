package com.ca.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ca.pojo.User;
import com.ca.service.DubboUserService;
import com.ca.service.UserService;
import com.ca.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(check = false)   //启动消费者时不去校验是否有生产者
    private DubboUserService dubboUserService;
    @Autowired
    private JedisCluster jedisCluster;

    //注册用户
    @RequestMapping("/doRegister")
    private JsonResult doRegist(User user) {
        System.out.println(user);
        dubboUserService.saveUser(user);
        System.out.println("ok");
        return JsonResult.success("注册成功!");
    }

    //用户的登录
    @RequestMapping("/doLogin")
    public JsonResult doLogin(User user, HttpServletResponse response) {
        System.out.println(user);

        System.out.println(response);
        String uuid = dubboUserService.doLogin(user);
        if (StringUtils.isEmpty(uuid)) {
            return JsonResult.fail();
        }
        //判断有没有rememberme

        //将uuid保存到Cookie中
        Cookie cookie = new Cookie("QK_TICKET", uuid);
        cookie.setMaxAge(30 * 24 * 60 * 60);  //让cookie 30天有效
        cookie.setPath("/");            //cookie在哪个url路径生效
        cookie.setDomain("Qk.com");     //设定cookie共享
        response.addCookie(cookie);


        return JsonResult.success("登录成功!");
    }


}
