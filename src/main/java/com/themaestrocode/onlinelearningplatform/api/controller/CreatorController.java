package com.themaestrocode.onlinelearningplatform.api.controller;

import com.themaestrocode.onlinelearningplatform.api.entity.Creator;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;
import com.themaestrocode.onlinelearningplatform.api.service.CreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CreatorController {

    @Autowired
    private CreatorService creatorService;


    @PostMapping("/register/creator")
    public ResponseEntity<Creator> registerAsCreator(@RequestBody UserModel userModel) {
        Creator creator = creatorService.registerAsCreator(userModel);

        return ResponseEntity.status(HttpStatus.OK).body(creator);
    }

    @GetMapping("/login/creator/{creatorId}")
    public ResponseEntity<Creator> loginAsCreator(@PathVariable("creatorId") Long creatorId) {
        Creator creator = creatorService.loginAsCreator(creatorId);

        return ResponseEntity.status(HttpStatus.OK).body(creator);
    }

}