package test.basic;

/**
 *【构造方法】：一组方法的名字，方法名和类名完全一样，无返回值类型（且不用写 void ，不用写 return）
 * 如果没有编写任何构造方法，那么编译器将会默认赠送一个构造方法，没有参数，没有方法体
 * public 方法名（也是类名）() {}
 * 一旦编写了至少一个构造方法（比如有形参，有方法体），则编译器将不再赠送
 * 一个类中不能只有【有参构造方法】，一旦编写了至少一个【有参构造方法】，
 * 则必须主动写上【无参构造方法】,否则在【主调方法】中【new】对象【类名 对象名 = new 类名()】这样表示会报错：除非不创造无参对象
 *
 * 格式： public 类名称(参数类型 参数名称) { 方法体 }
 * 同一类中的几种【构造方法】也是 overload: 方法名相同，参数列表不同（参数种类和个数）
 * （String 也可以看作一种构造方法？）
 */
public class Method2 {

    private String name;
    private int age;
    boolean b;
//    public Method() {} // 如果一个类中没有【构造方法】，系统默认会赠送一个构造方法，无参，无方法体

    // 无参构造方法
    public Method2() {
        System.out.println("无参");
    }

    // 含参构造方法
    public Method2(int age) {
        System.out.println("age构造");
        this.age = age;
    }

    public Method2(int age, String name) { // overload
        System.out.println("overloadPlus");
        this.age = age;
        this.name = name;
    }

    // 全参构造方法
    public Method2(int age, String name, boolean b) {
        System.out.println("全参构造方法执行");
        this.age = age;
        this.name = name;
        this.b = b;
    }

    public int Method() {
        return 0;
    }

    public void eat() { }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
