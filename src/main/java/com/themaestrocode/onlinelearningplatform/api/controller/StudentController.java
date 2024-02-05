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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
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
    public ResponseEntity<String> viewProfile(HttpServletRequest request) {
        User student = detectStudent(request);

        return ResponseEntity.ok(String.format("Welcome to your profile, %s %s", student.getFirstName(), student.getLastName()));
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
    public ResponseEntity<Course> enroll(@RequestParam Long courseId, HttpServletRequest request) {
        Course course = courseService.fetchCourseById(courseId);

        User student = detectStudent(request);

        Enrollment enrollment = enrollmentService.enrollStudent(student, course);

        return ResponseEntity.created(URI.create("/api/v1/student/courses/" + enrollment.getCourse().getCourseId())).body(enrollment.getCourse());
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<String> cancelEnrollment(@PathVariable("courseId") Long courseId, HttpServletRequest request) {
        User student = detectStudent(request);

        Enrollment enrollment = enrollmentService.fetchEnrollmentByCourseIdAndStudentId(courseId, student.getUserId());
        enrollmentService.cancelEnrollment(enrollment.getEnrollmentId());

        return ResponseEntity.ok("You have successfully de-enrolled for the course: " + enrollment.getCourse().getTitle());
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> fetchCoursesEnrolledForByStudent(HttpServletRequest request) {
        User student = detectStudent(request);

        return ResponseEntity.ok(enrollmentService.fetchCoursesEnrolledForByStudent(student.getUserId()));
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<Course> fetchEnrolledCourse(@PathVariable("courseId") Long courseId, HttpServletRequest request) {
        User student = detectStudent(request);

        return ResponseEntity.ok(enrollmentService.fetchEnrolledCourse(courseId, student.getUserId()));
    }

    private User detectStudent(HttpServletRequest request) {
        String studentEmail = request.getUserPrincipal().getName();

        User student = userService.findByEmail(studentEmail);

        if(student == null) throw new RuntimeException("Student could not be determined");

        return student;
    }
}
