package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Content;
import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.error.EntityNotFoundException;
import com.themaestrocode.onlinelearningplatform.api.error.StudentAlreadyEnrolledException;
import com.themaestrocode.onlinelearningplatform.api.error.StudentNotEnrolledException;
import com.themaestrocode.onlinelearningplatform.api.model.ContentModel;
import com.themaestrocode.onlinelearningplatform.api.model.CourseModel;

import java.util.List;

public interface CourseService {

    List<Course> fetchAllCreatorCourses(Long creatorId);

    List<Course> fetchCreatorCourseByTitle(String text, Long creatorId);

    Course fetchCreatorCourseById(Long courseId, Long creatorId) throws EntityNotFoundException;

    void deleteCreatorCourseById(Long courseId, Long creatorId) throws EntityNotFoundException;

    Course updateCreatorCourse(Long courseId, CourseModel courseModel, Long creatorId) throws EntityNotFoundException;

    List<Course> fetchAllCourses();

    List<Course> fetchFreeCourses();

    List<Course> fetchPaidCourses();

    Course fetchCourseById(Long courseId) throws EntityNotFoundException;

    Content addCourseContent(ContentModel contentModel, Course course);

    List<Course> fetchEnrolledCourseByTitle(Long userId, String text);

    Course fetchEnrolledCourseById(Long courseId, Long userId) throws StudentNotEnrolledException;

    List<Course> fetchEnrolledCourseList(Long userId);

    void cancelCourseEnrollment(Long courseId, Long userId) throws StudentNotEnrolledException;

    void enroll(User student, Course course) throws StudentAlreadyEnrolledException;

    Course createCourse(CourseModel courseModel, User creator);
}
