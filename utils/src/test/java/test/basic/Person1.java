package test.basic;

public abstract class Person1 {

    private String name;
    private int age;

    public Person1() {
    }

    public Person1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void eat(String food) {
        System.out.println(name + "正在吃" + food);
    }

    public void sleep(int hours) {
        System.out.println("睡" + hours + "小时");
    }

}

abstract class Player extends Person1$ {

    public Player() {
    }

    public Player(String name, int age) {
        super(name, age);
    }

    public abstract void study();

}

abstract class Coach extends Person1$ {

    public Coach() {
    }

    public Coach(String name, int age) {
        super(name, age);
    }

    public abstract void teach();

}

class BasketballPlayer extends Player{

    public BasketballPlayer() {
    }

    public BasketballPlayer(String name, int age) {
        super(name, age);
    }

    @Override
    public void study() {
        System.out.println("学习打篮球");
    }
}

class BasketballCoach extends Coach {

    public BasketballCoach() {
    }

    public BasketballCoach(String name, int age) {
        super(name, age);
    }

    @Override
    public void teach() {
        System.out.println("教运动员篮球");
    }
}

class PingPongPlayer extends Player {

    public PingPongPlayer() {
    }

    public PingPongPlayer(String name, int age) {
        super(name, age);
    }

    @Override
    public void study() {
        System.out.println("学习打篮球");
    }
}

class PingPongCoach extends Coach {

    public PingPongCoach() {
    }

    public PingPongCoach(String name, int age) {
        super(name, age);
    }

    @Override
    public void teach() {
        System.out.println("教乒乓球");
    }

}


