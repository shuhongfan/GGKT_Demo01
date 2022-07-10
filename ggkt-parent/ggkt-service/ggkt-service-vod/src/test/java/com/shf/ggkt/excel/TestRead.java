package com.shf.ggkt.excel;

import com.alibaba.excel.EasyExcel;

/**
 * EasyExcel读操作
 * 1.引入依赖
 * 2.创建和excel对应实体类，设置对应关系
 * 3.创建监听器进行一行一行解析过程
 *  3.1 创建类，继承类，封装解析方法
 * 4. 调用EasyExcel读方法实现操作
 */
public class TestRead {
    public static void main(String[] args) {
//        设置文件名称和路径
        String fileName = "C:\\Users\\shf\\Documents\\CODE\\GGKT_Demo01\\ggkt-parent\\ggkt-service\\ggkt-service-vod\\src\\test\\java\\com\\shf\\ggkt\\excel\\user.xlsx";

// 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(
                fileName,
                User.class,
                new ExcelListener()).sheet().doRead();

    }
}
