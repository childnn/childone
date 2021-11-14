package test.basic;

public class Anonymous {

//    Anonymous a = new Anonymous();

    public static void main(String[] args) {
        Anonymous000 an = new Anonymous000();
        an.showName("缪万", 24);
        Anonymous b = new Anonymous(); // 【Anonymous】自己的构造方法
        System.out.println(b);

        // 匿名对象【没有等号左边的 类名称 对象名】
        // 每一次【new】的匿名对象都是不同的匿名对象:即【匿名对象】只能使用【唯一一次】（以后每一次 new 出来的都是不同的 对象）
        new Anonymous000().name = "Jack";
        new Anonymous000().m(); // 如果这个【匿名对象】和上面的是一个对象，那么这里输出的会是【Jack】

    }

}
