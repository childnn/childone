package org.anonymous.se.origin.demo;

public class 进制 {
    public static void main(String[] args) {
        char c = 127;
        System.out.println(c); // == '\u007F'
        char c1 = '\u007F';
        System.out.println(c1);
        byte b = (byte) 128;
        System.out.println(b);
        int b1 = 0X11110000;
        System.out.printf("%d", b1);


    }
}
