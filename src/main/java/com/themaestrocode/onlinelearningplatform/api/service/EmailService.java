package com.themaestrocode.onlinelearningplatform.api.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmail(String userEmail, String message) throws MessagingException;
}
