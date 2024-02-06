package com.themaestrocode.onlinelearningplatform.api.controller;

import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.entity.VerificationToken;
import com.themaestrocode.onlinelearningplatform.api.error.EntityNotFoundException;
import com.themaestrocode.onlinelearningplatform.api.error.InvalidEmailException;
import com.themaestrocode.onlinelearningplatform.api.error.InvalidPasswordException;
import com.themaestrocode.onlinelearningplatform.api.model.UserAuthModel;
import com.themaestrocode.onlinelearningplatform.api.service.UserService;
import com.themaestrocode.onlinelearningplatform.api.service.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(@RequestBody UserAuthModel userAuthModel, HttpServletRequest request) throws InvalidEmailException, InvalidPasswordException {
        User user = userService.detectUser(request);

        if(!user.getEmail().equals(userAuthModel.getEmail())) {
            throw new InvalidEmailException("the provided email is incorrect!");
        }

        if(!passwordEncoder.matches(userAuthModel.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("incorrect password!");
        }

        VerificationToken verificationToken = verificationTokenService.generateVerificationToken(user);
        //verificationTokenService.saveVerificationToken(verificationToken);

        String accountDeletionLink = String.format("%s/delete-account/confirm?token=%s", applicationUrl(request), verificationToken.getToken());
        String name = user.getFirstName() + " " + user.getLastName();
        String message = String.format("Hi, %s!\n\nClick the link below to permanently delete your account.\n" +
                "Please note that this link is valid for only 12 hours\n%s", name, accountDeletionLink);

        System.out.println(String.format("\n\n%s\n\n", message));

        return ResponseEntity.ok("A link to delete your account has been sent to your email.");
    }

    @DeleteMapping("/delete-account/confirm")
    public ResponseEntity<String> confirmAccountDeleteOperation(@RequestParam("token") String token) throws EntityNotFoundException {
        VerificationToken verificationToken = verificationTokenService.findByToken(token);

        if(verificationToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.ok("Token expired! 12 hours exceeded.");
        }

        verificationTokenService.deleteVerificationTokenById(verificationToken.getTokenId());
        userService.deleteUserAccountById(verificationToken.getUser().getUserId());

        return ResponseEntity.ok("Account successfully deleted!");
    }

    private String applicationUrl(HttpServletRequest request) {
        return String.format("http://%s:%d/api/v1/user%s", request.getServerName(), request.getServerPort(), request.getContextPath());
    }
}
