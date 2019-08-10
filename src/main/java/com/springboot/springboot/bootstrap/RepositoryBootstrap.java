package com.springboot.springboot.bootstrap;

import com.springboot.springboot.repository.MyRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author liushuang
 * @create 2019-08-10 11:08
 */
@SpringBootApplication(scanBasePackages = {"com.springboot.springboot.repository"})
public class RepositoryBootstrap {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(RepositoryBootstrap.class)
                .web(WebApplicationType.NONE)
                .run(args);
        MyRepository bean = context.getBean(MyRepository.class);
        System.out.println(bean);
        context.close();
    }
}
