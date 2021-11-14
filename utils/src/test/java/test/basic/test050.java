package test.basic;

/**
 * 第五题:
 * 1.定义一个Phone类.包含如下属性
 * 品牌brand
 * 价格price
 * 生成所有成员变量set/get方法,空参构造和有参构造
 * <p>
 * 2.定义一个Person类,包含如下属性
 * 姓名name
 * 年龄age
 * 生成所有成员变量set/get方法,空参构造和有参构造
 * 定义一个玩手机行为 playPhone(Phone p) 要求打印:"YYY岁的XXX正在玩手机,手机品牌是ZZZ,价格为QQQ
 * <p>
 * PS：YYY是Person类中的 age属性值，XXX是Person 类中的 name属性值，ZZZ是Phone 类中的 brand属性值,QQQ是Phone 类中的 price属性值"
 * <p>
 * 3.在测试类TestDemo中,创建Person对象ren,调用ren的 playPhone(Phone p) 方法,打印出
 * "40岁的罗永浩正在玩手机,手机品牌是小米,价格为2999"
 * ps:参数p需要自行创建并初始化变量brand与 price
 */
class test05$ {

    public static void main(String[] args) {
        Person1$ ren = new Person1$("罗永浩", 40);
        Phone1 p = new Phone1("小米", 2999);
        // ren.playPhone(p);
    }

}

class Phone1 {

    String brand;
    double price;

    public Phone1() {
    }

    public Phone1(String brand, double price) {

        this.brand = brand;
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}

class Person1$ {

    String name;
    int age;

    public void playPhone(Phone1 p) {
        System.out.println(age + "岁的" + name + "正在玩手机,手机品牌是" + p.getBrand() + ",价格为" + p.getPrice());
    }

    public Person1$() {
    }

    public Person1$(String name, int age) {

        this.name = name;
        this.age = age;
    }

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

}