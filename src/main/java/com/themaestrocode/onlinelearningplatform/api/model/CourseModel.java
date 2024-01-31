package com.themaestrocode.onlinelearningplatform.api.model;

import com.themaestrocode.onlinelearningplatform.api.utility.CourseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseModel {

    private String title;
    private String description;
    private String courseUrl;
    private CourseType courseType;
}
