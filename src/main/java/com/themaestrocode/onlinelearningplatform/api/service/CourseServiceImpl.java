package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Content;
import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.model.ContentModel;
import com.themaestrocode.onlinelearningplatform.api.model.CourseModel;
import com.themaestrocode.onlinelearningplatform.api.repository.CourseRepository;
import com.themaestrocode.onlinelearningplatform.api.utility.CourseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private ContentService contentService;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course createCourse(CourseModel courseModel, User creator) {
        Course course = new Course();

        course.setTitle(courseModel.getTitle());
        course.setDescription(courseModel.getDescription());
        course.setCourseUrl(courseModel.getCourseUrl());
        course.setCourseType(courseModel.getCourseType());
        course.setCreator(creator);

        return courseRepository.save(course);
    }

    @Override
    public List<Course> fetchAllCreatorCourses(Long creatorId) {
        //return courseRepository.fetchAllCreatorCourses(creatorId);
        return courseRepository.findByCreatorUserId(creatorId);
    }

    @Override
    public List<Course> fetchCreatorCourseByTitle(String courseName, Long creatorId) {
        //List<Course> creatorCourses = courseRepository.fetchAllCreatorCourses(creatorId);
        List<Course> creatorCourses = courseRepository.findByCreatorUserId(creatorId);
        List<Course> coursesContainingCourseName = new ArrayList<>();

        for(Course course: creatorCourses) {
            if(course.getTitle().toLowerCase().contains(courseName.toLowerCase())) {
                coursesContainingCourseName.add(course);
            }
        }

        return coursesContainingCourseName;
    }

    @Override
    public Course fetchCreatorCourse(Long courseId, Long creatorId) {
        Course course = courseRepository.findByCourseIdAndCreatorUserId(courseId, creatorId);

        if(course == null) throw new NullPointerException("you do not have a course with the provided course id.");

        return course;
        //return courseRepository.fetchCreatorCourseById(courseId, creatorId);
    }

    @Override
    @Transactional
    public void deleteCreatorCourseById(Long courseId, Long creatorId) {

        if(courseRepository.findByCourseIdAndCreatorUserId(courseId, creatorId) == null) {
            throw new NullPointerException("course does not exist!");
        }
        courseRepository.deleteByCourseIdAndCreatorUserId(courseId, creatorId);
        //courseRepository.deleteCreatorCourseById(courseId, creatorId);
    }

    @Override
    public Course updateCreatorCourse(Long courseId, CourseModel courseModel, Long creatorId) {
        Course course = courseRepository.findByCourseIdAndCreatorUserId(courseId, creatorId);
        //Course course = courseRepository.fetchCreatorCourseById(courseId, creatorId);

        if(courseModel.getTitle() != null && !courseModel.getTitle().isEmpty()) {
            course.setTitle(courseModel.getTitle());
        }

        if(courseModel.getDescription() != null && !courseModel.getDescription().isEmpty()) {
            course.setDescription(courseModel.getDescription());
        }

        if(courseModel.getCourseType() != null && !courseModel.getCourseType().toString().isEmpty()) {
            course.setCourseType(courseModel.getCourseType());
        }

        if(courseModel.getCourseUrl() != null && !courseModel.getCourseUrl().isEmpty()) {
            course.setCourseUrl(courseModel.getCourseUrl());
        }

        return courseRepository.save(course);
    }

    @Override
    public List<Course> fetchAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public List<Course> fetchFreeCourses() {
        return courseRepository.findByCourseType(CourseType.FREE);
    }

    @Override
    public List<Course> fetchPaidCourses() {
        return courseRepository.findByCourseType(CourseType.PAID);
    }

    @Override
    public Course fetchCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).get();

        if(course == null) throw new NoSuchElementException("course does not exist!");

        return course;
    }

    @Override
    public void updateEnrolledStudentDetails(Course course, boolean enrolled) {
        if(enrolled) {
            course.setCurrentlyEnrolled(course.getCurrentlyEnrolled() + 1);
            course.setAllTimeEnrolled(course.getAllTimeEnrolled() + 1);
        }
        else {
            course.setCurrentlyEnrolled(course.getCurrentlyEnrolled() - 1);
        }

        courseRepository.save(course);
    }

    @Override
    public Content addCourseContent(ContentModel contentModel, Course course) {
        Content content = contentService.saveContent(contentModel);

        course.addContents().add(content);

        return content;
    }
}
