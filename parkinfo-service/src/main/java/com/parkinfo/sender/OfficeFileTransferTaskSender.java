package com.parkinfo.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-18 13:58
 **/
@Component
public class OfficeFileTransferTaskSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String id) {
        this.rabbitTemplate.convertAndSend("transfer-queue", id);
    }

}
