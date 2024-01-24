package com.themaestrocode.onlinelearningplatform.api.repository;

import com.themaestrocode.onlinelearningplatform.api.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

}
