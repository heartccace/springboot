package com.springboot.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class SpringbootApplication {
    private RedisTemplate template;
    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

}
