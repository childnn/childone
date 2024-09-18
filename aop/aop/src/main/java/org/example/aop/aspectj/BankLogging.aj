package org.example.aop.aspectj;

import java.util.logging.Logger;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @since 2024/9/12
 */
public aspect BankLogging extends AbstractLogging {

    public pointcut logPoints(): call(* org.example.aop.aspectj.MessageCommunicator.*(..));

    @Override
    public Logger getLogger() {
        return Logger.getLogger("banking");
    }
}
