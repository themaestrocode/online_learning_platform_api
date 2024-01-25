package com.themaestrocode.onlinelearningplatform.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/register")
public class RegistrationController {


    @GetMapping
    public ResponseEntity<String> registeration() {
        return ResponseEntity.ok("Register as a student or creator");
    }
}
