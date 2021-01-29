package org.anonymous.origin.demo;

public class TestThis {
    public static void main(String[] args) {
        A a = new A(10); //AAA,b都保存地址值
        A b = new A(20);
        a.show(); //AAA.show(AAA); 把a赋值给this
        b.show(); //b.show(b); 把b赋值给this
    }
}

class A {
    private final int i;

    public A(int i) { //构造函数在对象创建的同时被调用,this表示正在创建的对象,可以认为是main方法中的引用a,b
        this.i = i; //这里不加this上面输出的都是0,表明不加this的i都是形参i
    }

    public void show(/* AAA * this */) { //隐含this指针,谁调用show方法,this就指向谁
        System.out.println(i); //(*this).i
    }
}
