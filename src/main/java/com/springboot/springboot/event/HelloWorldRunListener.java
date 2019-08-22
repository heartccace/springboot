package com.springboot.springboot.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author liushuang
 * @create 2019-08-21 15:49
 */
public class HelloWorldRunListener implements SpringApplicationRunListener {
    // 默认构造器
    public HelloWorldRunListener(SpringApplication application, String[] args) {

    }
    @Override
    public void starting() {
        System.out.println("监听到方法  starting");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("监听到方法  environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("监听到方法  contextPrepared");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("监听到方法  contextLoaded");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("监听到方法  started");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }
}
