package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Student;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;
import com.themaestrocode.onlinelearningplatform.api.repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Student registerAsStudent(UserModel userModel) {

        Student student = new Student();
        student.setFirstName(userModel.getFirstName());
        student.setLastName(userModel.getLastName());
        student.setEmail(userModel.getEmail());
        student.setPassword(passwordEncoder.encode(userModel.getPassword()));
        student.setPhoneNo(userModel.getPhoneNo());

        return studentRepo.save(student);
    }

    @Override
    public Student loginAsStudent(Long studentId) {
        Student student = studentRepo.findById(studentId).get();

        return student;
    }

    @Override
    public Student getStudentProfileById(Long studentId) {
        Student student = studentRepo.findById(studentId).get();

        return student;
    }
}
