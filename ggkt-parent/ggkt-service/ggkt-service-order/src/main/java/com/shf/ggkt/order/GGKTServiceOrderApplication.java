package com.shf.ggkt.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.shf")
public class GGKTServiceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(GGKTServiceOrderApplication.class,args);
    }
}
