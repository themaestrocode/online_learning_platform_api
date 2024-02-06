package com.themaestrocode.onlinelearningplatform.api.controller;

import com.themaestrocode.onlinelearningplatform.api.model.UserAuthModel;
import com.themaestrocode.onlinelearningplatform.api.security.JwtService;
import com.themaestrocode.onlinelearningplatform.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;


    @PostMapping("/login")
    public String login(@RequestBody UserAuthModel userAuthModel) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthModel.getEmail(), userAuthModel.getPassword()));
        UserDetails user = userService.findByEmail(userAuthModel.getEmail());

        if(user != null) {
            return jwtService.generateToken(user);
        }

        return "Some error has occurred...";
    }
}
