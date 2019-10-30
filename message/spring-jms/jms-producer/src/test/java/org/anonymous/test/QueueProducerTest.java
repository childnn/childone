package org.anonymous.test;

import org.anonymous.jms.producer.QueueProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author child
 * 2019/6/26 15:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-jms-queue-producer.xml")
public class QueueProducerTest {

    @Autowired
    @Qualifier("queueProducer")
    private QueueProducer queueProducer;

    @Test
    public void test() {
        queueProducer.sendQueueTextMessage("welcome to spring-jms-queue...");
    }

}
