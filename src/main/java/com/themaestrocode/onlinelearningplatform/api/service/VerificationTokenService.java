package com.themaestrocode.onlinelearningplatform.api.service;


import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.entity.VerificationToken;

import java.time.LocalDateTime;

public interface VerificationTokenService {

    VerificationToken saveVerificationToken(VerificationToken verificationToken);

    VerificationToken validateVerificationToken(String token);

    void updateVerificationTokenConfirmationTime(String token, LocalDateTime now);

    VerificationToken findByToken(String token);

    void deleteVerificationToken(VerificationToken verificationToken);

    VerificationToken generateVerificationToken(User user);
}
