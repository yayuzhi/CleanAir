package com.ca.web.service.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ca.mapper.ItemDescMapper;
import com.ca.mapper.ItemMapper;
import com.ca.pojo.Item;
import com.ca.pojo.ItemDesc;
import com.ca.service.DubboItemService;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Service(timeout = 3000)
public class DubboItemServiceImpl implements DubboItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;

    //列表信息的展现
    @Override
    public PageInfo<Item> getProductPage(Integer page, Integer limit) {
        List<Item> items = itemMapper.findAllPageweb(page, limit);

        //处理首页显示照片
        for (Item item : items) {
            String image = item.getImage();
            String[] firstImage = image.split(",");
            String trueImage = firstImage[0];
            item.setImage(trueImage);
        }
        long total = itemMapper.countweb();
        PageInfo<Item> pageInfo = new PageInfo<>();
        pageInfo.setList(items);
        pageInfo.setTotal(total);
        return pageInfo;
    }


    @Override
    public PageInfo<Item> getProductPagebytitle(String title, Integer page, Integer limit) {
        List<Item> items = itemMapper.findAllpageweb(title, page, limit);
        //处理首页显示照片
        for (Item item : items) {
            String image = item.getImage();
            String[] firstImage = image.split(",");
            String trueImage = firstImage[0];
            item.setImage(trueImage);
        }
        long total = itemMapper.findBytitleCount(title);
        PageInfo<Item> pageInfo = new PageInfo<>();
        pageInfo.setList(items);
        pageInfo.setTotal(total);
        return pageInfo;

    }

    //在商品详细页显示数据1
    @Override
    public Item getProductById(Integer id) {
        return itemMapper.selectById(id);
    }

    //在商品详细页显示数据2
    @Override
    public ItemDesc getProductDescById(Integer id) {
        if (itemDescMapper.selectById(id) == null){
            return new ItemDesc();
        }
        return itemDescMapper.selectById(id);
    }
}
