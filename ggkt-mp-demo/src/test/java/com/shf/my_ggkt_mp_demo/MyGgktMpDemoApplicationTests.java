package com.shf.my_ggkt_mp_demo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shf.my_ggkt_mp_demo.entity.User;
import com.shf.my_ggkt_mp_demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class MyGgktMpDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {

    }

    /**
     * 查找操作
     */
    @Test
    public void findAll(){
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    /**
     * 添加操作
     */
    @Test
    public void addUser(){
        User user = new User();
        user.setName("尚硅谷");
        user.setAge(9);
        user.setEmail("shf@qq.com");
        int rows = userMapper.insert(user);
        System.out.println("rows:"+rows);
//        添加成功后，把添加之后生成的id值，回填到user对象里面
        System.out.println(user);
    }

//    3.修改操作
    @Test
    public void updateUser(){
//        根据id查询
        User user = userMapper.selectById(1544866935712555010L);
//        设置修改值
        user.setName("尚硅谷上海");
//        调用方法实现修改
        int rows = userMapper.updateById(user);
        System.out.println("rows="+rows);
    }

//    4.分页查询
    @Test
    public void findPage(){
//        创建page对象，传递两个参数；当前页 每页显示记录数
        Page<User> page = new Page<>(1, 3);
//        调用mp方法实现分页
        Page<User> pageModel = userMapper.selectPage(page, null);

        page.getRecords().forEach(System.out::println);
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

//    5. id删除
    @Test
    public void testDelete(){
        int rows = userMapper.deleteById(1544866754736721921L);
        System.out.println("rows="+rows);
    }

//    6. 批量删除
    @Test
    public void deleteBatch(){
        int rows = userMapper.deleteBatchIds(Arrays.asList("1544867077626789890", "1544870380871901186"));
        System.out.println("rows="+rows);
    }

    @Test
    public void query1(){
//        创建条件构造对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();

//        ge为例有两个参数：第一个参数
        wrapper.ge("age", 20);

        List<User> userList = userMapper.selectList(wrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void query2(){
//        创建条件构造对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();

//        ge为例有两个参数：第一个参数
        wrapper.eq("name", "尚硅谷");

        List<User> userList = userMapper.selectList(wrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void query3(){
//        创建条件构造对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();

//        ge为例有两个参数：第一个参数
//        wrapper.like("name", "尚硅谷");
//        wrapper.likeLeft("name", "尚硅谷");
        wrapper.likeRight("name", "尚硅谷");

        List<User> userList = userMapper.selectList(wrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void query4(){
//        创建条件构造对象
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.orderByDesc("id");

        List<User> userList = userMapper.selectList(wrapper);
        userList.forEach(System.out::println);
    }

    @Test
    public void query5(){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName, "尚硅谷");
        List<User> userList = userMapper.selectList(wrapper);
        userList.forEach(System.out::println);
    }
}
