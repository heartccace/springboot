package com.springboot.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liushuang
 * @create 2019-08-10 11:35
 */
@Configuration
public class HelloWorldConfiguration {
    @Bean
    public  String HelloWorld() {
        return "HELLO WORLD";
    }
}
