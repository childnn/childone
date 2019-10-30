package org.anonymous;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/10 23:15
 */
@SpringBootApplication(scanBasePackages = {"org.anonymous"})
public class TaskApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(TaskApplication.class, args);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
