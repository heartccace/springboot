package com.springboot.springboot.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author liushuang
 * @create 2019-08-21 12:11
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HelloWorldApplicationListenerAfter implements ApplicationListener<ContextRefreshedEvent>, Ordered {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("Hello World Event" + event.getApplicationContext().getId() + "," +
                "TimeStap" + event.getTimestamp());
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
