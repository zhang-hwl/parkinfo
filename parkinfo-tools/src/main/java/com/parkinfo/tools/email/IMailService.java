package com.parkinfo.tools.email;

public interface IMailService {

    void sendSimpleMail(String to, String subject, String content);
}
