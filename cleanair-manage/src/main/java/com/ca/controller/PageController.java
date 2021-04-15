package com.ca.controller;

import com.ca.pojo.Admin;
import com.ca.service.AdminService;
import com.ca.util.ShiroUtils;
import com.ca.vo.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yayuzhi
 */
@Controller
@RequestMapping("/")
public class PageController {

    @Autowired
    private AdminService adminService;


    @RequestMapping("doLoginUI")
    public String doLoginUI(){
        return "login";
    }


    @GetMapping("{moduleName}")
    public String doModuleUI(@PathVariable String moduleName){
        return moduleName;
    }

    @GetMapping("doIndex")
    public String doIndexUI(Model model){
        //获取登录用户
        String name = ShiroUtils.getAdminname();
        model.addAttribute("username", name);
        return "index";

    }
}
