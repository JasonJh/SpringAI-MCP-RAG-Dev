package com.wjh.controller;

import com.wjh.service.ChatService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("hello")
public class HelloController {

    @Resource
    private ChatService chatService;

    @GetMapping("world")
    public String world(){
        return "Hello wjh!";
    }

    @GetMapping("chat")
    public String chat(String msg){
        return chatService.chatTest(msg);
    }

    @GetMapping("chat/stream/response")
    public Flux<ChatResponse> chatStreamResponse(String msg){
        return chatService.streamResponse(msg);
    }

    @GetMapping("chat/stream/str")
    public Flux<String> chatStreamStr(String msg){
        return chatService.streamStr(msg);
    }

}
