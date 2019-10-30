package org.anonymous.test;

import org.anonymous.jms.producer.TopicProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author child
 * 2019/6/26 16:23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-jms-queue-producer.xml")
public class TopicProducerTest {

    @Autowired
    @Qualifier("topicProducer")
    private TopicProducer topicProducer;

    @Test
    public void test() {
        topicProducer.sentTopicTestMessage("welcome to spring-jms-topic...");
    }

}
