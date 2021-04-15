package com.ca.controller;


import com.ca.pojo.Admin;
import com.ca.pojo.Item;
import com.ca.service.AdminService;
import com.ca.util.ShiroUtils;
import com.ca.vo.JsonResult;
import com.ca.vo.LayUITbale;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yayuzhi
 */


@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 登录
     * @param adminname
     * @param adminpassword
     * @return
     */
    @RequestMapping("/admin/doLogin")
    public JsonResult doLogin(String adminname, String adminpassword) {
        //获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //通过subject提交用户信息，交给shiro框架进行认证操作
        //1对用户进行封装
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(adminname, adminpassword);
        //这里setRememberme 作为实现cookie的第一步
        usernamePasswordToken.setRememberMe(true);
        //2对用户信息进行身份认证
        subject.login(usernamePasswordToken);
        return JsonResult.success("login ok!");
    }


    /**
     * welcome页面展示1 回显登录用户的名称 在welcome页面
     * @return
     */
    @RequestMapping("/welcome/findAdminName")
    public JsonResult findAdminName() {
        Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
        return JsonResult.success(admin);
    }

    /**
     * 获取用户的名称 在index页面
     * @param model
     * @return
     */
    @RequestMapping("/index/findAdminName")
    public String doIndexUI(Model model) {
        //获取登录用户
        String name = ShiroUtils.getAdminname();
        model.addAttribute("username", name);
        return "index";
    }
    /**
     * 个人信息的展现  这里从shiro里面拿到 admin的信息
     * @return
     */
    @RequestMapping("/admin/getAdmin")
    public JsonResult getAdmin() {
        Admin admin = (Admin) SecurityUtils.getSubject().getPrincipal();
        return JsonResult.success(admin);
    }
    /**
     * 个人信息展现2 从拿到admin的信息中的name 进行处理
     * @param name
     * @return
     */
    @RequestMapping("/admin/getAdmin1")
    public JsonResult getAdmin1(String name) {
        Admin admin = adminService.findAdminByName(name);
        return JsonResult.success(admin);
    }
    /**
     * 更改个人信息1
     * @param admin
     * @return
     */
    @RequestMapping("/admin/editAdmin")
    public JsonResult editAdmin(Admin admin) {
        adminService.editAdmin(admin);
        return JsonResult.success("update ok");
    }
    /**
     * 更改个人信息2
     * @param admin
     * @return
     */
    @RequestMapping("/admin/editAdmin1")
    public JsonResult editAdmin1(Admin admin) {
        String name = admin.getName();
        String password = admin.getPassword();
        Admin admin1 = adminService.findAdminByName(name);
        admin1.setPassword(password);
        adminService.editAdmin1(admin1);
        return JsonResult.success("update ok");
    }
    /**页面显示
     * @param name
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/admin-list/findAdminByPage")
    public String findAdminByPage(String name, int page, int limit) {
        if (name == null) {
            int page1 = (page - 1) * limit;
            List<Admin> admins = adminService.findAdminByPage(page1, limit);
            int count = adminService.count();
            return new LayUITbale().LayUIResponseByAdmin(count, admins);
        } else {
            int page1 = (page - 1) * limit;
            List<Admin> admins = adminService.findAdminByname(name, page1, limit);
            int count = adminService.countbyname(name);
            return new LayUITbale().LayUIResponseByAdmin(count, admins);
        }
    }
    /**改变用户状态
     * @param id
     * @return
     */
    @RequestMapping("/admin-list/updateValidById")
    public JsonResult updateValidById(Integer id) {
        adminService.updateValidById(id);
        return JsonResult.success("update ok");
    }
    /**保存一个admin
     * @param admin
     * @return
     */
    @RequestMapping("/admin-list/addAdmin")
    public JsonResult addAdmin(Admin admin) {
        adminService.addAdmin(admin);
        return JsonResult.success("add ok");
    }
    /**根据name找有没有admin
     * @param value
     * @return
     */
    @RequestMapping("/admin-list/findAdminByName")
    public JsonResult findAdminByName(String value) {
        Admin admin = adminService.findAdminByName(value);
        return JsonResult.success(admin);
    }
    /**修改admin
     * @param admin
     * @return
     */
    @RequestMapping("/admin-list/updateAdmin")
    public JsonResult updateAdmin(Admin admin) {
        Integer id = admin.getId();
        Integer roleId = admin.getRoleId();
        adminService.updateRoleById(id, roleId);
        return JsonResult.success("update role ok! ");
    }
    /**删除单个admin
     * @param id
     * @return
     */
    @RequestMapping("/admin-list/deleteAdminById")
    public JsonResult deleteAdminById(Integer id) {
        adminService.deleteAdminById(id);
        return JsonResult.success("delete ok");
    }
    /**删除多个admin
     * @param ids
     * @return
     */
    @RequestMapping("/admin-list/deleteAdminsById")
    public JsonResult deleteAdminsById(Integer... ids) {
        for (Integer id : ids) {
            adminService.deleteAdminById(id);
        }
        return JsonResult.success("delete ok");
    }
    /**在首页index的显示方法接口
     * @param name
     * @return
     */
    @RequestMapping("/admin-list/findMenusByName")
    public JsonResult findMenusByName(String name) {
        List<Integer> menusId = adminService.findMenusByName(name);
        return JsonResult.success(menusId);
    }
}