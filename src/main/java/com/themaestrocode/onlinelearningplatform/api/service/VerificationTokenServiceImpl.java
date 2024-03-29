package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.entity.VerificationToken;
import com.themaestrocode.onlinelearningplatform.api.error.EntityNotFoundException;
import com.themaestrocode.onlinelearningplatform.api.repository.VerificationTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepo verificationTokenRepo;

    @Override
    public VerificationToken saveVerificationToken(VerificationToken verificationToken) {
        return verificationTokenRepo.save(verificationToken);
    }

    @Override
    public VerificationToken findByToken(String token) throws EntityNotFoundException {
        VerificationToken verificationToken = verificationTokenRepo.findByToken(token);

        if(verificationToken == null) throw new EntityNotFoundException("Token not found!");

        return verificationToken;
    }

    @Override
    public VerificationToken generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setCreationTime(LocalDateTime.now());
        verificationToken.setExpiryTime(LocalDateTime.now().plusHours(12));
        verificationToken.setUser(user);

        verificationTokenRepo.save(verificationToken);

        return verificationToken;
    }

    @Override
    public void deleteVerificationTokenById(Long tokenId) {
        verificationTokenRepo.deleteById(tokenId);
    }
}
