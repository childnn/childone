package test.basic;

/**
 * 【标准类：Java Bean】:
 * 所有【成员变量】都要使用【private】关键字修饰
 * 为每一个成员变量编写一对 Getter/Setter 方法
 *  编写一个【无参构造方法】
 *  编写一个【全参构造方法】
 */
public class ClassStandard {

    private String name;
    private int age;
    private boolean b;

    // 无参构造方法
    public ClassStandard() {}

    // 全参构造方法
    public ClassStandard(String name, int age, boolean b) {
        this.name = name;
        this.age = age;
        this.b = b;
    }

    // GetterAndSetter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

}
