package com.howen.rabbitmq.workqueue;

import com.howen.rabbitmq.Utils.ConnectionUtil;
import com.howen.rabbitmq.common.Consts;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {

    public static void main(String [] args) throws IOException, TimeoutException {
       Connection connection = ConnectionUtil.getConnection();

       Channel channel = connection.createChannel();

       channel.queueDeclare(Consts.WQ_QUEUE_NAME,false,false,false,null);

       Consumer consumer = new DefaultConsumer(channel){
           @Override
           public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

               String recvMsg = new String(body,"UTF-8");

               System.out.println("[2] Recv msg "+recvMsg);

               try {
                   Thread.sleep(3000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       };

       boolean autoAck = true;

       channel.basicConsume(Consts.WQ_QUEUE_NAME,autoAck,consumer);
    }
}
