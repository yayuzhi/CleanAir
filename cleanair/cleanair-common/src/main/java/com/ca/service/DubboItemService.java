package com.ca.service;

import com.ca.pojo.Item;
import com.github.pagehelper.PageInfo;

public interface DubboItemService {
    PageInfo<Item> getProductPage(Integer page, Integer limit);

    PageInfo<Item> getProductPagebytitle(String title,Integer page, Integer limit) ;
}
