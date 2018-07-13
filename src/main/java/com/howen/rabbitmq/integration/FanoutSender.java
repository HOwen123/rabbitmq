package com.howen.rabbitmq.integration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FanoutSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void fanOutSend(String message){
        System.out.println("fanoutSender"+message);
        this.rabbitTemplate.convertAndSend("fanoutExchange","",message);
    }
}
