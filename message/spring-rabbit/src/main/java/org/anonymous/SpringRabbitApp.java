package org.anonymous;

import org.anonymous.sender.MessageSender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/2 16:34
 */
@SpringBootApplication
public class SpringRabbitApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringRabbitApp.class, args);
        MessageSender sender = ctx.getBean(MessageSender.class);
        sender.send("Hello Rabbit~~~");
    }
}
