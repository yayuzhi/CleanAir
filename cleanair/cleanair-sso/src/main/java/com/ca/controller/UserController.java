package com.ca.controller;

import com.ca.vo.JsonResult;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ca.pojo.User;
import com.ca.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 需求:实现用户信息校验
     * 校验步骤:  需要接收用户的请求,之后利用RestFul获取数据,
     * 实现数据库校验,按照JSONP的方式返回数据.
     * url地址:   http://sso.jt.com/user/check/
     * 参数:      restFul方式获取
     * 返回值:    JSONPObject
     */
    @RequestMapping("/check")
    public JsonResult checkUser(User user) {
        //只需要校验数据库中是否有结果
        StringBuffer str  = userService.checkUser(user);
//        System.out.println(user);
//        System.out.println(str);

        return JsonResult.success(str);
    }


    /**
     * 业务说明：
     * 通过跨域请求方式，获取用户的json的数据
     * 1、url：http://sso.jt.com/user/query/
     * 2、请求参数：ticket
     * 3.返回值:   SysResult对象 (userJSON)
     * 需求: 通过ticket信息获取user JSON串
     */
    @RequestMapping("/query/{ticket}")
    public JSONPObject findUserByTicket(@PathVariable String ticket, String callback) {
        String userJSON = jedisCluster.get(ticket);
        if (StringUtils.isEmpty(userJSON)) {
            return new JSONPObject(callback, JsonResult.fail());
        } else {
            return new JSONPObject(callback, JsonResult.success(userJSON));
        }
    }




}
