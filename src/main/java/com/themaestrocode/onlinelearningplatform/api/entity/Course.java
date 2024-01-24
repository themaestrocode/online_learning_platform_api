package com.themaestrocode.onlinelearningplatform.api.entity;

import com.themaestrocode.onlinelearningplatform.api.enums.CourseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "course_table")
public class Course {

    @Id
    @SequenceGenerator(name = "course_sequence", sequenceName = "course_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_sequence")
    @Column(name = "course_id")
    private Long courseId;
    @Column(name = "title", unique = true, nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "course_url")
    private String courseUrl;
    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "creator_id")
    private Creator creator;
    @Column(name = "course_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseType courseType;
    @ManyToMany
    @JoinTable(name = "student_course_map",
                joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "course_id"),
                inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "student_id"))
    private List<Student> students;
}
