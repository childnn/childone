package org.example.aop.config.springaop;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.springframework.aop.Pointcut
 * @since 2024/6/11
 */
public class SpringPointcut extends StaticMethodMatcherPointcut {
    /**
     * Perform static checking whether the given method matches.
     * <p>If this returns {@code false} or if the {@link #isRuntime()}
     * method returns {@code false}, no runtime check (i.e. no
     * {@link #matches(java.lang.reflect.Method, Class, Object[])} call)
     * will be made.
     *
     * @param method      the candidate method
     * @param targetClass the target class
     * @return whether this method matches statically
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return true;
    }

}
