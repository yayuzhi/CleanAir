package com.ca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class pageController {
    //负责页面跳转 这里是访问index会 没有第一次渲染上 user的name
    //其实这个是可以解决的，只要在index的页面上写一个 同步渲染 查询 就可以了。
    @RequestMapping("doIndex")
    public String doIndexUI(){
        return "index";
    }

    @RequestMapping("{moduleName}")
    public String doModuleUI(@PathVariable String moduleName){
        return moduleName;
    }



}
