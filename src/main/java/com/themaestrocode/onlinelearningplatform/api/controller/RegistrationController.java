package com.themaestrocode.onlinelearningplatform.api.controller;

import com.themaestrocode.onlinelearningplatform.api.entity.Creator;
import com.themaestrocode.onlinelearningplatform.api.entity.Student;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;
import com.themaestrocode.onlinelearningplatform.api.service.CreatorService;
import com.themaestrocode.onlinelearningplatform.api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private CreatorService creatorService;

    @GetMapping("/api/v1/register/student")
    public String registerStudent(/*@RequestBody UserModel userModel*/) {
        //Student student = studentService.registerStudent(userModel);

        //return ResponseEntity.ok("registration successful!");
        return "successful";
    }

    @GetMapping("/api/v1/register/creator")
    public ResponseEntity<String> registerCreator(@RequestBody UserModel userModel) {
        Creator creator = creatorService.registerCreator(userModel);

        return ResponseEntity.ok("registration successful!");
    }
}
