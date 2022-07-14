package com.shf.ggkt.wechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.shf")
@MapperScan("com.shf.ggkt.wechat.mapper")
@ComponentScan(basePackages = "com.shf")
public class GGKTServiceWechatApplication {
    public static void main(String[] args) {
        SpringApplication.run(GGKTServiceWechatApplication.class, args);
    }
}
