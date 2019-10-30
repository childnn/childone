package com.example.bootweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableWebMvc
@MapperScan(basePackages = "com.example.bootweb.mapper")
@Controller
@SpringBootApplication
public class BootWebApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(BootWebApplication.class, args);
    }

    @RequestMapping("/index")
    public String index() {
        System.err.println("hello");
        return "index";
    }

  /*  @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();

        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.PrettyFormat);

        converter.setFastJsonConfig(config);

        converters.add(converter);
    }*/
}
