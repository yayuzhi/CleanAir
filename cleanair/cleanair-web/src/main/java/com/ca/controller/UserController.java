package com.ca.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ca.pojo.User;
import com.ca.service.DubboUserService;
import com.ca.service.UserService;
import com.ca.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(check = false)   //启动消费者时不去校验是否有生产者
    private DubboUserService dubboUserService;

    //注册用户
    @RequestMapping("/addUser")
    private JsonResult doRegist(User user){
        System.out.println(user);
        dubboUserService.saveUser(user);
        System.out.println("ok");
        return JsonResult.success("注册成功!");
    }

}
