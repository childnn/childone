package org.anonymous.config;

import org.anonymous.config.mybatis.spring.MyBatisConfig;
import org.anonymous.config.spring.tx.TxConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.springframework.context.annotation.ConfigurationClassParser 递归解析
 * @since 2020/12/15 9:31
 */
@ComponentScan(basePackages = {"org.anonymous.config", "org.anonymous.se", "org.anonymous.service"})
@Configuration
@Import({TxConfig.class, MyBatisConfig.class})
// @ImportResource("classpath:mybatis-spring.xml") // 引入 xml-based config
public class AppConfig {

}
