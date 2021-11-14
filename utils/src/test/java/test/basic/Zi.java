package test.basic;

/**
 * 父类构造方法只能被子类构造方法调用，不能被子类其他方法调用，且只能是子类构造方法的第一个语句，不能是其他位置的语句，而且只能调用一个父类构造
 * 父类构造方法优先加载
 * 若子类调用了自己的构造方法，则父类的构造方法不再赠送
 */
public class Zi extends Fu {

    public Zi() {
//        super(); // 子类默认继承父类无参构造，不写也可以。（若主动写了其他构造，就不赠送了）
        super(10);
//        this(10); // error, 构造方法必须第一句
        System.out.println("子类无参");
    }

    public Zi(int num) {
        super();
//        Zi();
        System.out.println("子类有参");
    }
}
