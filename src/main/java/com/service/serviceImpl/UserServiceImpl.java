package com.service.serviceImpl;


import com.dao.UserMapper;
import com.model.User;
import com.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    public User selectUser(long userId) {
        return this.userMapper.selectUser(userId);
    }

}