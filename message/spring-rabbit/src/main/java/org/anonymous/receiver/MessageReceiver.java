package org.anonymous.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static org.anonymous.config.RabbitConfig.QUEUE_NAME_OF_DELAYED_MESSAGE;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/2 17:09
 */
@Component
//@RabbitListener(queues = {QUEUE_NAME_OF_DELAYED_MESSAGE}) // 结合 {@code org.springframework.amqp.rabbit.annotation.RabbitHandler}
public class MessageReceiver {

    @RabbitListener(queues = {QUEUE_NAME_OF_DELAYED_MESSAGE})
//    @RabbitHandler
    public void receive(String msg) {
        System.out.println("消息接收时间: " + LocalDateTime.now());
        System.out.println(msg);
    }
}
