package org.anonymous.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author child
 * 2019/6/26 15:55
 * 不使用 spring-junit
 */
public class QueueConsumerTest0 {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-jms-queue-consumer.xml");
        applicationContext.start();
        System.in.read();
    }

}
