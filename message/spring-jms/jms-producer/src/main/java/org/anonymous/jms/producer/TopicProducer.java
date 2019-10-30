package org.anonymous.jms.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author child
 * 2019/6/26 16:18
 */
@Component
public class TopicProducer {

    private final JmsTemplate jmsTemplate;

    private final Destination destination;

    @Autowired
    public TopicProducer(@Qualifier("topicTextDestination") Destination destination,
                         @Qualifier("jmsTemplate") JmsTemplate jmsTemplate) {
        this.destination = destination;
        this.jmsTemplate = jmsTemplate;
    }

    public void sentTopicTestMessage(final String message) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                System.err.println("topic-producer-session = " + session.getClass());
                return session.createTextMessage(message);
            }
        });
    }
}
