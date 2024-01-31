package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Content;
import com.themaestrocode.onlinelearningplatform.api.model.ContentModel;

import java.util.List;

public interface ContentService {


    Content saveContent(ContentModel contentModel);

    List<Content> fetchAllContentsUnderACourse(Long courseId);
}
