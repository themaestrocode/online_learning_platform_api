package com.themaestrocode.onlinelearningplatform.api.repository;

import com.themaestrocode.onlinelearningplatform.api.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    @Modifying
    @Transactional
    @Query(value = "UPDATE verification_token_table SET confirmation_time = ?2 WHERE token = ?1", nativeQuery = true)
    void updateVerificationTokenConfirmationTime(String token, LocalDateTime now);
}
