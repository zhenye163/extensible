# RabbitMQ的使用场景

MQ，是Message Queue(消息队列)的简写。简而言之，RabbitMQ就是将消息储存在队列中。<br/>
在项目的实际开发过程中，可以将<font color="red">一些无需即时返回结果且耗时的操作</font>提取出来，进行异步处理。这种处理方式能够大大节省服务器的请求响应时间，从而提高系统的吞吐量。

比如：以去年双十一淘宝成交额为例：

![2017年双十一成交额](images/1.png)

**当天每秒下订单笔数超过32.5万笔，支付笔数超过25.6万笔。** <br/>
也就是说，需要阿里的服务器<font color="red">每秒进行32.5万个“生成订单”的操作，还要进行25.6万个“支付订单”的操作。</font> <br/>
而这些与money相关的，都是一些耗时的操作。如果要求即时返回这些操作的处理结果，服务器的压力太大。<br/>
实际能够进行优化后的做法是：把两种不同的操作，放到两个不同的队列中延后进行处理。<br/>
如：“剁手党”之一的我在当天进行了一个下单操作，服务器会将这个操作的具体逻辑放到“order_queue”队列中，就告知我下单成功，然后服务器会在空闲时处理并生成相应的订单。这种做法，不仅提升了用户的使用体验（我觉得很快就下单成功了），还能够缓解服务器的压力。

常用的消息队列还有：ZeroMQ,ActiveMQ,Kafka等等，有兴趣的可以自行了解。这篇博客主要是学习RabbitMQ的简单应用，这里就不探讨这几种消息队列的优劣势了。

# 在Windows下安装RabbitMQ

> RabbitMQ是使用Erlang语言编写的一个开源的消息队列。

1. <font color="red">首先，在[Erlang官网](http://www.erlang.org/download.html)下载对应版本的Erlang平台。</font>
![erlang依赖](images/2.png)
2. <font color="red">然后运行可执行文件(otp_win64_21.1)。</font>按默认配置进行安装，更改一下文件的存放目录即可。出现如下图说明Erlang平台安装成功。
![erlang平台安装成功](images/3.png)
3. <font color="red">再然后在[RabbitMQ官网](http://www.rabbitmq.com/install-windows.html)下载最新版本的RabbitMQ Server。</font>
![RabbitMQ Server下载](images/4.png)
4. 继续按默认配置进行安装，出现如下图说明RabbitMQ Server安装成功。
![RabbitMQ Server安装成功](images/5.png)
5. 这种做法，是将RabbitMQ暴露为Window的一个服务。在这里<font color="red">启动RabbitMQ后，可以在15672端口用默认账户密码进行登录（guest/guest）。</font>
![默认账户guest登录RabbitMQ Server](images/6.png)
6. 出现如下图说明登录成功。
![RabbitMQ Server主页](images/7.png)

# RabbitMQ中的几个重要概念

- <font color="red">Producer</font>

Producer，生产者。消息的发送方即为生产者。

- <font color="red">Consumer</font>

Consumer，消费者。消息的接收方即为消费者。

- <font color="red">Connection</font>

Connection，获取RabbitMQ(消息队列)服务的长连接。得到长连接实例的方式有两种:

1. 手动设置相关参数（host/port etc...）

```JAVA
/**
  * 不设置的话，会使用默认的参数（userName:guest,password:guest,virtualHost:/,hostName:localhost,portNumber:5672）
  */
ConnectionFactory factory = new ConnectionFactory();
factory.setUsername(userName);
factory.setPassword(password);
factory.setVirtualHost(virtualHost);
factory.setHost(hostName);
factory.setPort(portNumber);
Connection conn = factory.newConnection();
```

2. URI设置相关参数

```JAVA
/**
  * 不设置的话，会使用默认的参数（userName:guest,password:guest,virtualHost:/,hostName:localhost,portNumber:5672）
  */
ConnectionFactory factory = new ConnectionFactory();
factory.setUri("amqp://userName:password@hostName:portNumber/virtualHost");
Connection connection = factory.newConnection();
```

- <font color="red">Channel</font>

Channel，通道。通过通道来决定“生产者如何往队列中发送消息”、“消费者如何从队列中接收消息”。获取通道实例的方式如下：

```JAVA
Channel channel = connection.createChannel();
```

- <font color="red">Exchange</font>

Exchange，交换机。生产者往队列中发送消息，实际不是直接发送给队列，而是先发送给交换机，由交换机决定实际将消息发送给哪个队列。

- <font color="red">Queue</font>

Queue，队列。存储消息的地方。交换机和队列都是通过通道实例来声明，而且交换机与队列直接是存在对应关系的。具体的源码如下：

```JAVA
channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT, true);
channel.queueDeclare(queueName,true,false,false,null);
channel.queueBind(queueName, exchangeName, routingKey);
```

具体的代码解释如下：

通过`ChannelIN.exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable)`来声明交换机实例，第一个参数是交换机的名称，第二个参数是交换机的类型（direct/fanout/topic/headers）,第三个参数是该交换机是否持久化。<br/>
通过`ChannelIN.queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)`来声明队列实例，第一个参数是队列的名称，第二个参数是该队列是否持久化，第三个参数是是否只有自己能够看到该对列（排他性），第四个参数是当没有消费者占用该队列时是否删除该队列。<br/>
通过`queueBind(String queue, String exchange, String routingKey)`绑定该交换机和队列。routingKey是绑定队列和交换机之间的路由规则。

这些概念之间的关系结构图如下：

![RabbitMQ的元素结构图](images/8.png)

# RabbitMQ在java-web中的应用

- 必须要导入RabbitMQ Server的POM依赖

```XML
<dependency>
  <groupId>com.rabbitmq</groupId>
  <artifactId>amqp-client</artifactId>
  <version>4.0.3</version>
</dependency>
```

- 发送消息的生产者测试代码如下：

```JAVA
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
```

- 接收消息的消费者测试代码如下：

```JAVA
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
```

- 演示过程

连续运行5次`ProducerTest.main()`,即有5个消费者往队列（MY_QUEUE）中发送了消息。

RabbitMQ Server主页显示如下：

![主页效果图1](images/9.png)

结果表明，在RabbitMQ的所有队列中,还有5条待处理的消息。

接着运行`ConsumerTest.main()`，效果图如下：

![主页效果图2](images/10.png)

结果表明，该消费者一次性处理完了5条消息。

- 流程说明

生产者是通过`ChannelIN.basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)`来发送消息，第一个参数是交换机名称，第二个参数是路由规则Key,第三个参数是全局属性，第四个参数是消息实体。第一、二个参数是可以确定要保存消息的队列的。

消费者是通过`DefaultConsumer.handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)`来接收并处理消息的。由于消费者是直接从队列中获取消息，只要保证生产者存入消息的队列（交换机名称、路由Key确定的队列），与消费者接收消息的队列（直接指定QUEUE_NAME）相同，就能正确地取出消息。
