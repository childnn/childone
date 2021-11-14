package config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/30 11:49
 * @see PropertySourcesPlaceholderConfigurer
 */
@Configuration
@PropertySource({"classpath:app.properties"})
// @PropertySource({"classpath:app.yml"})
public class MyConfig {

    @Value("${my.test.name}")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
