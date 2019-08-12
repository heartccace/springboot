package com.springboot.springboot.bootstrap;

import com.springboot.springboot.service.CalculateService;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author liushuang
 * @create 2019-08-10 12:22
 */
@SpringBootApplication(scanBasePackages ="com.springboot.springboot.service")
public class CalculateServiceBootStrap {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(CalculateServiceBootStrap.class)
                .web(WebApplicationType.NONE)
                .profiles("Java8")
                .run(args);
        CalculateService service = context.getBean(CalculateService.class);
        System.out.println(service.sum(1,2,3,4,5,6,7,8,9,10));
        context.close();
    }
}
