package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Content;
import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.entity.Enrollment;
import com.themaestrocode.onlinelearningplatform.api.entity.User;
import com.themaestrocode.onlinelearningplatform.api.error.EntityNotFoundException;
import com.themaestrocode.onlinelearningplatform.api.error.StudentAlreadyEnrolledException;
import com.themaestrocode.onlinelearningplatform.api.error.StudentNotEnrolledException;
import com.themaestrocode.onlinelearningplatform.api.model.ContentModel;
import com.themaestrocode.onlinelearningplatform.api.model.CourseModel;
import com.themaestrocode.onlinelearningplatform.api.repository.CourseRepository;
import com.themaestrocode.onlinelearningplatform.api.utility.CourseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    private ContentService contentService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private CourseRepository courseRepository;


    @Override
    public Course createCourse(CourseModel courseModel, User creator) {
        Course course = new Course();

        course.setTitle(courseModel.getTitle());
        course.setDescription(courseModel.getDescription());
        course.setCourseType(courseModel.getCourseType());
        course.setCreator(creator);

        courseRepository.save(course);
        creator.addCourse().add(course);

        return course;
    }

    @Override
    public List<Course> fetchAllCreatorCourses(Long creatorId) {
        //return courseRepository.fetchAllCreatorCourses(creatorId);
        return courseRepository.findByCreatorUserId(creatorId);
    }

    @Override
    public List<Course> fetchCreatorCourseByTitle(String text, Long creatorId) {
        List<Course> coursesContainingText = courseRepository.findByCreatorUserIdAndTitleContaining(creatorId, text);

        return coursesContainingText;
    }

    @Override
    public Course fetchCreatorCourseById(Long courseId, Long creatorId) throws EntityNotFoundException {
        Course course = courseRepository.findByCourseIdAndCreatorUserId(courseId, creatorId);

        if(course == null) throw new EntityNotFoundException("course not found!");

        return course;
    }

    @Override
    @Transactional
    public void deleteCreatorCourseById(Long courseId, Long creatorId) throws EntityNotFoundException {
        Course course = courseRepository.findByCourseIdAndCreatorUserId(courseId, creatorId);

        if(course == null) throw new EntityNotFoundException("course not found!");

        courseRepository.deleteByCourseIdAndCreatorUserId(courseId, creatorId);
    }

    @Override
    public Course updateCreatorCourse(Long courseId, CourseModel courseModel, Long creatorId) throws EntityNotFoundException {
        Course course = courseRepository.findByCourseIdAndCreatorUserId(courseId, creatorId);

        if(course == null) throw new EntityNotFoundException("course not found!");

        if(courseModel.getTitle() != null && !courseModel.getTitle().isEmpty()) {
            course.setTitle(courseModel.getTitle());
        }

        if(courseModel.getDescription() != null && !courseModel.getDescription().isEmpty()) {
            course.setDescription(courseModel.getDescription());
        }

        if(courseModel.getCourseType() != null && !courseModel.getCourseType().toString().isEmpty()) {
            course.setCourseType(courseModel.getCourseType());
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
    public Course fetchCourseById(Long courseId) throws EntityNotFoundException {
        Course course = courseRepository.findById(courseId).get();

        if(course == null) throw new EntityNotFoundException("course not found!");

        return course;
    }

    @Override
    public void enroll(User student, Course course) throws StudentAlreadyEnrolledException {
        Enrollment enrollment = enrollmentService.enrollStudent(student, course);
        student.addEnrollment().add(enrollment);
        updateCourseEnrollmentDetails(course, true);
    }

    @Override
    public Content addCourseContent(ContentModel contentModel, Course course) {
        Content content = contentService.saveContent(contentModel);
        course.addContents().add(content);

        return content;
    }

    @Override
    public List<Course> fetchEnrolledCourseByTitle(Long userId, String text) {
        List<Enrollment> enrollments = enrollmentService.fetchEnrollmentsByStudentId(userId);
        List<Course> enrolledCourses = new ArrayList<>();

        for(Enrollment enrollment: enrollments) {
            if(enrollment.getCourse().getTitle().toLowerCase().contains(text.toLowerCase())) {
                enrolledCourses.add(enrollment.getCourse());
            }
        }

        return enrolledCourses;
    }

    @Override
    public Course fetchEnrolledCourseById(Long courseId, Long userId) throws StudentNotEnrolledException {
        Enrollment enrollment = enrollmentService.fetchEnrollmentByCourseIdAndStudentId(courseId, userId);

        return enrollment.getCourse();
    }

    @Override
    public List<Course> fetchEnrolledCourseList(Long userId) {
        List<Enrollment> enrollments = enrollmentService.fetchEnrollmentsByStudentId(userId);

        return enrollments.stream().map(Enrollment::getCourse).collect(Collectors.toList());
    }

    @Override
    public void cancelCourseEnrollment(Long courseId, Long userId) throws StudentNotEnrolledException {
        Enrollment enrollment = enrollmentService.fetchEnrollmentByCourseIdAndStudentId(courseId, userId);

        enrollmentService.cancelEnrollment(enrollment.getEnrollmentId());
        updateCourseEnrollmentDetails(courseRepository.findById(courseId).get(), false);
    }

    private void updateCourseEnrollmentDetails(Course course, boolean enrolled) {
        if(enrolled) {
            course.setCurrentlyEnrolled(course.getCurrentlyEnrolled() + 1);
            course.setAllTimeEnrolled(course.getAllTimeEnrolled() + 1);
        }
        else {
            course.setCurrentlyEnrolled(course.getCurrentlyEnrolled() - 1);
        }

        courseRepository.save(course);
    }

}
