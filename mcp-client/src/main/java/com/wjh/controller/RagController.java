package com.wjh.controller;

import com.wjh.bean.ChatEntity;
import com.wjh.service.ChatService;
import com.wjh.service.DocumentService;
import com.wjh.utils.WjhResult;
import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("rag")
public class RagController {

    @Resource
    private DocumentService documentService;

    @Resource
    private ChatService chatService;


    @PostMapping("/uploadRagDoc")
    public WjhResult uploadRagDoc(@RequestParam("file") MultipartFile file) {

        List<Document> documentList =  documentService.loadText(file.getResource(),file.getOriginalFilename());

        return WjhResult.ok(documentList);
    }

    @GetMapping("doSearch")
    public WjhResult doSearch(@RequestParam("question") String question) {

        return WjhResult.ok(documentService.doSearch(question));
    }

    @PostMapping("search")
    public void search(@RequestBody ChatEntity chatEntity) {
        //从库中查找出数据
        List<Document> list =  documentService.doSearch(chatEntity.getMessage());
        //把数据交给大模型处理
        chatService.doChatRagSerach(chatEntity,list);
    }

}
