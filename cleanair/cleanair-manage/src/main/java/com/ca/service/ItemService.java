package com.ca.service;

import com.ca.pojo.Item;

import com.ca.pojo.ItemDesc;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface ItemService {
    //查询所有item数据
    List<Item> findAllPage( int page,  int  limit);
    //查询所有item数量
    int count();

    int Countbytitle(String title);

    String LayUIResponse(Integer count,List<Item> items);
    //删除单个item
    void deleteItemById(Integer id);

    //更新商品状态
    void updateStatusById(Integer id);
    //上传一个商品
    void saveItem(Item item, ItemDesc itemDesc);
    //根据商品名称查询商品
    List<Item> findItemByTitle(String title,int page,int limit);
    //查询单个商品详情
    ItemDesc findItemDescById(Integer id);
    //保存编辑后的item
    void updateItem(Item item,ItemDesc itemDesc);


}
