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
 * 2019/6/26 12:13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appContext-jms-producer.xml")
public class QueueTest {

    @Autowired
    @Qualifier("queueProducer")
    private QueueProducer queueProducer;

    @Test
    public void test() {
        queueProducer.sendTextMessage("greeting from spring-jms...");
    }
}
