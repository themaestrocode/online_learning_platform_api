package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Content;
import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.model.ContentModel;
import com.themaestrocode.onlinelearningplatform.api.model.CourseModel;

import java.util.List;

public interface CourseService {

    Course createCourse(CourseModel courseModel, User creator);

    List<Course> fetchAllCreatorCourses(Long creatorId);

    List<Course> fetchCreatorCourseByTitle(String courseName, Long creatorId);

    Course fetchCreatorCourse(Long courseId, Long creatorId);

    void deleteCreatorCourseById(Long courseId, Long creatorId);

    Course updateCreatorCourse(Long courseId, CourseModel courseModel, Long creatorId);

    List<Course> fetchAllCourses();

    List<Course> fetchFreeCourses();

    List<Course> fetchPaidCourses();

    Course fetchCourseById(Long courseId);

    void updateEnrolledStudentDetails(Course course, boolean enrolled);

    Content addCourseContent(ContentModel contentModel, Course course);
}
