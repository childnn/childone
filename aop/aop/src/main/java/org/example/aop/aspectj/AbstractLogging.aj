package org.example.aop.aspectj;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @since 2024/9/12
 * 抽象切面
 * Note that an abstract aspect by itself does not cause any weaving to occur; you must provide con- crete subaspects to do so.
 */
public abstract aspect AbstractLogging {

    public abstract pointcut logPoints();
    public abstract Logger getLogger();

    before(): logPoints() {
        getLogger().log(Level.INFO, "Before: " + thisJoinPoint);
    }

}
