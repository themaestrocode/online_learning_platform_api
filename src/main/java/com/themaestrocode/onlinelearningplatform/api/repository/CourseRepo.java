package com.themaestrocode.onlinelearningplatform.api.repository;

import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Long> {
}
