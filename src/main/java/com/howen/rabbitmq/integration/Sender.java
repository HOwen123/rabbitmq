package com.howen.rabbitmq.integration;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Sender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * @author 练浩文
     * @TODO (注：简单队列的发送)
     * @param message
     * @DATE: 2018/7/13 11:32
     */
    @RequestMapping(value = "/sendMsg")
    public void sendMsg(String message){
        System.out.println("[send]:"+message);
        this.rabbitTemplate.convertAndSend("queue_springboot",message);
    }

    /**
     * @author 练浩文
     * @TODO (注：竞争队列)
     * @param i
     * @DATE: 2018/7/13 11:34
     */
    @RequestMapping(value = "sendNeo")
    public void sendNeo(int i){
        for (int j = 0; j < i; j++) {
            send(j);
        }
    }

    public void send(int i) {
        String context = "spirng boot neo queue"+" ****** "+i;
        System.out.println("Sender1 : " + context);
        this.rabbitTemplate.convertAndSend("queue_springboot_neo", context);
    }
}
