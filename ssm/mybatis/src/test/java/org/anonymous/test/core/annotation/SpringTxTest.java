package org.anonymous.test.core.annotation;

import org.anonymous.config.AppConfig;
import org.anonymous.dao.annotation.UserDao;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/12/15 8:29
 * 正确方式参见:
 * @see MyBatisSpring
 * @see org.springframework.test.context.ContextConfiguration
 * @see org.springframework.test.context.transaction.TransactionalTestExecutionListener
 */
public class SpringTxTest {

    // @Transactional // 非 spring-boot-test 环境, 不能注解这样加注解
    @Test
    public void tx() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        UserDao mapper = ctx.getBean(UserDao.class);
        mapper.findById(1);
        mapper.findById(1);
    }

}
