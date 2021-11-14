package test.basic;

public class Anonymous000 {

    /*private */String name;
    int age;

    public void showName(String name, int age) {
        System.out.println("我叫" + name + "，年龄" + /*this.*/age); // 这里没加【this】，加了就是【this.age】的默认值
    }

    public void m(/*String name*/) {
//        this.name = name;
        System.out.println(name);
    }

}
