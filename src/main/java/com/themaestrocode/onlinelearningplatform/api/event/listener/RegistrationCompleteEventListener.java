package com.themaestrocode.onlinelearningplatform.api.event.listener;

import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.entity.VerificationToken;
import com.themaestrocode.onlinelearningplatform.api.event.RegistrationCompleteEvent;
import com.themaestrocode.onlinelearningplatform.api.service.EmailService;
import com.themaestrocode.onlinelearningplatform.api.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private EmailService emailService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        User user = event.getUser();
        VerificationToken verificationToken = verificationTokenService.generateVerificationToken(user);
        verificationTokenService.saveVerificationToken(verificationToken);

        //build the link/url for email verification
        String verificationLink = event.getApplicationUrl() + "/registration/verify-registration?token=" + verificationToken.getToken();
        String userName = user.getFirstName() + " " + user.getLastName();
        String message = String.format("Hi, %s!\n\nClick the link below to verify your email and activate your account. " +
                "Kindly note that this link will expire in 30 minutes.\n%s", userName, verificationLink);

//        try {
//            emailService.sendEmail(user.getUserEmail(), message);
//        } catch (MessagingException e) {
//            throw new RuntimeException("failed to send email!");

        System.out.println(String.format("\n\nYour verification link: %s\n\n", verificationLink));
    }

}
