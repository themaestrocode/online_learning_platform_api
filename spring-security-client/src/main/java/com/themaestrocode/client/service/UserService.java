package com.themaestrocode.client.service;

import com.themaestrocode.client.entity.User;
import com.themaestrocode.client.model.UserModel;

public interface UserService {
    User registerStudent(UserModel userModel);

    User registerCreator(UserModel userModel);

    User findByUserEmail(String email);

    String confirmRegistration(String token);
}
