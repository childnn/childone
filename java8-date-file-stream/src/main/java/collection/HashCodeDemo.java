package cn.itheima;

public class HashCodeDemo {
    public static void main(String[] args) {
        Person p1 = new Person();
        System.out.println(p1); //Person@75412c2f
        int h1 = p1.hashCode();
        System.out.println(h1); //1967205423  / 重写哈希值 1
        Person p2 = new Person();
        System.out.println(p2); //Person@282ba1e
        int h2 = p2.hashCode();
        System.out.println(h2); //42121758 / 1

        String s1 = "a";
        String s2 = "a";
        System.out.println(s1.hashCode()); //97
        System.out.println(s2.hashCode()); //97

        System.out.println("重地".hashCode()); //1179395
        System.out.println("通话".hashCode()); //1179395
        System.out.println("缪万".hashCode()); //1029149
        System.out.println("万".hashCode()); //19975
        System.out.println("缪".hashCode()); //32554

    }
}

class Person {
    @Override
    public int hashCode() {
        return 1;
    }
}