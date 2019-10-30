package org.ssm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author child
 * 2019/4/13 16:57
 * EJB 思想: IBM 提出
 *  由于 EJB 适用于大型项目, 后由 EBJ 抽取,得到 轻量级框架: spring, hibernate(dao层框架)
 *  Spring是分层的 Java SE/EE应用 full-stack 轻量级开源框架，以 IoC（Inverse Of Control：反转控制）
 *      和 AOP（Aspect Oriented Programming：面向切面编程）为内核，提供了 展现层(web层/控制层) SpringMVC
 *      和 持久层 Spring JDBC 以及业务层事务管理等众多的企业级应用技术，还能整合开源世界众多著名的第三方框架和类库，逐渐成为使用最多的Java EE 企业应用开源框架。
 * Spring 5.0: jdk 1.7+ -- 优化反射(框架的核心)
 *  ORM: object relational mapping 对象关系映射
 *  JPA: Java persistence API -- Java 持久层 api -- mybaits， hiberbate 都实现了 jpa 规范(称为 jpa 框架)
 *  API： application programming interface 应用程序编程接口
 *
 * spring ioc : 解耦
 *   所谓耦合度高，就是 当我们调用外部的 类时，必须要外来的 jar 包（否则创建对象，会报错（编译错））
 *   如果希望 编译期不报错（不关系运行）,则需要通过 反射的方式,降低耦合,以解决 耦合度高的问题
 *   spring 提供的 ioc 容器, 可以将对象间的依赖关系交由 spring 来控制,避免硬编码造成的过度程序耦合,用户也不必再为
 *      单例模式类,属性配置文件解析 等底层需求编写代码, 可以更专注于上层的应用
 *  程序的解耦合: 工厂 + 反射 + 配置文件(ioc) == 生产对象
 *  单例工厂与多例工厂: D:\Develop\IDEA_Project\maven-project\spring_day01_a\src\test\java\org\anonymous\test\Demo.java
 *
 * ioc: 反射
 * aop: 动态代理((jdk)接口/(cglib)类(不需要接口))
 *
 * day01
 *  一: ioc 相关概念: Inversion Of Control -- 封装 配置文件 + 工厂 + 反射
 *   ioc 控制反转
 *      ioc(map 容器): 控制反转
 *      作用: 用来处理程序的解耦合并创建存储对象
 *      传统方式获取对象: new
 *      ioc 方式获取对象: 从容器中获取 -- 加载配置文件时,就将 对象创建好,并放入
 *      ioc 是 spring 的核心技术
 *   spring 配置文件介绍:
 *       D:\Develop\IDEA_Project\maven-project\spring_day01_b\src\test\java\org\anonymous\test\Demo01.java
 *       D:\Develop\IDEA_Project\maven-project\spring_day01_b\src\main\resources\bean.xml
 *      <bean> 标签: 指定要创建的对象
 *           id 属性: 唯一标识
 *           class 属性: 全限定名
 *        对象的声明周期：（重点！！）
 *           scope 属性: 对象级别(单例/多例)
 *               取值: singleton 单例对象,
 *                          随着配置文件的加载 容器创建, 对象被创建, 并放入容器
 *                   prototype 多例对象
 *                          容器无关
 *                          getBean() 方法调用,创建对象, 调用一次创建一次
 *            init-method 属性: 配置 容器/对象 初始化方法  -- 对应注解： javax.annotation  @PostConstrut   -- 祥见： D:\Develop\IDEA_Project\maven-project\spring_day02_xml_annotation\src\main\java\org\anonymous\serviceimpl\AccountServiceImpl1.java
 *                  配置文件解析- 容器创建- 单例对象创建- 初始化方法执行
 *                  getBean() 调用- 多例对象创建- 初始化方法执行
 *            destroy-method 属性: 配置容器(单例对象)的销毁方法  -- 对应注解： javax.annotation  @PreDestory
 *                  close() 调用 - 容器销毁 - 单例对象销毁 - 销毁方法执行
 *                  即: 只有在 scope="singleton" 时, 销毁方法才有效
 *                    而 scope="prototype" 时, 多例对象与容器无关, 对象需要等到垃圾回收才会销毁
 *    spring ioc 生产对象的生命周期属性: scope 属性, init-method 属性, destory-method 属性
 *
 *   以上是反射的方式 调用 空参构造,创建对象
 *   为了适配老的方式,没有 spring 的时候,老的代码都要用 工厂模式(自定义的工厂类，不是spring提供的)解耦合: spring 提供了另外两种 创建对象的方式(了解)
 *    祥见： D:\Develop\IDEA_Project\maven-project\spring_day01_b\src\main\resources\bean1.xml
 *   第二种: 静态工厂
 *       创建对象的方法是静态的,可以类名直接调用
 *   第三种: 实例化工厂
 *       创建对象的方法是非静态的,必须得先有 工厂对象,通过对象调用方法
 *
 *   二: spring 的 di (依赖注入) dependency injection
 *      构造注入： constructor injection - 对象生成时，注入
 *      setter 方法注入： setter injection - 对象生成后，注入
 *      接口注入： interface injection - 了解
 *      构造方法注入。这种注入方式的优点就是，对象在构造完成之后，即已进入就绪状态，可以马上使用。
 *              缺点就是，当依赖对象比较多的时候，构造方法的参数列表会比较长。而通过反射构造对象的时候，
 *              对相同类型的参数的处理会比较困难，维护和使用上也比较麻烦。而且在Java中，构造方法无法被继承，
 *              无法设置默认值。对于非必须的依赖处理，可能需要引入多个构造方法，而参数数量的变动可能造成维护上的不便。
 *      setter方法注入。因为方法可以命名，所以setter方法注入在描述性上要比构造方法注入好一些。
 *          另外，setter方法可以被继承，允许设置默认值，而且有良好的IDE支持。
 *           缺点当然就是对象无法在构造完成后马上进入就绪状态。
 *
 *    D:\Develop\IDEA_Project\maven-project\spring_day01_c\src\main\resources\bean.xml
 *          <--引入 外部的 数据库连接信息:
 *          类路径下的配置文件: classpath:properties文件名
 *          使用引入文件: spring-el 标签获取 value
 *          -->
 *     <context:property-placeholder location="classpath:jdbc.properties"/>
 *
 *     1. set 方式(必须要有对应的 set 方法): property 标签
 *           <--druid-->
 *      <bean id="druid" class="com.alibaba.druid.pool.DruidDataSource">
 *         <--set 方式-->
 *         <property name="driverClassName" value="${jdbc.driver}"/>
 *         <property name="url" value="${jdbc.url}"/>
 *         <property name="username" value="${jdbc.username}"/>
 *         <property name="password" value="${jdbc.password}"/>
 *      </bean>
 *
 *     2. 构造器方式: constructor-arg 标签
 *          <--jdbcTemplate: 创建 jdbcTemplate 对象,并 使用 有参构造 赋值: datasource - druid-->
 *      <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
 *         <--name:  创建 jdbcTemplate 对象需要的 连接池, ref: 引入 ioc 管理的 druid 对象的 id -->
 *         <constructor-arg name="dataSource" ref="druid"/>
 *      </bean>
 *
 *      <!--简单属性注入方式一: 构造器方式-->
 *     <bean id="accountService" class="org.anonymous.serviceimpl.AccountServiceImpl">
 *         <!--构造器方式
 *              constructor-arg 标签: 给当前对象的属性使用 构造器方式赋值
 *                 属性:
 *                     index: 参数 索引 （The index is 0-based.）
 *                     name: 参数 变量名
 *                     type: 参数 类型(类型全类名)
 *                     ******* 上面 3 个参数用来定位参数//下面 2 个参数用来给参数赋值
 *                     value: 给简单属性赋值
 *                     ref: 给 spring 容器管理的对象 赋值的
 *         -->
 *         <!--构造器方式一:index 赋值-->
 *         <!--<constructor-arg index="0" value="英雄"/>
 *         <constructor-arg index="1" value="12"/>-->
 *
 *         <!--构造器方式二: name 赋值-->
 *        <!-- <constructor-arg name="name" value="张三"/>
 *         <constructor-arg name="age" value="111"/>-->
 *
 *         <!--构造器方式三: type 赋值-->
 *         <constructor-arg type="java.lang.String" value="jack"/>
 *         <constructor-arg type="java.lang.Integer" value="2222"/>
 *    </bean>
 *
 *
 *  day02 : ioc/di
 *    1.使用 spring 全 xml 配置的方式操作数据的 crud (重点)  : ioc + di
 *        spring 整合数据库连接池(c3p0/druid) 和 jdbcTemplate -- spring ioc 除了可以 管理自定义的 java 类,也可以管理 第三方的 jar 中的 Java 类
 *        使用:
 *          1) 导入连接池,mysql,jdbcTemplate 坐标
 *          2) 将 第三方的 Java类 交给 spring ioc 管理(由 spring 去放射创建对象)
 *          3) 通过 spring 的依赖注入(di)设置属性值
 *        优化: 在 spring 的配置文件中加载外部的数据库连接信息(.properties文件)
 *              在内部 : 1)先引入约束(添加上源文件中没有的 xml文件及其约束) -- <context:property-placeholder location="classpath:jdbc.properties"/>
 *               (<beans xmlns="http://www.springframework.org/schema/beans"
 *                       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 *                       xmlns:context="http://www.springframework.org/schema/context"
 *                       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
 *                           http://www.springframework.org/schema/context
 *                           http://www.springframework.org/schema/context/spring-context.xsd">)
 *                2) 再通过 spring-el : ${properties文件key值} 获取 value (数据库连接具体信息: driver,url,username,password)
 *
 *  2.使用 spring 半 xml 半注解的方式操作数据的 crud (重点)
 *        自己的资源用注解, 第三方资源继续保留 xml 文件方式(很难给第三方加注解)
 *        使用条件: spring 的注解默认是关闭的, 需要在 xml 中配置 注解扫描器 开启注解功能
 *              注解扫描器 : 告诉 spring 去哪些包下解析 编写好的注解
 *              <context:component-scan base-package="org.anonymous"/>
 *    一:创建对象：(放入 ioc 容器)
 *       @Component( " 类的id 唯一标识"): 老版本/非三层的其他类
 *
 *       @Controller( " 类的id 唯一标识"): web 控制层
 *       @Service( "类的id 唯一标识") ： service 业务逻辑层
 *       @Repository( " 类的id 唯一标识") : dao 持久层
 *    二：依赖注入(di)
 *       1.对象属性注入：
 *          @Autowired  -- 去 spring 容器中 找 相对应的对象, 自动注入
 *              1. 默认按照对应的类型（接口）从容器中 查找对象 并注入， 针对单个接口的实现类
 *              2. 以属性名（接口的变量名）作为唯一标识从容器中查找对象并注入， 针对 多个 接口实现类时，需要区分 使用 @Qualifier()
 *          @Qualifier( value="实现类的id")
 *              配合 @Autowired, 指定具体需要 注入 容器中的 哪个实现类
 *          eg：
 *          @Autowired
 *          @Qualifier ("accountDao0") //多个实现类对象注入冲突的解决方式二: 指定 id -- 该 id 对应 实现类的 id
 *          private AccountDao accountDao; //接口
 *
 *        注: 如果该接口 有多个实现类, 正常来说, 应该使用 @Qualifier(指定实现类 id)
 *          如果不希望写 @Qualifier(), 可以 让配置/注解 中注入的 id == 需要引入该接口实现类的 变量名
 *          如:  有接口: interface AccountService
 *             两个实现类:
 *              配置中 <bean id="accountService0" class="全限定名"/> // 或注解: @Component(value="accountService0")
 *              配置中 <bean id="accountService1" class="全限定名"/> // 或注解: @Component(value="accountService1")
 *            则 需要 使用的 类中必须写: private AccountService accountService0/accountService1 -- 变量名不能随便写
 *          但是, 推荐使用 @Qualifier(id): 这样更清晰
 *
 *       2.基本类型数据注入： int， String 。。。
 *         1) @Value("赋值")： 给指定的 变量赋值
 *              eg：
 *              @Value( "Jack")
 *              private String name;
 *         2) @Value("${.properties 配置文件的key}")： 获取被 spring ioc 管理的 properties 配置文件的 value
 *              eg： 数据库连接信息的配置文件： jdbc.driver=com.mysql.jdbc.Driver
 *                 在 .xml 中引入配置文件 : <context:property-placeholder location="classpath:jdbc.properties"/>
 *                 获取配置文件中的值
 *                  @Value( "${jdbc.driver}")
 *                  private String driver; // 配置文件被解析后, driver 被赋值为: com.mysql.jdbc.Driver
 *
 *     3.纯注解的方式 操作数据的 crud  (了解:过于复杂)
 *        取消配置文件: 用配置类 替代 -- D:\Develop\IDEA_Project\maven-project\spring_day02_z_annotation
 *        @Configuration: 定义当前类为配置类
 *        //加载 properties 配置文件
 *        @ComponentScan( basePackages="org.anonymous") 开启注解扫描器: <context:component-scan base-package="org.anonymous"/>
 *        @Import( Class[] 配置类的 class 数组) //引入外部配置类(其他三层的配置类) -- 由此 核心配置类 加载其他配置类
 *     4.spring 整合 junit
 *        1) 导入 spring-test 坐标 + junit 坐标
 *        2) 指定谁加载配置文件:
 *          @RunWith( SpringJUnit4ClassRunner.class)
 *        3) 注入
 *              @ContextConfiguration( locations = "classpath:bean.xml") //指定配置文件: 数组
 *              @ContextConfiguration( classes = {SpringConfig.class}) //指定 配置类: 数组
 *              public class test {
 *                  @Autowired //为 测试类 注入 对象(从容器中 获取 对象)
 *                  private AccountService accountService;
 *              }
 *        4) 运行: junit @Test
 *
 *day03: aop
 * aop: Aspect Oriented Programming (面向切面编程) -- 封装 动态代理
 *   在不改变源码的基础上,对源码进行增强
 *    动态代理:
 *      1.基于接口的动态代理(jdk提供): 有一个接口即可- 代理类是接口的子类(与接口实现类是兄弟关系) -- 不用导 cglib 的包
 *      2.基于子类的动态代理(cglib提供): 导包  可以不需要接口 - 代理类是 目标类(被代理类)的子类 - 因此,目标类不能被 final 修饰
 *          代理对象 obj = Enhancer.create(对象.getClass(), new MethodInterceptor() {
 *             /**
 *              * @param o           代理对象的引用
 *              * @param method      被增强的方法
 *              * @param objects     被增强的方法运行过程中需要的参数
 *              * @param methodProxy 方法对象的代理对象
 *              * @return
 *              * @throws Throwable
 *            @Override
 *            public Object intercept(Object o,Method method,Object[]objects,MethodProxy methodProxy)throws Throwable{
 *                  try{
 *                      System.out.println("之前增强。。。");
 *                      Object invoke=method.invoke(user,objects);
 *                      System.out.println("之后增强。。。");
 *                      }catch(IllegalAccessException|IllegalArgumentException|InvocationTargetException e){
 *                          e.printStackTrace();
 *                     }finally{
 *                          System.out.println("最终");
 *                     }
 *
 *              return null;
 *          }
 *          }); --> 对应 Enhancer.create(...);
 *
 *  spring 的底层会自动抉择,选择哪一种代理方式: 如果有接口, 选择第一种; 如果没有接口,选择第二种
 *
 *  需求: 性能监控
 *       测试 某个方法的性能(执行效率)
 *   解决方法:
 *    1. 在源码的的前后 内嵌硬编码, 测试 当前方法的消耗时间 -- 改变了源码 (pass)
 *    2. 在不修改源码的基础上, 在测试类中,分别定义方法 记录测试开始时间/结束时间, 在中间调用 待测试方法(动态代理), 可以得到 测试性能
 *       这也是 可以认为是对原方法的增强
 *    此即 spring aop 的思想, 利用 动态代理,增强 接口/类 的方法
 *      使用:
 *        1.自定义需要增强的逻辑(方法), 告诉 spring 给 ioc 容器中管理的哪个对象的哪个方法进行增强
 *        2.配置文件
 *
 * aop 相关术语:(面试)
 *    Target: 目标对象(被增强对象)
 *    Proxy: 代理对象 (对目标对象的增强封装)
 *    JoinPoint: 连接点(目标对象的所有方法)
 *    Pointcut: 切入点 (目标方法/要被增强的方法)
 *    Advice: 通知/增强 (曾强方法的代码)
 *          前置通知: 要在目标对象方法之前做增强业务 @Before //无论如何，第一个执行
 *          后置通知: 要在目标对象方法之后做增强业务 @AfterReturning //切入点异常，就不会执行
 *          异常通知: 目标对象方法出现异常时做增强业务 @AfterThrowing //切入点异常，就执行
 *          最终通知: 不管成功还是失败,都会做的业务 @After //无论如何，都会执行（afterReturning 之前， afterThrowing 之前）
 *        环绕通知: 所有通知的整合称为 环绕通知 -- 单独使用（用了环绕通知，就不要在使用 其他通知）
 *   Aspect: 切面 (切入点+通知=切面) - 目标方法(被增强方法)和增强方法 合在一起称为 切面
 *   Weaving: 织入 将 切入点(目标方法) 集成到切面的这一过程,我们称为 织入过程
 *      将目标方法和增强方法合成在一起的过程,叫 织入过程
 *      底层实现就是动态代理
 *
 * 通知的执行顺序：(不包括 环绕)
 *      1) before: 前置. 无论如何,都会执行,且都会第一个执行
 *      2) after-returning: 后置. 异常时,不会执行
 *      3) after-throwing: 异常. 异常时,才会执行
 *      4) after: 最终. 无论如何都会执行(不一定是最后一个执行, 最终不代表最后)
 *   1. xml 方式:
 *       1) before: 一定会第一个执行, 与标签顺序无关
 *       2) after: 一定会执行, 但顺序不一定(但一定不会在 before 之前执行)
 *       3) after, after-throwing, after-returning
 *          a) after-throwing 与 after-returning 不会共存(永远只会有一个会执行)
 *          b) after 与 after-throwing, after 与 after-returning 的执行顺序, 与 xml 中的定义顺序有关
 *
 *   2. 注解方式:
 *      1) @Before: 一定会第一个执行的 通知, 与注解顺序无关
 *      2) @After: 一定会执行, 一定会紧接着 切入点之后 执行 (与 xml 不同的地方)
 *      3) @AfterThrowing/@AfterReturning: 不会同时执行, 但一定在 @After 之后执行
 *
 * aop 的三种配置方式：
 *    aop 在 自定义的事务管理器 中的应用
 *  全 xml： D:\Develop\IDEA_Project\maven-project\spring_day03_proxy\src\test\resources\bean.xml
 *  xml + 注解： D:\Develop\IDEA_Project\maven-project\spring_day03_xml_annotation\src\test\resources\bean.xml
 *  全注解： D:\Develop\IDEA_Project\maven-project\spring_day03_annotation\src\test\java\org\anonymous\springConfig\SpringConfig.java
 *
 *   案例: 对方法增强
 *   在 xml 配置文件中： 最终通知 和 后置通知的 执行顺序，与通知的定义顺序有关
 *    前置通知 ： <!--<aop:pointcut id="pt" expression="execution(public void org.anonymous.service.UserServiceImpl.save(..))"/>-->
 *    后置通知
 *    异常通知
 *    最终通知
 *
 *    环绕通知： around - 单独使用- 可以指定 各种通知的执行顺序
 *
 * AspectJ 与 事务：AspectJ 的事务管理： 动态代理 - 自定义切面类
 * 一: 全 xml 方式 使用 aop
 *     1. 把 目标类 交给 aop 管理: target - <bean id="userService" class="org.anonymous.service.UserServiceImpl"/>
 *     2. 把 自定义的切面类 交给 aop 管理: 里面都是通知 - <bean id="aspect" class="org.anonymous.aspect.Aspect"/>
 *     3. 告诉 spring, 切面类的 哪些通知(增强方法) 要在 ioc 中 哪个对象(目标对象: 其中是 连接点)的 哪个方法(切入点)上执行
 *          <aop:config>
 *               <aop:aspect ref="aspect">
 *                <!--定义 切入点(目标方法)-->
 *               <aop:pointcut id="pt" expression="execution(* org.anonymous..UserServiceImpl.save(..))"/>
 *                <!--定义通知: 其他四大通知(略)-->
 *                <!--环绕通知: 必须单独用(不可和其他 通知一起使用)-->
 *                    //method: 通知(方法名)// pointcut-ref: 引入 切入点(目标方法)
 *               <aop:around method="around" pointcut-ref="pt"/>
 *               </aop:aspect>
 *          </aop:config>
 * 二: 半 xml 半 annotation 使用 aop: 一般就使用 环绕 @Round()
 *    1. 在 xml 中 开启注解扫描器: 把 指定 类交给 ioc 管理: <context:component-scan base-package="org.anonymous"/>
 *        使用注解， 把 类交给 ioc 管理： @Component(value=".."), @Controller(), @Service(), @Repository()
 *    2. 开启 aop 注解 (@AspectJ) 支持：
 *          <aop:aspectj-autoproxy/> //等价于 使用配置类时的 @EnableAspectJAutoProxy -- 自动扫描 带有 @Aspect 注解的类
 *    3. 指定 切面类: @Aspect
 *    4. 在 通知(方法) 上指定， 该通知的类别
 *        前置: @Before("execution(切入点)")
 *        后置: @AfterReturning(..)
 *        异常: @AfterThrowing(..)
 *        最终: @After(..)
 *
 *        环绕: @Round(..)
 *    5. 切入点 execution() 表达式的抽取
 *       定义一个 无参无返回值 无实际方法内容的 方法:
 *          1) Pointcut methods should have empty body
 *          2) Pointcut methods should not have throws clause
 *          3) Pointcut methods should have void return type
 *       使用: 谁要使用, 直接调用 方法名()
 *        @Pointcut( value = "execution(* org.anonymous..UserServiceImpl.save(..))")
 *        public void pt() {}
 *       ps: 使用的 aspectjweaver.jar (切面织入工具包) 与注解结合时, 与 版本有关(目前最新 1.9.3, 使用 1.8 以上)
 * 三: 全注解:完全不依赖配置文件
 *   1. 主配置类:
 *      @Configuration // 指定主配置类 //@Import(value = Class[]) : 可选 - 引入 其他 配置类//其他配置类不需要 @Configuration
 *      @ComponentScan( basePackages = "包名") //开启 注解包扫描
 *      @EnableAspectJAutoProxy //开启 aop 注解支持: 自动扫描 带有 @Aspect 注解的类
 *    以上三个 注解, 全部在 主配置类 上使用
 *  2. ioc: @Component(), @Controller(), @Service(), @Repository()
 *  3. 切面类: aop -> @Aspect
 *       通知: @Around(execution(切入点))
 *
 * 案例:
 *   SpringJunit -> accountService -> accountDao -> jdbcTemplate -> druid -> database
 *    accountService: 事务 - 用 aop 给 transfer 方法做事务增强
 *
 *day04: 声明式事务(spring 封装好的 事务管理器: 相当于切面类)
 *      声明式事务是 基于 aop 的事务, 本质就是对方法前后进行拦截, 然后在目标开始之前创建或者加入一个事务,
 *      在执行完成目标方法之后根据执行情况提交或者回滚事务.
 *      声明式事务的最大优点: 不需要通过编程的方式管理事务,这样就无需在业务代码中掺杂事务管理的代码,
 *      只需在配置文件中做相关的事务规则声明(或者通过基于 @Transactional 注解的方式), 便可以将事务规则应用到业务逻辑中
 *      这就是 "非侵入式" 开发方式
 *      声明式事务的缺点: 只能作用到方法级别, 无法做到像 编程式事务 那样可以作用到代码块级别
 *   另: spring 支持 编程式事务管理, 使用 TransactionTemplate
 *   1.jdbcTemplate 在 dao 层的 crud
 *      关键: jdbcTemplate 交给 ioc 时,需要 注入 dataSource 依赖- 构造器方式/set方式
 *       <!--
 *             细节:
 *             只要 dao 层 继承了 JdbcDaoSupport, 就不再需要 JdbcTemplate 属性, 也 不再需要 在配置文件中 将 JdbcTemplate 交给 ioc 管理了
 *             只需要 给 dao 注入 一个 dataSource, 相当于 间接调用 父类(JdbcDaoSupport) 的 setDateSource(dataSource) 方法, 创建 JdbcTemplate 对象
 *             然后 调用 父类的 getJdbcTemplate() 方法, 获取 JdbcTemplate 对象, 即可使用
 *         -->
 *         <property name="dataSource" ref="springDriver"/>
 *     </bean>
 *
 *     <!--  spring 源码: org.springframework.jdbc.core.support.JdbcDaoSupport
 *          @Nullable
 *          private JdbcTemplate jdbcTemplate;
 *
 *          public final void setDataSource(DataSource dataSource) {
 *             // 如果没有 JdbcTemplate,
 *             if (this.jdbcTemplate == null || dataSource != this.jdbcTemplate.getDataSource()) {
 *                 //就利用 传进来的 dataSource : new JdbcTemplate(dataSource);
 *                 this.jdbcTemplate = this.createJdbcTemplate(dataSource);
 *                 this.initTemplateConfig();
 *             }
 *         }
 *         protected JdbcTemplate createJdbcTemplate(DataSource dataSource) {
 *             return new JdbcTemplate(dataSource);
 *         }
 *     -->
 *
 *  2. spring 的声明式事务： 事务管理器  == TransactionManager
 *      祥见: D:\Develop\IDEA_Project\maven-project\spring_day04_b\src\test\resources\bean.xml
 *      提供了事务处理的 切面类(事务管理器): 封装了各种 通知
 *      使用:
 *        1) 把 事务管理器 交给 ioc : PlatformTransactionManager(接口)
 *              实现类(切面类):
 *                   DataSourceTransactionManager : 给 mybatis, jdbcTemplate 做事务
 *                   HibernateTransactionManager : 给 Hibernate 做事务 - hibernate 是 orm 框架的一种
 *                          ORM 框架: 对象关系映射(操作对象就相当于操作数据库的表)
 *                   JPATransactionManager : jpa(Java Persistence API) - 给所有的 orm 框架做事务操作
 *        2) 配置事务处理的 aop (通知, 切入点)
 *             xml 配置: isolation(隔离级别)
 *              一般用 默认值: isolation_default
 *
 *     事务定义对像: TransactionDefinition
 *       一：设置事务的隔离级别 : isolation (一般就用默认值)
 *           事务: 逻辑上的一组操作, 要么同时成功,要么同时失败
 *           特性: acid
 *              atomicity: 原子性 - 整个事务的所有操作, 要么全部完成, 要么全部不完成, 不可能停滞在中间某个环节.
 *                    如果 在事务执行过程中,发生错误,会被 rollback 到事务开始前的状态,就像这个事务从未执行过一样
 *              consistency: 一致性 - 一个事务可以封装改变状态(除非它是一个只读的). 事务必须始终保持系统处于一致的状态, 不管在任何给定的时间并发事务有多少
 *                     事务执行前后, 数据总量不变.
 *              isolation: 隔离性 - 隔离状态执行事务,使它们好像是系统在给定时间内执行的唯一操作.如果有两个事务,运行在相同时间内,执行相同的功能,事务的隔离性
 *                      将确保每一事务在系统中认为只有该事务在使用系统. 这种属性有时称为 串行化, 为了防止事务操作间的混淆,必须串行化或序列化,是的在同一时间仅有一个请求用于同一数据
 *                      在不同的隔离级别下,事务的读取操作,会得到不同的结果.
 *              durability: 持久性 - 在事务完成之后,该事务对数据库所作的更改便持久的保存在数据库中,并不会回滚
 *                     由于一项操作通常会包含许多子操作, 而这些子操作可能会因为硬件的损坏或其他因素产生问题,要正确实现 acid 并不容易
 *                     acid 建议数据库将所有需要更新以及修改的资料一次操作完毕,但实际上并不可行
 *              目前实现 acid 主要有两种方式: 一是 (WAL) write ahead logging(日志式: 现代数据库均基于这种方式); 二是 shadow paging
 *                  WAL 的中心思想 是对数据文件的修改(它们是表和索引的载体),必须是只能发生在 这些修改已经记录了日志之后, 也就是说,在日志记录冲刷到永久存储器之后
 *                      如果我们遵循这个过程,那么我们就不需要在每次事务提交的时候,都把数据页冲刷到磁盘, 因为我们知道在出现崩溃的情况下, 我们可以用日志来恢复数据库
 *                      任何尚未附加到数据页的记录都将先从日志记录中 重做(这叫向前滚动恢复: redo),然后那些未提交的事务做的修改将被从数据页中删除(这叫向后滚动恢复: undo)
 *         isolation: 事务隔离性
 *          数据操作存在的问题:
 *             脏读: 一个事务读取到另一个事务未提交的数据操作结果.
 *             不可重复读: Non-repeatable Reads 一个事务对同一行数据重复读取两次, 但是却得到不同的结果
 *                 包括:
 *                    虚读: 事务 T1 读取某一数据后,事务 T2 对其做了修改,当事务 T1 再次读取该数据时,得到与前一次不同的值 - 针对同一行数据
 *                    幻读: Phantom Reads 事务在操作过程中进行两次查询, 第二次查询的结果包含了第一次查询中未出现的数据 - 增加或减少了 行数
 *                         或者 缺少了第一次查询中出现的数据(这里并不要求两次查询的 sql 语句相同). 这是因为在两次查询过程中有 另一个事务 插入/删除数据造成的
 *          解决方案: 隔离级别 - 一般来说，禁止脏读即可
 *              Read Uncommitted: 读未提交 (未授权读取)  (什么都解决不了) 允许 脏读,但不允许更新丢失. 如果一个事务已经开始写入数据, 则另一个事务
 *                   不允许同时进行 写操作, 但允许其他事务读取此行数据. -- 该隔离级别可以通过 '排他写锁' 实现
 *              Read Committed: 读提交(授权读取) (解决脏读) 允许不可重复读取, 但是有可能出现 幻读. -- 可以通过 '瞬间共享读锁' 和 '排他写锁' 实现
 *              Repeatable Read: 可重复读 (解决脏读和不可重复读)  禁止 不可重复读 和 脏读, 但是有可能出现幻读, 可以通过 '共享读锁' 和 '排他写锁' 实现
 *                    读取数据的事务将会禁止写事务(允许读事务), 写事务则禁止任何其他事务
 *              Serializable: 序列化 (解决所有问题,但是不用) 提供严格的事务隔离. 它要求事务序列化执行, 事务只能一个接着一个的执行,不能并发执行. 仅仅通过 '行级锁' 无法实现
 *                    事务序列化, 必须通过其他机制保证新插入的数据不会被刚执行查询的事务访问到
 *              隔离级别越高, 越能保证数据的完整性和一致性,但是对并发性能的影响也越大.对于多数应用程序,可以优先考虑把数据库系统的隔离级别设置为 Read Committed
 *                它可以避免脏读, 而且具有较好的并发性能,尽管会 导致 不可重复读,幻读,第二类丢失更新这些并发问题.在可能出现这类问题的个别场合,可以由应用程序
 *                采取 悲观锁 或 乐观锁 来控制.
 *      二：设置超时时间
 *          所谓 事务超时, 就是指一个事务所允许执行的最长时间, 如果超过该时间限制但事务还没有完成(回滚/提交), 则自动回滚事务
 *           默认值： -1
 *           xml 配置： timeout
 *           一般用默认值
 *      三：设置是否只读事务
 *          只读事务 ： 对查询有效
 *          非只读事务： 对增删改有效
 *          xml 配置： read-only -- true(只读事务) false(非只读事务)
 *      四: 事务的传播行为
 *          多方法嵌套调用的时候,被调用方法对事物的支持情况.
 *          REQUIRED: 曾删改 - 需要事务
 *          SUPPORTS: 查询 - 可以不要事务
 *    基本设置:
 *       <tx:attributes>
 *           <tx:method name="*" propagation="REQUIRED" read-only="false"/> //增删改,需要事务,非只读
 *           <tx:method name="find*" propagation="SUPPORTS" read-only="true"/> //查询,不需要事务,只读(提高查询效率)
 *       </tx:attributes>
 *
 *  总结: 事务配置方式:
 *      隔离级别, 超时时间 使用默认值
 *    增删改操作:
 *       1). 传播行为: REQUIRED
 *       2). 是否只读: false
 *    查询操作:
 *       1). 查询操作: SUPPORTS
 *       2). 是否只读: true
 *
 *  事务状态对象: TransactionStatus
 *       作用: 获取当前事务是否完成
 *            获取当前事务是否回滚
 *
 *   3. spring 的声明式事务配置
 *       1) 全 xml： D:\Develop\IDEA_Project\maven-project\spring_day04_b\src\test\resources\bean.xml
 *       2) 半 xml： D:\Develop\IDEA_Project\maven-project\spring_day04_b\src\test\resources\bean1.xml
 *       3) 全注解: D:\Develop\IDEA_Project\maven-project\spring_day04_c\src\test\java\org\anonymous\springConfig\SpringConfiguration.java
 *
 * spring5 新特性
 *   spring 使用大量的反射：(ioc/aop) 针对不同的 jdk 版本 效率不同
 *   jdk 1.8 中，对反射的性能 进行了大量的提升。
 *   spring 5 才开始对 jdk 1.8 有支持
 *   也就是说， jdk 1.8+ + spring 5，反射性能极大提升
 *   另: junit 5 的支持
 *
 * 脏读: read out of invalid data, 一个事务读取到了另一事务未提交的修改, 如果修改事务回滚, 则 读取的数据无效
 * 不可重复读: 一个事务范围内, 两个相同的查询返回不同的结果. (一般是针对修改操作, 读取同一行数据)
 *     事务 T1 执行读操作, 同时 事务 T2 执行 修改操作并提交事务, 此后事务 T1 再次执行相同的读操作, 得到与前一次的读取不同的结果
 * 幻读(虚读): 事务 T1 对表中的数据进行了修改, 比如这种修改涉及到表中的 "全部数据行",
 *     同时, 事务 T2 向该表中执行 插入操作, 此后, 事务 T1 提交后, 发现表中还存在没有修改的数据行.
 * 注: 不可重复读 是指 读的时候, 别的事务修改了数据  update
 *     幻读 是指 读的时候, 别的事务插入/删除了数据 insert/delete
 *
 * 关于 @Transactional 注解
 *   value/transactionManager: String, 指定需要使用的 事务管理器的 id
 *   propagation: enum, 传播行为
 *   isolation: enum, 隔离级别
 *   timeout: int, 超时时间
 *   readOnly: boolean, 是否只读(查询设置 true, 增删改 设置 false)
 *   rollbackFor: Class<? extends Throwable>[], 导致事务回滚的异常类数组
 *   rollbackForClassName: String[], 导致事务回滚的异常类全限定名数组
 *   noRollBackFor: Class<? extends Throwable>[], 不会导致事务回滚的异常类数组
 *   noRollBackForClassName: String[], 不会导致事务回滚的异常类全限定名数组
 * 该注解可以配置在 接口, 类, 接口/类的方法上, 需要实现事务功能的方法必须是 public 的
 *  由于 注解不能继承/实现, 不建议在 接口上使用 该注解
 *
 *
 */
public class SSM_Spring {
    public static void main(String[] args) {
        /*java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
        System.out.println(date); //2019-04-15
        Timestamp timestamp = new Timestamp(new java.util.Date().getTime());
        System.out.println(timestamp); //2019-04-15 19:59:54.172*/
        List<Integer> list = Arrays.asList(1, 2, 3);
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }

    //both methods have same erasure
    //表面上看, 两个方法形参不同, 构成了重载: 但是由于 编译时的泛型擦除机制, 两个方法实际上被认为是具有相同参数的方法
    /*public void method(ArrayList<String> list) { //error
        //do something
    }*/

    public void method(ArrayList<Date> list) {
        //do something
    }
}
