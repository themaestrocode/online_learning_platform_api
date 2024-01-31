package com.themaestrocode.onlinelearningplatform.api.controller;

import com.themaestrocode.onlinelearningplatform.api.entity.Content;
import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.model.ContentModel;
import com.themaestrocode.onlinelearningplatform.api.model.CourseModel;
import com.themaestrocode.onlinelearningplatform.api.service.ContentService;
import com.themaestrocode.onlinelearningplatform.api.service.CourseService;
import com.themaestrocode.onlinelearningplatform.api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/creator")
public class CreatorController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private ContentService contentService;


    @GetMapping
    public String viewProfile() {
        return "Welcome to your profile";
    }

    @PostMapping("/courses")
    public String createCourse(@RequestBody CourseModel courseModel, HttpServletRequest request) {
        User creator = detectCreator(request);

        Course course = courseService.createCourse(courseModel, creator);

        return "Course successfully added!";
    }

    @GetMapping("/courses")
    public List<Course> fetchAllCourses(HttpServletRequest request) {
        User creator = detectCreator(request);

        return courseService.fetchAllCourses(creator.getUserId());
    }

    @GetMapping("/courses/course-name/{courseName}")
    public List<Course> fetchCourseByTitle(@PathVariable("courseName") String courseName, HttpServletRequest request) {
        User creator = detectCreator(request);

        return courseService.fetchCourseByTitle(courseName, creator.getUserId());
    }

    @GetMapping("/courses/{courseId}")
    public Course fetchCourseById(@PathVariable("courseId") Long courseId, HttpServletRequest request) {
        User creator = detectCreator(request);

        return courseService.fetchCourseById(courseId, creator.getUserId());
    }

    @DeleteMapping("/courses/{courseId}")
    public String deleteCourseById(@PathVariable("courseId") Long courseId, HttpServletRequest request) {
        User creator = detectCreator(request);
        courseService.deleteCourseById(courseId, creator.getUserId());

        return "Course successfully deleted!";
    }

    @PutMapping("/courses/{courseId}")
    public Course updateCourse(@PathVariable("courseId") Long courseId, @RequestBody CourseModel courseModel,HttpServletRequest request) {
        User creator = detectCreator(request);

        Course course = courseService.updateCourse(courseId, courseModel, creator.getUserId());

        return course;
    }

    @PostMapping("/courses/contents")
    public Content addContent (@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
                               @RequestParam("description") String description,
                               @RequestParam("courseId") Long courseId) throws IOException {

        Course course = courseService.fetchCourseById(courseId);

        ContentModel contentModel = new ContentModel(name, file.getBytes(), description, course);

        return contentService.saveContent(contentModel);
    }

    @GetMapping("/courses/{courseId}/contents")
    public List<Content> fetchAllContentsUnderACourse(@PathVariable("courseId") Long courseId) {
        return contentService.fetchAllContentsUnderACourse(courseId);
    }

    private User detectCreator(HttpServletRequest request) {
        String creatorEmail = request.getUserPrincipal().getName();

        User creator = userService.findByEmail(creatorEmail);

        if(creator == null) throw new RuntimeException("Creator could not be determined");

        return creator;
    }

}
