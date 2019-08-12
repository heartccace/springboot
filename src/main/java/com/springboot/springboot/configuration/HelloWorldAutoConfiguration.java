package com.springboot.springboot.configuration;

import com.springboot.springboot.annotation.EnableHelloWorld;
import com.springboot.springboot.condition.ConditionalOnSystemProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liushuang
 * @create 2019-08-12 16:06
 */
@Configuration // spring模式注解装配
@EnableHelloWorld // spring @enble模块装配
@ConditionalOnSystemProperty(name="user.name",value="admin") // spring条件装配
public class HelloWorldAutoConfiguration {

}
