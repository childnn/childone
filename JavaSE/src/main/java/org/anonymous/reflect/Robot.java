package org.anonymous.reflect;

public class Robot {
    static {
        System.out.println("Hello Robot");
    }

    private String name;

    public void sayHi(String helloSentence) {
        System.out.println(helloSentence + " " + name);
    }

    private String throwHello(String tag) {
        return "Hello " + tag;
    }
}
