package com.wjh.mcp.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class DateTool {

    @Tool(description = "获取当前时间")
    public String getCurrentTime(){
        log.info("======== 调用mcp工具：getCurrentTime ========");
        String result = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String currentTime = String.format("当前时间是 %s", result);
        return currentTime;
    }
}
