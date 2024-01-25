package com.themaestrocode.onlinelearningplatform.api.controller;

import com.themaestrocode.onlinelearningplatform.api.entity.Student;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;
import com.themaestrocode.onlinelearningplatform.api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @PostMapping("/register/student")
    public ResponseEntity<Student> registerAsStudent(@RequestBody UserModel userModel) {
        Student student = studentService.registerAsStudent(userModel);

        return ResponseEntity.ok(student);
    }

    @GetMapping("/login/student/{studentId}")
    public ResponseEntity<Student> loginAsStudent(@PathVariable("studentId") Long studentId) {
        Student student = studentService.loginAsStudent(studentId);

        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

    @GetMapping("/student/profile/{studentId}")
    public ResponseEntity<Student> getStudentProfileById(@PathVariable("studentId") Long studentId) {
        Student student = studentService.getStudentProfileById(studentId);

        return ResponseEntity.status(HttpStatus.OK).body(student);
    }

}
