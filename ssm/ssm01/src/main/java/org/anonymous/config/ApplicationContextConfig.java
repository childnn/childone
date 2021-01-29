package org.anonymous.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author child
 * 2019/4/24 11:18
 */
@Configuration
@ComponentScan(basePackages = "org.anonymous")
@Import(JdbcConfig.class)
@Deprecated
public class ApplicationContextConfig {

    @Bean("jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(@Qualifier("druid") DataSource dataSource) {

        return new JdbcTemplate(dataSource);
    }

   /* @Bean("jdbcSupport")
    public JdbcSupport getJdbcDaoSupport(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate) {

        JdbcSupport jdbcSupport = new JdbcSupport();
        jdbcSupport.setJdbcTemplate(jdbcTemplate);
        return jdbcSupport;
    }

    @Bean("jdbcSupport0")
    public JdbcSupport getJdbcSupport(@Qualifier("dataSource") DataSource dataSource) {
        JdbcSupport jdbcSupport = new JdbcSupport();
        jdbcSupport.setDataSource(dataSource);

        return jdbcSupport;
    }*/

}
