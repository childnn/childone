package org.example.aop.aspectj;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @since 2024/8/7
 */
public aspect MessageCommunicatorHindiSalutationAspect {

    // Pointcut to capture the deliver() method
    pointcut sayToPerson(String person)
            : call(* org.example.aop.aspectj.MessageCommunicator.deliver(java.lang.String, java.lang.String))
            && args(person, java.lang.String);

    //  Advice to the pointcut
    void around(String person): sayToPerson(person) {
        proceed(person + "-ji");
    }

}
