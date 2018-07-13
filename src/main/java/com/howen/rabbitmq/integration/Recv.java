package com.howen.rabbitmq.integration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@RabbitListener(queues = "queue_springboot")
public class Recv {

    @RabbitHandler
    public void process(String msg){
        System.out.println("我接收了一个："+msg);
    }

}
