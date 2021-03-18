package org.anonymous.nacos.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/17 14:39
 */
@RestController
public class ExtensionConfig implements EnvironmentAware {

    @Value("${another_new_group.xyz}")
    private String extConfig;

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @GetMapping("extConfig")
    public String getExtConfig() {
        return env.getProperty("another_new_group.xyz");
    }


}
