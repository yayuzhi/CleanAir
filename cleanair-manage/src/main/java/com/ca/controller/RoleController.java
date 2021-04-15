package com.ca.controller;


import com.ca.pojo.Item;
import com.ca.pojo.Role;
import com.ca.pojo.RoleMenu;
import com.ca.service.RoleService;
import com.ca.vo.JsonResult;
import com.ca.vo.LayUITbale;
import com.ca.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yayuzhi
 */
@RequestMapping("/role-list")
@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**显示所有的角色 分页查询
     * @param name
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/findRoleByPage")
    public String findRole(String name,int page, int limit) {
        if (name == null) {
            int page1 = (page - 1) * limit;
            List<Role> roles = roleService.findAllPage(page1, limit);
            int count = roleService.count();
            return new LayUITbale().LayUIResponseByrole(count, roles);
        }else {
            int page1 = (page - 1) * limit;
            List<Role> roles = roleService.findPageByName(name,page1, limit);
            int count = roleService.countbyname(name);
            return  new LayUITbale().LayUIResponseByrole(count, roles);
        }

    }


    /**返回所有的role直接返回
     * @return
     */
    @RequestMapping("/findAllRole")
    public  JsonResult findAllRole(){
        List<Role> roles = roleService.findAllRole();
        return  JsonResult.success(roles);
    }


    /**通过role的id来找role的name
     * @param roleId
     * @return
     */
    @RequestMapping("/findRoleNameByRoleId")
    public JsonResult findRoleNameByRoleId(Integer roleId){
        String name =  roleService.findRoleNameByRoleId(roleId);
        return JsonResult.success(name);
    }

    /**增加职位角色
     * @param role
     * @param menuIds
     * @return
     */
    @RequestMapping("/saveRole")
    public JsonResult saveRole(Role role,Integer[] menuIds){
        //类似itemDesc的操作
        roleService.saveRole(role,menuIds);
        return JsonResult.success("save role ok");
    }

    /**删除职位角色
     * @param id
     * @return
     */
    @RequestMapping("/deleteRoleById")
    public JsonResult deleteRoleById(Integer id){
        roleService.deleteRoleById(id);
        return JsonResult.success("delete ok");
    }

    /**批量删除职位角色
     * @param ids
     * @return
     */
    @RequestMapping("/deleteRolesById")
    public  JsonResult deleteRolesById(Integer... ids){
        for(Integer id : ids){
            roleService.deleteRoleById(id);
        }
        return JsonResult.success("delete All ok");
    }

    /**修改的第一步 回显数据
     * @param id
     * @return
     */
    @RequestMapping("/findMenusByRoleId")
    public JsonResult findMenusByRoleId(Integer id){
        List<RoleMenu> roleMenus = roleService.findMenusByRoleId(id);
        return JsonResult.success(roleMenus);
    }

    /**修改第二步 修改 数据
     * @param role
     * @param menuIds
     * @return
     */
    @RequestMapping("/updateRole")
    public  JsonResult updateRole(Role role,Integer[] menuIds){
        roleService.updateRole(role,menuIds);
        return JsonResult.success("update ok");
    }


}
