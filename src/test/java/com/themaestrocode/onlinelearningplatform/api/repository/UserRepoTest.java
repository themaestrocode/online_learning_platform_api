package com.themaestrocode.onlinelearningplatform.api.repository;

import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.security.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserRepoTest {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void saveStudent() {
        User user = User.builder()
                .firstName("victor").lastName("soderu").userEmail("victorsoderu@gmail.com").userPassword(passwordEncoder.encode("victor1234"))
                .phoneNo("+2349070986581").userRole(UserRole.STUDENT).build();

        userRepo.save(user);
        System.out.println("\nUser: " + user + "\n");
    }
}