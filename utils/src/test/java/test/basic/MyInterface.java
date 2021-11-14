package test.basic;

public interface MyInterface {
    public void get();
}

class test000 {

    public static void main(String[] args) {
        // 匿名内部类，就是在 new 接口（或者父类）的实现类（子类），只不过不知道子类的名字
        // 这就是 多态！！！ 父引用指向子对象
        MyInterface one = new MyInterface() { // 注意，这里不是在 创建接口对象，接口是无法 new 对象的！！！
            @Override
            public void get() {
                System.out.println("匿名内部类的方法");
            }

            public void set() {
                System.out.println("匿名内部类特有方法");
            }
        }; // 注意这里的分号

        one.get(); //
//        one.set();  // 父引用无法访问子类特有成员，匿名内部类也无法实现强转（向下转型）
    }
}