package com.atguigu;

import com.atguigu.entity.User;
import com.atguigu.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class QueryTestsDemo1 {

    //注入mapper
    @Autowired
    private UserMapper userMapper;

    //2 LambdaQueryWrapper
    @Test
    public void testLambdaQuery2() {
        //创建条件构造对象
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        wrapper.like(User::getName,"尚硅谷");
        //调用方法查询
        List<User> userList = userMapper.selectList(wrapper);
        System.out.println(userList);
    }

    //5 LambdaQueryWrapper
    @Test
    public void testLambdaQuery1() {
        //创建条件构造对象
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        wrapper.eq(User::getName,"尚硅谷北京");
        //调用方法查询
        List<User> userList = userMapper.selectList(wrapper);
        System.out.println(userList);
    }

    //4 queryWrapper
    //orderByDesc、orderByAsc
    @Test
    public void query4() {
        //创建条件构造对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //调用方法实现条件查询
        List<User> userList = userMapper.selectList(wrapper);
        System.out.println(userList);
    }

    //3 queryWrapper
    //like、likeLeft、likeRight
    @Test
    public void query3() {
        //创建条件构造对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //like、likeLeft、likeRight
        //wrapper.like("name","尚硅谷");
        wrapper.likeLeft("name","尚硅谷");
        //调用方法实现条件查询
        List<User> userList = userMapper.selectList(wrapper);
        System.out.println(userList);
    }

    //2 queryWrapper
    //eq ne
    @Test
    public void query2() {
        //创建条件构造对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //eq ne
        //eq为例有两个参数：第一个参数表字段名称，第二个参数值
        wrapper.eq("name","尚硅谷北京");
        //调用方法实现条件查询
        List<User> userList = userMapper.selectList(wrapper);
        System.out.println(userList);
    }

    //1 queryWrapper
    //ge、gt、le、lt
    @Test
    public void query1() {
        //创建条件构造对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //ge、gt、le、lt
        //ge为例有两个参数：第一个参数表字段名称，第二个参数值
        wrapper.ge("age",20);
        //调用方法实现条件查询
        List<User> userList = userMapper.selectList(wrapper);
        System.out.println(userList);
    }

}
