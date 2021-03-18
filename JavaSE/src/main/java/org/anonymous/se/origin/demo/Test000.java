package org.anonymous.se.origin.demo;

class AAA {

    public AAA() {
        System.out.println("构造");
    }
	
	/* public int AAA() {
		System.out.println("函数");
		return 0;
	} */

    public static int AAA() {
        System.out.println("静态");
        return 0;
    }
}

class Test11 {

    public static void main(String[] args) {
        AAA a = new AAA(); //创建对象时调用构造函数
        AAA.AAA(); //静态
        AAA.AAA(); //类名直接调用
    }

}