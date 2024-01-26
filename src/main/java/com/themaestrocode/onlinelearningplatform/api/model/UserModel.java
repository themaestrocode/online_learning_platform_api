package com.themaestrocode.onlinelearningplatform.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserModel {

    private String firstName;
    private String lastName;
    private String userEmail;
    private String phoneNo;
    private String userPassword;
    private String matchingPassword;
}
