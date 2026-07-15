package com.example.javademo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    /**
     * 项目首页。
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 登录页面。
     *
     * 这里只处理 GET /login。
     * POST /login 由 Spring Security 处理。
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * 登录后才能访问的个人主页。
     */
    @GetMapping("/home")
    public String home(
            Authentication authentication,
            Model model
    ) {
        model.addAttribute(
                "username",
                authentication.getName()
        );

        return "home";
    }
}