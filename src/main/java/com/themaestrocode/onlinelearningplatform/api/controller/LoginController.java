package com.themaestrocode.onlinelearningplatform.api.controller;

import com.themaestrocode.onlinelearningplatform.api.model.UserLoginModel;
import com.themaestrocode.onlinelearningplatform.api.security.JwtService;
import com.themaestrocode.onlinelearningplatform.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;


    @PostMapping
    public String login(@RequestBody UserLoginModel userLoginModel) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginModel.getEmail(), userLoginModel.getPassword()));
        UserDetails user = userService.findByEmail(userLoginModel.getEmail());

        if(user != null) {
            return jwtService.generateToken(user);
        }

        return "Some error has occurred...";
    }
}
