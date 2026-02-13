package com.wjh;

import com.wjh.mcp.tool.DateTool;
import com.wjh.mcp.tool.EmailTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 注册MCP工具
     * @param dateTool
     * @return
     */
    @Bean
    public ToolCallbackProvider registerMcpTools(DateTool dateTool, EmailTool emailTool) {
        return MethodToolCallbackProvider.builder().toolObjects(dateTool,emailTool).build();
    }
}
