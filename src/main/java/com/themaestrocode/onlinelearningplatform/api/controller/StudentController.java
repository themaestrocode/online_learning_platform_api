package com.themaestrocode.onlinelearningplatform.api.controller;

import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.error.EntityNotFoundException;
import com.themaestrocode.onlinelearningplatform.api.error.StudentAlreadyEnrolledException;
import com.themaestrocode.onlinelearningplatform.api.error.StudentNotEnrolledException;
import com.themaestrocode.onlinelearningplatform.api.service.CourseService;
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


    @GetMapping
    public ResponseEntity<String> viewProfile(HttpServletRequest request) {
        User student = userService.detectUser(request);

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
    public ResponseEntity<Course> enroll(@RequestParam Long courseId, HttpServletRequest request) throws EntityNotFoundException, StudentAlreadyEnrolledException {
        Course course = courseService.fetchCourseById(courseId);

        User student = userService.detectUser(request);

        courseService.enroll(student, course);

        return ResponseEntity.created(URI.create("/api/v1/student/courses/" + course.getCourseId())).body(course);
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<String> cancelEnrollment(@PathVariable("courseId") Long courseId, HttpServletRequest request) throws StudentNotEnrolledException {
        User student = userService.detectUser(request);

        courseService.cancelCourseEnrollment(courseId, student.getUserId());

        return ResponseEntity.ok("You have successfully de-enrolled");
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> fetchCoursesEnrolledForByStudent(HttpServletRequest request) {
        User student = userService.detectUser(request);

        List<Course> enrolledCourses = courseService.fetchEnrolledCourseList(student.getUserId());

        return ResponseEntity.ok(enrolledCourses);
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<Course> fetchEnrolledCourseById(@PathVariable("courseId") Long courseId, HttpServletRequest request) throws StudentNotEnrolledException {
        User student = userService.detectUser(request);

        Course course = courseService.fetchEnrolledCourseById(courseId, student.getUserId());

        return ResponseEntity.ok(course);
    }

    @GetMapping("/courses/course-title/{text}")
    public ResponseEntity<List<Course>> fetchEnrolledCourseByTitle(@PathVariable("text") String text, HttpServletRequest request) {
        User student = userService.detectUser(request);

        List<Course> enrolledCoursesMatchingText = courseService.fetchEnrolledCourseByTitle(student.getUserId(), text);

        return ResponseEntity.ok(enrolledCoursesMatchingText);
    }

}
