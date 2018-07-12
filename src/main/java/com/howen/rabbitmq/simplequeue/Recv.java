package com.howen.rabbitmq.simplequeue;

import com.howen.rabbitmq.Utils.ConnectionUtil;
import com.howen.rabbitmq.common.Consts;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {

    public static void main(String [] args) throws IOException, TimeoutException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //创建通道（并发的时候你可以确定哪条管道发送）
        Channel channel = connection.createChannel();
        //申明一个队列
        channel.queueDeclare(Consts.SM_QUEUE_NAME,false,false,false,null);
        System.out.println("请等待......");

        //消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println("[x] Received '"+message+"'");
            }
        };
        //监听
        channel.basicConsume(Consts.SM_QUEUE_NAME,true,consumer);
    }
}
