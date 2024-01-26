package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.utility.EmailValidator;
import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.entity.VerificationToken;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;
import com.themaestrocode.onlinelearningplatform.api.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private UserService userService;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private EmailService emailService;
    private String linkEndPoint = "http://localhost:8080/api/v1/registration/confirm?token=";

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

        String token = userService.signUpUser(user);
        String verificationLink = linkEndPoint + token;
        String userName = user.getFirstName() + " " + user.getLastName();
        String message = String.format("Hi, %s! Click the link below to verify your email. It will expire in 20 minutes\n", userName);

        emailService.sendEmail(user.getUserEmail(), message + verificationLink);

        return verificationLink;
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

        String token = userService.signUpUser(user);
        String link = linkEndPoint + token;
        String userName = user.getFirstName() + " " + user.getLastName();

        emailService.sendEmail(user.getUserEmail(), buildEmail(userName, link));

        return token;
    }

    @Transactional // added this annotation because of the update statements in this method
    public ResponseEntity<String> confirmRegistration(String token) {
        VerificationToken verificationToken = verificationTokenExistsAndIsNotConfirmedAndIsNotExpired(token);

        if(verificationToken != null) {
            verificationTokenService.setConfirmationTime(token);
            userService.enableUser(verificationToken.getUser().getUserEmail());
        }

        return ResponseEntity.status(HttpStatus.OK).body("Account verification successful! Your email has been confirmed.");
    }

    private VerificationToken verificationTokenExistsAndIsNotConfirmedAndIsNotExpired(String token) {
        //check if the token passed in the url exist in the db.
        VerificationToken verificationToken = verificationTokenService.findVerificationToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found!"));

        /*
        the confirmation time represents the time the user confirmed their registration via email.
        if it is null, then the user hasn't confirmed the registration and their account has not been enabled.
         */
        if(verificationToken.getConfirmationTime() != null) {
            throw new IllegalStateException("you have already confirmed your registration.");
        }

        LocalDateTime expiryTime = verificationToken.getExpiryTime(); // get the expiry time of the token

        /*
        check if the expiry time is before the current time. if before, then the 20 minutes validity of the token has elapsed.
        hence, the user cannot be validated, they will need to register again.
         */
        if(expiryTime.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Account verification token expired! Verification must be done within 20 minutes.");
        }

        return verificationToken;
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
