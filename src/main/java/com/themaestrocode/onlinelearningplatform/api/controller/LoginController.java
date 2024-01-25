package com.themaestrocode.onlinelearningplatform.api.controller;

import com.themaestrocode.onlinelearningplatform.api.entity.Creator;
import com.themaestrocode.onlinelearningplatform.api.entity.Student;
import com.themaestrocode.onlinelearningplatform.api.repository.CreatorRepo;
import com.themaestrocode.onlinelearningplatform.api.service.CreatorService;
import com.themaestrocode.onlinelearningplatform.api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {
    
    @Autowired
    private StudentService studentService;
    @Autowired
    private CreatorService creatorService;

    @GetMapping
    public ResponseEntity<String> login() {
        return ResponseEntity.ok("Login to your account as a student or creator");
    }

//    @GetMapping("/{studentId}")
//    public ResponseEntity<Student> studentLogin(@PathVariable("studentId") Long studentId) {
//        Student student = studentService.fetchStudentById(studentId);
//        String name = student.getFirstName() + " " + student.getLastName();
//        
//        return ResponseEntity.status(HttpStatus.OK).body(student);
//        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body("<h1>Error</h1>\nAccount does not exist. Please, register and create your profile");
//    }
//
//    @GetMapping("/{creatorId}")
//    public ResponseEntity<String> creatorLogin(@PathVariable("creatorId") Long creatorId) {
//        Creator creator = creatorService.fetchCreatorById(creatorId);
//        
//        if(creator != null) {
//            String name = creator.getFirstName() + " " + creator.getLastName();
//            return ResponseEntity.status(HttpStatus.OK).body("Welocme, " + name);
//        }
//       return ResponseEntity.status(HttpStatus.NOT_FOUND).body("<h1>Error</h1>\nAccount does not exist. Please, register and create your profile");
//    }
}
