package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.entity.Enrollment;
import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.error.StudentAlreadyEnrolledException;
import com.themaestrocode.onlinelearningplatform.api.error.StudentNotEnrolledException;

import java.util.List;

public interface EnrollmentService {

    Enrollment enrollStudent(User student, Course course) throws StudentAlreadyEnrolledException;

    Enrollment fetchEnrollmentByCourseIdAndStudentId(Long courseId, Long userId) throws StudentNotEnrolledException;

    void cancelEnrollment(Long enrollmentId);

    List<Enrollment> fetchEnrollmentsByStudentId(Long userId);
}
