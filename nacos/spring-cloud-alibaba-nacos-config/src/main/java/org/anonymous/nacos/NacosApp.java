package org.anonymous.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.anonymous.nacos.entity.TestConfigEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Properties;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/17 9:05
 * @see org.springframework.context.support.ApplicationContextAwareProcessor
 * @see com.alibaba.cloud.nacos.NacosConfigBootstrapConfiguration
 * @see org.springframework.boot.context.properties.bind.JavaBeanBinder properties-bean 赋值
 */
@RestController
@SpringBootApplication
public class NacosApp implements EnvironmentAware, // 获取 env
        ApplicationContextAware, // 获取 ctx
        InitializingBean { // init-method

    private final Logger log = LoggerFactory.getLogger(getClass());

    private ConfigurableApplicationContext ctx;

    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(NacosApp.class, args);
    }

    /**
     * 当 nacos 配置更新时, 使用 @Value 获取的值不会同步更新
     * 而通过 spring-env 获取的值会立即更新
     */
    @Value("${my-config.key1}")
    private String key1;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.cloud.nacos.config.file-extension}")
    private String fileExt;

    @Value("${spring.cloud.nacos.config.group}")
    private String group;

    @Value("${spring.cloud.nacos.config.namespace}")
    private String namespace;

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;

    private String dataId;

    // con't refresh
    @GetMapping("key1")
    public String key1() {
        return key1;
    }

    // can refresh
    @GetMapping("config")
    public String config() {
        return env.getProperty("my-config.key1");
    }

    @GetMapping("config/{id}")
    public String config(@PathVariable("id") String id) {
        return ctx.getEnvironment().getProperty("my-config.key" + id);
    }

    /**
     * 覆盖原始配置
     */
    @PostMapping("put")
    public String put() throws JsonProcessingException, NacosException {
        @SuppressWarnings("serial")
        Properties prop = new Properties() {{
            put(PropertyKeyConst.NAMESPACE, namespace);
            put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        }};

        final ConfigService configService = NacosFactory.createConfigService(prop);
        configService.publishConfig(dataId, group, new ObjectMapper().writeValueAsString(
                new TestConfigEntity(LocalDateTime.now())));

        return "OK";
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("init---env");
        this.env = environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("init---ctx");
        if (applicationContext instanceof ConfigurableApplicationContext) {
            this.ctx = (ConfigurableApplicationContext) applicationContext;
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("after properties set");
        // 测试几种方式获取的 env 是否相同
        // System.out.println(env);
        // System.out.println(ctx.getEnvironment());
        System.out.println(env == ctx.getEnvironment());

        this.dataId = appName + "." + fileExt;
        Assert.notNull(dataId, "init data-id error...");
        // Assert.notNull(namespace, "init namespace error..");
        log.info("namespace: [{}], group: [{}], data-id: [{}], server-addr: [{}]",
                namespace, group, dataId, serverAddr);

    }
}
