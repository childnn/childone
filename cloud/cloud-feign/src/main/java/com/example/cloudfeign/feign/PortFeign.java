package com.example.cloudfeign.feign;

import com.example.cloudfeign.entity.PortEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/12/6 20:38
 * feign 是对 ribbon 的封装, 不需要使用 {@link org.springframework.web.client.RestTemplate}
 */
@FeignClient("product-data-service")
public interface PortFeign {

    @GetMapping("ports")
    List<PortEntity> ports();

}
