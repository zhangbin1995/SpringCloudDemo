package com.herobin.scd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author binzhang
 * @date 2019-11-06
 */
@EnableSwagger2
@SpringBootApplication
@EnableDiscoveryClient
public class Bank1Application {
    public static void main(String[] args) {
        SpringApplication.run(Bank1Application.class, args);
    }
}
