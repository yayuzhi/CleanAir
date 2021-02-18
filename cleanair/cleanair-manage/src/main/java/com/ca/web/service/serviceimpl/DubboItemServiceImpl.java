package com.ca.web.service.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ca.mapper.ItemMapper;
import com.ca.pojo.Item;
import com.ca.service.DubboItemService;
import com.ca.service.DubboUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


@Service(timeout = 3000)
public class DubboItemServiceImpl implements DubboItemService{

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public PageInfo<Item> getProductPage(Integer page, Integer limit) {
        List<Item> items = itemMapper.findAllPage(page, limit);
        List<Item> trueItem =new ArrayList<>();
        for (Item item : items){
            if (item.getStatus() == 1){
                trueItem.add(item);
            }
        }
        long total = trueItem.size();
        PageInfo<Item>  pageInfo = new PageInfo<>();
        pageInfo.setList(trueItem);
        pageInfo.setTotal(total);
        return pageInfo;
    }
}
