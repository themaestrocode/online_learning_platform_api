package com.themaestrocode.client.service;

import com.themaestrocode.client.entity.User;
import com.themaestrocode.client.entity.VerificationToken;
import com.themaestrocode.client.model.UserModel;
import com.themaestrocode.client.repository.UserRepository;
import com.themaestrocode.client.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserServiceImpl implements UserService{

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User registerStudent(UserModel userModel) {
        //validating email
        boolean isValidEmail = validateEmail(userModel.getUserEmail());
        if(!isValidEmail) throw new IllegalStateException(String.format("The provided email '%s' is not valid.", userModel.getUserEmail()));

        boolean emailExists = checkIfEmailExists(userModel.getUserEmail());
        if(emailExists) throw new IllegalStateException(String.format("The provided email '%s' is registered with another account", userModel.getUserEmail()));

        //validating password
        int isValidPassword = validatePassword(userModel.getUserPassword(), userModel.getMatchingPassword());
        if(isValidPassword < 0) throw new IllegalStateException("password too short! Password must not be less than 8 characters.");
        else if(isValidPassword == 0) throw new IllegalStateException(String.format("passwords do not match!"));

        User user = new User();
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setUserEmail(userModel.getUserEmail());
        user.setUserPassword(passwordEncoder.encode(userModel.getUserPassword()));
        user.setPhoneNo(userModel.getPhoneNo());
        user.setUserRole(UserRole.STUDENT);

        return userRepository.save(user);
    }

    @Override
    public User registerCreator(UserModel userModel) {
        //validating email
        boolean isValidEmail = validateEmail(userModel.getUserEmail());
        if(!isValidEmail) throw new IllegalStateException(String.format("The provided email '%s' is not valid.", userModel.getUserEmail()));

        boolean emailExists = checkIfEmailExists(userModel.getUserEmail());
        if(emailExists) throw new IllegalStateException(String.format("The provided email '%s' is registered with another account", userModel.getUserEmail()));

        //validating password
        int isValidPassword = validatePassword(userModel.getUserPassword(), userModel.getMatchingPassword());
        if(isValidPassword < 0) throw new IllegalStateException("password too short! Password must not be less than 8 characters.");
        else if(isValidPassword == 0) throw new IllegalStateException(String.format("passwords do not match!"));

        User user = new User();
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setUserEmail(userModel.getUserEmail());
        user.setUserPassword(passwordEncoder.encode(userModel.getUserPassword()));
        user.setPhoneNo(userModel.getPhoneNo());
        user.setUserRole(UserRole.CREATOR);

        return userRepository.save(user);
    }

    @Override
    public User findByUserEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    @Transactional
    @Override
    public String confirmRegistration(String token) {
        VerificationToken verificationToken = validateVerificationToken(token);

        verificationTokenService.updateVerificationTokenConfirmationTime(token, LocalDateTime.now());
        userRepository.enableUser(verificationToken.getUser().getUserEmail());

        return "You have successfully verified your account";
    }

    public VerificationToken validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);

        if(verificationToken == null) {
            throw new IllegalStateException("Token not found!");
        } else if(verificationToken.getConfirmationTime() != null) {
            throw new IllegalStateException("You have already confirmed your registration.");
        } else if(verificationToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            verificationTokenService.deleteVerificationToken(verificationToken);
            throw new IllegalStateException("Verification token already expired! Please, register again and be sure to verify your account within 30 minutes.");
        }

        return verificationToken;
    }

    private boolean validateEmail(String email) {
        if (email == null) return false;

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    private boolean checkIfEmailExists(String email) {
        User user = findByUserEmail(email);

        if(user == null) return false;

        return true;
    }

    private int validatePassword(String password, String matchingPassword) {
        if(password.length() < 8) return -1;
        else if(!password.equals(matchingPassword)) return 0;

        return 1;
    }

}
