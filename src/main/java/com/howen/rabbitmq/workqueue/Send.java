package com.howen.rabbitmq.workqueue;

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
        channel.queueDeclare(Consts.WQ_QUEUE_NAME,false,false,false,null);

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
