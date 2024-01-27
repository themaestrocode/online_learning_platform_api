package com.themaestrocode.onlinelearningplatform.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private String firstName;
    private String lastName;
    private String userEmail;
    private String phoneNo;
    private String userPassword;
    private String matchingPassword;
}
