package com.jiyonggg.spring_project1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // 일반적인 페이지 조회 및 이동 -> GET
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register/index";
    }

    @GetMapping("/loginPage")
    public String loginPage() {
        return "login/index";
    }
}
