package com.springboot.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liushuang
 * @create 2019-07-31 19:17
 */
@Controller
public class ThymeLeafController {
    @RequestMapping("/")
    public String index (Model model) {
        model.addAttribute("test","继承thymeleaf");
        return "index";
    }
}
