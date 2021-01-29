package org.anonymous.beans;


/**
 * @author child
 * 2019/4/25 11:37
 * depends-on 的测试
 */
public class AccountDepend {
    private static Animal animal;

    static {
        animal = new Animal();
    }

    public AccountDepend() {
        System.out.println("account:" + animal);
    }

    public static void setAnimal(Animal animal) {
        AccountDepend.animal = animal;
    }

    public void destroy() {
        System.out.println("account: destroy...");
    }
}
