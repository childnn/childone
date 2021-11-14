package test.basic;

/**
 * 第十题:
 1.定义一个工具类MathUtils,包含一个静态方法add,功能是:求两个数之和,并将其返回.
 2.在测试类中的主方法中测试自己定义的工具类,能通过 类名.方法  调用add方法,计算两个数的和
 */
public class test10 {

    public static void main(String[] args) {
        System.out.println("和为：" + MathUtils.add(12, 13));
    }

}
