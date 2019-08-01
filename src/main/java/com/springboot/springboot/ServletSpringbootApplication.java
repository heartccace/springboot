package com.springboot.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author liushuang
 * @create 2019-08-01 13:39
 */
@SpringBootApplication
@ServletComponentScan(basePackages = "com.springboot.springboot.servlet")
public class ServletSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServletSpringbootApplication.class, args);
    }
}
