package com.springboot.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

/**
 * @author heartccace
 * @create 2020-04-03 14:02
 * @Description TODO
 * @Version 1.0
 */
@Controller
public class JspController {

    @RequestMapping("/index")
    public String index(Map<String,Object> model) {
        System.out.println("entry");
        model.put("message","message");
        model.put("date", new Date());
        return "index";
    }
}
