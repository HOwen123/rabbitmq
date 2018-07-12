package com.howen.rabbitmq.tx;

import com.howen.rabbitmq.Utils.ConnectionUtil;
import com.howen.rabbitmq.common.Consts;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public static void main(String [] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(Consts.TX_QUEUE_NAME,false,false,false,null);

        String msg="事务";
        //开启事务
        channel.txSelect();

        try{
            channel.basicPublish("",Consts.TX_QUEUE_NAME,null,msg.getBytes());
            System.out.println(msg);
            //提交事务
            channel.txCommit();
        }catch (Exception e){
            System.out.println("msg rollback");
            //事务回滚
            channel.txRollback();
        }finally {
            channel.close();
            connection.close();
        }

    }
}
