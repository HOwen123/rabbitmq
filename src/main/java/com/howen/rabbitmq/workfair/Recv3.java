package com.howen.rabbitmq.workfair;

import com.howen.rabbitmq.Utils.ConnectionUtil;
import com.howen.rabbitmq.common.Consts;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv3 {

    public static void main(String [] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(Consts.WQ_QUEUE_NAME,true,false,false,null);

        //保证一次只分发一次
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String recvMsg = new String(body,"UTF-8");

                System.out.println("[3] Recv msg "+recvMsg);

                channel.basicAck(envelope.getDeliveryTag(),false);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("done");
                }
            }
        };

        boolean autoAck = false;

        channel.basicConsume(Consts.WQ_QUEUE_NAME,autoAck,consumer);
    }
}
