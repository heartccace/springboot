package com.springboot.springboot.controller;

import com.springboot.springboot.service.FirstService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heartccace
 * @create 2020-03-30 23:21
 * @Description TODO
 * @Version 1.0
 */
@RestController
public class FirstController {
    @Autowired
    private FirstService firstService;
    @GetMapping("/first")
    public String test() {
        return firstService.test();
    }

    @GetMapping("/publishEvent")
    public void testEvent() {
        this.firstService.publishEvent();
    }
}
