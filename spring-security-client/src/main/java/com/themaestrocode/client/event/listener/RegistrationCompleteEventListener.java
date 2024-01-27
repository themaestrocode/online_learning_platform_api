package com.themaestrocode.client.event.listener;

import com.themaestrocode.client.entity.User;
import com.themaestrocode.client.entity.VerificationToken;
import com.themaestrocode.client.event.RegistrationCompleteEvent;
import com.themaestrocode.client.service.EmailService;
import com.themaestrocode.client.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        VerificationToken verificationToken = generateVerificationToken(user);
        verificationTokenService.saveVerificationToken(verificationToken);

        //build the link/url for email verification
        String verificationLink = event.getApplicationUrl() + "/verifyregistration?token=" + verificationToken.getToken();
        String userName = user.getFirstName() + " " + user.getLastName();
        String message = String.format("Hi, %s!\n\nClick the link below to verify your email and activate your account. " +
                "Kindly note that this link will expire in 30 minutes.\n%s", userName, verificationLink);

//        try {
//            emailService.sendEmail(user.getUserEmail(), message);
//        } catch (MessagingException e) {
//            throw new RuntimeException("failed to send email!");


        System.out.println(String.format("\n\nYour verification link: %s\n\n", verificationLink));
    }

    private VerificationToken generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setCreationTime(LocalDateTime.now());
        verificationToken.setExpiryTime(LocalDateTime.now().plusMinutes(30));
        verificationToken.setUser(user);

        return verificationToken;
    }
}
