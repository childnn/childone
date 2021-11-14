// package test.basic;
//
// // 局部变量在【栈 stack】中，只能在方法内部使用,【new】出来的在【堆 heap】中，
// class Test1 {
//
//     public static void main(String[] args) {
//         // System.in.scanf
//         Test00 a = new Test00();
//         Test00 b = new Test00();
//         System.out.println(a); // 地址值A
//         System.out.println(b); // 地址值B != 地址值A
//
//         System.out.println(a.i); // 20
//         System.out.println(a.j); // 30
//         // System.out.printf("%d %d\n", a.i, a.j);
//         // System.out.printf("%d,%d\n", a.i, a.j);
//         System.out.printf("%d\n（out）", a.i); // 【%d】前后可以加任何东西？【%】加【数据类型的表示字母：F，L，D等】表示【对应变量的占位符】
//         System.out.println();
//
//         Test00 c = a; // 对象c 也指向 a 所指向的 地址值A
//         System.out.println(c); // 地址值A
//         c.i = 100; // 改变对象 c 所指向地址中的 i 的值（属性 i 的值）
//         // 输出 对象 a 所指向地址中此时 i 的值：若 a.i = 20,则说明二者所指地址不同；
//         // 若 a.i = 100,则说明二者指向相同地址，可以认为 对象 a 和对象 c 是同一个对象
//         System.out.println(a.i); // 100, 验证结论
//
//         // private
// //        a.a = 10; // 私有全局变量
//         a.get(12); // 由方法【get()】输出
//
//         Test00 a1 = new Test00(); // 对象 a1 有自己指向的地址（不同于 对象 a）
//         a1 = a; // 把 对象 a 所指向的地址赋值给 对象 a1
//         a1.i = 120;
//         // 输出 a.i = 20 ? 120?
//         System.out.println(a.i);
//
//     }
//
// }
