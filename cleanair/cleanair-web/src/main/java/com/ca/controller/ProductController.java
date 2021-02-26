package com.ca.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ca.pojo.Item;
import com.ca.pojo.ItemDesc;
import com.ca.service.DubboItemService;
import com.ca.vo.ItemPlus;
import com.ca.vo.JsonResult;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Reference(check = false)
    private DubboItemService itemService;

    @RequestMapping("getProductById/{id}")
    public JsonResult getProductById(@PathVariable Integer id) {

        Item item = itemService.getProductById(id);
        ItemDesc itemDesc = itemService.getProductDescById(id);

        ItemPlus itemPlus = new ItemPlus();
        itemPlus.setId(id);
        if (item.getTitle() != null) {
            itemPlus.setTitle(item.getTitle());
        }
        if (item.getPrice() != null) {
            itemPlus.setPrice(item.getPrice());
        }
        if (item.getSellPoint() != null) {
            itemPlus.setSell_point(item.getSellPoint());
        }
        if (itemDesc.getItemDesc() != null) {
            itemPlus.setItemDesc(itemDesc.getItemDesc());
        }
        if (item.getImage() != null) {
            itemPlus.setImage(item.getImage());
        }
        if (item.getNum() != null){
            itemPlus.setNum(item.getNum());
        }


        return JsonResult.success(itemPlus);
    }
}
