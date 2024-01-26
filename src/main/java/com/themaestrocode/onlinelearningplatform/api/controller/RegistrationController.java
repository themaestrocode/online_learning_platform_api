package com.themaestrocode.onlinelearningplatform.api.controller;

import com.themaestrocode.onlinelearningplatform.api.model.UserModel;
import com.themaestrocode.onlinelearningplatform.api.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;


    @PostMapping("/student")
    public ResponseEntity<String> registerAsStudent(@RequestBody UserModel userModel) {
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.registerAsStudent(userModel));
    }

    @PostMapping("/creator")
    public ResponseEntity<String> registerAsCreator(@RequestBody UserModel userModel) {
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.registerAsCreator(userModel));
    }

    @GetMapping(path = "/confirm")
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") String token) {
        return registrationService.confirmRegistration(token);
    }
}
