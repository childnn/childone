package org.anonymous.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author child
 * 2019/4/14 15:51
 * 只要加载 配置类,配置类中的方法会全部执行, 将创建的对象,放在 ioc 容器中
 */
//配置类代替 xml 配置文件
@Configuration //定义 当前类 为配置类
//加载自己资源的 注解
@ComponentScan(basePackages = "org.anonymous") //开启 注解扫描器: <context:component-scan base-package="org.anonymous"/>
//加载 properties 配置文件
//@PropertySource(value = "classpath:jdbc.properties") //<context:property-placeholder location="classpath:jdbc.properties"/>
@Import(value = {JdbcConfig.class, ServiceConfig.class, AspectConfig.class, TxConfig.class}) //引入外部配置类: Class 数组 -- mvc
public class SpringConfig {
   /* @Value("jdbc.driver")
    private String driver;
    @Value("jdbc.url")
    private String url;
    @Value("jdbc.username")
    private String username;
    @Value("jdbc.password")
    private String password;
    *//*
     * 加载 第三方 资源, 将方法的返回对象 放入 ioc 容器
     *//*

    //数据库连接池: druid
    @Bean(value ="druid") //将返回的 对象 放入 ioc 容器中
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
    }*/


}
