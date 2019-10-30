package org.anonymous.jms.producer;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author child
 * 2019/6/25 19:40
 */
public class QueueProducer {

    public static void main(String[] args) throws JMSException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.32.46:61616");
        //获取连接
        Connection connection = connectionFactory.createConnection();
        //启动连接
        connection.start();
        //获取 session: 会话对象
        // 参数一: 事务, 参数二: 消息确认方式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建队列: 指定队列名称
        Queue queue = session.createQueue("myQueue");

        //创建消息生产者对象
        MessageProducer producer = session.createProducer(queue);
        //创建消息对象: 文本
        TextMessage textMessage = session.createTextMessage("welcome to activeMQ");
        //发送消息
        producer.send(textMessage);

        //释放资源
        producer.close();
        session.close();
        connection.close();

    }
}
