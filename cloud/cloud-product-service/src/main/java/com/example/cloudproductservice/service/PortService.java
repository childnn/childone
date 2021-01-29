package com.example.cloudproductservice.service;

import com.example.cloudproductservice.entity.PortEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/12/6 19:58
 */
@Service
public class PortService {
    @Value("${spring.profiles.active}")
    private String profile;
    @Value("${server.port}")
    private int port;
    @Value("${spring.application.name}")
    private String appName;

    public List<PortEntity> ports() {
        return Collections.singletonList(new PortEntity(port, appName, profile));
    }

}
