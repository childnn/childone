package com.example.cloudribbon.ribbon;

import com.example.cloudribbon.entity.PortEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/12/6 20:07
 */
@Component
public class PortRibbon {
    @Autowired
    private RestTemplate restTemplate;

    public List<PortEntity> ports() {
        // 启动多个不同端口的 product-data-service 服务, 重复请求 Ribbon-Controller, 查看返回信息(在不同的端口之间请求)
        return restTemplate.getForObject("http://product-data-service/ports", List.class);
    }
}
