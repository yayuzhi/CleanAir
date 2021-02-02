package com.ca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class pageController {
    //负责页面跳转

    @RequestMapping("doIndex")
    public String doIndexUI(){
        return "index";
    }

    @RequestMapping("{moduleName}")
    public String doModuleUI(@PathVariable String moduleName){
        return moduleName;
    }


}
