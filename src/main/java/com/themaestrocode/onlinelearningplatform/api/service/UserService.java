package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.entity.VerificationToken;
import com.themaestrocode.onlinelearningplatform.api.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenService verificationTokenService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByUserEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format("user with %s not found", email)));
    }

    public String signUpUser(User user) {
        //check if another user exists with the provided email adrress
        boolean userExists = userRepo.findByUserEmail(user.getUserEmail()).isPresent();

        if(userExists) {throw new IllegalStateException("the provided email is already taken");}

        user.setUserPassword(passwordEncoder.encode(user.getPassword())); //encode user password
        userRepo.save(user); //save user with a disabled and inaccessible account

        VerificationToken verificationToken = createVerificationToken(user); //generate verification token for user who just registered.
        verificationTokenService.saveVerificationToken(verificationToken); //save the verification token.

        // TODO: send email

        return verificationToken.getToken();
    }

    private VerificationToken createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();  // generate the token as a UUID.

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setCreationTime(LocalDateTime.now());
        verificationToken.setExpiryTime(LocalDateTime.now().plusMinutes(20)); // set the token validity to 20 minutes
        verificationToken.setUser(user);

        return verificationToken;
    }

    public void enableUser(String userEmail) {
        userRepo.enableUser(userEmail);
    }
}
