package com.springboot.springboot.context;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;

/**
 * @author liushuang
 * @create 2019-08-21 11:48
 */
public class HelloWorldApplicationContextInitializerAfter implements ApplicationContextInitializer,Ordered {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("after applicationContext id:" + applicationContext.getId());
    }


    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
