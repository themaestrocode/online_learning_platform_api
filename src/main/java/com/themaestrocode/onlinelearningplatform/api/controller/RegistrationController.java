package com.themaestrocode.onlinelearningplatform.api.controller;

import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.entity.VerificationToken;
import com.themaestrocode.onlinelearningplatform.api.event.RegistrationCompleteEvent;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;
import com.themaestrocode.onlinelearningplatform.api.service.UserService;
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


    @GetMapping
    public String registration() {
        return "Register as a student or creator";
    }

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

    @GetMapping("/regeneratetoken")
    public String regenerateVerificationToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
        VerificationToken verificationToken = userService.generateNewVerificationToken(oldToken);

        User user = verificationToken.getUser();
        resendVerificationTokenMail(user, applicationUrl(request), verificationToken);

        return "A new verification link has been sent to your email. It will expire in 30 minutes.";
    }

    private void resendVerificationTokenMail(User user, String applicationUrl, VerificationToken verificationToken) {
        String verificationLink = String.format("%s/registration/verifyregistration?token=%s", applicationUrl, verificationToken.getToken());
        String userName = user.getFirstName() + " " + user.getLastName();
        String message = String.format("Hi, %s!\n\nClick the link below to verify your email and activate your account. " +
                "Kindly note that this link will expire in 30 minutes.\n%s", userName, verificationLink);

//        try {
//            emailService.sendEmail(user.getUserEmail(), message);
//        } catch (MessagingException e) {
//            throw new RuntimeException("failed to send email!");

        System.out.println(String.format("\n\nYour verification link: %s\n\n", verificationLink));
    }

    private String applicationUrl(HttpServletRequest request) {
        return String.format("http://%s:%d/api/v1%s", request.getServerName(), request.getServerPort(), request.getContextPath());
    }


}
