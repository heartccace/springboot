package com.springboot.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author heartccace
 * @create 2020-04-03 12:59
 * @Description TODO
 * @Version 1.0
 */
@SpringBootApplication
public class LoggerSpringbootApplication {
    private static final Logger logger = LoggerFactory.getLogger(LoggerSpringbootApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(LoggerSpringbootApplication.class,args);
    }

    @PostConstruct
    public void myLog() {
        logger.trace("Trace message");
        logger.debug("Debug message");
        logger.info("Info message");
        logger.warn("Warn message");
        logger.error("Error message");

    }
}
