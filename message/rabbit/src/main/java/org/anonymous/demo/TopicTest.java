package org.anonymous.demo;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/14 19:54
 * 主题模式: 多对多.
 */
public class TopicTest {

    public final static String EXCHANGE_NAME = "test_topic_exchange";

    public final static String[] ROUTING_KEYS = {
            "usa.news",
            "usa.weather",
            "europe.news",
            "europe.weather",
    };

    public final static String ROUTING_KEY_USA = "usa.*";

    public final static String ROUTING_KEY_NEWS = "*.news";

    public static final String[] MESSAGES = {
            "美国新闻",
            "美国天气",
            "欧洲新闻",
            "欧洲天气",
    };

    @Test
    public void producer() throws IOException, TimeoutException {
        // 创建连接工厂.
        ConnectionFactory factory = new ConnectionFactory();
        // 设置连接属性...这里使用默认值
        Connection conn = factory.newConnection();
        // 创建通道.
        Channel channel = conn.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        for (int i = 0; i < ROUTING_KEYS.length; i++) {
            String routingKey = ROUTING_KEYS[i];
            String msg = MESSAGES[i];

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());

            System.out.printf("发送消息到路由: %s, 内容是: %s%n ", routingKey, msg);
        }

        System.in.read();

        // 关闭通道和连接.
        channel.close();
        conn.close();

    }

    @Test
    public void consumer4USA() throws IOException, TimeoutException {
        // 当前消费者名.
        String name = "consumer-usa";

        // 创建连接工厂.
        ConnectionFactory factory = new ConnectionFactory();
        // 获取连接.
        Connection conn = factory.newConnection();
        // 创建信道.
        Channel channel = conn.createChannel();
        // 声明交换机.
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        // 获取临时队列.
        String queueName = channel.queueDeclare().getQueue();

        // 接收 USA 信息.
        channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY_USA);
        System.out.println(name + " 等待接收信息...");

        // 告诉服务器我们需要哪个频道的消息, 如果频道中有消息, 就会执行回调函数 handle.
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(name + " 接收到消息 '" + new String(body, StandardCharsets.UTF_8) + "'");
            }
        };

        // 自动回复队列应答 -- RabbitMQ 中的消息确认机制.
        channel.basicConsume(queueName, true, consumer);

        System.in.read();
    }

    @Test
    public void consumer4NEWS() throws IOException, TimeoutException {
        // 当前消费者名.
        String name = "consumer-news";

        // 创建连接工厂.
        ConnectionFactory factory = new ConnectionFactory();
        // 获取连接.
        Connection conn = factory.newConnection();
        // 创建信道.
        Channel channel = conn.createChannel();
        // 声明交换机.
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        // 获取临时队列.
        String queueName = channel.queueDeclare().getQueue();

        // 接收 USA 信息.
        channel.queueBind(queueName, EXCHANGE_NAME, ROUTING_KEY_NEWS);
        System.out.println(name + " 等待接收信息...");

        // 告诉服务器我们需要哪个频道的消息, 如果频道中有消息, 就会执行回调函数 handle.
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(name + " 接收到消息 '" + new String(body, StandardCharsets.UTF_8) + "'");
            }
        };

        // 自动回复队列应答 -- RabbitMQ 中的消息确认机制.
        channel.basicConsume(queueName, true, consumer);

        System.in.read();
    }
}
