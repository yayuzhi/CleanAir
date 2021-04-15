package com.ca.service.serviceimpl;

import com.ca.annotation.RequiredLog;
import com.ca.mapper.AdminMapper;
import com.ca.mapper.RoleMapper;
import com.ca.mapper.RoleMenuMapper;
import com.ca.pojo.Admin;
import com.ca.pojo.Role;
import com.ca.pojo.RoleMenu;
import com.ca.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private AdminMapper adminMapper;

    //查询roles数据
    @Override
    public List<Role> findAllPage(int page, int limit) {
        List<Role> roles = roleMapper.findAllPage(page, limit);
        return roles;
    }

    //查询role数量
    @Override
    public int count() {
        return roleMapper.count();
    }

    @Override
    public int countbyname(String name) {
        return roleMapper.countbyname(name);
    }

    @Override
    public List<Role> findPageByName(String name, int page, int limit) {
        List<Role> roles = roleMapper.findPageByName(name,page,limit);
        return roles;
    }

    //保存role
    @RequiresPermissions("qk:role:add")
    @RequiredLog("添加职位")
    @Override
    public void saveRole(Role role, Integer[] menuIds) {
        roleMapper.insert(role);
        for (Integer menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(role.getId());
            roleMenuMapper.insert(roleMenu);
        }
    }

    //删除一个role
    //同时将包含该role的admin的roleId改为0 0定义为无职业 且将无职业的valid改为2 被禁用
    @RequiresPermissions("qk:role:delete")
    @RequiredLog("删除职位")
    @Override
    public void deleteRoleById(Integer id) {
        roleMapper.deleteById(id);
        roleMenuMapper.deleteByRoleId(id);
        List<Admin> admins = adminMapper.selectByRoleId(id);
        for (Admin admin:admins){
            admin.setRoleId(0);
            admin.setValid(2);
            adminMapper.updateById(admin);
        }
    }

//    //删除多个role
//    @Override
//    public void deleteRolesById(Integer... ids) {
//        for (Integer id:ids){
//            roleMapper.deleteById(id);
//            roleMenuMapper.deleteByRoleId(id);
//            List<Admin> admins = adminMapper.selectByRoleId(id);
//            for (Admin admin:admins){
//                admin.setRoleId(0);
//                admin.setValid(2);
//                adminMapper.updateById(admin);
//            }
//        }
//    }

    //编辑回显role的menus在ztree
    @Override
    public List<RoleMenu> findMenusByRoleId(Integer id) {

        return  roleMenuMapper.findMenusByRoleId(id);
    }

    @RequiresPermissions("qk:role:update")
    @RequiredLog("编辑职位")
    @Override
    public void updateRole(Role role, Integer[] menuIds) {
        roleMapper.updateById(role);
        roleMenuMapper.deleteByRoleId(role.getId());
        for (Integer id : menuIds){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(id);
            roleMenu.setRoleId(role.getId());
            roleMenuMapper.insert(roleMenu);
        }

    }

    @Override
    public String findRoleNameByRoleId(Integer roleid) {
        String name;
        Role role =  roleMapper.selectById(roleid);
//        if (role.getName() ==null){
//             name = "";
//        }else {
//            name = role.getName();
//        }
        name = role.getName();
        return name;
    }

    @Override
    public List<Role> findAllRole() {

        return roleMapper.findAllRole();
    }
}
