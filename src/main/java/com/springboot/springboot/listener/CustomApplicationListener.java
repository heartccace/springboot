package com.springboot.springboot.listener;

import com.springboot.springboot.event.CustomApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author heartccace
 * @create 2020-04-02 19:37
 * @Description TODO
 * @Version 1.0
 */
public class CustomApplicationListener implements ApplicationListener<CustomApplicationEvent> {
    @Override
    public void onApplicationEvent(CustomApplicationEvent event) {
        System.out.println(event);
    }
}
