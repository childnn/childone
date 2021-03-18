package org.anonymous.se.origin.demo;

import java.util.ArrayList;

public class Test1 {
    static Test11 test = new Test11();

    public Test1() {
        System.out.println("空参:" + test);
    }

    public static void main(String[] args) {
        System.out.println(test);

        ArrayList<Integer> list = new ArrayList<Integer>();
        ArrayList list1 = new ArrayList();
        System.out.println(list);
        System.out.println(list1);
    }
}
