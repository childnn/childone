package org.anonymous.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/2 16:38
 */
@Configuration
public class RabbitConfig {

    // 延时插件定义的 交换器类型, 名称固定.
    public static final String EXCHANGE_TYPE_OF_DELAYED_MESSAGE = "x-delayed-message";
    // 延时交换器名称.
    public static final String EXCHANGE_NAME_OF_DELAYED_MESSAGE = "test-delayed";
    // 延时路由 key.
    public static final String ROUTING_KEY_OF_DELAYED_MESSAGE = "test-delayed-routing-key";
    // 延时队列名称.
    public static final String QUEUE_NAME_OF_DELAYED_MESSAGE = "test_delay_queue";

    /**
     * 交换器.
     *
     * @return 延时交换器.
     */
    @Bean
    public CustomExchange delayExchange() {
        // 参数一: 交换器名称. 自定义.
        // 参数二: 交换器类型. 固定.
        return new CustomExchange(EXCHANGE_NAME_OF_DELAYED_MESSAGE, EXCHANGE_TYPE_OF_DELAYED_MESSAGE, true, false,
                Collections.singletonMap("x-delayed-type", "direct"));
    }

    // 延时队列.
    @Bean
    public Queue queue() {
//        return QueueBuilder.
        return new Queue(QUEUE_NAME_OF_DELAYED_MESSAGE);
    }

    // 延时 binding: 将延时队列绑定到延时交换器.
    @Bean
    public Binding binding(@Qualifier("queue") Queue queue, @Qualifier("delayExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_OF_DELAYED_MESSAGE).noargs();
    }

}
