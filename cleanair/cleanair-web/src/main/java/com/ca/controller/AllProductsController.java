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
@RequestMapping("/AllProduct")
public class AllProductsController {

    @Reference(check = false)
    private DubboItemService itemService;

    @RequestMapping("/getProductPage")
    public Map<String, Object> getProductPage(Integer page, Integer limit, String title) {
        System.out.println(title);
        if (title == null) {
            Map<String, Object> map = new HashMap<String, Object>();
            int page1 = (page - 1) * limit;
            PageInfo<Item> pageInfo = itemService.getProductPage(page1, limit);
            map.put("code", 0);
            map.put("msg", "操作成功");
            map.put("count", pageInfo.getTotal());
            map.put("data", pageInfo.getList());
            return map;
        }else {
            Map<String, Object> map = new HashMap<String, Object>();
            int page1 = (page - 1) * limit;
            PageInfo<Item> pageInfo = itemService.getProductPagebytitle(title, page1, limit);
            map.put("code", 0);
            map.put("msg", "操作成功");
            map.put("count", pageInfo.getTotal());
            map.put("data", pageInfo.getList());
            return map;
        }
    }
}
