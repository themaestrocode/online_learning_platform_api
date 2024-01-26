package com.themaestrocode.onlinelearningplatform.api.controller;

import com.themaestrocode.onlinelearningplatform.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<String> login() {
        return ResponseEntity.status(HttpStatus.OK).body("Login to your account as a student or creator");
    }

}
