package org.example.aop.aspectj;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @since 2024/8/7
 */
public aspect MannersAspect {

    // pointcut declaration
    public pointcut deliverMessage()
            : call(* org.example.aop.aspectj.MessageCommunicator.deliver(..));

    // advice
    before(): deliverMessage() {
        System.out.println("Hello! ");
    }

}
