package com.howen.rabbitmq.simplequeue;

import com.howen.rabbitmq.Utils.ConnectionUtil;
import com.howen.rabbitmq.common.Consts;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    public static void main(String [] args) throws IOException, TimeoutException {
        //创建连接
        Connection connection = ConnectionUtil.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //申明队列
        channel.queueDeclare(Consts.SM_QUEUE_NAME,false,false,false,null);

        String message = "我觉得也是";
        //发布信息
        channel.basicPublish("",Consts.SM_QUEUE_NAME,null,message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

    }
}
