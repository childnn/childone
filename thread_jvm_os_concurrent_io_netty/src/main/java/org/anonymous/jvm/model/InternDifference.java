package org.anonymous.jvm.model;

public class InternDifference {
    public static void main(String[] args) {
        String s = "a";
        s.intern();
        String s2 = "a";
        System.out.println(s == s2);

        String s3 = "a" + "a";
        s3.intern();
        String s4 = "aa";
        System.out.println(s3 == s4);
    }
}
