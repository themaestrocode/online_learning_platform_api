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
@RequestMapping("/creator/api/v1")
public class CreatorController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;
    @Autowired
    private ContentService contentService;


    @GetMapping
    public String viewProfile(HttpServletRequest request) {
        User creator = detectCreator(request);

        return String.format("Welcome to your profile, %s", creator.getFirstName() + " " + creator.getLastName());
    }

    @PostMapping("/courses")
    public String createCourse(@RequestBody CourseModel courseModel, HttpServletRequest request) {
        User creator = detectCreator(request);

        Course course = courseService.createCourse(courseModel, creator);

        return "Course successfully added!";
    }

    @GetMapping("/courses")
    public List<Course> fetchAllCreatorCourses(HttpServletRequest request) {
        User creator = detectCreator(request);

        return courseService.fetchAllCreatorCourses(creator.getUserId());
    }

    @GetMapping("/courses/course-name/{courseName}")
    public List<Course> fetchCreatorCourseByTitle(@PathVariable("courseName") String courseName, HttpServletRequest request) {
        User creator = detectCreator(request);

        return courseService.fetchCreatorCourseByTitle(courseName, creator.getUserId());
    }

    @GetMapping("/courses/{courseId}")
    public Course fetchCreatorCourseById(@PathVariable("courseId") Long courseId, HttpServletRequest request) {
        User creator = detectCreator(request);

        return courseService.fetchCreatorCourseById(courseId, creator.getUserId());
    }

    @DeleteMapping("/courses/{courseId}")
    public String deleteCreatorCourseById(@PathVariable("courseId") Long courseId, HttpServletRequest request) {
        User creator = detectCreator(request);
        courseService.deleteCreatorCourseById(courseId, creator.getUserId());

        return "Course successfully deleted!";
    }

    @PutMapping("/courses/{courseId}")
    public Course updateCreatorCourse(@PathVariable("courseId") Long courseId, @RequestBody CourseModel courseModel, HttpServletRequest request) {
        User creator = detectCreator(request);

        Course course = courseService.updateCreatorCourse(courseId, courseModel, creator.getUserId());

        return course;
    }

    @PostMapping("/courses/contents")
    public Content addCourseContent(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("courseId") Long courseId, HttpServletRequest request) throws IOException {

        User creator = detectCreator(request);

        Course course = courseService.fetchCreatorCourseById(courseId, creator.getUserId());

        ContentModel contentModel = new ContentModel(name, file.getBytes(), description, course);

        return contentService.addCourseContent(contentModel);
    }

    @GetMapping("/courses/{courseId}/contents")
    public List<Content> fetchAllContentUnderACourse(@PathVariable("courseId") Long courseId, HttpServletRequest request) {
        User creator = detectCreator(request);

        Course course = courseService.fetchCreatorCourseById(courseId, creator.getUserId());

        return contentService.fetchAllContentUnderACourse(courseId);
    }

    private User detectCreator(HttpServletRequest request) {
        String creatorEmail = request.getUserPrincipal().getName();

        User creator = userService.findByEmail(creatorEmail);

        if(creator == null) throw new RuntimeException("Creator could not be determined");

        return creator;
    }

}
