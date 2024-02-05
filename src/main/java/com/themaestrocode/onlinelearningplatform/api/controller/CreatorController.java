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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
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
    public ResponseEntity<String> viewProfile(HttpServletRequest request) {
        User creator = detectCreator(request);

        return ResponseEntity.ok(String.format("Welcome to your profile, %s %s", creator.getFirstName(), creator.getLastName()));
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody CourseModel courseModel, HttpServletRequest request) {
        User creator = detectCreator(request);

        Course course = courseService.createCourse(courseModel, creator);

        return ResponseEntity.created(URI.create("/api/v1/creator/" + course.getCourseId())).body((course));
    }

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> fetchAllCreatorCourses(HttpServletRequest request) {
        User creator = detectCreator(request);

        return ResponseEntity.ok(courseService.fetchAllCreatorCourses(creator.getUserId()));
    }

    @GetMapping("/courses/course-name/{courseName}")
    public ResponseEntity<List<Course>> fetchCreatorCourseByTitle(@PathVariable("courseName") String courseName, HttpServletRequest request) {
        User creator = detectCreator(request);

        return ResponseEntity.ok(courseService.fetchCreatorCourseByTitle(courseName, creator.getUserId()));
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<Course> fetchCreatorCourseById(@PathVariable("courseId") Long courseId, HttpServletRequest request) {
        User creator = detectCreator(request);

        return ResponseEntity.ok(courseService.fetchCreatorCourse(courseId, creator.getUserId()));
    }

    @DeleteMapping("/courses/{courseId}")
    public ResponseEntity<String> deleteCreatorCourseById(@PathVariable("courseId") Long courseId, HttpServletRequest request) {
        User creator = detectCreator(request);
        courseService.deleteCreatorCourseById(courseId, creator.getUserId());

        return ResponseEntity.ok("Course successfully deleted!");
    }

    @PutMapping("/courses/{courseId}")
    public ResponseEntity<Course> updateCreatorCourse(@PathVariable("courseId") Long courseId, @RequestBody CourseModel courseModel, HttpServletRequest request) {
        User creator = detectCreator(request);

        Course course = courseService.updateCreatorCourse(courseId, courseModel, creator.getUserId());

        return ResponseEntity.ok(course);
    }

    @PostMapping("/courses/{courseId}/contents")
    public ResponseEntity<Content> addCourseContent(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @PathVariable("courseId") Long courseId, HttpServletRequest request) throws IOException {

        User creator = detectCreator(request);

        Course course = courseService.fetchCreatorCourse(courseId, creator.getUserId());

        ContentModel contentModel = new ContentModel(name, file.getBytes(), description, course);
        Content content = courseService.addCourseContent(contentModel, course);
        String uri = String.format("/api/v1/courses/%d/contents/%d", courseId, content.getContentId());

        return ResponseEntity.created(URI.create(uri)).body(content);
    }

    @GetMapping("/courses/{courseId}/contents")
    public ResponseEntity<List<Content>> fetchAllContentUnderACourse(@PathVariable("courseId") Long courseId, HttpServletRequest request) {
        User creator = detectCreator(request);

        Course course = courseService.fetchCreatorCourse(courseId, creator.getUserId());

        return ResponseEntity.ok(contentService.fetchAllContentUnderACourse(courseId));
    }

    @DeleteMapping("/courses/{courseId}/contents/{contentId}")
    public ResponseEntity<String> deleteContent(@PathVariable("courseId") Long courseId, @PathVariable("contentId") Long contentId) {
        Content content = contentService.fetchContent(contentId, courseId);

        contentService.deleteContentById(content.getContentId());

        return ResponseEntity.ok("Content successfully deleted");
    }

    private User detectCreator(HttpServletRequest request) {
        String creatorEmail = request.getUserPrincipal().getName();

        User creator = userService.findByEmail(creatorEmail);

        if(creator == null) throw new RuntimeException("Creator could not be determined");

        return creator;
    }

}
