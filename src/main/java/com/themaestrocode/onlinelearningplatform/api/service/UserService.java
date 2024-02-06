package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.entity.VerificationToken;
import com.themaestrocode.onlinelearningplatform.api.error.EntityNotFoundException;
import com.themaestrocode.onlinelearningplatform.api.error.InvalidEmailException;
import com.themaestrocode.onlinelearningplatform.api.error.InvalidPasswordException;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User registerStudent(UserModel userModel) throws InvalidEmailException, InvalidPasswordException;

    User registerCreator(UserModel userModel) throws InvalidEmailException, InvalidPasswordException;

    User findByEmail(String email);

    String confirmRegistration(String token) throws EntityNotFoundException;

    VerificationToken generateNewVerificationToken(String oldToken) throws EntityNotFoundException;

    User detectUser(HttpServletRequest request);

    void deleteUserAccountById(Long userId);
}
