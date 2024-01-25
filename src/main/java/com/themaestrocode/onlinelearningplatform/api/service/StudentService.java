package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Student;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;

public interface StudentService {
    Student registerStudent(UserModel userModel);
}
