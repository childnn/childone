package execeptions;

public class Person {
    private final String name;
    private final int age;

   /* public Person() {
    }*/

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class Student extends Person {
    /* public Student() {
     }*/
    //可以通过子类的构造方法，把参数传递给父类处理（间接）：这些参数本质上属于父类
    public Student(String name, int age) {
        super(name, age); //(在父类没有无参构造的情况下)把这里注释掉就会报错
    }
}