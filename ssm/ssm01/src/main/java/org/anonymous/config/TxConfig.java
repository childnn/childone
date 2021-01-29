package org.anonymous.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/9/9 20:58
 */
@EnableTransactionManagement //开启 事务支持 //<tx:annotation-driven/> - 使用 spring 的 事务管理器: DataSourceTransactionManager
public class TxConfig {

    @Bean("transactionManager") //事务管理器: 事务管理切面类
    //    @Lazy(true) //定义懒加载
    //    @Scope(value = "singleton", proxyMode = ScopedProxyMode.DEFAULT) //定义 对象属性(单例/多例) 和 代理类别
    public DataSourceTransactionManager getDataSourceTransactionManager(@Qualifier("druid") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
