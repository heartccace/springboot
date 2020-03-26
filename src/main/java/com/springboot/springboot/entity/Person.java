package com.springboot.springboot.entity;

import java.util.Date;

/**
 * @author heartccace
 * @create 2020-03-26 13:26
 * @Description TODO
 * @Version 1.0
 */
public class Person {
    private String id;
    private String name;
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
