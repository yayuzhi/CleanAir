package com.ca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.ca.pojo.Menu;

import com.ca.vo.MenuVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * @author yayuzhi
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 返回所有的menus信息
     * @return
     */
    @Select("select * from qk_menus")
    List<Menu> findAllMenus();


    /**查询所有菜单信息
     * @return
     */
    @Select("select c.*,(select p.name  from qk_menus p  where c.parentId=p.id) parentName from qk_menus c")
    List<Map<String,Object>> findObjects();


}
