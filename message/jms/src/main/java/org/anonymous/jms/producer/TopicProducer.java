package org.anonymous.jms.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author child
 * 2019/6/25 20:03
 */
public class TopicProducer {

    public static void main(String[] args) throws JMSException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.32.46:61616");
        //获取连接
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();

        //获取 session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建 主题 对象
        Topic topic = session.createTopic("myTopic");
        //创建消息生产者对象
        MessageProducer producer = session.createProducer(topic);
        //创建文本消息对象
        TextMessage textMessage = session.createTextMessage("welcome to myTopic");
        //发送消息
        producer.send(textMessage);

        //释放资源
        producer.close();
        session.close();
        connection.close();
    }
}
