package com.ca.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/{moduleName}")
    public String doModuleUI(@PathVariable String moduleName){
        return moduleName;
    }

}
