package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.EmailValidator;
import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;
import com.themaestrocode.onlinelearningplatform.api.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private UserService userService;

    public String registerAsStudent(UserModel userModel) {
        boolean isValidEmail = emailValidator.test(userModel.getUserEmail());
        boolean passwordMatch = userModel.getUserPassword().equals(userModel.getMatchingPassword());

        if(!isValidEmail) {
            throw new IllegalStateException(String.format("%s not a valid email", userModel.getUserEmail()));
        }

        if(!passwordMatch) {
            throw new RuntimeException(String.format("Passwords do not match! (%s) != (%s)", userModel.getUserPassword(), userModel.getMatchingPassword()));
        }

        User user = new User();
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setUserEmail(userModel.getUserEmail());
        user.setUserPassword(userModel.getUserPassword());
        user.setPhoneNo(userModel.getPhoneNo());
        user.setUserRole(UserRole.STUDENT);

        return userService.signUpUser(user);
    }

    public String registerAsCreator(UserModel userModel) {
        boolean isValidEmail = emailValidator.test(userModel.getUserEmail());
        boolean passwordMatch = userModel.getUserPassword().equals(userModel.getMatchingPassword());

        if(!isValidEmail) {
            throw new IllegalStateException(String.format("%s not a valid email", userModel.getUserEmail()));
        }

        if(!passwordMatch) {
            throw new RuntimeException(String.format("Passwords do not match! (%s) != (%s)", userModel.getUserPassword(), userModel.getMatchingPassword()));
        }

        User user = new User();
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setUserEmail(userModel.getUserEmail());
        user.setUserPassword(userModel.getUserPassword());
        user.setPhoneNo(userModel.getPhoneNo());
        user.setUserRole(UserRole.CREATOR);

        return userService.signUpUser(user);
    }
}
