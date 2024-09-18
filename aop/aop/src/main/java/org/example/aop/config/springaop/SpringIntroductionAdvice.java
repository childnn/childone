package org.example.aop.config.springaop;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.IntroductionAdvisor;
import org.springframework.aop.IntroductionInterceptor;
import org.springframework.aop.support.DefaultIntroductionAdvisor;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

interface Lockable {
    void lock();

    void unlock();

    boolean locked();
}

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2024/6/13
 * https://docs.spring.io/spring-framework/reference/core/aop-api/advice.html
 */
public class SpringIntroductionAdvice implements IntroductionAdvisor {
    // Introduction advice cannot be used with any pointcut, as it applies only at the class, rather than the method, level.
    // You can only use introduction advice with the IntroductionAdvisor


    /**
     * Return the filter determining which target classes this introduction
     * should apply to.
     * <p>This represents the class part of a pointcut. Note that method
     * matching doesn't make sense to introductions.
     *
     * @return the class filter
     */
    @Override
    public ClassFilter getClassFilter() {
        return null;
    }

    /**
     * Can the advised interfaces be implemented by the introduction advice?
     * Invoked before adding an IntroductionAdvisor.
     *
     * @throws IllegalArgumentException if the advised interfaces can't be
     *                                  implemented by the introduction advice
     */
    @Override
    public void validateInterfaces() throws IllegalArgumentException {

    }

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

    /**
     * Return the additional interfaces introduced by this Advisor or Advice.
     *
     * @return the introduced interfaces
     */
    @Override
    public Class<?>[] getInterfaces() {
        return new Class[0];
    }
}

class SpringIntroductionInterceptor implements IntroductionInterceptor {

    /**
     * Implement this method to perform extra treatments before and
     * after the invocation. Polite implementations would certainly
     * like to invoke {@link org.aopalliance.intercept.Joinpoint#proceed()}.
     *
     * @param invocation the method invocation joinpoint
     * @return the result of the call to {@link org.aopalliance.intercept.Joinpoint#proceed()};
     * might be intercepted by the interceptor
     * @throws Throwable if the interceptors or the target object
     *                   throws an exception
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return null;
    }

    /**
     * Does this introduction advice implement the given interface?
     *
     * @param intf the interface to check
     * @return whether the advice implements the specified interface
     */
    @Override
    public boolean implementsInterface(Class<?> intf) {
        return false;
    }
}

class SpringIntroInterceptor extends DelegatingIntroductionInterceptor {

}

class LockMixin extends DelegatingIntroductionInterceptor implements Lockable {

    private boolean locked;

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }

    public boolean locked() {
        return this.locked;
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (locked() && invocation.getMethod().getName().indexOf("set") == 0) {
            throw new LockedException();
        }
        return super.invoke(invocation);
    }
}

class LockedException extends RuntimeException {
}


class LockMixinAdvisor extends DefaultIntroductionAdvisor {

    public LockMixinAdvisor() {
        super(new LockMixin(), Lockable.class);
    }
}