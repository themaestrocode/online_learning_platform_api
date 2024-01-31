package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.entity.VerificationToken;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User registerStudent(UserModel userModel);

    User registerCreator(UserModel userModel);

    User findByEmail(String email);

    String confirmRegistration(String token);

    VerificationToken generateNewVerificationToken(String oldToken);
}
