package org.anonymous.sample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * （1）spring的ioc和aop的实现机制是什么？了解cglib吗？cglib和jdk动态代理的区别是什么？
 * （2）spring循环依赖怎么处理？prototype和singleton模式下的处理分别是怎么样的？
 * （3）spirng mvc的工作原理是什么？
 * （4）hibernate/mybatis中的一二级缓存的实现原理是什么？
 * （5）mybatis的工作原理？
 * （6）你有没有读过哪个开源框架的源码啊？你自己上网随便搜一些spring内部原理的博客，你就看看spring的内部原理，看看spring内部都用了哪些设计模式，就给面试官讲讲这个。spring源码大致看过一些。
 * <p>
 * <p>
 * 1.spring的ioc和aop的实现机制是什么？
 * IoC（Inversion of Control）  
 * <p>
 *   (1). IoC（Inversion of Control）是指容器控制程序对象之间的关系，而不是传统实现中，由程序代码直接操控。控制权由应用代码中转到了外部容器，控制权的转移是所谓反转。 对于Spring而言，就是由Spring来控制对象的生命周期和对象之间的关系；IoC还有另外一个名字——“依赖注入（Dependency Injection）”。从名字上理解，所谓依赖注入，即组件之间的依赖关系由容器在运行期决定，即由容器动态地将某种依赖关系注入到组件之中。  
 * <p>
 * (2). 在Spring的工作方式中，所有的类都会在spring容器中登记，告诉spring这是个什么东西，你需要什么东西，然后spring会在系统运行到适当的时候，把你要的东西主动给你，同时也把你交给其他需要你的东西。所有的类的创建、销毁都由 spring来控制，也就是说控制对象生存周期的不再是引用它的对象，而是spring。对于某个具体的对象而言，以前是它控制其他对象，现在是所有对象都被spring控制，所以这叫控制反转。
 * <p>
 * (3). 在系统运行中，动态的向某个对象提供它所需要的其他对象。  
 * <p>
 * (4). 依赖注入的思想是通过反射机制实现的，在实例化一个类时，它通过反射调用类中set方法将事先保存在HashMap中的类属性注入到类中。 总而言之，在传统的对象创建方式中，通常由调用者来创建被调用者的实例，而在Spring中创建被调用者的工作由Spring来完成，然后注入调用者，即所谓的依赖注入or控制反转。 注入方式有两种：依赖注入和设置注入； IoC的优点：降低了组件之间的耦合，降低了业务对象之间替换的复杂性，使之能够灵活的管理对象。
 * <p>
 * AOP（Aspect Oriented Programming）
 * <p>
 * (1). AOP面向方面编程基于IoC，是对OOP的有益补充；
 * <p>
 * (2). AOP利用一种称为“横切”的技术，剖解开封装的对象内部，并将那些影响了 多个类的公共行为封装到一个可重用模块，并将其名为“Aspect”，即方面。所谓“方面”，简单地说，就是将那些与业务无关，却为业务模块所共同调用的 逻辑或责任封装起来，比如日志记录，便于减少系统的重复代码，降低模块间的耦合度，并有利于未来的可操作性和可维护性。
 * <p>
 * (3). AOP代表的是一个横向的关 系，将“对象”比作一个空心的圆柱体，其中封装的是对象的属性和行为；则面向方面编程的方法，就是将这个圆柱体以切面形式剖开，选择性的提供业务逻辑。而 剖开的切面，也就是所谓的“方面”了。然后它又以巧夺天功的妙手将这些剖开的切面复原，不留痕迹，但完成了效果。
 * <p>
 * (4). 实现AOP的技术，主要分为两大类：一是采用动态代理技术，利用截取消息的方式，对该消息进行装饰，以取代原有对象行为的执行；二是采用静态织入的方式，引入特定的语法创建“方面”，从而使得编译器可以在编译期间织入有关“方面”的代码。
 * <p>
 * (5). Spring实现AOP：JDK动态代理和CGLIB代理 JDK动态代理：其代理对象必须是某个接口的实现，它是通过在运行期间创建一个接口的实现类来完成对目标对象的代理；其核心的两个类是InvocationHandler和Proxy。 CGLIB代理：实现原理类似于JDK动态代理，只是它在运行期间生成的代理对象是针对目标类扩展的子类。CGLIB是高效的代码生成包，底层是依靠ASM（开源的java字节码编辑类库）操作字节码实现的，性能比JDK强；需要引入包asm.jar和cglib.jar。     使用AspectJ注入式切面和@AspectJ注解驱动的切面实际上底层也是通过动态代理实现的。
 * <p>
 * 了解cglib吗？cglib和jdk动态代理的区别是什么？
 * 1、JDK动态代理
 * 利用拦截器(拦截器必须实现InvocationHanlder)加上反射机制生成一个实现代理接口的匿名类，
 * <p>
 * 在调用具体方法前调用InvokeHandler来处理。
 * <p>
 * 2、CGLIB动态代理
 * 利用ASM开源包，对代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。
 * <p>
 * 2.spring循环依赖怎么处理？prototype和singleton模式下的处理分别是怎么样的？
 * 1).构造方法产生的循环依赖   不能解决
 * 2).Setter setter方式单例，默认方式 Spring先用构造器实例化Bean对象----->将实例化结束的对象放到一个Map中，
 * 并且Spring提供获取这个未设置属性的实例化对象的引用方法。结合我们的实例来看，
 * 当Spring实例化了StudentA、StudentB、StudentC后，紧接着会去设置对象的属性，
 * 此时StudentA依赖StudentB，就会去Map中取出存在里面的单例StudentB对象，以此类推，不会出来循环的问题喽。
 * 3）setter方式原型，prototype  部分放弃依赖注入：当A每次需要B时，主动向容器请求新的Bean实例，即可保证每次注入的B都是最新的实例。
 * <p>
 * <p>
 * （3）spirng mvc的工作原理是什么？
 * 流程说明（重要）：
 * <p>
 * （1）客户端（浏览器）发送请求，直接请求到 DispatcherServlet。
 * <p>
 * （2）DispatcherServlet 根据请求信息调用 HandlerMapping，解析请求对应的 Handler。
 * <p>
 * （3）解析到对应的 Handler（也就是我们平常说的 Controller 控制器）后，开始由 HandlerAdapter 适配器处理。
 * <p>
 * （4）HandlerAdapter 会根据 Handler 来调用真正的处理器开处理请求，并处理相应的业务逻辑。
 * <p>
 * （5）处理器处理完业务后，会返回一个 ModelAndView 对象，Model 是返回的数据对象，View 是个逻辑上的 View。
 * <p>
 * （6）ViewResolver 会根据逻辑 View 查找实际的 View。
 * <p>
 * （7）DispaterServlet 把返回的 Model 传给 View（视图渲染）。
 * <p>
 * （8）把 View 返回给请求者（浏览器）
 * <p>
 * （5）mybatis的工作原理？
 * 工作原理解析
 * mybatis应用程序通过SqlSessionFactoryBuilder从mybatis-config.xml配置文件（也可以用Java文件配置的方式，
 * 需要添加@Configuration）中构建出SqlSessionFactory（SqlSessionFactory是线程安全的）；
 * 然后，SqlSessionFactory的实例直接开启一个SqlSession，再通过SqlSession实例获得Mapper对象并运行Mapper映射的SQL语句，
 * 完成对数据库的CRUD和事务提交，之后关闭SqlSession。
 */
public class Spring {
    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        fixedThreadPool.submit(new LongTimeTask(1));
//        for (int i = 0; i < 10; i++) {
//            final int index = i;
//            fixedThreadPool.execute(new Runnable() {
//
//                @Override
//                public void run() {
//                    try {
//                        System.out.println(index);
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }

    }
}
