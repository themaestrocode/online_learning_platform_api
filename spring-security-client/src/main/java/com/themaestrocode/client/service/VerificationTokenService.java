package com.themaestrocode.client.service;

import com.themaestrocode.client.entity.VerificationToken;

import java.time.LocalDateTime;

public interface VerificationTokenService {

    void saveVerificationToken(VerificationToken verificationToken);

    VerificationToken validateVerificationToken(String token);

    void updateVerificationTokenConfirmationTime(String token, LocalDateTime now);

    VerificationToken findByToken(String token);

    void deleteVerificationToken(VerificationToken verificationToken);
}
