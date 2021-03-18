package org.anonymous.nacos.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/18 0:30
 */
@RestController
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ProviderApp {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApp.class, args);
    }


    @GetMapping("get")
    public String get() {
        return "Provider App";
    }
}
