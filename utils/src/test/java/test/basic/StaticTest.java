package test.basic;

/**
 * static 关键字
 */
public class StaticTest {

    public static void main(String[] args) {
        Static1 sta = new Static1();
        sta.a = 10;
        sta.show(); // 10
        sta.man(); // ok
        sta.woman(); // ok

        Static1 sta1 = new Static1();
        sta1.a = 20;
        sta1.show(); // 20
        sta.show(); // 20  【关键】: 【static】修饰词说明 【Static】这个类中的【成员变量 a】是属于【Static类本身】
                  // 可以直接用【类名.a】这样表示，一旦赋值，所有的【对象】中都相同（静止）
        Static1.a = 40; // 【重点】注意表示方式，是【类名.a】,而不是【对象名.a】

        sta.m(32); // 32 32
        sta.show(); // 32




    }
}
