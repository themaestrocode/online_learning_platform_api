package com.themaestrocode.onlinelearningplatform.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "enrollment_table")
public class Enrollment {

    @Id
    @SequenceGenerator(name = "enrollment_sequence", sequenceName = "enrollment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enrollment_sequence")
    @Column(name = "enrollment_id")
    private Long enrollmentId;
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "user_id", nullable = false)
    private User student;
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id", nullable = false)
    private Course course;
    @Column(name = "enrollment_date", nullable = false)
    private LocalDateTime enrollmentDate;
    @Column(name = "completed")
    private boolean completed = false;
    @Column(name = "completion_date")
    private LocalDateTime completionDate;
}
