package com.wjh;

import com.wjh.mcp.tool.DateTool;
import com.wjh.mcp.tool.EmailTool;
import com.wjh.mcp.tool.ProductTool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@MapperScan("com.wjh.mapper")
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
    public ToolCallbackProvider registerMcpTools(DateTool dateTool, EmailTool emailTool, ProductTool productTool) {
        return MethodToolCallbackProvider.builder().toolObjects(dateTool,emailTool,productTool).build();
    }
}
