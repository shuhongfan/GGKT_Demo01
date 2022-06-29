package com.atguigu.service.impl;

import com.atguigu.entity.User;
import com.atguigu.mapper.UserMapper;
import com.atguigu.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
//    //注入mapper
//    @Autowired
//    private UserMapper userMapper;

}
