package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/30 11:49
 */
@Configuration
@PropertySource({"classpath:app.yml"})
public class MyConfig {

    @Value("${my.test.name}")
    private String name;
}
