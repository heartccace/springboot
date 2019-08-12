package com.springboot.springboot.bootstrap;

import com.springboot.springboot.condition.ConditionalOnSystemProperty;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author liushuang
 * @create 2019-08-10 16:57
 */
@SpringBootApplication
public class SystemPropertyConditionBootstrap {

    @Bean
    @ConditionalOnSystemProperty(name="user.name",value="admin")
    public String helloWorld() {
        return "Hello World";
    }
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(SystemPropertyConditionBootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);
        String bean = context.getBean("helloWorld",String.class);
        System.out.println(bean);
        context.close();
    }
}
