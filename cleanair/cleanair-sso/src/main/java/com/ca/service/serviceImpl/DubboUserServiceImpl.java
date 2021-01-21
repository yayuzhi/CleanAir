package com.ca.service.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ca.mapper.UserMapper;
import com.ca.pojo.User;
import com.ca.service.DubboUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

@Service(timeout = 3000)
public class DubboUserServiceImpl implements DubboUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void saveUser(User user) {
        //密码采用md5方式进行加密处理
        String password = user.getPassword();
        String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(md5Pass);
        userMapper.insert(user);
    }
}
