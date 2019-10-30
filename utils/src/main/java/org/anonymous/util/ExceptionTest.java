package org.anonymous.util;

import org.junit.jupiter.api.Test;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/16 9:54
 */
public class ExceptionTest {
    @Test
    public void test() {
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        for (StackTraceElement s : stackTrace) {
            String className = s.getClassName();
            String methodName = s.getMethodName();
            String fileName = s.getFileName();
            int lineNumber = s.getLineNumber();
            System.out.println(className);
            System.out.println(methodName);
            System.out.println(fileName);
            System.out.println(lineNumber);
        }
    }
}
