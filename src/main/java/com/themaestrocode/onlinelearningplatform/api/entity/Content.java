package com.themaestrocode.onlinelearningplatform.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "content_table")
public class Content {

    @Id
    @SequenceGenerator(name = "content_sequence", sequenceName = "content_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_sequence")
    @Column(name = "content_id")
    private Long contentId;
    @Column(name = "name", length = 50)
    private String name;
    @Lob
    @Column(name = "file_data", columnDefinition = "MEDIUMBLOB")
    private byte[] fileData;
    @Column(name = "description")
    private String description;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;
}
