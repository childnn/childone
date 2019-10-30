package org.anonymous.demo;

import cn.hutool.core.util.RandomUtil;
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/14 17:51
 * 广播模式, 一个生产者会把消息发送给所有对应的在监听的监听者.
 * 注: 先启动消费者, 在启动生产者.
 */
public class FanoutTest {
    public final static String EXCHANGE_NAME = "test_fanout_exchange";

    @Test
    public void producer() throws IOException, TimeoutException {
        // 创建连接工厂. -- 全部使用默认的属性.
        ConnectionFactory factory = new ConnectionFactory();
        // 创建连接.
        Connection conn = factory.newConnection();
        // 创建信道.
        Channel channel = conn.createChannel();
        // 声明交换机.
        AMQP.Exchange.DeclareOk declareOk = channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        for (int i = 0; i < 100; i++) {
            String msg = "fanout " + i;

            // 发送消息.
            channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());

            System.out.println("发送消息: " + msg);
        }

        System.in.read();
        // channel.close();
        // conn.close();
    }

    @Test
    public void consumer1() throws IOException, TimeoutException {
        // 为消费者随机取名
        final String name = "consumer-" + RandomUtil.randomString(5);

        // 创建连接工厂.
        ConnectionFactory factory = new ConnectionFactory();
        // 创建连接.
        Connection conn = factory.newConnection();
        // 创建信道.
        Channel channel = conn.createChannel();
        // 声明交换机.
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        // 获取一个临时队列.
        String queueName = channel.queueDeclare().getQueue();
        // 队列与交换机绑定(指定 队列名称, 交换机名称, routingKey 忽略)
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(name + " 等待接收消息...");

        // 告诉服务器我们需要哪个频道的消息, 如果频道中有消息, 就会执行回调函数 handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(name + " 接收到消息 '" + new String(body, StandardCharsets.UTF_8) + "'");
            }
        };

        // 自动恢复队列应答 -- RabbitMQ 消息确认机制.
        channel.basicConsume(queueName, true, consumer);

        System.in.read();
    }

    @Test
    public void consumer2() throws IOException, TimeoutException {
        // 为消费者随机取名
        String name = "consumer-" + RandomUtil.randomString(5);

        // 创建连接工厂.
        ConnectionFactory factory = new ConnectionFactory();
        // 创建连接.
        Connection conn = factory.newConnection();
        // 创建信道.
        Channel channel = conn.createChannel();
        // 声明交换机.
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        // 获取一个临时队列.
        String queueName = channel.queueDeclare().getQueue();
        // 队列与交换机绑定(指定 队列名称, 交换机名称, routingKey 忽略)
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(name + " 等待接收消息...");

        // 告诉服务器我们需要哪个频道的消息, 如果频道中有消息, 就会执行回调函数 handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(name + " 接收到消息 '" + new String(body, StandardCharsets.UTF_8) + "'");
            }
        };

        // 自动恢复队列应答 -- RabbitMQ 消息确认机制.
        channel.basicConsume(queueName, true, consumer);

        System.in.read();
    }


    @Test
    public void consumer3() throws IOException, TimeoutException {
        // 为消费者随机取名
        String name = "consumer-" + RandomUtil.randomString(5);

        // 创建连接工厂.
        ConnectionFactory factory = new ConnectionFactory();
        // 创建连接.
        Connection conn = factory.newConnection();
        // 创建信道.
        Channel channel = conn.createChannel();
        // 声明交换机.
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        // 获取一个临时队列.
        String queueName = channel.queueDeclare().getQueue();
        // 队列与交换机绑定(指定 队列名称, 交换机名称, routingKey 忽略)
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(name + " 等待接收消息...");

        // 告诉服务器我们需要哪个频道的消息, 如果频道中有消息, 就会执行回调函数 handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(name + " 接收到消息 '" + new String(body, StandardCharsets.UTF_8) + "'");
            }
        };

        // 自动恢复队列应答 -- RabbitMQ 消息确认机制.
        channel.basicConsume(queueName, true, consumer);

        System.in.read();
    }
}
