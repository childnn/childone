package org.anonymous.test.spring;

import org.anonymous.beans.Animal;
import org.anonymous.beans.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author child
 * 2019/4/24 19:44
 * 19点55分: 测试 多个配置文件, bean 的 id 冲突问题
 * 测试: bean0.xml 与 bean00.xml
 * 当多个不相关的配置文件中, 相同类 具有相同的 id/name 时, 后加载的 xml 会覆盖 先加载的 xml
 * (联想 Map 的存储方式: 同 key 的属性, 后者覆盖前者)
 * 可以交换 bean0.xml 和 bean00.xml 在 ClassPathXmlApplicationContext 中的顺序, 会得到不同的结果
 * <p>
 * <p>
 * 如果相同的类没有指定 id/name 等 具有唯一标识的 属性值,则会由 ioc 分配唯一的 id,
 * 这样就不存在冲突的问题, 但是 如果不自己指定 id/name, 就只能通过 getBean(Class beanClass)
 * 获取该 bean 的对象, 但是者仅限于 该类 有且只有一个 bean 标签时,才可以在不指定 id/name
 * 的情况下, 通过 Class 类型获取, 如果 该类 由多个 bean 标签注入, 再通过 Class 属性获取,
 * 就会 报异常:
 * org.springframework.beans.factory.NoUniqueBeanDefinitionException:
 * No qualifying bean of type 'org.anonymous.domain.Animal' available:
 * expected single matching bean but found 2: animal00,animal
 * <p>
 * 对于同一个 bean 标签的属性来说: id 和 name 是等价的
 * 如果 同一个类 定义了多个不同的 bean, id/name 不冲突,则 这些 不同的 id/name 就是不同的对象
 * 即使 这些 bean 都被定义为 singleton, 也就是说, singleton 只是针对 某一个 特定的 di/name 而言
 * 同一个类的 不同 bean 标签,不同 id/name,就是这个类的不同 对象
 */
public class Test1 {

    public static void main(String[] args) {
        /**
         * bean00.xml 中的 user 会 覆盖 bean0.xml 中的 user
         * console:
         * user--多例对象!!!!  1    //bean0.xml 中的 user 实例化
         * aninmiinininimal--constructor-based di  //animal 通过 有参构造 实例化: 所以必须先由 user 对象
         * animal-singleton  //bean00.xml 中的 animal 通过 无参构造 实例化: 实例化时不需要 user 对象
         * user--多例对象!!!!  2  //user 对象实例化: 准备给 animal 通过 set 方法注入
         * animal -- setter-based di //为 animal 注入 user 依赖:  通过  set  方法
         * main---main
         *
         * user--多例对象!!!!  3  //getBean() 方法调用: 多例对象实例化
         * User{name='张三', age=10}
         *
         * false
         * Animal{user=null} //注入的是 bean0.xml 中的 user (user 的属性没有赋值,就是 null)
         * Animal{user=User{name='张三', age=10}} // bean00.xml 中的 user 把 bean0.xml 中的 user 覆盖了
         */
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean0.xml", "bean00.xml");
        System.out.println("main---main\n");

        User user = applicationContext.getBean(User.class, "张三", 19);
        System.out.println(user + "\n");

        //        Animal animal = applicationContext.getBean(Animal.class); //NoUniqueBeanDefinitionException
        // 获取 bean0.xml 的 animal 对象 : Animal{user=null}
        Animal animal = applicationContext.getBean("animal0", Animal.class);
        //获取 bean00.xml 的 animal 对象 : Animal{user=User{name='张三', age=10}}
        Animal animal1 = applicationContext.getBean("animal00", Animal.class);
        System.out.println(animal == animal1);

        //查看 animal 注入了的 user
        System.out.println(animal);
        System.out.println(animal1);

    }
}
