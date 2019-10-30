package org.anonymous.jms.producer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * @author child
 * 2019/6/25 19:53
 * 点对点: 一个消息能且只能被一个消费者接收
 * 先开启的消费者先接收
 * 消息可以预先发送, 存储在消息队列中(topic 模式时, 消息的消费者必须先开启, 消息的生产者发送消息即意味着消费)
 * <p>
 * 多个消费者同时启动， 监听消息， 最终也只会有一个 消费者接收到消息
 */
public class QueueConsumer {

    @Test
    public void consumer1() throws JMSException, IOException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.32.46:61616");
        //获取连接
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //获取 session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列对象
        Queue queue = session.createQueue("myQueue");
        //创建消费者
        MessageConsumer consumer = session.createConsumer(queue);
        //监听消息
        consumer.setMessageListener(message -> {
            try {
                //这里接收的是文本, 就直接强转文本消息对象, 获取文本信息
                String text = ((TextMessage) message).getText();
                System.out.println("queue1: 点对点 = " + text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        //监听
        System.in.read();

        //释放资源
        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void consumer2() throws JMSException, IOException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.32.46:61616");
        //获取连接
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //获取 session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列对象
        Queue queue = session.createQueue("myQueue");
        //创建消费者
        MessageConsumer consumer = session.createConsumer(queue);
        //监听消息
        consumer.setMessageListener(message -> {
            try {
                //这里接收的是文本, 就直接强转文本消息对象, 获取文本信息
                String text = ((TextMessage) message).getText();
                System.out.println("queue1: 点对点 = " + text);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        //监听
        System.in.read();

        //释放资源
        consumer.close();
        session.close();
        connection.close();
    }
}
