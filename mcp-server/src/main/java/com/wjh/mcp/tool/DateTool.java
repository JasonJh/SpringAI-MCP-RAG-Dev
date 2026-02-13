package com.wjh.mcp.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class DateTool {

    @Tool(description = "根据城市所在时区id获取当前时间")
    public String getCurrentTimeByZoneId(String cityName,String zoneId){
        log.info("======== 调用mcp工具：getCurrentTimeByZoneId ========");
        log.info(String.format("======== 参数 cityName：%s ========",cityName));
        log.info(String.format("======== 参数 zoneId：%s ========",zoneId));

        ZoneId zone = ZoneId.of(zoneId);

        //获取该时区对应的当前时间
        ZonedDateTime zonedDateTime = ZonedDateTime.now(zone);

        String result = zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String currentTime = String.format("当前时间是 %s", result);
        return currentTime;
    }

    @Tool(description = "获取当前时间")
    public String getCurrentTime(){
        log.info("======== 调用mcp工具：getCurrentTime ========");
        String result = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String currentTime = String.format("当前时间是 %s", result);
        return currentTime;
    }
}
