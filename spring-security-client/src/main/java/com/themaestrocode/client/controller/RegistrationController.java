package com.themaestrocode.client.controller;

import com.themaestrocode.client.entity.User;
import com.themaestrocode.client.event.RegistrationCompleteEvent;
import com.themaestrocode.client.model.UserModel;
import com.themaestrocode.client.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostMapping("/student")
    public String registerStudent(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.registerStudent(userModel);

        //created a registration complete event and published it after user successfully registered.
        //this is to send the verification token, which follows next
        eventPublisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));

        return String.format("Welcome, %s! Check your email to verify your account.", user.getFirstName() + " " + user.getLastName());
    }

    @PostMapping("/creator")
    public String registerCreator(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = userService.registerCreator(userModel);
        eventPublisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));

        return String.format("Welcome, %s! Check your email to verify your account.", user.getFirstName() + " " + user.getLastName());
    }

    @GetMapping("/verifyregistration")
    public String confirmRegistration(@RequestParam("token") String token) {
        return userService.confirmRegistration(token);
    }

    private String applicationUrl(HttpServletRequest request) {
        return String.format("http://%s:%d/api/v1%s", request.getServerName(), request.getServerPort(), request.getContextPath());
    }
}
