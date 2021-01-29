package org.anonymous.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author child
 * 2019/4/14 16:15
 * 数据库相关的 第三方 资源的 配置: 只用来配置数据库相关,
 * 加载任务 交给 总的 配置类: SpringConfig  -- 配置类 的设置 也要符合 mvc 思想
 */
@PropertySource("classpath:jdbc.properties") //加载 数据库连接配置文件
public class JdbcConfig {
 /*   @Value("${jdbc.driver}") // ?
    private String driver;
    @Value("jdbc.url")
    //@Value("#{jdbc.url}") // ?
    private String url;
    @Value("jdbc.username")
    private String username;
    @Value("jdbc.password")
    private String password;*/

    @Value("${jdbc.driver}")
    private String driver;
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    /*
     * 加载 第三方 资源, 将方法的返回对象 放入 ioc 容器
     */

    //数据库连接池: druid
    @Bean(value = "druid") //将返回的 对象 放入 ioc 容器中
    public DataSource getDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }

    @Bean("jdbcTemplate") //把对象放入容器       //@Qualifier(value=""):  指定容器中的 对象: 给 该参数赋值
    public JdbcTemplate getJdbcTemplate(@Qualifier(value = "druid") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean("dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(driver);
        driverManagerDataSource.setUrl(url);
        driverManagerDataSource.setUsername(username);
        driverManagerDataSource.setPassword(password);
        return driverManagerDataSource;
    }
}
