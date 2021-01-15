package com.ca.service.realm;


import com.ca.pojo.Admin;
import com.ca.pojo.Menu;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ShiroAdnibRealm extends AuthorizingRealm {



    /**
     * 设置凭证匹配器(与用户添加操作使用相同的加密算法)
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        //构建凭证匹配对象
        HashedCredentialsMatcher cMatcher = new HashedCredentialsMatcher();
        //设置加密算法
        cMatcher.setHashAlgorithmName("MD5");
        //设置加密次数
        cMatcher.setHashIterations(1);
        super.setCredentialsMatcher(cMatcher);
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1获取用户id（登录界面输入）
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        String adminname = usernamePasswordToken.getUsername();
        //2基于用户id查询用户信息

        //5.封装用户信息
//        ByteSource credentialsSalt = ByteSource.Util.bytes(admin.getSalt());

        //记住：构建什么对象要看方法的返回值
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
//                new SimpleAuthenticationInfo(admin,//principal (身份)
//                        admin.getPassword(),//hashedCredentials
//                        credentialsSalt, //credentialsSalt
//                        getName());//realName
        //6.返回封装结果
        return info;//返回值会传递给认证管理器(后续
        //认证管理器会通过此信息完成认证操作)
    }

    //这里是验证用户的职务的权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //1获取登录用户的信息
        Admin admin = (Admin) principalCollection.getPrimaryPrincipal();

        //2get admin 的roleID
//        Integer roleId = admin.getRoleId();
//        if (roleId == null || roleId == 0) throw new AuthorizationException();
//        //从role中获取它的menus 依靠role的id 找到它的menus
//        List<Integer> menuIds = roleMenuMapper.findMenuIdsByRoleId(roleId);
//        if(menuIds==null||menuIds.size()==0) throw new AuthorizationException();
////        System.out.println(menuIds);
//        Set<String> set = new HashSet<>();
//        for (Integer menuId : menuIds) {
//            Menu menu = menuMapper.selectById(menuId);
////            System.out.println(menu);
//            String permission = menu.getPermission();
////            System.out.println(permission);
//            if (!StringUtils.isEmpty(permission)){
//                set.add(permission);
//            }
//        }
//        System.out.println(set);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        info.setStringPermissions();
        return info;
    }

}
