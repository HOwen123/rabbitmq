package com.howen.rabbitmq.routing;

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

        channel.exchangeDeclare(Consts.RT_EXCHANGE_NAME,"direct");

        String msg = "[send] message: hello routing";

        System.out.println(msg);

        String routingKey = Consts.RT_ROUTING_ERROR;

        channel.basicPublish(Consts.RT_EXCHANGE_NAME,routingKey,null,msg.getBytes());

        channel.close();

        connection.close();
    }

}
