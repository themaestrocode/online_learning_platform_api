package com.themaestrocode.client.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HomeController {

    @GetMapping
    public ResponseEntity<String> home() {
        return ResponseEntity.status(HttpStatus.OK).body("Welcome to themaestrocode online leaning platform. Everything you wish to learn under your fingertips");
    }

}
