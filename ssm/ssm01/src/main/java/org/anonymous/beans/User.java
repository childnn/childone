package org.anonymous.beans;

/**
 * @author child
 * 2019/4/24 18:45
 * 设置多例对象
 */
public class User {

    private static int i = 1; //计数对象个数
    private String name;
    private Integer age;

    public User(String name, Integer age) {
        System.out.println("user--多例对象!!!-- constructor-based:" + i++);
        this.name = name;
        this.age = age;
    }

    private User() {
        System.out.println("user--多例对象!!!! -- setter-based: " + i++);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void destroy() {
        System.out.println("user:destroy...");
    }
}
