package com.springboot.springboot.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author liushuang
 * @create 2019-08-10 16:33
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@Conditional(OnSystemPropertyCondition.class)
public @interface ConditionalOnSystemProperty {
    /**
     * 系统属性名称
     * @return
     */
    String name();

    /**
     * 系统属性值
     * @return
     */
    String value();
}
