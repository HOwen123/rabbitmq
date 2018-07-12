package com.howen.rabbitmq.common;

public class Consts {
    //简单队列
    public static final String SM_QUEUE_NAME = "simple_test";
    //分发队列
    public static final String WQ_QUEUE_NAME = "work_queue";
    //email队列
    public static final String PS_QUEUE_NAME_EMAIL = "email_queue";
    //短信队列
    public static final String PS_QUEUE_NAME_MSG = "message_queue";
    //交换机
    public static final String PS_EXCHANGE_NAME = "notify_exchange";
    //路由模式交换机
    public static final String RT_EXCHANGE_NAME = "rout_exchange";
    //路由模式队列
    public static final String RT_QUEUE_NAME_1 = "rout_queue_direct_1";

    public static final String RT_QUEUE_NAME_2 = "rout_queue_direct_2";
    //路由key
    public static final String RT_ROUTING_ERROR = "error";

    public static final String RT_ROUTING_INFO = "info";

    public static final String RT_ROUTING_WARNING = "warning";
    //topic主题模式
    public static final String TP_EXCHANGE_NAME = "exchange_topic";
    //topic队列一
    public static final String TP_QUEUE_NAME_1 = "queue_tp_1";
    //topic队列二
    public static final String TP_QUEUE_NAME_2 = "queue_tp_2";

    public static final String TX_QUEUE_NAME = "queue_tx";
    //confirm模式
    public static final String CF_QUEUE_NAME = "queue_cf";

}
