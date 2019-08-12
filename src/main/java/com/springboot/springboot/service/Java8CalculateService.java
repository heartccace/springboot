package com.springboot.springboot.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * java8 Lambda实现 {@link CalculateService}
 * @author liushuang
 * @create 2019-08-10 12:11
 */
@Profile("Java8")
@Service
public class Java8CalculateService implements CalculateService {
    @Override
    public Integer sum(Integer... values) {
        int sum = Stream.of(values).reduce(0, Integer::sum);
        System.out.println("java8 Lambda实现累加");
        return sum;
    }
}
