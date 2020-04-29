package com.springboot.springboot.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author heartccace
 * @create 2020-04-02 19:33
 * @Description TODO
 * @Version 1.0
 */
public class CustomApplicationEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public CustomApplicationEvent(Object source) {
        super(source);
    }
}
