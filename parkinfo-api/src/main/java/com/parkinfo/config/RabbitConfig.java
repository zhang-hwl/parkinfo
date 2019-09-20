package com.parkinfo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * When I wrote this, only God and I understood what I was doing
 * Now, God only knows
 *
 * @author cnyuchu@gmail.com
 * @create 2019-09-18 13:56
 **/
@Configuration
public class RabbitConfig {
    @Bean
    public Queue queue(){
        return new Queue("transfer-queue");
    }
}
