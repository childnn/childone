package org.anonymous.jms.consumer;

import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author child
 * 2019/6/26 15:02
 * 监听 点对点消息的生产
 * 配置文件加载, 监听器方法自动运行
 */
@Component //ioc
public class QueueConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("org.anonymous.jms.consumer.QueueConsumer.onMessage#textMessage.getText() = " + textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
