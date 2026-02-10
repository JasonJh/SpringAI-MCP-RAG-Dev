package com.wjh.controller;

import com.wjh.bean.ChatEntity;
import com.wjh.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("chat")
public class ChatController {

    @Resource
    private ChatService chatService;


    @PostMapping("doChat")
    public void doChat(@RequestBody ChatEntity chatEntity){
        chatService.doChat(chatEntity);
    }

}
