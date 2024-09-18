package org.example.aop.aspectj;

/**
 * ~~ Talk is cheap. Show me the code. ~~ (^ℨ^)-🌟
 *
 * @author MiaoOne
 * @since 2024/9/12
 * @see org.aspectj.runtime.reflect.JoinPointImpl thisJoinPoint 的默认实现
 */
public aspect AccountJoinPointTraceAspect {

    private int _callDepth = -1;

    //  The capturing
    // of trace points
    pointcut tracePoints(): !within(AccountJoinPointTraceAspect);

    before(): tracePoints() {
        _callDepth++;
        print("Before", thisJoinPoint);
    }
    after(): tracePoints() {
        print("After", thisJoinPoint);
        _callDepth--;
    }
    // c Before advice
    // d After advice
    // The join point model 53
    private void print(String prefix, Object message) {
        for (int i = 0, spaces = _callDepth * 2; i < spaces; i++) {
            System.out.print(" ");
        }
        System.out.println(prefix + ": " + message);
    }

}
