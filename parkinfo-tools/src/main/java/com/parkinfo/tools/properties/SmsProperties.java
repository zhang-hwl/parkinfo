package com.parkinfo.tools.properties;

import lombok.Data;

@Data
public class SmsProperties {

    private String product = "Dysmsapi";
    private String domain = "dysmsapi.aliyuncs.com";

    // 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    private String accessKeyId;

    private String accessKeySecret;

    private String msgSign="灵狗优优";

    private String templateCode;
}
