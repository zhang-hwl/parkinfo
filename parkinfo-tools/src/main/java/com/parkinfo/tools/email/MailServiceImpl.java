package com.parkinfo.tools.email;

import com.parkinfo.tools.properties.ToolsProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MailServiceImpl implements IMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ToolsProperties toolsProperties;
    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(toolsProperties.getMail().getFrom());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            javaMailSender.send(message);
            log.info("邮件已经发送");
        } catch (Exception e) {
            log.error("发送邮件时发生异常！", e);
        }

    }
}
