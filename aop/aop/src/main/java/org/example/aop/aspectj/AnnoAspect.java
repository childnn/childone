package org.example.aop.aspectj;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @since 2024/9/12
 */
@Aspect // ajc: Advice must be declared inside an aspect type
public class AnnoAspect {

    @Before("execution(* org.example.aop.*.*(..))")
    public void beforeAdvice() {
        System.out.println("方法执行前...");
    }// aspect


}
