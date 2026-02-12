package com.wjh.service;

import com.wjh.bean.ChatEntity;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import reactor.core.publisher.Flux;

import java.util.List;

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
     * 和大模型交互
     * @param
     * @return
     */
    public void doChat(ChatEntity chatEntity);


    /**
     * RAG知识库搜索汇总给大模型输出
     * @param chatEntity
     * @param ragContext
     */
    public void doChatRagSerach(ChatEntity chatEntity, List<Document> ragContext);

    /**
     * 基于searXNG实时联网搜索
     * @param chatEntity
     */
    public void doInternetSearch(ChatEntity chatEntity);
}
