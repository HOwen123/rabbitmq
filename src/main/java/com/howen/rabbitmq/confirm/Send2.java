package com.howen.rabbitmq.confirm;

import com.howen.rabbitmq.Utils.ConnectionUtil;
import com.howen.rabbitmq.common.Consts;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;


public class Send2 {

    /**
     * @author 练浩文
     * @TODO (注：异步调用)
     * @param
     * @DATE: 2018/7/12 16:51
     */
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare(Consts.CF_QUEUE_NAME, false, false, false, null);

        String msg = "confirm模式";
        //开启confirm模式
        channel.confirmSelect();
        //未确认的消息标识
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        //添加通道监听
        channel.addConfirmListener(new ConfirmListener() {
            //没有问题的handleAck
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if (multiple){
                    System.out.println("---handleAck-----multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else {
                    System.out.println("---handleAck-----multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }
            //handleNack 回执有问题的
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple){
                    System.out.println("---handleNack-----multiple");
                    confirmSet.headSet(deliveryTag+1).clear();
                }else {
                    System.out.println("---handleNack-----multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        while(true){
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("",Consts.CF_QUEUE_NAME,null,msg.getBytes());
            confirmSet.add(seqNo);
        }


    }
}
