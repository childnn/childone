package org.example.aop.bean;

import org.example.aop.service.AopExpService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2024/6/5
 */
@Configurable/* (preConstruction = true, dependencyCheck = true) */
public class SimplePojo implements Pojo {

    @Autowired
    private AopExpService aopExpService;

    @Override
    public void foo() {
        // this works, but... gah!
        ((Pojo) AopContext.currentProxy()).bar();
    }

    @Override
    public void bar() {
        // some logic...
        aopExpService.txTest();
    }

}
