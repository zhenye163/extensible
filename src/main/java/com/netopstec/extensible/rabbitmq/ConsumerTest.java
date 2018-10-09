package com.netopstec.extensible.rabbitmq;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ中消费者测试代码
 * @author zhenye 2018/9/29
 */
@Slf4j
public class ConsumerTest {
    private final static String QUEUE_NAME = "MY_QUEUE";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
        log.info("Consumer is waiting!");
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"UTF-8");
                log.info("Consumer received message, the content is:" + message);
            }
        };
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
