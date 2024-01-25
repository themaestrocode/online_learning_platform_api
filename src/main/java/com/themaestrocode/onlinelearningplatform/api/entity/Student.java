package com.themaestrocode.onlinelearningplatform.api.entity;

import com.themaestrocode.onlinelearningplatform.api.security.Role;
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
@Table(name = "student_table")
public class Student {

    @Id
    @SequenceGenerator(name = "student_sequence", sequenceName = "student_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    @Column(name = "student_id")
    private Long studentId;
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
    @Column(name = "email_address", length = 100, unique = true, nullable = false)
    private String email;
    @Column(name = "password", length = 60, nullable = false)
    private String password;
    @Column(name = "recovery_email", length = 100, unique = true)
    private String recoveryEmail;
    @Column(name = "phone_no", length = 30)
    private String phoneNo;
    @Column(name = "role", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.STUDENT;
    private boolean enabled = false;
}
