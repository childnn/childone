package test.basic;

public class Student1 {

    String name;
    int age;
    String gra;

    public Student1(String name, int age, String gra) {
        this.name = name;
        this.age = age;
        this.gra = gra;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gra='" + gra + '\'' +
                '}';
    }
}
