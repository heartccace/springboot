package com.springboot.springboot.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author liushuang
 * @create 2019-08-21 15:36
 */
public class SpringApplicationEventBootstrap {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.addApplicationListener(event -> {
            System.out.println("监听到事件-------> " + event);
        });
        context.refresh();
        context.publishEvent("hello world");
        context.close();
    }
}
