package com.example.demo.service;

import org.springframework.stereotype.Service;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2023/7/14
 */
@Service
public class ServiceReplace {

    public void original(String name) {
        System.out.println("我是原始代码:" + name);
    }

}
