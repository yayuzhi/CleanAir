package com.ca.controller;

import com.alibaba.fastjson.JSON;
import com.ca.mapper.ItemDescMapper;
import com.ca.mapper.ItemMapper;
import com.ca.pojo.Item;
import com.ca.pojo.ItemDesc;
import com.ca.vo.ItemPlus;
import com.ca.vo.JsonResult;
import com.ca.service.ItemService;


import com.ca.vo.LayUITbale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Service;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;

@RequestMapping("/item-list")
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;
    /**
     * 业务需求:
     * 	以分页的形式查询商品列表信息.
     * 业务接口文档:
     *  	url地址: findItemByPages
     * 		参数信息:  page 当前页数    limit 每页展现的行数
     * 		返回值:  layUITable对象
     *      利用
     */

    @RequestMapping("/findItemByPage")
    public String findItemByTitle(String title, int page, int limit) {
        if (title == null) {
            int page1 = (page - 1) * limit;
            List<Item> items = itemService.findAllPage(page1, limit);
            int count = itemService.count();

            return new LayUITbale().LayUIResponseByitem(count, items);

        } else {
            int page1 = (page - 1) * limit;
            List<Item> items = itemService.findItemByTitle(title, page1, limit);
            int count = itemService.Countbytitle(title);

            return new LayUITbale().LayUIResponseByitem(count, items);
        }
    }


    /**
     * url:items/deleteItemById
     * 删除单个item
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteItemById")
    @ResponseBody
    public JsonResult deleteItemById(Integer id) {
        itemService.deleteItemById(id);
        return JsonResult.success("delete ok");
    }


    /**
     * url: items/deleteItemsById
     * 批量删除items
     *
     * @param ids
     * @return
     */
    @RequestMapping("/deleteItemsById")
    @ResponseBody
    public JsonResult deleteItemsById(Integer... ids) {

        for(Integer id:ids){
            itemService.deleteItemById(id);
        }
        return JsonResult.success("delete All ok");
    }

    /**
     * url items//updateStatusById
     * 更新单个item的状态
     *
     * @param id
     * @return
     */
    @RequestMapping("/updateStatusById")
    @ResponseBody
    public JsonResult updateStatusById(Integer id) {
        itemService.updateStatusById(id);
        return JsonResult.success("update status ok");
    }

    /**
     * /url:/item-list/saveitem
     * 增加商品 但是商品信息分了两个表来存储，所以这里用了两个类
     *
     * @param item
     * @param itemDesc
     * @return
     */
    @RequestMapping("/saveItem")
    public JsonResult saveItem(Item item, ItemDesc itemDesc) {

        itemService.saveItem(item, itemDesc);
        return JsonResult.success("save item ok");

    }
    /**
     * 编辑item 第一步
     * 在item-update.html显示itemdesc和item信息
     * 查询item的详细信息
     */
    @RequestMapping("/findItemDescById")
    public  JsonResult findItemDescById(Integer id){
        ItemDesc itemDesc1 =itemService.findItemDescById(id);
        String itemDesc =  itemDesc1.getItemDesc();
        return JsonResult.success(itemDesc);
    }

    /**
     * 编辑item 第二步
     * 更新item
     * @param item
     * @param itemDesc
     * @return
     */
    @RequestMapping("/updateItem")
    public JsonResult updateItem(Item item,ItemDesc itemDesc){
        System.out.println(item);
        System.out.println(itemDesc);
        itemService.updateItem(item,itemDesc);
        return  JsonResult.success("update item ok");
    }



}
