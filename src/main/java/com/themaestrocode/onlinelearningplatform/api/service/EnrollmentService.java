package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.entity.Enrollment;
import com.themaestrocode.onlinelearningplatform.api.entity.User;

import java.util.List;

public interface EnrollmentService {

    Enrollment enrollStudent(User student, Course course);

    Course fetchEnrolledCourse(Long courseId, Long studentId);

    List<Course> fetchCoursesEnrolledForByStudent(Long studentId);

    Enrollment fetchEnrollmentByCourseIdAndStudentId(Long courseId, Long userId);


    void cancelEnrollment(Long enrollmentId);
}
