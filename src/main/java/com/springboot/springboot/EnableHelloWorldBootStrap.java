package com.springboot.springboot;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author liushuang
 * @create 2019-08-10 11:43
 */
@SpringBootApplication
public class EnableHelloWorldBootStrap {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(EnableHelloWorldBootStrap.class)
                .web(WebApplicationType.NONE)
                .run(args);
        String helloWorld = context.getBean("HelloWorld", String.class);
        System.out.println(helloWorld);
        context.close();
    }
}
