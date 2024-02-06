package com.themaestrocode.onlinelearningplatform.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthModel {

    private String email;
    private String password;


}
