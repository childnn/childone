package test.basic;

class test11 {

    public static void main(String[] args) {
        // 内部类属于外部类的成员，需要外部类的对象来调用
        OuterClass.InnerClass innerClass = new OuterClass().new InnerClass();
        innerClass.getIn();
//        innerClass.getOut(); // error，不是继承关系，不能通过内部类对象名调用外部类成员
    }

}
