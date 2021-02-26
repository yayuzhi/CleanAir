package com.ca.service.serviceimpl;

import com.ca.annotation.RequiredLog;
import com.ca.mapper.AdminMapper;
import com.ca.mapper.RoleMapper;
import com.ca.mapper.RoleMenuMapper;
import com.ca.pojo.Admin;
import com.ca.pojo.Role;
import com.ca.service.AdminService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    //页面的展现 admin-list
    @Override
    public List<Admin> findAdminByPage(int page, int limit) {
        return adminMapper.findAdminByPage(page, limit);
    }

    @Override
    public int count() {
        return adminMapper.count();
    }

    @RequiresPermissions("qk:admin:valid")
    @RequiredLog("账户禁用/启用")
    @Override
    public void updateValidById(Integer id) {
        Admin admin = adminMapper.selectById(id);
        Integer valid = admin.getValid();
        if (valid == 1) {
            admin.setValid(2);
        } else {
            admin.setValid(1);
        }
        adminMapper.updateById(admin);
    }

    @RequiresPermissions("qk:admin:add")
    @RequiredLog("添加账户")
    @Override
    public void addAdmin(Admin admin) {
        String adminname = admin.getName();
        String password = admin.getPassword();
        String salt = UUID.randomUUID().toString();
        SimpleHash sh = new SimpleHash("MD5", password, salt);
        String newpassword = sh.toHex();

        admin.setSalt(salt);
        admin.setPassword(newpassword);

        adminMapper.insert(admin);
    }

    @Override
    public Admin findAdminByName(String name) {
        Admin admin = adminMapper.selectByName(name);

        return admin;
    }

    @RequiresPermissions("qk:admin:update")
    @RequiredLog("账户职位更新")
    @Override
    public void updateRoleById(Integer id, Integer roleId) {
        Admin admin = adminMapper.selectById(id);
        admin.setRoleId(roleId);
        adminMapper.updateById(admin);
    }

    @RequiresPermissions("qk:admin:delete")
    @RequiredLog("账户删除")
    @Override
    public void deleteAdminById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public List<Admin> findAdminByname(String name,int page, int limit) {
        List<Admin> admins =  adminMapper. findAdminByname(name,page,limit);
        return admins;
    }

    @Override
    public int countbyname(String name) {
        return adminMapper.countbyname(name);
    }

    @Override
    public List<Integer> findMenusByName(String name) {
        //1通过admin 的name 找到role的id
        Admin admin = adminMapper.selectByName(name);
        Integer roleId = admin.getRoleId();
        //2获取role 通过roleId
        Role role = roleMapper.selectById(roleId);
        //用role得到的menu的id
        List<Integer> menusId =  roleMenuMapper.findMenuIdsByRoleId(roleId);

        return menusId;
    }

    @Override
    public void editAdmin(Admin admin) {
        String name = admin.getName();
        String email = admin.getEmail();
        String phone = admin.getPhone();
        Admin admin1 = adminMapper.selectByName(name);
        admin1.setPhone(phone);
        admin1.setEmail(email);
        adminMapper.updateById(admin1);
    }

    @Override
    public void editAdmin1(Admin admin) {
        System.out.println(admin);
        String password = admin.getPassword();
        String salt = UUID.randomUUID().toString();
        SimpleHash sh = new SimpleHash("MD5", password, salt);
        String newpassword = sh.toHex();

        admin.setSalt(salt);
        admin.setPassword(newpassword);

        adminMapper.updateById(admin);

    }
}
