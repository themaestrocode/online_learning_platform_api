package com.themaestrocode.client.repository;

import com.themaestrocode.client.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserEmail(String email);


    @Transactional
    @Modifying
    @Query(value = "UPDATE user_table SET enabled = true WHERE email_address = ?1", nativeQuery = true)
    void enableUser(String userEmail);
}
