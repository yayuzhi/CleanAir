package com.ca.service;

import com.ca.pojo.Admin;

import java.util.List;

public interface AdminService {


    //显示admin
    List<Admin> findAdminByPage(int page, int limit);

    int count();

    //启用禁用账户
    void updateValidById(Integer id);

    //add admin
    void addAdmin(Admin admin);

    //添加用户查询是否存在
    Admin findAdminByName(String name);

    //管理员修改用户的role
    void updateRoleById(Integer id, Integer roleId);

    //删除单个admin
    void deleteAdminById(Integer id);

    //模糊查询admin
    List<Admin> findAdminByname(String name,int page, int limit);

    //在登录后的index展示相对应的页面
    List<Integer> findMenusByName(String name);

    //个人信息修改
    void editAdmin(Admin admin);

    //个人信息修改2
    void editAdmin1(Admin admin);
}