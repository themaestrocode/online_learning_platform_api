package com.themaestrocode.client.service;

import com.themaestrocode.client.entity.VerificationToken;
import com.themaestrocode.client.repository.VerificationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService{

    @Autowired
    private VerificationTokenRepo verificationTokenRepo;

    @Override
    public void saveVerificationToken(VerificationToken verificationToken) {
        verificationTokenRepo.save(verificationToken);
    }

    @Override
    public VerificationToken validateVerificationToken(String token) {
        return null;
    }

    @Override
    public void updateVerificationTokenConfirmationTime(String token, LocalDateTime now) {
        verificationTokenRepo.updateVerificationTokenConfirmationTime(token, now);
    }

    @Override
    public VerificationToken findByToken(String token) {
        return verificationTokenRepo.findByToken(token);
    }

    @Override
    public void deleteVerificationToken(VerificationToken verificationToken) {
        verificationTokenRepo.delete(verificationToken);
    }
}
