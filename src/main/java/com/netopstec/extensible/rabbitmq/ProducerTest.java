package com.netopstec.extensible.rabbitmq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ中生产者测试代码
 * @author zhenye 2018/9/29
 */
@Slf4j
public class ProducerTest {

    private final static String EXCHANGE_NAME = "MY_EXCHANGE";
    private final static String QUEUE_NAME = "MY_QUEUE";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT,true);
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        String routingKey = "123";
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,routingKey);
        String message = "Hello RabbitMQ, I will send some message to the consumer.";
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,message.getBytes("UTF-8"));
        log.info("Producer send message, the content is :" + message);
        channel.close();
        connection.close();
    }
}
