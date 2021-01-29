package org.anonymous.test.spring;

import org.anonymous.beans.Animal;
import org.anonymous.beans.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author child
 * 2019/4/24 18:55
 * 19点18分 测试 单例对象/多例对象的创建时机
 * 正常情况下: 单例对象随容器创建而创建(仅此一次),
 * 多例对象在 getBean() 方法调用时创建(调用一次方法,创建一个新的对象)
 * <p>
 * 当一个 单例对象 依赖于 另一个 多例对象 时,
 * 如果注入方式是 构造方法(constructor-based di)的方式: 则 多例对象会优先创建,
 * 之后再以构造方法注入的方式 创建单例对象
 * 如果注入方式是 set 方法(setter-based di)的方式: 则 单例对象会先创建,
 * 之后再以 set 方法的方式为 单例对象 注入其依赖的 多例对象
 * <p>
 * <p>
 * 参考 Test00 + bean00.xml: setter-based di
 * 本测试类用来测试 constructor-based di //结合 bean0.xml
 * console:
 * user--多例对象!!!!  1
 * aninmiinininimal--constructor-based di
 * main-----single
 * <p>
 * animal:true
 * ===================================
 * user--多例对象!!!!  2
 * user--多例对象!!!!  3
 * user:false
 */
public class Test0 {

    public static void main(String[] args) {

        /*
         * 第一句话执行完毕:
         *    1. 多例对象实例化: User 被依赖 -- 等待 依赖对象使用
         *    2. 单例对象实例化: Animal, constructor-based di
         */
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean0.xml");
        System.out.println("main-----single\n");
        //从 ioc 中获取 单例 对象: 不会新建
        Animal animal = applicationContext.getBean(Animal.class);
        Animal animal1 = applicationContext.getBean(Animal.class);
        System.out.println(animal); // 测试 user 属性是否赋值成功
        System.out.println("animal:" + (animal == animal1));
        System.out.println("===================================");
        //调用一次 getBean(): 实例化一个 多例对象
        User user = applicationContext.getBean(User.class);
        User user0 = applicationContext.getBean(User.class);
        System.out.println(user);
        System.out.println("user:" + (user == user0));

        //容器关闭，单例对象销毁
        applicationContext.close(); //animal: destroy..
    }
}
