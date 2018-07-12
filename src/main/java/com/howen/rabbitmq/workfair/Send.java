package com.howen.rabbitmq.workfair;

import com.howen.rabbitmq.Utils.ConnectionUtil;
import com.howen.rabbitmq.common.Consts;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    public static void main(String [] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(Consts.WQ_QUEUE_NAME,true,false,false,null);

        /**
         * 每个消费者发送确认消息之前，消息队列只给消费者发送一条消息
         *
         * 限制发送给消费者不超过一条信息
         */
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        for (int i = 0; i <50 ; i++) {
            String sendMsg = "信息"+i;

            System.out.println(sendMsg);

            channel.basicPublish("",Consts.WQ_QUEUE_NAME,null,sendMsg.getBytes());

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("done");
            }
        }

        channel.close();
        connection.close();

    }

}
