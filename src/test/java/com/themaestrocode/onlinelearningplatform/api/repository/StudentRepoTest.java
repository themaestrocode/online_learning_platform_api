package com.themaestrocode.onlinelearningplatform.api.repository;

import com.themaestrocode.onlinelearningplatform.api.entity.Student;
import com.themaestrocode.onlinelearningplatform.api.security.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

class StudentRepoTest {

    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void saveStudent() {
        Student student = Student.builder()
                .firstName("victor").lastName("soderu").email("victorsoderu@gmail.com").password(passwordEncoder.encode("victor1234"))
                .phoneNo("+2349070986581").userRole(UserRole.STUDENT).build();

        studentRepo.save(student);
        System.out.println("\nStudent: " + student + "\n");
    }
}