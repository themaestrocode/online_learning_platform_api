package com.themaestrocode.onlinelearningplatform.api.service;


import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.entity.VerificationToken;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;

public interface UserService {
    User registerStudent(UserModel userModel);

    User registerCreator(UserModel userModel);

    User findByUserEmail(String email);

    String confirmRegistration(String token);

    VerificationToken generateNewVerificationToken(String oldToken);
}
