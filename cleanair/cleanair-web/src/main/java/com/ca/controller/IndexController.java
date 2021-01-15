package com.ca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {


    @RequestMapping("/index")
    public String index(){

        return "index";
    }

    @GetMapping("{moduleName}")
    public String doModuleUI(@PathVariable String moduleName){
        return moduleName;
    }


    //1231231212312412412431241241242

}
