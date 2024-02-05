package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.entity.Enrollment;
import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Override
    public Enrollment enrollStudent(User student, Course course) {
        boolean alreadyEnrolled = checkIfAlreadyEnrolled(course.getCourseId(), student.getUserId());

        if(alreadyEnrolled) throw new IllegalStateException("You have previously enrolled for this course");

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDateTime.now());

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Course fetchEnrolledCourse(Long courseId, Long studentId) {
        Enrollment enrollment = enrollmentRepository.findByCourseCourseIdAndStudentUserId(courseId, studentId);

        if(enrollment == null) throw new NullPointerException("You have not registered for the requested course!");

        return enrollment.getCourse();
    }

    @Override
    public List<Course> fetchCoursesEnrolledForByStudent(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentUserId(studentId);
        List<Course> courses = new ArrayList<>();

        for(Enrollment enrollment: enrollments) {
            courses.add(enrollment.getCourse());
        }

        return courses;
    }

    @Override
    public Enrollment fetchEnrollmentByCourseIdAndStudentId(Long courseId, Long userId) {

        Enrollment enrollment = enrollmentRepository.findByCourseCourseIdAndStudentUserId(courseId, userId);

        if(enrollment == null) {
            throw new NullPointerException("You are not currently enrolled for this course!");
        }

        return enrollment;
    }

    @Override
    public void cancelEnrollment(Long enrollmentId) {
        enrollmentRepository.deleteById(enrollmentId);
    }

    private boolean checkIfAlreadyEnrolled(Long courseId, Long stuentId) {
        Enrollment enrollment = enrollmentRepository.findByCourseCourseIdAndStudentUserId(courseId, stuentId);

        if(enrollment != null) return true;

        return false;
    }
}
