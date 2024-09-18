package org.example.aop.config.springaop;

import org.springframework.aop.ThrowsAdvice;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2024/6/13
 */
public class SpringThrowAdvice implements ThrowsAdvice {

    //  * <p>There are not any methods on this interface, as methods are invoked by
    //  * reflection. Implementing classes must implement methods of the form:
    //  *
    //  * <pre class="code">void afterThrowing([Method, args, target], ThrowableSubclass);</pre>

    // If a throws-advice method throws an exception itself, it overrides the original exception
    // (that is, it changes the exception thrown to the user). The overriding exception is typically a RuntimeException,
    // which is compatible with any method signature. However, if a throws-advice method throws a checked exception,
    // it must match the declared exceptions of the target method and is, hence, to some degree coupled to specific target method signatures.
    // Do not throw an undeclared checked exception that is incompatible with the target method’s signature!
}
