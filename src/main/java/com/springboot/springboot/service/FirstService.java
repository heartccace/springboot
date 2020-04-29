package com.springboot.springboot.service;

import com.springboot.springboot.event.CustomApplicationEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author heartccace
 * @create 2020-03-30 23:22
 * @Description TODO
 * @Version 1.0
 */
@Service
public class FirstService implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String test() {
        return applicationContext.getEnvironment().getProperty("key1");
    }

    public void publishEvent() {
        applicationContext.publishEvent(new CustomApplicationEvent("custom event"));
    }
}
