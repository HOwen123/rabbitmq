package com.howen.rabbitmq.Utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        //主机
        factory.setHost("192.168.8.12");
        //协议端口号
        factory.setPort(5672);
        //用户名
        factory.setUsername("rckj");
        //密码
        factory.setPassword("root");
        //虚拟主机路径（相当于数据库名）
        factory.setVirtualHost("/testVH");
        //返回连接
        return factory.newConnection();
    }
}
