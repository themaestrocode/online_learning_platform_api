package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.model.CourseModel;

import java.util.List;

public interface CourseService {

    Course createCourse(CourseModel courseModel, User creator);

    List<Course> fetchAllCourses(Long creatorId);

    List<Course> fetchCourseByTitle(String courseName, Long creatorId);

    Course fetchCourseById(Long courseId, Long creatorId);

    void deleteCourseById(Long courseId, Long creatorId);

    Course updateCourse(Long courseId, CourseModel courseModel, Long creatorId);

    Course fetchCourseById(Long courseId);
}
