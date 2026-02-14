package com.wjh.mcp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.ai.tool.annotation.ToolParam;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    @ToolParam(description = "收件人的邮箱")
    private String email;
    @ToolParam(description = "发送邮件的标题/主题")
    private String subject;
    @ToolParam(description = "发送邮件的消息/正文内容")
    private String message;

    @ToolParam(description = "邮件的内容是否为html还是markdown格式如果是markdown格式，则为1；如果是html格式则为2")
    private Integer contentType;
}
