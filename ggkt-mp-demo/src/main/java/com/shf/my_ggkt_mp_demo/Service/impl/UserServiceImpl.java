package com.shf.my_ggkt_mp_demo.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shf.my_ggkt_mp_demo.Service.UserService;
import com.shf.my_ggkt_mp_demo.entity.User;
import com.shf.my_ggkt_mp_demo.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
