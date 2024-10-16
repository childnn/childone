package org.anonymous.se;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import static com.rabbitmq.client.ConnectionFactory.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/12 16:16
 * direct 模式必须先启动 consumer 吗?
 */
public class DirectTest {

    public static final String EXCHANGE_NAME = "test_direct_exchange";

    public static final String ROUTING_KEY = "test.direct";

    public static final String QUEUE_NAME = "test_direct_queue";


    @Test
    public void producer() throws IOException, TimeoutException, InterruptedException {
        // 创建 连接工厂.
        ConnectionFactory factory = new ConnectionFactory();
        // 设置 RabbitMQ 相关信息.
        factory.setHost(DEFAULT_HOST);
        factory.setUsername(DEFAULT_USER);
        factory.setPassword(DEFAULT_PASS);
        factory.setVirtualHost(DEFAULT_VHOST);
        factory.setPort(DEFAULT_AMQP_PORT);

        // 创建一个长连接.
        Connection conn = factory.newConnection();
        // 创建 信道 channel. -- 一个连接中有多个信道.
        Channel channel = conn.createChannel();
        channel.confirmSelect();

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) {
                System.out.println("confirm: " + deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) {

            }
        });

        // 交换机: 声明后会在 rabbit 客户端看到该交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, false, true, null);

        // send.
        StringBuilder msg = new StringBuilder("hello world RabbitMQ---Direct Exchange Message...");
        for (int i = 0; i < 5; i++) {
            msg.append(i);
            System.out.println("direct 消息: " + i);
            // 默认的 direct 交换机就是 空字符串(名称)
            // 发送消息: 指定 交换机, 路由 key
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.toString().getBytes(StandardCharsets.UTF_8));
        }

        // When called on a non-Confirm channel, it will throw an IllegalStateException.
        // 必须先调用 channel.confirmSelect();
        // channel.waitForConfirmsOrDie(5000L);
        if (channel.waitForConfirms()) {
            System.out.println("成功消费");
        } else {
            System.out.println("没有成功消费");
        }

        // System.in.read();
        // 释放资源.
        // channel.close();
        // conn.close();

    }

    @Test
    public void consumer() throws IOException, TimeoutException {
        // 创建连接.
        ConnectionFactory factory = new ConnectionFactory();

        factory.setVirtualHost(DEFAULT_VHOST);
        factory.setHost(DEFAULT_HOST);
        factory.setPort(DEFAULT_AMQP_PORT);
        factory.setUsername(DEFAULT_USER);
        factory.setPassword(DEFAULT_PASS);

        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(3000);

        Connection conn = factory.newConnection();

        Channel channel = conn.createChannel();

        // 如果直接声明队列: 默认表示 该队列与 “” 交换机绑定
        // 声明一个交换机.
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, false, true, false, null);
        // 声明一个队列.
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
        // 建立一个绑定关系: 使用 路由 key 绑定 交换机和队列
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumerTag = " + consumerTag);
                System.out.println("receive: " + new String(body));
                System.out.println("envelope = " + envelope);
            }
        };

        // 自动回复队列应答 -- RabbitMQ 中的消息确认机制.
        final String consumerTag = channel.basicConsume(QUEUE_NAME, true, consumer);
        // GetResponse getResponse = channel.basicGet(QUEUE_NAME, true);
        // byte[] body = getResponse.getBody();
        // System.out.println(new String(body));
        System.err.println(consumerTag);

        System.in.read();
       /* while (true) {
            Consumer consumer1 = consumer.getChannel().getD();
        }*/
    }

    @Test
    public void consumer1() throws IOException, TimeoutException {
        // 创建连接.
        ConnectionFactory factory = new ConnectionFactory();

        factory.setVirtualHost(DEFAULT_VHOST);
        factory.setHost(DEFAULT_HOST);
        factory.setPort(DEFAULT_AMQP_PORT);
        factory.setUsername(DEFAULT_USER);
        factory.setPassword(DEFAULT_PASS);

        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(3000);

        Connection conn = factory.newConnection();

        Channel channel = conn.createChannel();

        // 声明一个交换机.
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, false, true, false, null);
        // 声明一个队列.
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
        // 建立一个绑定关系.
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("receive: " + new String(body));
            }
        };

        // 自动回复队列应答 -- RabbitMQ 中的消息确认机制.
        final String consumerTag = channel.basicConsume(QUEUE_NAME, true, consumer);
        // GetResponse getResponse = channel.basicGet(QUEUE_NAME, true);
        // byte[] body = getResponse.getBody();
        // System.out.println(new String(body));
        System.err.println(consumerTag);

        System.in.read();
       /* while (true) {
            Consumer consumer1 = consumer.getChannel().getD();
        }*/
    }

    @Test
    public void consumer2() throws IOException, TimeoutException {
        // 创建连接.
        ConnectionFactory factory = new ConnectionFactory();

        factory.setVirtualHost(DEFAULT_VHOST);
        factory.setHost(DEFAULT_HOST);
        factory.setPort(DEFAULT_AMQP_PORT);
        factory.setUsername(DEFAULT_USER);
        factory.setPassword(DEFAULT_PASS);

        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(3000);

        Connection conn = factory.newConnection();

        Channel channel = conn.createChannel();

        // 声明一个交换机.
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, false, true, false, null);
        // 声明一个队列.
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
        // 建立一个绑定关系.
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("receive: " + new String(body));
            }
        };

        // 自动回复队列应答 -- RabbitMQ 中的消息确认机制.
        final String consumerTag = channel.basicConsume(QUEUE_NAME, true, consumer);
        // GetResponse getResponse = channel.basicGet(QUEUE_NAME, true);
        // byte[] body = getResponse.getBody();
        // System.out.println(new String(body));
        System.err.println(consumerTag);

        System.in.read();
       /* while (true) {
            Consumer consumer1 = consumer.getChannel().getD();
        }*/
    }
}
