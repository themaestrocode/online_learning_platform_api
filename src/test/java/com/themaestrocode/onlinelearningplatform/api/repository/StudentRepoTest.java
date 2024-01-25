package com.themaestrocode.onlinelearningplatform.api.repository;

import com.themaestrocode.onlinelearningplatform.api.security.SecurityConfig;
import com.themaestrocode.onlinelearningplatform.api.entity.Student;
import com.themaestrocode.onlinelearningplatform.api.security.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class StudentRepoTest {

    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private SecurityConfig securityConfig;

    @Test
    public void saveStudent() {
        Student student = Student.builder()
                .firstName("victor").lastName("soderu").email("victorsoderu@gmail.com").password(securityConfig.passwordEncoder().encode("victor1234"))
                .phoneNo("+2349070986581").role(Role.STUDENT).build();

        studentRepo.save(student);
        System.out.println("\nStudent: " + student + "\n");
    }
}