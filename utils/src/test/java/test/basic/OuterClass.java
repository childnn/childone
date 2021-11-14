package test.basic;

/**
 * 外部类： public  /  (default)
 * 内部类：
 *      成员内部类：类中，方法外 public / protected / (default) / private
 *      局部内部类：方法内 // 无修饰符，如果一个方法的内部有一个类，那么方法内的局部变量必须是【有效final的】
 *                  从 java 8 开始，只要局部变量事实不变，那么 final 关键字可以省略
 *                这是因为 局部类的对象（在堆中） 的生命周期比 局部变量（栈中）的生命周期长
 *    （匿名内部类）：重点！！！
 *              如果接口的实现类（或者父类的子类）只需要使用一次，那么就可以省略该类的定义，改为使用【匿名内部类】
 *
 * 外部类访问内部类，需要创建内部类对象
 * 内部类访问外部类，可以访问外部类私有成员
 */
public class OuterClass {
    // 外部类成员变量
    private int a = 10;
    // 内部类
    public class InnerClass {

//        public /*static*/ void main(String[] args) { // 内部类无法运行主方法
//            getOut();
//        }

        public void getIn() {
            System.out.println("in");
            System.out.println(a);
//            getOut(); // 内部类方法直接调用外部成员 // 跟下面的死循环
        }
    }
    // 外部类的方法
    public void getOut() {
        System.out.println("out");
        InnerClass innerClass = new InnerClass();
        innerClass.getIn();
    }

    public static void main(String[] args) {
//        getOut(); // error, 静态不能调用非静态
//        getIn(); // error
//        new InnerClass().getIn(); // error
        InnerClass i = new OuterClass().new InnerClass(); // 注意格式
        i.getIn(); // ok，，main 方法是 static 的，InnerClass 必须 static
//        InnerClass in = new InnerClass();// error, main方法属于外部类的静态方法，不能访问非静态内部类
    }
}