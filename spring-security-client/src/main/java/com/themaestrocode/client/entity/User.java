package com.themaestrocode.client.entity;

import com.themaestrocode.client.security.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_table")
public class User {

    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
    @Column(name = "email_address", length = 100, unique = true, nullable = false)
    private String userEmail;
    @Column(name = "password", length = 60, nullable = false)
    private String userPassword;
    @Column(name = "recovery_email", length = 100, unique = true)
    private String recoveryEmail;
    @Column(name = "phone_no", length = 30)
    private String phoneNo;
    @Column(name = "user_role", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private boolean enabled = false;
    private boolean locked = false;

}
