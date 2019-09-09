package com.parkinfo.tools.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix = "lingouu")
@PropertySource("classpath:tools.properties")
@Data
public class ToolsProperties {

    private OssProperties oss;

    private SmsProperties sms;

    private MailProperties mail;

    private VodProperties vod;
}
