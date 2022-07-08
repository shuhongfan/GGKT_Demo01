package com.shf.my_ggkt_mp_demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.shf.my_ggkt_mp_demo")
@SpringBootApplication
public class MyGgktMpDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyGgktMpDemoApplication.class, args);
    }

}
