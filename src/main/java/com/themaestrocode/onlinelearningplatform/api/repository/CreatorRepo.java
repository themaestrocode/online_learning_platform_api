package com.themaestrocode.onlinelearningplatform.api.repository;

import com.themaestrocode.onlinelearningplatform.api.entity.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreatorRepo extends JpaRepository<Creator, Long> {

}
