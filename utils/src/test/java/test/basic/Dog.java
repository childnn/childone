package test.basic;

public class Dog {

    private String kind;
    private int age;
    private String color;

    public Dog() {
    }

    public Dog(String kind, int age, String color) {
        this.kind = kind;
        this.age = age;
        this.color = color;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
