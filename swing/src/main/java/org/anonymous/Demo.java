package org.anonymous;

/**
 * @author child
 * 2019/5/2 14:01
 */
public class Demo {

    public static void main(String[] args) throws ClassNotFoundException {
        String property = System.getProperty("java.version");
        System.out.println(property);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000 * 1000 * 1000; i++) {
            Class<?> aClass = Class.forName("java.lang.String");
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

}
