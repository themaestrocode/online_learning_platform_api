package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.entity.VerificationToken;
import com.themaestrocode.onlinelearningplatform.api.error.EntityNotFoundException;
import com.themaestrocode.onlinelearningplatform.api.error.InvalidEmailException;
import com.themaestrocode.onlinelearningplatform.api.error.InvalidPasswordException;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;
import com.themaestrocode.onlinelearningplatform.api.repository.UserRepository;
import com.themaestrocode.onlinelearningplatform.api.utility.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByEmail(email);

        if(userDetails == null) throw new UsernameNotFoundException(String.format("user with the provided email: '%s' does not exist!", email));

        return userRepository.findByEmail(email);
    }

    @Override
    public User registerStudent(UserModel userModel) throws InvalidEmailException, InvalidPasswordException {
        validateEmail(userModel.getUserEmail()); //validating email
        validatePassword(userModel.getUserPassword(), userModel.getMatchingPassword()); //validating password

        User student = new User();
        student.setFirstName(userModel.getFirstName());
        student.setLastName(userModel.getLastName());
        student.setEmail(userModel.getUserEmail());
        student.setPassword(passwordEncoder.encode(userModel.getUserPassword()));
        student.setDateJoined(LocalDateTime.now());
        student.setUserRole(UserRole.ROLE_STUDENT);

        return userRepository.save(student);
    }

    @Override
    public User registerCreator(UserModel userModel) throws InvalidEmailException, InvalidPasswordException {
        validateEmail(userModel.getUserEmail());
        validatePassword(userModel.getUserPassword(), userModel.getMatchingPassword());

        User creator = new User();
        creator.setFirstName(userModel.getFirstName());
        creator.setLastName(userModel.getLastName());
        creator.setEmail(userModel.getUserEmail());
        creator.setPassword(passwordEncoder.encode(userModel.getUserPassword()));
        creator.setDateJoined(LocalDateTime.now());
        creator.setUserRole(UserRole.ROLE_CREATOR);

        return userRepository.save(creator);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public String confirmRegistration(String token) throws EntityNotFoundException {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);

        if(verificationToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            return "Verification token expired! Generate a new token and be sure to verify your account within 12 hours.";
        }

        userRepository.enableUser(verificationToken.getUser().getEmail());
        verificationTokenService.deleteVerificationTokenById(verificationToken.getTokenId());

        return "You have successfully verified your account";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) throws EntityNotFoundException {
        VerificationToken verificationToken = verificationTokenService.findByToken(oldToken);

        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setCreationTime(LocalDateTime.now());
        verificationToken.setExpiryTime(LocalDateTime.now().plusHours(12));

        verificationTokenService.saveVerificationToken(verificationToken);

        return verificationToken;
    }

    @Override
    public User detectUser(HttpServletRequest request) {
        String userEmail = request.getUserPrincipal().getName();

        User user = userRepository.findByEmail(userEmail);

        if(user == null) throw new UsernameNotFoundException("User could not be determined!");

        return user;
    }

    @Override
    public void deleteUserAccountById(Long userId) {
        userRepository.deleteById(userId);
    }

    private boolean validateEmail(String email) throws InvalidEmailException {
        Matcher matcher = EMAIL_PATTERN.matcher(email);

        if (email == null || !matcher.matches()) {
            throw new InvalidEmailException("Invalid email!");
        }
        else if(userRepository.findByEmail(email) != null) {
            throw new InvalidEmailException("Email already registered with another account!");
        }

        return true;
    }

    private boolean validatePassword(String password, String matchingPassword) throws InvalidPasswordException {
        if(password.length() < 8) throw new InvalidPasswordException("Password too short! At least 8 characters long.");
        else if(!password.equals(matchingPassword)) throw new InvalidPasswordException("Passwords do not match!");

        return true;
    }

}
