package com.ca.service;

import com.ca.pojo.Menu;
import com.ca.vo.MenuVO;


import java.util.List;
import java.util.Map;

public interface MenuService {

    List<MenuVO> findAllMenus();

    List<Map<String,Object>> findObjects();
}
