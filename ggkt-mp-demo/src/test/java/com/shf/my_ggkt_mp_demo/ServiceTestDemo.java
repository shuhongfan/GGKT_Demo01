package com.shf.my_ggkt_mp_demo;

import com.shf.my_ggkt_mp_demo.Service.UserService;
import com.shf.my_ggkt_mp_demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ServiceTestDemo {
    @Autowired
    private UserService userService;

    @Test
    public void testQuery2(){
        List<User> list = userService.list();
        System.out.println(list);
    }
}
