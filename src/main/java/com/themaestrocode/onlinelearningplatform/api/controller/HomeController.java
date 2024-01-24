package com.themaestrocode.onlinelearningplatform.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/onlinelearningplatform/v1/home")
    public String home() {
        return "Welcome to themaestrocode online leaning platform. Everything you wish to learn under your fingertips";
    }
}
