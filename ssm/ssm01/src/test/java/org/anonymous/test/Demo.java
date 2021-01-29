package org.anonymous.test;

import org.anonymous.service.AccountService;
import org.anonymous.util.BeanFactory;
import org.anonymous.util.BeanFactory0;

/**
 * @author child
 * 2019/4/13 16:45
 * 如何解决 耦合度过高的问题： 让编译不报错，降低代码和其他类之间的依赖关系
 * 反射： 工厂类，专门用来生产 对象
 * 程序的解耦 = 工厂 + 反射 + 配置文件  == 生产对象
 */
public class Demo {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        //解决编译期异常: 无论该接口有无实现类， 编译时期不会报错
        //多例 工厂
        AccountService bean = (AccountService) BeanFactory.getBean("accountService");
        AccountService bean1 = (AccountService) BeanFactory.getBean("accountService");
        System.out.println(bean == bean1); //false
        //        bean.save();
        //单例工厂
        AccountService bean2 = (AccountService) BeanFactory0.getBean("accountService");
        AccountService bean3 = (AccountService) BeanFactory0.getBean("accountService");
        System.out.println(bean2 == bean3); //true
    }
}
