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

    @Test
    public void test1() {
        try {
            exe2();
        } catch (Exception e) {
            // e.printStackTrace();
            String errorMsg;
            Throwable cause = e.getCause();
            if (cause == null) {
                errorMsg = e.getMessage();
            } else {
                Throwable c = cause;
                while (cause != null) {
                    c = cause;
                    cause = cause.getCause();
                }
                errorMsg = c.getMessage();
            }


            System.out.println(errorMsg);

        }
    }

    private void exe() {
        int i = 1 / 0;
    }

    private void exe1() {
        try {
            exe();
        } catch (Exception e) {
            throw new IllegalArgumentException("exe1 error...", e);
        }
    }

    private void exe2() {
        try {
            exe1();
        } catch (Exception e) {
            throw new RuntimeException("exe2 error..", e);
        }
    }

}
