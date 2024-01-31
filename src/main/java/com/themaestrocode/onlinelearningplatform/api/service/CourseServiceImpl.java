package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.model.CourseModel;
import com.themaestrocode.onlinelearningplatform.api.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

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
    public List<Course> fetchAllCourses(Long creatorId) {
        //return courseRepository.fetchAllCreatorCourses(creatorId);
        return courseRepository.findByCreatorUserId(creatorId);
    }

    @Override
    public List<Course> fetchCourseByTitle(String courseName, Long creatorId) {
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
    public Course fetchCourseById(Long courseId, Long creatorId) {
        return courseRepository.findByCourseIdAndCreatorUserId(courseId, creatorId);
        //return courseRepository.fetchCreatorCourseById(courseId, creatorId);
    }

    @Override
    @Transactional
    public void deleteCourseById(Long courseId, Long creatorId) {
        courseRepository.deleteByCourseIdAndCreatorUserId(courseId, creatorId);
        //courseRepository.deleteCreatorCourseById(courseId, creatorId);
    }

    @Override
    public Course updateCourse(Long courseId, CourseModel courseModel, Long creatorId) {
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
    public Course fetchCourseById(Long courseId) {
        return courseRepository.findById(courseId).get();
    }
}
