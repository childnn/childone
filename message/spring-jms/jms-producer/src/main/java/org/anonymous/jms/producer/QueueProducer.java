package org.anonymous.jms.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.*;

/**
 * @author child
 * 2019/6/26 10:00
 */
@Component
public class QueueProducer {

    private final JmsTemplate jmsTemplate;

    private final Destination destination;

    @Autowired
    public QueueProducer(@Qualifier("queueTextDestination") Destination destination,
                         @Qualifier("jmsTemplate") JmsTemplate jmsTemplate) {
        this.destination = destination;
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * @param text 文本消息
     */
    public void sendQueueTextMessage(final String text) {
        /**
         * 封装对象的创建
         *  1. 创建连接工厂: {@link ConnectionFactory} {@link org.apache.activemq.ActiveMQConnectionFactory}
         *  2. 获取连接(开启连接): {@link Connection}
         *  3. 获取会话对象: {@link Session}
         *  4. 创建队列: {@link Queue} {@link Destination}
         *  5. 创建生产者: {@link MessageProducer}
         */
        jmsTemplate.send(destination, new MessageCreator() {
            /**
             * @param session 会话对象
             * @return 消息对象
             * @throws JMSException
             */
            @Override
            public Message createMessage(Session session) throws JMSException {
                //利用会话对象, 创建 消息对象
                return session.createTextMessage(text);
            }
        });
    }
}
