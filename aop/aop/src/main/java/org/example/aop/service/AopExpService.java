package org.example.aop.service;

import org.springframework.stereotype.Component;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator
 * @since 2024/6/4
 */
@Component
public class AopExpService {

    public String txTest() {
        System.out.println("AopExpService txTest");
        return "AopExpService txTest result";
    }

}
