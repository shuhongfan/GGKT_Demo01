package com.shf.ggkt.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * EasyExcel实现写操作
 * 1. 引入依赖
 * 2. 创建实体类（和excel对象），设置excel表头
 * 3. 调用EasyExcel方法实现写操作
 */
public class TestWrite {
    public static void main(String[] args) {
//        设置文件名称和路径
        String fileName = "C:\\Users\\shf\\Documents\\CODE\\GGKT_Demo01\\ggkt-parent\\ggkt-service\\ggkt-service-vod\\src\\test\\java\\com\\shf\\ggkt\\excel\\user.xlsx";

//        调用方法
        EasyExcel.write(fileName, User.class)
                .sheet("写操作")
                .doWrite(data());
    }

    //循环设置要添加的数据，最终封装到list集合中
    private static List<User> data() {
        List<User> list = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            User data = new User();
            data.setId(i);
            data.setName("张三"+i);
            list.add(data);
        }
        return list;
    }
}
