package com.springboot.springboot.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author heartccace
 * @create 2020-04-02 15:33
 * @Description TODO
 * @Version 1.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpringBootApplicationRunnerListener implements SpringApplicationRunListener {

    // 默认构造器
    public SpringBootApplicationRunnerListener(SpringApplication application, String[] args) {

    }
    @Override
    public void starting() {
        System.out.println("SpringBootApplicationRunnerListener starting is invoked");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("SpringBootApplicationRunnerListener environmentPrepared is invoked");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("SpringBootApplicationRunnerListener contextPrepared is invoked");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("SpringBootApplicationRunnerListener contextLoaded is invoked");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("SpringBootApplicationRunnerListener started is invoked");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("SpringBootApplicationRunnerListener running is invoked");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("SpringBootApplicationRunnerListener failed is invoked");
    }
}
