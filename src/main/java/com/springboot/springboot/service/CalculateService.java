package com.springboot.springboot.service;

/**
 * @author liushuang
 * @create 2019-08-10 12:09
 */
public interface CalculateService {
    /**
     * 从多个整数求和
     * @param values 多个整数
     * @return sum 累加值
     */
    Integer sum(Integer... values);
}
