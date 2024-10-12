package com.sunmeat.hibernate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";  // вернёт файл index.html из папки templates
    }
}