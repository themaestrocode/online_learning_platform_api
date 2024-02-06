package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.entity.Enrollment;
import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.error.StudentAlreadyEnrolledException;
import com.themaestrocode.onlinelearningplatform.api.error.StudentNotEnrolledException;
import com.themaestrocode.onlinelearningplatform.api.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public Enrollment enrollStudent(User student, Course course) throws StudentAlreadyEnrolledException {
        boolean alreadyEnrolled = checkIfAlreadyEnrolled(course.getCourseId(), student.getUserId());

        if(alreadyEnrolled) throw new StudentAlreadyEnrolledException("You have previously enrolled for this course!");

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDateTime.now());

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment fetchEnrollmentByCourseIdAndStudentId(Long courseId, Long userId) throws StudentNotEnrolledException {

        Enrollment enrollment = enrollmentRepository.findByCourseCourseIdAndStudentUserId(courseId, userId);

        if(enrollment == null) throw new StudentNotEnrolledException("You are not currently enrolled for this course!");

        return enrollment;
    }

    @Override
    public void cancelEnrollment(Long enrollmentId) {
        enrollmentRepository.deleteById(enrollmentId);
    }

    @Override
    public List<Enrollment> fetchEnrollmentsByStudentId(Long userId) {
        return enrollmentRepository.findByStudentUserId(userId);
    }

    private boolean checkIfAlreadyEnrolled(Long courseId, Long stuentId) {
        Enrollment enrollment = enrollmentRepository.findByCourseCourseIdAndStudentUserId(courseId, stuentId);

        if(enrollment != null) return true;

        return false;
    }
}
