package com.ca.service;

import com.ca.pojo.User;
import org.springframework.transaction.annotation.Transactional;

public interface DubboUserService {
    @Transactional
    void saveUser(User user);

    String doLogin(User user);

    User findUserByUserId(Long userId );


    void updateUser(User user);
}
