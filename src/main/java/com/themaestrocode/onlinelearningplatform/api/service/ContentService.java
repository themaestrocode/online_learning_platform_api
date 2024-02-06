package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Content;
import com.themaestrocode.onlinelearningplatform.api.error.EntityNotFoundException;
import com.themaestrocode.onlinelearningplatform.api.model.ContentModel;

import java.util.List;

public interface ContentService {


    Content saveContent(ContentModel contentModel);

    List<Content> fetchAllContentUnderACourse(Long courseId);

    Content fetchContent(Long contentId, Long courseId) throws EntityNotFoundException;

    void deleteContentById(Long contentId);
}
