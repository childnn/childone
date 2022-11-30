package org.anonymous.se;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/11/8 22:33
 * 默认交换机: ”“
 *
 */
public class DefaultDirectTest {

    // The default exchange is implicitly bound to every queue, with a routing key equal to the queue name.
    // It is not possible to explicitly bind to, or unbind from the default exchange.
    // It also cannot be deleted.
    public static final String DEFAULT_EXCHANGE = "";
    public static final String ROUTING_KEY = "default.direct";
    public static final String QUEUE = "default.direct.queue";

    // 向默认交换机发送消息
    // 指定 路由 key(队列): 此时 队列 即为 路由 key
    @Test
    public void producer() throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connFac = new ConnectionFactory();
        // connFac.setHost();
        // connFac.setPort();
        // connFac.setUsername();
        // connFac.setPassword();
        // connFac.setVirtualHost();
        // connFac.setNioParams();

        // 连接
        Connection conn = connFac.newConnection();

        // 信道
        Channel chan = conn.createChannel();

        chan.confirmSelect();

        StringBuilder sb = new StringBuilder("消息: ");

        for (int i = 1; i < 5; i++) {
            sb.append(i);

            // 参数二正常为 路由 key
            // 但是当使用 默认的交换机 ”“ 时, 直接使用 消费者的 队列名
            // 即此时 路由 key == 对列名
            chan.basicPublish(DEFAULT_EXCHANGE, QUEUE, null, sb.toString().getBytes(StandardCharsets.UTF_8));
        }

        chan.waitForConfirmsOrDie(5000L);

        chan.close();
        conn.close();

    }


    @Test
    public void consumer() throws IOException, TimeoutException {
        ConnectionFactory connFac = new ConnectionFactory();
        // 连接
        Connection conn = connFac.newConnection();

        // 信道
        Channel chan = conn.createChannel();

        // 队列
        // auto-delete: 当消费者关闭时, 队列自动删除
        // 在 rabbit 客户端 queues 页面可以查看到队列随着 consumer 的启停 新增和删除
        chan.queueDeclare(QUEUE, false, false, true, null);

        // 消费
        chan.basicConsume(QUEUE, true, "我是消费者", new DefaultConsumer(chan) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("consumerTag = " + consumerTag);
                System.out.println("envelope = " + envelope);
                System.out.println("body = " + new String(body));
            }
        });

        System.in.read();
    }

}
