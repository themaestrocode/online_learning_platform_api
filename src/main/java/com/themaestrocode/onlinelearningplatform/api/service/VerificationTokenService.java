package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.VerificationToken;
import com.themaestrocode.onlinelearningplatform.api.repository.VerificationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class VerificationTokenService {

    @Autowired
    private VerificationTokenRepo verificationTokenRepo;

    public void saveVerificationToken(VerificationToken verificationToken) {
        verificationTokenRepo.save(verificationToken);
    }

    public Optional<VerificationToken> findVerificationToken(String token) {
        return verificationTokenRepo.findByToken(token);
    }

    public void setConfirmationTime(String token) {
        verificationTokenRepo.updateConfirmationTime(token, LocalDateTime.now());
    }
}
