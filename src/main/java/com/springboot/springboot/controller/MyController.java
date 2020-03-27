package com.springboot.springboot.controller;

import com.springboot.springboot.entity.Person;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @author heartccace
 * @create 2020-03-26 13:21
 * @Description TODO
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MyController {
    @GetMapping("/person")
    public Person test() {
        Person person = new Person();
        person.setId(UUID.randomUUID().toString());
        person.setName("jetty");
        person.setBirthday(new Date());
        return person;
    }

    @GetMapping("/domain")
    public Object getDomain() {
        return this.getClass().getProtectionDomain();
    }
}
