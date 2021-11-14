package com.example.bootactuator.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;

import javax.validation.constraints.NotNull;
import java.net.InetAddress;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/25 15:58
 */
@ConfigurationProperties(prefix = "my.prop") // setter 方法必须
@Profile("default") // 在指定环境启用该配置
public class MyProperties {

    // Reason: No setter found for property: address
    @NotNull // 可以开启必要的校验
    private String name;

    private InetAddress address;

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InetAddress getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}
