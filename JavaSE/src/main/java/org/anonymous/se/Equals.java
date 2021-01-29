package org.anonymous.se;

import java.util.Objects;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/1/6 10:12
 * 自反性: 任意非 null 的 x, x.equals(x) == true
 * 对称性: 任意非 null 的 x, y, 若 x.equals(y) == true, 则 y.equals(x) == true
 * 传递性: 对任意非 null 的 x, y, z, 若 x.equals(y) == true, y.equals(z) == true, 则 x.equals(z)
 * 一致性: 对任意非 null 的 x, y, 若对象中用于等价比较的信息没有改变, 那么无论调用 x.equals(y) 多少次, 返回的结果应该保持一致.
 * 对任何非 null 的 x, x.equals(null) == false 恒成立
 */
public class Equals {

    private int age;

    private String name;

    public static void main(String[] args) {
        EqualsSon equalsSon = new EqualsSon();
        boolean b = equalsSon instanceof Equals;
        System.out.println(b);
        Class clazz = equalsSon.getClass();
        boolean b1 = clazz == Equals.class;
        System.out.println("b1 = " + b1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // 由于 instanceof 运算符会将子类判断为父类的 instance
        // 如果只希望判断当前类,而不涉及到子类,应使用 obj.getClass() == Equals.class, 此为更严格的校验
        if (!(o instanceof Equals)) return false;

        Equals toString = (Equals) o;

        if (age != toString.age) return false;
        return Objects.equals(name, toString.name);
    }

    @Override
    public int hashCode() {
        int result = age;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}

class EqualsSon extends Equals {

}