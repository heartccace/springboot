package com.springboot.springboot.annotation;

import com.springboot.springboot.configuration.HelloWorldConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author liushuang
 * @create 2019-08-10 11:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(HelloWorldConfiguration.class) // 驱动方式实现
// @Import(HelloWorldSelector.class) // 接口实现方式
public @interface EnableHelloWorld {
}
