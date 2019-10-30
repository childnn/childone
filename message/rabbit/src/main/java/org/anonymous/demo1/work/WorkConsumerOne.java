package org.anonymous.demo1.work;

import com.rabbitmq.client.*;
import org.anonymous.demo1.util.ConnectionUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class WorkConsumerOne {

    /***
     * 消息消费者
     * @param args
     * @throws IOException
     * @throws TimeoutException
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建链接
        Connection connection = ConnectionUtil.getConnection();

        //创建频道
        Channel channel = connection.createChannel();

        //创建队列
        channel.queueDeclare("work_queue", true, false, false, null);

        //创建消费者，并设置消息处理
        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            /***
             * @param consumerTag   消息者标签，在channel.basicConsume时候可以指定
             * @param envelope      消息包的内容，可从中获取消息id，消息routingkey，交换机，消息和重传标志(收到消息失败后是否需要重新发送)
             * @param properties    属性信息
             * @param body           消息
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //路由的key
                String routingKey = envelope.getRoutingKey();
                //获取交换机信息
                String exchange = envelope.getExchange();
                //获取消息ID
                long deliveryTag = envelope.getDeliveryTag();
                //获取消息信息
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Work-One:routingKey:" + routingKey + ",exchange:" + exchange + ",deliveryTag:" + deliveryTag + ",message:" + message);
            }
        };

        /**
         * 消息监听
         * 参数1：队列名称
         * 参数2：是否自动确认，设置为true为表示消息接收到自动向mq回复接收到了，mq接收到回复会删除消息，设置为false则需要手动确认
         * 参数3：消息接收到后回调
         */
        channel.basicConsume("work_queue", true, defaultConsumer);

        //关闭资源(不建议关闭，建议一直监听消息)
        //channel.close();
        //connection.close();
    }
}
