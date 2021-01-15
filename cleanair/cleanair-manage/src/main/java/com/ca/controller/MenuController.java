package com.ca.controller;


import com.alibaba.fastjson.JSON;
import com.ca.pojo.Menu;
import com.ca.service.MenuService;

import com.ca.vo.JsonResult;
import com.ca.vo.LayUITbale;
import com.ca.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/menus-list")
@RestController
public class MenuController {

    @Autowired
    private MenuService menuService;

    //这里是写ztree 返回规定的json对象 ztree的初始化
    @RequestMapping("/findAllMenus")
    public JsonResult findAllMenus(){
//        System.out.println(menuService.findAllMenus());
        List<MenuVO> menuVOS =menuService.findAllMenus();
        String json= JSON.toJSONString(menuVOS);
//        System.out.println(json);
        return JsonResult.success(menuVOS);
    }

}
