package com.hwt.sbct.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/web")
public class WebController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "欢迎使用Spring Boot!");
        return "index";
    }
    
    @GetMapping("/about")
    public String about() {
        return "about";
    }
}