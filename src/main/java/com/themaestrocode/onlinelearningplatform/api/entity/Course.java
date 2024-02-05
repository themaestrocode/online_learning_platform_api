package com.themaestrocode.onlinelearningplatform.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.themaestrocode.onlinelearningplatform.api.utility.CourseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "user_id")
    private User creator;
    @Column(name = "course_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CourseType courseType;
    @Column(name = "currently_enrolled")
    private int currentlyEnrolled = 0;
    @Column(name = "all_time_enrolled")
    private int allTimeEnrolled = 0;
    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Content> contents;

    public List<Content> addContents() {
        if (contents == null) {
            contents = new ArrayList<>();
        }
        return contents;
    }

}