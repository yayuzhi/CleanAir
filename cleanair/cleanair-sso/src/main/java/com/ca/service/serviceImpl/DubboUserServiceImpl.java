package com.ca.service.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ca.mapper.UserMapper;
import com.ca.pojo.User;
import com.ca.service.DubboUserService;
import com.ca.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;

import java.util.UUID;

@Service(timeout = 3000)
public class DubboUserServiceImpl implements DubboUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JedisCluster jedisCluster;

    //注册
    @Override
    public void saveUser(User user) {
        //密码采用md5方式进行加密处理
        String password = user.getPassword();
        String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(md5Pass);
        userMapper.insert(user);
    }

    //登录
    @Override
    public String doLogin(User user) {
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);//u/p不能
        //根据对象中不为空的属性,充当where条件.
        User userDB = userMapper.selectOne(queryWrapper);
        if (userDB == null){
            ////根据用户名和密码错误
            return null;
        }

        //开始进行单点登录业务操作
        String uuid = UUID.randomUUID()
                .toString()
                .replace("-", "");
        userDB.setPassword("123456你信不?");   //去除有效信息.
        String userJSON = ObjectMapperUtil.toJSON(userDB);
        jedisCluster.setex(uuid, 30*24*60*60, userJSON);

        return uuid;
    }
}
