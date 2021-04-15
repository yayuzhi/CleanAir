package com.ca.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ca.pojo.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author yayuzhi
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 按照roleid删除rolemenu对应的数据
     * @param id
     */
    @Delete("delete from qk_role_menus where role_id = #{id}")
    void deleteByRoleId(@Param("id") int id);

    /**
     * 按照roleid找到对应的rolemenu
     * @param id
     * @return
     */
    @Select("select * from qk_role_menus where role_id = #{id}")
    List<RoleMenu> findMenusByRoleId(@Param("id") int id);

    /**
     * 按照roleid 返回一堆 menusids
     * @param id
     * @return
     */
    @Select("select menu_id from qk_role_menus where role_id = #{id}")
    List<Integer> findMenuIdsByRoleId(@Param("id") int id);

}
