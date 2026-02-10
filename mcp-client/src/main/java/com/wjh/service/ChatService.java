package com.wjh.service;

import com.wjh.bean.ChatEntity;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

public interface ChatService {

    /**
     *
     * @param prompt
     * @return
     * @Description: 测试大模型交互聊天
     */
    public String chatTest(String prompt);

    /**
     *
     * @param prompt
     * @return
     * 测试大模型交互聊天（流式响应 json）
     */
    public Flux<ChatResponse> streamResponse(String prompt);

    /**
     *
     * @param prompt
     * @return
     * 测试大模型交互聊天（流式响应字符串）
     */
    public Flux<String> streamStr(String prompt);

    /**
     *
     * @param
     * @return
     * 和大模型交互
     */
    public void doChat(ChatEntity chatEntity);
}
