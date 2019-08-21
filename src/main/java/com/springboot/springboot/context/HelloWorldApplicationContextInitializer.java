package com.springboot.springboot.context;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author liushuang
 * @create 2019-08-21 11:48
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HelloWorldApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("applicationContext id:" + applicationContext.getId());
    }
}
