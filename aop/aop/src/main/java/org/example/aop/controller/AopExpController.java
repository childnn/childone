package org.example.aop.controller;

import org.example.aop.service.AopExpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2024/6/4
 */
@Component
public class AopExpController {
    @Autowired
    private AopExpService aopExpService;

    public String txTest() {
        System.out.println("AopExpController txTest");
        return aopExpService.txTest();
    }


}
