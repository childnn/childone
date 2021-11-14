package test.basic;

public class RandomScanner1 {
    private static RandomScanner1 ourInstance = new RandomScanner1();

    public static RandomScanner1 getInstance() {
        return ourInstance;
    }

    private RandomScanner1() { //将构造私有，只能通过 静态方法获取对象而不能通过构造创建对象
    }
}
