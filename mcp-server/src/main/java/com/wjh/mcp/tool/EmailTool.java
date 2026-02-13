package com.wjh.mcp.tool;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class EmailTool {

    private final JavaMailSender mailSender;
    private final String from;

    @Data
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmailRequest {
        @ToolParam(description = "收件人的邮箱")
        private String email;
        @ToolParam(description = "发送邮件的标题/主题")
        private String subject;
        @ToolParam(description = "发送邮件的消息/正文内容")
        private String message;

        @ToolParam(description = "邮件的内容是否为html还是markdown格式如果是markdown格式，则为1；如果是html格式则为2")
        private Integer contentType;
    }

    @Autowired
    public EmailTool(JavaMailSender mailSender, @Value("${spring.mail.username}") String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    //13242033603@163.com
    @Tool(description = "查询我的邮件/邮箱地址")
    public String getMyEmailAddress(){
        log.info("======== 调用mcp工具：getMyEmailAddress() ========");
        return "13242033603@163.com";
    }

    @Tool(description = "给指定邮箱发送邮件信息，email 为收件人邮箱,subject 为邮件标题,message 为邮件内容")
    public void sendMailMessage(EmailRequest emailRequest){

        log.info("======== 调用mcp工具：sendMailMessage() ========");
        log.info("======== 参数：emailRequest: {} ========",emailRequest.toString());
        Integer contentType = emailRequest.contentType;

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);


            helper.setFrom(from);
            helper.setTo(emailRequest.getEmail());
            helper.setSubject(emailRequest.getSubject());
            if (contentType == 2) {
                helper.setText(emailRequest.getMessage(),true);
            } else if (contentType == 1){
                //markdown 转 html
                helper.setText(covertToHtml(emailRequest.getMessage()),true);
            }else {
                helper.setText(emailRequest.getMessage());
            }
//



            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
//            throw new RuntimeException(e);
            log.error("======== 发送邮件失败，错误信息：{} ========",e.getMessage());
        }

    }

    /**
     * markdown 转 html
     * @param markdownStr
     * @return
     */
    public static String covertToHtml(String markdownStr){
        MutableDataSet dataSet = new MutableDataSet();
        Parser parser = Parser.builder(dataSet).build();
        HtmlRenderer htmlRenderer = HtmlRenderer.builder(dataSet).build();

        return htmlRenderer.render(parser.parse(markdownStr));
    }
}
