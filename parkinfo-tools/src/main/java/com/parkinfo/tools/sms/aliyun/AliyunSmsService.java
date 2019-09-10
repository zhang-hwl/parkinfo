package com.parkinfo.tools.sms.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.parkinfo.exception.NormalException;
import com.parkinfo.tools.properties.SmsProperties;
import com.parkinfo.tools.properties.ToolsProperties;
import com.parkinfo.tools.sms.ISmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AliyunSmsService implements ISmsService {

    @Autowired
    private ToolsProperties toolsProperties;
    @Override
    public boolean send(String phone, String randomCode) throws  ClientException {
        SmsProperties smsProperties = toolsProperties.getSms();
        String jsonContent = "{\"code\":\"" + randomCode + "\"}";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", phone);
        paramMap.put("msgSign",smsProperties.getMsgSign() );
        paramMap.put("templateCode", smsProperties.getTemplateCode());
        paramMap.put("jsonContent", jsonContent);
        SendSmsResponse sendSmsResponse = this.sendSms(paramMap);
        if (!(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK"))) {
            if (sendSmsResponse.getCode() == null) {
                //这里可以抛出自定义异常
                log.error(sendSmsResponse.getMessage());
                throw new NormalException("服务无响应，请稍后再试");
            }
            if (!sendSmsResponse.getCode().equals("OK")) {
                log.error(sendSmsResponse.getMessage());
                throw new NormalException(sendSmsResponse.getMessage());
                //这里可以抛出自定义异常
            }
        }
        return true;
    }

    @Override
    public boolean send(String phone, String jsonContent, String templateCode) throws  ClientException {
        SmsProperties smsProperties = toolsProperties.getSms();
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", phone);
        paramMap.put("msgSign",smsProperties.getMsgSign() );
        paramMap.put("templateCode", templateCode);
        paramMap.put("jsonContent", jsonContent);
        SendSmsResponse sendSmsResponse = this.sendSms(paramMap);
        if (!(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK"))) {
            if (sendSmsResponse.getCode() == null) {
                //这里可以抛出自定义异常
                log.error(sendSmsResponse.getMessage());
                throw new NormalException("服务无响应，请稍后再试");
            }
            if (!sendSmsResponse.getCode().equals("OK")) {
                log.error(sendSmsResponse.getMessage());
                throw new NormalException(sendSmsResponse.getMessage());
                //这里可以抛出自定义异常
            }
        }
        return true;
    }

    private SendSmsResponse sendSms(Map<String, String> paramMap) throws ClientException {

        SmsProperties smsProperties = toolsProperties.getSms();
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsProperties.getAccessKeyId(), smsProperties.getAccessKeySecret());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", smsProperties.getProduct(),smsProperties.getDomain());
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(paramMap.get("phoneNumber"));
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(paramMap.get("msgSign"));
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(paramMap.get("templateCode"));
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam(paramMap.get("jsonContent"));

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
//        request.setSmsUpExtendCode(paramMap.get("extendCode"));

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
//        request.setOutId(paramMap.get("outId"));

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse;
    }

}
