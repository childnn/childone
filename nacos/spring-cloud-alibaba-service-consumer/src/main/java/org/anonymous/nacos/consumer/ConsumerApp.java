package org.anonymous.nacos.consumer;

import org.anonymous.nacos.consumer.feign.ProviderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/18 0:36
 */
@RestController
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApp.class, args);
    }

    @Autowired
    private ProviderFeign providerFeign;

    @RequestMapping("get")
    public String get() {
        return providerFeign.get();
    }
}
