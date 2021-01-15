package com.ca.service.serviceimpl;

import com.alibaba.fastjson.JSON;
import com.ca.annotation.RequiredLog;
import com.ca.mapper.ItemMapper;
import com.ca.mapper.ItemDescMapper;
import com.ca.pojo.Item;
import com.ca.pojo.ItemDesc;
import com.ca.service.ItemService;


import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;
    @Autowired
    private ItemService itemService;

    //查询item数据
    @Override
    public List<Item> findAllPage(int page, int limit) {
        List<Item> items = itemMapper.findAllPage(page, limit);

        return items;
    }

    //查询item的数量
    @Override
    public int count() {
        return itemMapper.count();
    }

    //根据商品名称查询商品
    @Override
    public List<Item> findItemByTitle(String title, int page, int limit) {
        List<Item> items = itemMapper.findAllpage(title,page,limit);
        return items;
    }


    //根据商品编号查询商品详情
    @RequiresPermissions("qk:item:update")
    @RequiredLog("商品编辑")
    @Override
    public ItemDesc findItemDescById(Integer id) {

        return itemDescMapper.selectById(id);
    }
    //保存编辑后的item
    @RequiresPermissions("qk:item:update")
    @RequiredLog("商品编辑")
    @Override
    public void updateItem(Item item, ItemDesc itemDesc) {

        itemMapper.updateById(item);
        itemDesc.setItemId(item.getId());
        itemDescMapper.updateById(itemDesc);
    }

    //删除item
    @RequiresPermissions("qk:item:delete")
    @RequiredLog("删除商品")
    @Override
    public void deleteItemById(@PathVariable  Integer id) {
        itemMapper.deleteById(id);
    }


    //
    @RequiresPermissions("qk:item:status")
    @RequiredLog("商品上下架")
    @Override
    public void updateStatusById(Integer id) {
        Item item = itemMapper.selectById(id);
        Integer status = item.getStatus();
        if (status == 1){
            item.setStatus(2);
        }else {
            item.setStatus(1);
        }
        itemMapper.updateById(item);
    }

    @RequiresPermissions("qk:item:add")
    @RequiredLog("商品添加")
    @Override
    public void saveItem(Item item, ItemDesc itemDesc) {
        item.setStatus(1);	//默认是正常状态
        itemMapper.insert(item); //执行数据库入库操作,动态生成ID
        //如何实现主键自增的回显功能? 可以通过标签的配置实现,但是MP已经实现该功能
        //2.入库详情信息  如何保证item与itemDesc主键信息一致?
        itemDesc.setItemId(item.getId());
        itemDescMapper.insert(itemDesc);
    }



    @Override
    public String LayUIResponse(Integer count, List<Item> items) {
        String json = JSON.toJSONString(items);
        //*****转为layui需要的json格式，必须要这一步，博主也是没写这一步，在页面上数据就是数据接口异常
        String jso = "{\"code\":0,\"msg\":\"\",\"count\":" + count + ",\"data\":" + json + "}";
        return jso;
    }
}
