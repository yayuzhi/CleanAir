package com.ca.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ca.mapper.UserMapper;
import com.ca.pojo.User;
import com.ca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public StringBuffer checkUser(User user) {
        StringBuffer str =new StringBuffer();
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        QueryWrapper<User> queryWrapper2 = new QueryWrapper<>();
        QueryWrapper<User> queryWrapper3 = new QueryWrapper<>();
        //1判断名字是否存在
        String name = user.getUsername();
        queryWrapper1.eq("username", name);
        int count1 = userMapper.selectCount(queryWrapper1);

        //2判断手机号是否存在
        String phone = user.getPhone();
        queryWrapper2.eq("phone", phone);
        int count2 = userMapper.selectCount(queryWrapper2);

        //3判断邮箱是否存在
        String email = user.getEmail();
        queryWrapper3.eq("email", email);
        int count3 = userMapper.selectCount(queryWrapper3);


        String str1 = "用户名已经存在!";
        String str2 = "手机已被注册!";
        String str3 = "邮箱已被注册!";

        if (count1 > 0) {

            str.append(str1);
        }

        if (count2 > 0) {

            str.append(str2);
        }

        if (count3 > 0) {

            str.append(str3);
        }
        return str;
    }
}
