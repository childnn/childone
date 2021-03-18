package org.anonymous.service;

import org.anonymous.dao.annotation.UserDao;
import org.anonymous.domain.User;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/12/15 10:41
 */
@Service
public class TxService implements ApplicationContextAware {

    // private ApplicationContext ctx;

    @Autowired
    private SqlSessionFactory ssf;

    @Autowired
    private UserDao mapper;

    /**
     * 注释 @Transactional 注解, 查看运行结果
     * 不使用事务注解, 则默认会调用 session.commit(); 清空一级缓存
     * 在 mybatis-spring 中, SqlSession 是动态代理生成
     *
     * @see org.mybatis.spring.SqlSessionTemplate#sqlSessionProxy
     * 源码:
     * @see org.mybatis.spring.SqlSessionTemplate.SqlSessionInterceptor
     * if (!isSqlSessionTransactional(sqlSession, SqlSessionTemplate.this.sqlSessionFactory)) {
     * // force commit even on non-dirty sessions because some databases require
     * // a commit/rollback before calling close()
     * sqlSession.commit(true);
     * }
     */
    // @Transactional
    public void execute() {
        // UserDao mapper = ctx.getBean(UserDao.class);
        // 如果不开启事务, 每次执行方法后都会 commit, 即关闭当前的 SqlSession, 每次都重新获取 SqlSession.
        // 因此一级缓存立即失效.
        User u1 = mapper.findById(1);
        System.err.println(u1);
        User u2 = mapper.findById(1);
        System.err.println(u1 == u2);

        User u3 = mapper.findById(2);
        System.err.println("u3 = " + u3);

        User u4 = mapper.findById(1);

        System.err.println(u4 == u1);

        // User u5 = mapper.findById();
        // System.err.println("u1 == u5: " + (u1 == u5));
    }

    public Configuration mybatisConfig() {
        return ssf.getConfiguration();
    }


    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        // this.ctx = ctx;
    }
}
