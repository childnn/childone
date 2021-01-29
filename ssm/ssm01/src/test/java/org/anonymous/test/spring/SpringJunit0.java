package org.anonymous.test.spring;

import org.anonymous.config.ApplicationContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author child
 * 2019/4/24 11:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationContextConfig.class)
public class SpringJunit0 {

    @Test
    public void test() {

    }
}
