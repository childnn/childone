package org.anonymous.nacos.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/18 0:35
 */
@FeignClient("spring-cloud-alibaba-service-provider")
public interface ProviderFeign {

    @GetMapping("get")
    String get();
}
