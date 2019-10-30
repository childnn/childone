package com.example.mock.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/9/27 16:38
 */
@RestController
public class MockHelloController {

    @RequestMapping("/")
    public String mock(String name) {
        return "hello," + name;
    }

}
