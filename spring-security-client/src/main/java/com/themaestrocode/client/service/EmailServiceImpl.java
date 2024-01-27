package com.themaestrocode.client.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String userEmail, String message) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("heysamsungA52@gmail.com");
        helper.setTo(userEmail);
        helper.setSubject("Confirm your email");
        helper.setText(message, true);

        javaMailSender.send(mimeMessage);
    }
}
