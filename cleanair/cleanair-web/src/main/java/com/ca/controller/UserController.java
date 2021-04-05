package com.ca.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ca.pojo.User;
import com.ca.service.DubboUserService;
import com.ca.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Reference(check = false)   //启动消费者时不去校验是否有生产者
    private DubboUserService dubboUserService;
    @Autowired
    private JedisCluster jedisCluster;

    //注册用户
    @RequestMapping("/doRegister")
    @ResponseBody
    private JsonResult doRegist(User user) {
        System.out.println(user);
        dubboUserService.saveUser(user);
        System.out.println("ok");
        return JsonResult.success("注册成功!");
    }

    //用户的登录
    @RequestMapping("/doLogin")
    @ResponseBody
    public JsonResult doLogin(User user, HttpServletResponse response) {
//        System.out.println(user);

//        System.out.println(response);
        String uuid = dubboUserService.doLogin(user);
        if (StringUtils.isEmpty(uuid)) {
            return JsonResult.fail();
        }
        //判断有没有rememberme

        //将uuid保存到Cookie中
        Cookie cookie = new Cookie("QK_TICKET", uuid);
        cookie.setMaxAge(30 * 24 * 60 * 60);  //让cookie 30天有效
        cookie.setPath("/");            //cookie在哪个url路径生效
        cookie.setDomain("qk.com");     //设定cookie共享
        response.addCookie(cookie);


        return JsonResult.success("登录成功!");
    }


    //用户退出
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        //1.获取Cookie中的JT_TICKET值
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length>0){
            for (Cookie cookie : cookies){
                if(cookie.getName().equals("QK_TICKET")){
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

        return "redirect:/";
    }

}
