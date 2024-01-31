package com.themaestrocode.onlinelearningplatform.api.model;

import com.themaestrocode.onlinelearningplatform.api.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentModel {

    private String name;
    private byte[] fileData;
    private String description;
    private Course course;
}
