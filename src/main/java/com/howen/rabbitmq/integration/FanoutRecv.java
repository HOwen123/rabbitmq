package com.howen.rabbitmq.integration;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author 练浩文
 * @TODO (注：发布订阅模式消费者)
  * @param
 * @DATE: 2018/7/13 15:53
 */
@Component
@RabbitListener(queues = "fanout.A")
public class FanoutRecv {
    @RabbitHandler
    public void process(String msg){
        System.out.println("fanout Receeiver A :"+msg);
    }
}

@Component
@RabbitListener(queues = "fanout.B")
class FanoutRecv1 {
    @RabbitHandler
    public void process(String msg){
        System.out.println("fanout Receeiver B :"+msg);
    }
}

@Component
@RabbitListener(queues = "fanout.C")
class FanoutRecv2 {
    @RabbitHandler
    public void process(String msg){
        System.out.println("fanout Receeiver C :"+msg);
    }
}