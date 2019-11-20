package com.herobin.scd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author binzhang
 * @date 2019-11-06
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
//@EnableOAuth2Sso
public class ZuulApplication{
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//        .authorizeRequests()
//        .antMatchers("/login", "/user/**")
//        .permitAll()
//        .anyRequest()
//        .authenticated()
//        .and()
//        .csrf()
//        .disable();
//    }
}
