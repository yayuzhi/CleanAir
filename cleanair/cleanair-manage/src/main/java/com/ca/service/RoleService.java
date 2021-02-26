package com.ca.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.ca.pojo.Item;
import com.ca.pojo.Role;
import com.ca.pojo.RoleMenu;

import java.util.List;

public interface RoleService {
    //查询所有role数据
    List<Role> findAllPage(int page, int limit);

    //查询所有role数量
    int count();

    //查询所有role数量 by name
    int countbyname(String name);

    List<Role> findPageByName(String name,int page, int limit);

    //保存一个role和他的,menu
    void saveRole(Role role, Integer[] menuIds);

    //删除一个role 和他的menus
    void deleteRoleById(Integer id);

    //删除多个role
//    void deleteRolesById(Integer... ids);

    //查询role的menus
    List<RoleMenu> findMenusByRoleId(Integer id);

    //修改role
    void updateRole(Role role,Integer[] menuIds);

    //给admin 找role 的name
    String findRoleNameByRoleId(Integer roleid);

    //返回所有的role
    List<Role>  findAllRole();


}
