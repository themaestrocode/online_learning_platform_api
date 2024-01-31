package com.themaestrocode.onlinelearningplatform.api.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {


    public String login(@PathVariable("email") String userEmail, @PathVariable("password") String password) {

        return "Login successful";
    }
}
