package org.anonymous.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static org.anonymous.config.RabbitConfig.EXCHANGE_NAME_OF_DELAYED_MESSAGE;
import static org.anonymous.config.RabbitConfig.ROUTING_KEY_OF_DELAYED_MESSAGE;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/2 16:54
 */
@Component
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Object msg) {
        // 参数一: 交换器名称.
        // 参数二: 路由 key.
        rabbitTemplate.convertAndSend(EXCHANGE_NAME_OF_DELAYED_MESSAGE, ROUTING_KEY_OF_DELAYED_MESSAGE, msg, message -> {
            System.out.println("消息发送时间: " + LocalDateTime.now());
            byte[] body = message.getBody();
            System.out.println("msg = " + new String(body, StandardCharsets.UTF_8));
            message.getMessageProperties().setDelay(5000); // 设置延时时间.
//            message.getMessageProperties().setHeader(MessageProperties.X_DELAY, 3000); // 等价.
            return message;
        });
    }
}
