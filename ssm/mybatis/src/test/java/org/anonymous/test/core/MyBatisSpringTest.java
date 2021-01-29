package org.anonymous.test.core;

import org.anonymous.dao.annotation.UserDao;
import org.anonymous.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.mybatis.spring.mapper.MapperFactoryBean factory-bean 会优先加载
 * @see org.mybatis.spring.mapper.MapperScannerConfigurer#postProcessBeanDefinitionRegistry(org.springframework.beans.factory.support.BeanDefinitionRegistry)
 * --- mybatis-spring 一级缓存无事务时执行清空的源码:
 * @see org.mybatis.spring.SqlSessionTemplate.SqlSessionInterceptor
 * java-code:
 * if (!isSqlSessionTransactional(sqlSession, SqlSessionTemplate.this.sqlSessionFactory)) {
 * // force commit even on non-dirty sessions because some databases require
 * // a commit/rollback before calling close()
 * sqlSession.commit(true);
 * }
 * ---- spring-boot
 * @see org.springframework.boot.context.properties.EnableConfigurationProperties#value()
 * value 属性设置 class, 等价于依赖注入, spring 会自动注入 value 设置的 class
 * 或者使用 {@link org.springframework.context.annotation.Bean} 等 IoC 注解等同
 * ---
 * todo:
 * 1. {@link org.springframework.transaction.annotation.Transactional} 注解如何生效?
 * 2. {@link org.springframework.boot.context.properties.EnableConfigurationProperties}
 * 与 {@link org.springframework.boot.context.properties.ConfigurationPropertiesScan} 比较
 * @since 2020/12/13 17:34
 */
public class MyBatisSpringTest {

    // 注解形式参见: org.anonymous.demo.Demo
    // @Transactional // 使用事务注解时, 需要开启注解支持: <tx:annotation-driven transaction-manager="transactionManager"/>
    @Test
    public void myBatisSpring() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("mybatis-spring.xml");

        // 同一事务, 使用同一个 SqlSession. 不使用事务, 两条 sql 则为两次事务
        // 注释以下两行事务相关代码, 查看运行结果.
        // DataSourceTransactionManager txManager = ctx.getBean(DataSourceTransactionManager.class);
        // TransactionStatus transaction = txManager.getTransaction(new DefaultTransactionDefinition());
        //---------------------------------------------

        UserDao mapper = ctx.getBean(UserDao.class);
        // 不同的 session: @see: org.mybatis.spring.SqlSessionTemplate.sqlSessionProxy 动态代理生成
        User u1 = mapper.findById(1);
        User u2 = mapper.findById(1);
        System.out.println(u1 == u2);


        SqlSessionFactory ssf = ctx.getBean(SqlSessionFactory.class);
        // 同一个 session
        SqlSession ss = ssf.openSession();
        UserDao mapper1 = ss.getMapper(UserDao.class);
        System.out.println(mapper1.findById(1) == mapper1.findById(1));

    }
}
