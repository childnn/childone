package org.anonymous.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @author child
 * 2019/6/26 15:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-jms-queue-consumer.xml")
public class QueueConsumerTest {

 /*   @Autowired
    @Qualifier("queueConsumer")
    private QueueConsumer queueConsumer;*/

    @Test
    public void test() throws IOException {
        System.in.read();
    }

}
