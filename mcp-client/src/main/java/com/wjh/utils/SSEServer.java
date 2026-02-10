package com.wjh.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.wjh.enums.SSEMsgType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

@Slf4j
public class SSEServer {

    private static final Map<String, SseEmitter> sseClients = new ConcurrentHashMap<>();

    /**
     * 连接SSE服务
     * @param userId
     * @return
     */
    public static SseEmitter connect(String userId){
        //设置超时，0L表示永不过期；默认30秒，超时未完成任务会抛超时异常
        SseEmitter sseEmitter = new SseEmitter(0L);
        //注册回调方法
        sseEmitter.onTimeout(timeoutCallback(userId));
        sseEmitter.onCompletion(completionCallback(userId));
        sseEmitter.onError(errorCallback(userId));

        sseClients.put(userId, sseEmitter);

        log.info("SSE连接创建成功，连接的用户ID为：{}",userId);

        return sseEmitter;
    }

    public static void sendMsg(String userId,String message, SSEMsgType msgType){
        if(CollectionUtil.isEmpty(sseClients)){
            return;
        }
        if(sseClients.containsKey(userId)){
           SseEmitter sseEmitter =  sseClients.get(userId);
           sendEmitterMessage(sseEmitter,userId,message,msgType);
        }

    }

    public static void sendMsgToAllUsers(String message){
        if(CollectionUtil.isEmpty(sseClients)){
            return;
        }
        sseClients.forEach((userId,sseEmitter)->{
            sendEmitterMessage(sseEmitter,userId,message,SSEMsgType.MESSAGE);
        });
    }

    private static void sendEmitterMessage(SseEmitter sseEmitter,
                                   String userId, String message,
                                   SSEMsgType msgType){

        try {
            SseEmitter.SseEventBuilder msgEvent = SseEmitter.event()
                    .id(userId)
                    .data(message)
                    .name(msgType.type);
            sseEmitter.send(msgEvent);
        } catch (IOException e) {
            log.error("SSE 异常{}",e.getMessage());
            remove(userId);
        }
    }

    public static Consumer<Throwable> errorCallback(String userId){
        return Throwable -> {
            log.error("SSE 异常");
            //删除用户连接
            remove(userId);
        };
    }

    public static Runnable timeoutCallback(String userId){
        return () -> {
            log.info("SSE 超时");
          //删除用户连接
            remove(userId);
        };
    }

    public static Runnable completionCallback(String userId){
        return () -> {
            log.info("SSE 完成");
            //删除用户
            remove(userId);
        };
    }

    public static void remove(String userId){
        sseClients.remove(userId);
        log.info("删除 连接 {}", userId);
    }
}
