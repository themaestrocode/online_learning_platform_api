package com.themaestrocode.onlinelearningplatform.api.controller;

import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.entity.Enrollment;
import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.service.ContentService;
import com.themaestrocode.onlinelearningplatform.api.service.CourseService;
import com.themaestrocode.onlinelearningplatform.api.service.EnrollmentService;
import com.themaestrocode.onlinelearningplatform.api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student/api/v1")
public class StudentController {

    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private ContentService contentService;
    @Autowired
    private EnrollmentService enrollmentService;


    @GetMapping
    public String viewProfile(HttpServletRequest request) {
        User student = detectStudent(request);

        return String.format("Welcome to your profile, %s", student.getFirstName() + " " + student.getLastName());
    }

//    @GetMapping("/courses")
//    public void fetchAllCourses(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/api/v1/courses");
//    }
//
//    @GetMapping("/free-courses")
//    public void fetchFreeCourses(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/api/v1/courses/free");
//    }
//
//    @GetMapping("/paid-courses")
//    public void fetchPaidCourses(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/api/v1/courses/paid");
//    }

    @PostMapping("/enroll")
    public Course enroll(@RequestParam Long courseId, HttpServletRequest request) {
        Course course = courseService.fetchCourseById(courseId);

        User student = detectStudent(request);

        Enrollment enrollment = enrollmentService.enrollStudent(student, course);

        return enrollment.getCourse();
    }

    @GetMapping("/courses")
    public List<Course> fetchAllCoursesEnrolledForByStudent(HttpServletRequest request) {
        User student = detectStudent(request);

        return enrollmentService.fetchAllEnrollmentsByStudent(student.getUserId());
    }

    @GetMapping("/courses/{courseId}")
    public Course fetchEnrolledCourse(@PathVariable("courseId") Long courseId, HttpServletRequest request) {
        User student = detectStudent(request);

        return enrollmentService.fetchEnrolledCourse(courseId, student.getUserId());
    }

    private User detectStudent(HttpServletRequest request) {
        String studentEmail = request.getUserPrincipal().getName();

        User student = userService.findByEmail(studentEmail);

        if(student == null) throw new RuntimeException("Student could not be determined");

        return student;
    }
}
