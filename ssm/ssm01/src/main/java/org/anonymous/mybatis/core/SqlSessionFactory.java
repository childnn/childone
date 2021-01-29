package org.anonymous.mybatis.core;

import org.anonymous.beans.Configuration;
import org.anonymous.mybatis.core.impl.AnnotationSqlSession;
import org.anonymous.mybatis.core.impl.DefaultSqlSession;

/**
 * @author child
 * 2019/4/9 21:48
 * 工厂模式
 * 生产 操作数据库的 SqlSession 接口的实现类 对象
 */
public class SqlSessionFactory {
    private final Configuration configuration;

    public SqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    //生产 SqlSession: DefaultSqlSession
    public SqlSession open(boolean isAnnotation) {
        return isAnnotation ? new AnnotationSqlSession(configuration) : new DefaultSqlSession(configuration);
    }

}
