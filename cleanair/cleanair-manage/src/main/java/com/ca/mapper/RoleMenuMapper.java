package com.ca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ca.pojo.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Delete("delete from qk_role_menus where role_id = #{id}")
    void deleteByRoleId(@Param("id") int id);

    @Select("select * from qk_role_menus where role_id = #{id}")
    List<RoleMenu> findMenusByRoleId(@Param("id") int id);

    @Select("select menu_id from qk_role_menus where role_id = #{id}")
    List<Integer> findMenuIdsByRoleId(@Param("id") int id);

}
