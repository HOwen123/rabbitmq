package com.howen.rabbitmq.publishSubscribe;

import com.howen.rabbitmq.Utils.ConnectionUtil;
import com.howen.rabbitmq.common.Consts;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {

    public static void main(String [] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(Consts.PS_QUEUE_NAME_MSG,true,false,false,null);
        //队列与交换机绑定
        channel.queueBind(Consts.PS_QUEUE_NAME_MSG,Consts.PS_EXCHANGE_NAME,"");

        //保证一次只分发一次
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String recvMsg = new String(body,"UTF-8");

                System.out.println("[2] Recv msg "+recvMsg);

                channel.basicAck(envelope.getDeliveryTag(),false);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        boolean autoAck = false;

        channel.basicConsume(Consts.PS_QUEUE_NAME_MSG,autoAck,consumer);
    }

}
