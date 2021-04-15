package com.ca.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ca.mapper.MenuMapper;
import com.ca.mapper.RoleMenuMapper;
import com.ca.pojo.Menu;
import com.ca.service.MenuService;
import com.ca.vo.MenuVO;
import javafx.scene.Parent;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;


    @Override
    public List<MenuVO> findAllMenus() {
        List<Menu> menus = menuMapper.findAllMenus();

        ArrayList<MenuVO> menuVOS = new ArrayList<>();
        for (Menu menu : menus) {
            MenuVO menuVO = new MenuVO();
            menuVO.setId(menu.getId());
            menuVO.setName(menu.getName());
            menuVO.setPid(menu.getParentId());
//            if (menuMapper.selectById(menuVO.getId()) == null) {
//                menuVO.setParent(false);
//            }
            menuVOS.add(menuVO);
        }

        return menuVOS;
    }

    @Override
    public List<Map<String, Object>> findObjects() {
        return menuMapper.findObjects();
    }

}
