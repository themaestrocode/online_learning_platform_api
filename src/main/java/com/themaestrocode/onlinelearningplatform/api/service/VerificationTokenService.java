package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.entity.VerificationToken;
import com.themaestrocode.onlinelearningplatform.api.error.EntityNotFoundException;

public interface VerificationTokenService {

    VerificationToken saveVerificationToken(VerificationToken verificationToken);

    VerificationToken findByToken(String token) throws EntityNotFoundException;

    VerificationToken generateVerificationToken(User user);

    void deleteVerificationTokenById(Long tokenId);
}
