package test.basic;

/**
 * 【限制符】：public, private, default（不加任何限制），protect
 * 【修饰符】：static，非static？
 * static： 修饰【成员变量】和【成员方法】
 * 【关键】 当static 修饰成员方法【 main 方法除外】时，可以被其他静态或非静态方法调用，但 不可以调用其他 【非静态方法或非静态变量】，(可以调用静态方法)
 * 【关键】 【静态 static】且 【非私有 非private】的成员变量才能直接引用为【类名.静态成员变量名】
 */
class Static1 {
    static int a;
    int b;

//    public static void main(String[] args) {
//        System.out.println(a);
//    }

    /*public*/ void show() {
        System.out.println(a);
//        man(a); // OK
    }

    public static void man() {
        System.out.println("ok");
//        show(); // ERROR 【静态方法】不可以调用【非静态方法】
    }

    public static void woman() {
        man(); // 静态可以互相调用
    }

    public void m(int a) { // 方法前不能用【static】修饰，否则不能用【this】
        this.a = a;
        b = a;
        System.out.printf("%d %d\n", this.a, this.b);
    }

}
