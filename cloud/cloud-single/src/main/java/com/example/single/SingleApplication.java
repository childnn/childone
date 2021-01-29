package com.example.single;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @see EnableConfigurationProperties
 * @see org.springframework.boot.context.properties.ConfigurationProperties
 */
@SpringBootApplication
public class SingleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SingleApplication.class, args);
    }

}
