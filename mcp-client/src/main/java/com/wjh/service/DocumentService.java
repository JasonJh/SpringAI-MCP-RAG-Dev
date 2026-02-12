package com.wjh.service;


import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;

import java.util.List;

public interface DocumentService {

    /**
     * 记载文档并保存到知识库
     * @param resource
     * @param fileName
     */
    public List<Document> loadText(Resource resource, String fileName);

    /**
     * 根据提问从知识库中查询相应的知识
     * @param question
     * @return
     */
    public List<Document> doSearch(String question);
}
