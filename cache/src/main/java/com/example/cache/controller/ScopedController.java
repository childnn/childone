package com.example.cache.controller;

import com.example.cache.scopedproxy.SingletonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2023/7/18
 */
@RestController
public class ScopedController {
    @Autowired
    private SingletonService singletonService;

    @GetMapping("test")
    public String test() {
        singletonService.test();
        return "hello";
    }

}
