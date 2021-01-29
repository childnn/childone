package org.anonymous.config.spring.tx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.springframework.context.annotation.PropertySource 可以查看文档
 * @see org.springframework.transaction.annotation.TransactionManagementConfigurer
 * @since 2020/12/15 8:30
 */
// @Configuration // 在 AppConfig 通过 @Import 引入, 可以不需要此注解
@EnableTransactionManagement //  <tx:annotation-driven transaction-manager="transactionManager"/>
@PropertySource("classpath:jdbc.properties")
public class TxConfig/* implements TransactionManagementConfigurer*/ {

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String pwd;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource(url, username, pwd);
        ds.setDriverClassName(driver);

        return ds;
    }

    @Bean
    public TransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /*
     * 在有多个  TransactionManager 的情况下, 才需要实现此接口的方法
     * 或者, 不实现 TransactionManagementConfigurer 接口, 在多个 TransactionManager 中默认的
     * 一个上使用 @Primary 注解
     * 等价:
     *     先注入
     *     @Bean
     *     public TransactionManager transactionManager() {
     *         return new DataSourceTransactionManager(dataSource());
     *     }
     *      后实现方法
     *      @Override
            public TransactionManager annotationDrivenTransactionManager() {
                return transactionManager();
            }
     */
    /*
    @Bean // 查看文档: 不同的实现方式
    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }*/
}
