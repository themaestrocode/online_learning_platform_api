package com.themaestrocode.onlinelearningplatform.api.service;

import com.themaestrocode.onlinelearningplatform.api.entity.Content;
import com.themaestrocode.onlinelearningplatform.api.error.EntityNotFoundException;
import com.themaestrocode.onlinelearningplatform.api.model.ContentModel;
import com.themaestrocode.onlinelearningplatform.api.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Override
    public Content saveContent(ContentModel contentModel) {
        Content content = new Content();

        content.setName(contentModel.getName());
        content.setFileData(contentModel.getFileData());
        content.setDescription(contentModel.getDescription());
        content.setCourse(contentModel.getCourse());

        return contentRepository.save(content);
    }

    @Override
    public List<Content> fetchAllContentUnderACourse(Long courseId) {
        return contentRepository.findByCourseCourseId(courseId);
    }

    @Override
    public Content fetchContent(Long contentId, Long courseId) throws EntityNotFoundException {
        Content content = contentRepository.findByContentIdAndCourseCourseId(contentId, courseId);

        if(content == null) throw new EntityNotFoundException("Content not found1");

        return content;
    }

    @Override
    public void deleteContentById(Long contentId) {
        contentRepository.deleteById(contentId);
    }
}
