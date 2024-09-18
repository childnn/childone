package org.example.aop.aspectj;

interface BankingEntity {
}

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @since 2024/8/7
 */
public aspect ExampleAspect {

    // Advice
    before(): execution(void org.example.aop.aspectj.Account.credit(float)) {
        System.out.println("About to perform credit operation");
    }

    // static introduction
    declare parents:Account implements BankingEntity;

    // compile-time declaration
    declare warning : call(void org.example.aop.aspectj.Persistence.save(java.lang.Object))
            :"Consider using Persistence.saveOptimized()";
}

class Persistence {
    void save(Object obj) {
        // ...
    }
}
