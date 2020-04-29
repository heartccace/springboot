package com.springboot.springboot.listener;

import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;

/**
 * 实现{@link ApplicationListener} 监听器，监听所有springboot事件
 * @author heartccace
 * @create 2020-04-02 15:26
 * @Description TODO
 * @Version 1.0
 */
public class SpringApplicationListener  implements ApplicationListener<SpringApplicationEvent> {

    @Override
    public void onApplicationEvent(SpringApplicationEvent event) {
        System.out.println("springboot event----------------------> " + event.getClass().getSimpleName());
    }
}
