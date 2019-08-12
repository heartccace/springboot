package com.springboot.springboot.bootstrap;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author liushuang
 * @create 2019-08-12 16:03
 */
@EnableAutoConfiguration()
@ComponentScan(basePackages = "com.springboot.springboot.configuration")
public class EnableAutoConfigurationBootstrap {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(EnableAutoConfigurationBootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);
        String result = context.getBean("HelloWorld",String.class);
        System.out.println("retult :" + result);
        context.close();
    }
}
