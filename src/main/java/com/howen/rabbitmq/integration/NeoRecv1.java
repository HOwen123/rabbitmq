package com.howen.rabbitmq.integration;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "queue_springboot_neo")
public class NeoRecv1 {
    @RabbitHandler
    public void process(String neo) {
        System.out.println("Recv 1:" + neo);
    }
}
