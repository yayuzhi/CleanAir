package com.ca.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ca.pojo.Item;
import com.ca.service.DubboItemService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class AllProductsController {

    @Reference(check = false)
    private DubboItemService itemService;

    @RequestMapping("/getProductPage")
    public Map<String, Object> getProductPage(@RequestParam("page")Integer page,
                                              @RequestParam("limit")Integer limit){
        Map<String,Object> map = new HashMap<String, Object>();
        PageInfo<Item> pageInfo = itemService.getProductPage(page, limit);
        map.put("code", 0);
        map.put("msg", "操作成功");
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }
}
