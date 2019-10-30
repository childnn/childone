package com.example.demo.controller;

import com.example.demo.spi.controller.HelloControllerInterface;
import org.springframework.web.bind.annotation.*;

/**
 * @author MiaoOne
 * 2019/7/31 10:58
 */
@RestController
@RequestMapping("/hello")
public class HelloController implements HelloControllerInterface {

    @GetMapping("/nihao")
    @Override
    public String hello(String name) {
        return "welcome to swagger! " + name;
    }

    /**
     * post 提交: 必须使用
     * @param aaa
     * @return
     */
    @PostMapping("/test")
    @Override
    public String greet(@RequestBody String aaa) {
        return "best wishes for you! " + aaa;
    }
}
