package com.atguigu;

import com.atguigu.entity.User;
import com.atguigu.mapper.UserMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class GgktMpDemoApplicationTests {

    //注入mapper
    @Autowired
    private UserMapper userMapper;

    //6 批量删除
    @Test
    public void deleteBatch() {
        int rows = userMapper.deleteBatchIds(Arrays.asList(1, 2));
        System.out.println(rows);
    }
    
    //5 id删除
    @Test
    public void deleteId() {
        int rows = userMapper.deleteById(1513724149690908674L);
        System.out.println(rows);
    }

    //4 分页查询
    @Test
    public void findPage() {
        //创建Page对象，传递两个参数：当前页    每页显示记录数
        Page<User> page = new Page<>(1,3);
        //调用mp方法实现分页
        userMapper.selectPage(page,null);
        //IPage<User> pageModel = userMapper.selectPage(page,null);
        List<User> list = page.getRecords();
        System.out.println(list);
        System.out.println(page.getCurrent());
        System.out.println(page.getPages());
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
    }

    //3 修改操作
    @Test
    public void updateUser() {
        //根据id查询
        User user = userMapper.selectById(1513696369812992001L);
        //设置修改值
        user.setName("尚硅谷上海");
        //调用方法实现修改
        int rows = userMapper.updateById(user);
        System.out.println(rows);
    }

    //2 添加操作
    @Test
    public void addUser() {
        User user = new User();
        user.setName("尚硅谷上海");
        user.setAge(10);
        user.setEmail("atguigu.com");
        int rows = userMapper.insert(user);
        System.out.println("rows:"+rows);
        //添加成功之后，把添加之后生成id值，回填到user对象里面
        System.out.println(user);
    }

    //1 查询表所有数据
    @Test
    public void findAll() {
        List<User> userList = userMapper.selectList(null);
        for (User user:userList) {
            System.out.println(user);
        }
    }

}
