package com.howen.rabbitmq.routing;

import com.howen.rabbitmq.Utils.ConnectionUtil;
import com.howen.rabbitmq.common.Consts;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv5 {

    public static void main(String [] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(Consts.RT_QUEUE_NAME_1,false,false,false,null);

        //保证一次只分发一次
        channel.basicQos(1);

        channel.queueBind(Consts.RT_QUEUE_NAME_1,Consts.RT_EXCHANGE_NAME,Consts.RT_ROUTING_ERROR);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String recvMsg = new String(body,"UTF-8");

                System.out.println("[5] Recv msg "+recvMsg);

                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };

        boolean autoAck = true;

        channel.basicConsume(Consts.RT_QUEUE_NAME_1,autoAck,consumer);
    }
}
