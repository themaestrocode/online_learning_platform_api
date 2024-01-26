package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;
import com.themaestrocode.onlinelearningplatform.api.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByUserEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("user with %s not found", email)));
    }

    @Override
    public String signUpUser(User user) {
        //check if another user exists with the provided email adrress
        boolean userExists = userRepo.findByUserEmail(user.getUserEmail()).isPresent();

        if(userExists) {
            throw new IllegalStateException("the provided email is already taken");
        }

        user.setUserPassword(passwordEncoder.encode(user.getPassword())); //encode user password
        userRepo.save(user);

        // TODO: send confirmation token

        return "user registered";
    }
}
