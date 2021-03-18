package org.anonymous.nacos1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/17 23:16
 * @see org.springframework.cloud.client.SpringCloudApplication
 */
@SpringBootApplication
public class NacosApp1 {

    public static void main(String[] args) {
        SpringApplication.run(NacosApp1.class, args);
    }

    @Value("${another_new_group.xyz}")
    private String xyz;

    @PostConstruct
    public void init() {
        System.out.println("xyz = " + xyz);
    }

}
