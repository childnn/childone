package org.anonymous.demo1.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.anonymous.demo1.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicProducer {

    /**
     * exchange
     */
    public static final String EXCHANGE_TOPIC = "topic_exchange";

    /**
     * queue
     */
    public static final String QUEUE_TOPIC_1 = "topic_queue_1";
    public static final String QUEUE_TOPIC_2 = "topic_queue_2";

    /**
     * routing key
     */
    public static final String ROUTING_KEY_ITEM_ALL = "item.*";
    public static final String ROUTING_KEY_ITEM_DELETE = "item.delete";
    public static final String ROUTING_KEY_ITEM_UPDATE = "item.update";
    public static final String ROUTING_KEY_ITEM_INSERT = "item.insert";


    /***
     * 订阅模式-Topic
     * @param args
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建链接对象
        Connection connection = ConnectionUtil.getConnection();

        //创建频道
        Channel channel = connection.createChannel();

        /**
         * 声明交换机
         * 参数1：交换机名称
         * 参数2：交换机类型，fanout、topic、direct、headers
         */
        channel.exchangeDeclare(EXCHANGE_TOPIC, BuiltinExchangeType.TOPIC);

        /**
         * 声明队列
         * 参数1：队列名称
         * 参数2：是否定义持久化队列
         * 参数3：是否独占本次连接
         * 参数4：是否在不使用的时候自动删除队列
         * 参数5：队列其它参数
         */
        channel.queueDeclare(QUEUE_TOPIC_1, true, false, false, null);
        channel.queueDeclare(QUEUE_TOPIC_2, true, false, false, null);

        //队列绑定交换机
        // 当前 channel 的 交换机 会根据 routingKey 将消息路由到指定的 监听队列. 
        channel.queueBind(QUEUE_TOPIC_1, EXCHANGE_TOPIC, ROUTING_KEY_ITEM_UPDATE);
        channel.queueBind(QUEUE_TOPIC_1, EXCHANGE_TOPIC, ROUTING_KEY_ITEM_DELETE);
        channel.queueBind(QUEUE_TOPIC_2, EXCHANGE_TOPIC, ROUTING_KEY_ITEM_ALL);

        //消息-item.insert
        String messageInsert = "发布订阅模式-Topic-item.insert: 欢迎来到AMQP-RabitMQ！";
        /**
         * 消息发送到指定的 交换机
         * 参数1：交换机名称，如果没有指定则使用默认Default Exchage
         * 参数2：路由key,简单模式可以传递队列名称
         * 参数3：消息其它属性
         * 参数4：消息内容
         */
        channel.basicPublish(EXCHANGE_TOPIC, ROUTING_KEY_ITEM_INSERT, null, messageInsert.getBytes());

        //消息-item.update
        String message_update = "发布订阅模式-Topic-item.update: 欢迎来到AMQP-RabitMQ！";
        channel.basicPublish(EXCHANGE_TOPIC, ROUTING_KEY_ITEM_UPDATE, null, message_update.getBytes());

        //消息-item.delete
        String message_delete = "发布订阅模式-Topic-item.delete: 欢迎来到AMQP-RabitMQ！";
        channel.basicPublish(EXCHANGE_TOPIC, ROUTING_KEY_ITEM_DELETE, null, message_delete.getBytes());

        //关闭资源
        channel.close();
        connection.close();
    }
}
