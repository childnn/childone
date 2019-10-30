package org.anonymous.jms.producer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 * @author child
 * 2019/6/25 20:09
 * 一对多: 一个消息可以被多个消费者接收
 * 即时的: 消费者必须先开启, 否则 生产者的消息发送,无人接收意味着浪费
 */
public class TopicConsumer {

    @Test
    public void consumer1() throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.32.46:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("myTopic");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(m -> {
            String text = null;
            try {
                text = ((TextMessage) m).getText();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            System.out.println("text1 = " + text);
        });

        //监听
        System.in.read();

        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void consumer2() throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.32.46:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("myTopic");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(m -> {
            String text = null;
            try {
                text = ((TextMessage) m).getText();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            System.out.println("text2 = " + text);
        });

        //监听
        System.in.read();

        consumer.close();
        session.close();
        connection.close();
    }
}