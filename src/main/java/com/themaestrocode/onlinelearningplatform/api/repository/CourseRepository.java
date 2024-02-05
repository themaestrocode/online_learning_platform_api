package com.themaestrocode.onlinelearningplatform.api.repository;

import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import com.themaestrocode.onlinelearningplatform.api.utility.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query(value = "SELECT * FROM course_table WHERE creator_id = ?1", nativeQuery = true)
    List<Course> fetchAllCreatorCourses(Long creatorId);

    @Query(value = "SELECT * FROM course_table WHERE course_id = ?1 AND creator_id = ?2", nativeQuery = true)
    Course fetchCreatorCourseById(Long courseId, Long creatorId);

    @Query(value = "DELETE FROM course_table WHERE course_id = ?1 AND creator_id = ?2", nativeQuery = true)
    void deleteCreatorCourseById(Long courseId, Long creatorId);

    Course findByCourseIdAndCreatorUserId(Long courseId, Long creatorId);

    @Transactional
    void deleteByCourseIdAndCreatorUserId(Long courseId, Long creatorId);

    List<Course> findByCreatorUserId(Long creatorId);

    List<Course> findByCourseType(CourseType courseType);

}
