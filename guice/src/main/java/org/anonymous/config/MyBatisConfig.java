package org.anonymous.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/6 16:53
 * mybatis-spring-boot 自定义配置.
 */
@Configuration
public class MyBatisConfig {
    @Bean
    ConfigurationCustomizer mybatisConfiguration() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
//                configuration.setEnvironment(new Environment());
            }
        };
    }

    @Bean
    public SqlSessionFactory masterSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        factoryBean.setDataSource();
        factoryBean.setVfs(SpringBootVFS.class);

        return factoryBean.getObject();
    }

}
