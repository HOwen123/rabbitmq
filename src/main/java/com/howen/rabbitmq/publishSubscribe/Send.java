package com.howen.rabbitmq.publishSubscribe;

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

       //声明分发
       channel.exchangeDeclare(Consts.PS_EXCHANGE_NAME,"fanout");

       String msg = "hello rabbit";

       //发布到交换机
       channel.basicPublish(Consts.PS_EXCHANGE_NAME,"",null,msg.getBytes());

       System.out.println("send :"+msg);

       channel.close();
       connection.close();

    }

}
