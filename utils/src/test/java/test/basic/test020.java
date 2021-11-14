package test.basic;

/**
 * 第二题:
 * 1.定义一个Phone抽象类,包含如下方法:
 * 普通方法: call() 输出:"打电话"
 * 普通方法: sendMessage() 输出:"发短信"
 * 抽象方法: playGame();
 * <p>
 * <p>
 * 2.定义一个IPhone类,继承Phone类
 * 重写sendMessage()方法,输出:"发彩信"
 * 实现playGame()方法,输出:"打王者农药"
 * 定义自己特有的行为 :
 * siri() 打印"人工智障"
 * <p>
 * 3.定义测试类IPhoneTest, 创建IPhone的对象,调用方法,打印出如下语句
 * 打电话
 * 发彩信
 * 打王者农药
 * 人工智障
 */
class test02$ {

    public static void main(String[] args) {
        IPhone i = new IPhone();
        // i.call();
        i.sendMessage();
        i.playGame();
        i.siri();
    }

}

class Phone {

    public void call() {
        System.out.println("打电话");
    }

    public void sendMessage() {
        System.out.println("发短信");
    }

    public void playGame() {}

}

class IPhone extends Phone {

    @Override
    public void sendMessage() {
        System.out.println("发彩信");
    }

    @Override
    public void playGame() {
        System.out.println("打王者农药");
    }

    public void siri() {
        System.out.println("人工智障");
    }

}