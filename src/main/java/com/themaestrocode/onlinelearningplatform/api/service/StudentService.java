package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Student;
import com.themaestrocode.onlinelearningplatform.api.model.UserModel;

import java.util.List;

public interface StudentService {
    Student registerAsStudent(UserModel userModel);

    Student loginAsStudent(Long studentId);

    Student getStudentProfileById(Long studentId);
}
