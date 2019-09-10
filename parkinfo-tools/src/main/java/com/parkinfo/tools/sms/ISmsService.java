package com.parkinfo.tools.sms;

import com.aliyuncs.exceptions.ClientException;

public interface ISmsService {
    boolean send(String phone, String randomCode) throws ClientException;

    boolean send(String phone, String jsonContent, String templateCode) throws  ClientException;
}
