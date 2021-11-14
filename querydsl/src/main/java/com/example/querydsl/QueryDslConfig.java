package com.example.querydsl;

import com.alibaba.druid.pool.DruidDataSource;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.sql.DefaultSQLExceptionTranslator;
import com.querydsl.sql.MySQLTemplates;
import com.querydsl.sql.SQLQueryFactory;
import com.querydsl.sql.SQLTemplates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/18 19:31
 */
@Configuration
public class QueryDslConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String pwd;

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public com.querydsl.sql.Configuration querydslConfiguration() {
        SQLTemplates templates = MySQLTemplates.builder().build();
        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
        configuration.setExceptionTranslator(DefaultSQLExceptionTranslator.DEFAULT);
        return configuration;
    }

    @Bean
    public DataSource druid() {
        final DruidDataSource ds = new DruidDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(pwd);
        return ds;
    }

    @Bean
    public SQLQueryFactory sqlQueryFactory() {
        return new SQLQueryFactory(querydslConfiguration(), druid());
    }

}
