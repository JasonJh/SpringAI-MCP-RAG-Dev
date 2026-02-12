package com.wjh.controller;

import com.wjh.bean.ChatEntity;
import com.wjh.service.ChatService;
import com.wjh.service.DocumentService;
import com.wjh.service.SearXngService;
import com.wjh.utils.WjhResult;
import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("internet")
public class InternetController {

    @Resource
    private SearXngService searXngService;

    @Resource
    private ChatService chatService;


    @GetMapping("/test")
    public Object test(@RequestParam("query") String query) {

        return searXngService.search(query);
    }

    @PostMapping("/search")
    public void search(@RequestBody ChatEntity chatEntity) {
        chatService.doInternetSearch(chatEntity);
    }

}
