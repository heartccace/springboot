package com.springboot.springboot.annotation;

import org.springframework.stereotype.Repository;

import java.lang.annotation.*;

/**
 * 派生性
 * @author liushuang
 * @create 2019-08-10 10:54
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repository
public @interface FirstLeveRepository {
    String value() default "";
}
