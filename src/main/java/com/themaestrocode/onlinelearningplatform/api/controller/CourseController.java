package com.themaestrocode.onlinelearningplatform.api.controller;

import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;


    @GetMapping
    public List<Course> fetchAllCourses() {
        return courseService.fetchAllCourses();
    }

    @GetMapping("/free")
    public List<Course> fetchFreeCourses() {
        return courseService.fetchFreeCourses();
    }

    @GetMapping("/paid")
    public List<Course> fetchPaidCourses() {
        return courseService.fetchPaidCourses();
    }

    @GetMapping("/{courseId}")
    public Course fetchCourseById(@PathVariable("courseId") Long courseId) {
        return courseService.fetchCourseById(courseId);
    }
}
