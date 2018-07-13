package com.howen.rabbitmq.integration;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String addresses;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;
    @Value("${spring.rabbitmq.publisher-confirms}")
    private Boolean publisherConfirms;
    //    @Value("${spring.rabbitmq.publisher-returns}")
//    private Boolean publisherReturns;
    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;


    @Bean
    public Queue Queue() {
        return new Queue("queue_springboot");
    }

    @Bean
    public Queue Queue2() {
        return new Queue("queue_springboot_neo");
    }

    @Bean
    public Queue AMessage() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue BMessage() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue CMessage() {
        return new Queue("fanout.C");
    }

    /**
     * @author 练浩文
     * @TODO (注：创建连接工厂)
     * @param
     * @DATE: 2018/7/13 15:35
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPublisherConfirms(publisherConfirms);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }

    /**
     * @param
     * @author 练浩文
     * @TODO (注：这里使用了确认机制，开启本方法时先注释掉NeoRecv1和NeoRecv2)
     * @DATE: 2018/7/13 15:24
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(Queue2());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);//消息确认后才能删除
        container.setPrefetchCount(5);//每次处理5条消息
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                System.out.println("消费端接收到消息 : " + new String(body));
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        });
        return container;
    }

    /**
     * @author 练浩文
     * @TODO (注：创建交换机)
      * @param
     * @DATE: 2018/7/13 15:37
     */
    @Bean
    FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * @author 练浩文
     * @TODO (注：将队列绑定到交换机上)
      * @param
     * @DATE: 2018/7/13 15:39
     */
    @Bean
    Binding bindingExchangeA(Queue AMessage,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }
    @Bean
    Binding bindingExchangeB(Queue BMessage,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue CMessage,FanoutExchange fanoutExchange){
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }

}
