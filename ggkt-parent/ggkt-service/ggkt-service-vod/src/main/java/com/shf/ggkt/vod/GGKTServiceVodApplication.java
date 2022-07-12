package com.shf.ggkt.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.shf")
@EnableDiscoveryClient
public class GGKTServiceVodApplication {
    public static void main(String[] args) {
        SpringApplication.run(GGKTServiceVodApplication.class, args);
    }
}
