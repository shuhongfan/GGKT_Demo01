package com.atguigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.atguigu.mapper")
public class GgktMpDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(GgktMpDemoApplication.class, args);
    }

}
