package org.example.aop.config.springaop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.springframework.aop.support.DefaultPointcutAdvisor
 * It is possible to mix advisor and advice types in Spring in the same AOP proxy.
 * For example, you could use an interception around advice, throws advice, and before advice in one proxy configuration.
 * Spring automatically creates the necessary interceptor chain.
 * @see org.springframework.aop.aspectj.AspectJExpressionPointcut
 * @since 2024/6/13
 * In Spring, an Advisor is an aspect that contains only a single advice object associated with a pointcut expression.
 */
public class SpringAdvisor implements Advisor {
    /**
     * Return the advice part of this aspect. An advice may be an
     * interceptor, a before advice, a throws advice, etc.
     *
     * @return the advice that should apply if the pointcut matches
     * @see org.aopalliance.intercept.MethodInterceptor
     * @see org.springframework.aop.BeforeAdvice
     * @see org.springframework.aop.ThrowsAdvice
     * @see org.springframework.aop.AfterReturningAdvice
     */
    @Override
    public Advice getAdvice() {
        return null;
    }
}
