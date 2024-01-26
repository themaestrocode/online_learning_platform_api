package com.themaestrocode.onlinelearningplatform.api.entity;

import com.themaestrocode.onlinelearningplatform.api.utility.ContentType;
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
    private String name;
    @Enumerated(EnumType.STRING)
    private ContentType contentType;
    private byte[] contentFile;
    private String description;
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id")
    private Course course;
}
