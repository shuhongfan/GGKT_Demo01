package com.shf.ggkt.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.shf.ggkt.user.mapper")
public class GGKTServiceUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(GGKTServiceUserApplication.class, args);
    }
}
