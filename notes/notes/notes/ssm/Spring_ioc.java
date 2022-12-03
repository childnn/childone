package org.ssm.spring;

/**
 * @author child
 * 2019/4/23 22:36
 * ObjectFactory, FactoryBean, BeanFactory, ObjectProvider
 * InitializingBean(@PostConstruct//init-method), DisposableBean(@PreDestroy//destroy-method),
 * BeanPostProcessor (RequiredAnnotationBeanPostProcessor), Ordered, Lifecycle, SmartLifecycle, LifecycleProcessor, Phased,
 * BeanFactoryPostProcessor,
 * AutowiredAnnotationBeanPostProcessor, CommonAnnotationBeanPostProcessor, PersistenceAnnotationBeanPostProcessor, RequiredAnnotationBeanPostProcessor
 * BeanDefinition，InjectionPoint
 *
 * bean.xml 中 .properties 文件的引入:
 *  <context:property-placeholder location="classpath:jdbc.properties"/>
 *  该标签是对 spring 的类的封装: PropertySourcesPlaceholderConfigurer
 *
 * 注解 和 xml 同时使用时, xml 会覆盖 注解:
 * Annotation injection is performed before XML injection.
 *  Thus, the XML configuration overrides the annotations for properties wired through both approaches.
 *
 * mybatis 的 方法: getMapper()
 * spring 的 方法: getBean()
 *
 * 何为依赖(dependency): 被依赖的 bean 是 依赖 bean 的一个属性
 *      If a bean is a dependency of another bean,
 *       that usually means that one bean is set as a property of another.
 *
 * 术语:
 *  regular expression: 正则表达式
 *  presentation objects : 表现层对象
 *  service layer objects: business logic - 服务层(业务逻辑层) 对象
 *  data access objects (DAOs)： repository/persistence - 数据访问层对象(持久层)
 *  the fully qualified classname: 全限定名
 *  JPA Object-Relational Mapping standard: Java persistence api orm standard - java 持久层 api 对象关系映射标准
 *  fully qualified resource locations(absolute locations): 绝对路径
 *  relative paths: 相对路径
 *  instantiation: 实例化
 *  decouple: 解耦
 *  constructor-based dependency injection - 基于构造方法的依赖注入
 *  setter-based dependency injection - 基于 set 方法的依赖注入
 *  POJO ： has no dependencies on container specific interfaces, base classes or annotations.
 *  abstract method: 抽象方法
 *  concrete method: 具体方法
 *  AOP interceptors : aop 拦截器
 *
 *
 * design philosophy
 *   1. provide choice at every level
 *   2. accommodate diverse perspectives
 *   3. maintain strong backward compatibility : 向后兼容性
 *   4. care about api(application programming interface) design
 *   5. set high statndards for code quality
 *
 *  naming beans: java.beans.Introspector.decapitalize  -- ioc 命名规范
 *
 * ioc：
 *      IoC is also known as dependency injection (DI).
 *      A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container.
 *      The (org.springframework.context.ApplicationContext) interface represents the Spring IoC container
 *          and is responsible for instantiating, configuring, and assembling the beans.
 *    子类: ClassPathXmlApplicationContext or FileSystemXmlApplicationContext.
 *
 * BeanFactory -> listableBeanFactory -> ConfigurableListableBeanFactory -> DefaultListableBeanFactory
 *                                    -> ApplicationContext
 *
 *  ClassPathXmlApplicationContext <- AbstractXmlApplicationContext <- AbstractRefreshableConfigApplicationContext <-AbstractRefreshableApplicationContext
 *  <- AbstractApplicationContext <- ConfigurableApplicationContext (extends Closeable) <- ApplicationContext <- ListableBeanFactory <- BeanFactory (The root interface for accessing a Spring bean container.)
 */
public class Spring_ioc {
    /**
     * Inner class names
     * If you want to configure a bean definition for a static nested class, you have to use the binary name of the nested class.
     * For example, if you have a class called SomeThing in the com.example package,
     *      and this SomeThing class has a static nested class called OtherThing,
     *      the value of the class attribute on a bean definition would be com.example.SomeThing$OtherThing.
     * Notice the use of the $ character in the name to separate the nested class name from the outer class name.
     */
    /**
     *  factory bean: "factory bean" refers to a bean that is configured in the Spring container
     *                  and that creates objects through an instance or static factory method
     *
     * static factory method: 静态工厂本身可以不需要 构造
     *  xml-code: 静态工厂方法: 将方法返回值(对象)作为 bean 注册到 ioc
     *   <bean id="clientService" class="examples.ClientService" factory-method="createInstance"/>
     *
     * The following example shows a class that would work with the preceding bean definition:
     * public class ClientService {
     *     private static ClientService clientService = new ClientService(); //这里创建的对象就是 静态工厂类本身的对象, 也可以是其他任意类的对象
     *     private ClientService() {}
     *
     *     public static ClientService createInstance() { //工厂方法必须为静态方法
     *         return clientService;
     *     }
     * }
     *----------------------------------
     * instance factory method: 实例工厂方法 (non-static method) //工厂本身必须是 可以被实例化的 - 通过工厂的对象去调用 工厂的方法
     * <!-- the factory bean, which contains a method called createInstance() -->
     * <bean id="serviceLocator" class="examples.DefaultServiceLocator">
     *     <!-- inject any dependencies required by this locator bean -->
     * </bean>
     * <!-- the bean to be created via the factory bean -->
     * <bean id="clientService" factory-bean="serviceLocator" factory-method="createClientServiceInstance"/>
     * The following example shows the corresponding Java class:
     * public class DefaultServiceLocator {
     *
     *     private static ClientService clientService = new ClientServiceImpl();
     *
     *     public ClientService createClientServiceInstance() {
     *         return clientService;
     *     }
     * }
     *
     * plus:----------------
     *  One factory class can also hold more than one factory method, as the following example shows:
     * <bean id="serviceLocator" class="examples.DefaultServiceLocator">
     *     <!-- inject any dependencies required by this locator bean -->
     * </bean>
     *
     * <bean id="clientService" factory-bean="serviceLocator" factory-method="createClientServiceInstance"/> //对象一
     *
     * <bean id="accountService" factory-bean="serviceLocator" factory-method="createAccountServiceInstance"/> //对象二
     *
     * The following example shows the corresponding Java class:
     * public class DefaultServiceLocator { //实例工厂: 用来实例化任意对象
     *     private static ClientService clientService = new ClientServiceImpl();
     *     private static AccountService accountService = new AccountServiceImpl();
     *     public ClientService createClientServiceInstance() {
     *         return clientService;
     *     }
     *     public AccountService createAccountServiceInstance() {
     *         return accountService;
     *     }
     * }
     */
    /** list, set, map, properties
     *
     * <bean id="moreComplexObject" class="example.ComplexObject">
     *     <!-- results in a setAdminEmails(java.util.Properties) call -->
     *     <property name="adminEmails">
     *         <props>
     *             <prop key="administrator">administrator@example.org</prop>
     *             <prop key="support">support@example.org</prop>
     *             <prop key="development">development@example.org</prop>
     *         </props>
     *     </property>
     *     <!-- results in a setSomeList(java.util.List) call -->
     *     <property name="someList">
     *         <list>
     *             <value>a list element followed by a reference</value>
     *             <ref bean="myDataSource" />
     *         </list>
     *     </property>
     *     <!-- results in a setSomeMap(java.util.Map) call -->
     *     <property name="someMap">
     *         <map>
     *             <entry key="an entry" value="just some string"/>
     *             <entry key ="a ref" value-ref="myDataSource"/>
     *         </map>
     *     </property>
     *     <!-- results in a setSomeSet(java.util.Set) call -->
     *     <property name="someSet">
     *         <set>
     *             <value>just some string</value>
     *             <ref bean="myDataSource" />
     *         </set>
     *     </property>
     * </bean>
     */
    /**
     * Collection Merging: 集合 同 key 覆盖
     * <beans>
     *     <bean id="parent" abstract="true" class="example.ComplexObject">
     *         <property name="adminEmails">
     *             <props>
     *                 <prop key="administrator">administrator@example.com</prop>
     *                 <prop key="support">support@example.com</prop>
     *             </props>
     *         </property>
     *     </bean>
     *     <bean id="child" parent="parent">
     *         <property name="adminEmails">
     *             <!-- the merge is specified on the child collection definition -->
     *             <props merge="true"> //合并集合: 相同的 key, 后者覆盖前者
     *                 <prop key="sales">sales@example.com</prop>
     *                 <prop key="support">support@example.co.uk</prop>
     *             </props>
     *         </property>
     *     </bean>
     * <beans>
     *
     *  Notice the use of the "merge=true" attribute on the <props/> element of the "adminEmails" property
     *      of the child bean definition. When the child bean is resolved and instantiated by the container,
     *      the resulting instance has an adminEmails Properties collection that contains the result of
     *      merging the child’s adminEmails collection with the parent’s adminEmails collection.
     *   The following listing shows the result:
     *
     *      administrator=administrator@example.com
     *      sales=sales@example.com
     *      support=support@example.co.uk
     */
    /** strongly-typed collection: 泛型集合
     * public class SomeClass {
     *     private Map<String, Float> accounts;
     *     public void setAccounts(Map<String, Float> accounts) {
     *         this.accounts = accounts;
     *     }
     * }
     * <beans>
     *     <bean id="something" class="x.y.SomeClass">
     *         <property name="accounts">
     *             <map>
     *                 <entry key="one" value="9.99"/>
     *                 <entry key="two" value="2.75"/>
     *                 <entry key="six" value="3.99"/>
     *             </map>
     *         </property>
     *     </bean>
     * </beans>
     * When the accounts property of the something bean is prepared for injection,
     * the generics information about the element type of the strongly-typed Map<String, Float> is
     * available by reflection. Thus, Spring’s type conversion infrastructure recognizes
     * the various value elements as being of type Float, and the string values (9.99, 2.75, and 3.99)
     * are converted into an actual Float type.
     */
    /**
     * Null and Empty String Values : null 与 空字符串
     *
     * <bean class="ExampleBean">
     *     <property name="email" value=""/>
     * </bean>
     * The preceding example is equivalent to the following Java code:
     *       exampleBean.setEmail("");
     *
     * <bean class="ExampleBean">
     *     <property name="email">
     *         <null/>
     *     </property>
     * </bean>
     * The preceding configuration is equivalent to the following Java code:
     *       exampleBean.setEmail(null);
     */
    /**
     * lazy-initialized beans: 懒加载
     *  A lazy-initialized bean tells the IoC container to create a bean instance
     *      when it is first requested, rather than at startup.
     *
     *  懒加载的全局定义:
     *   <beans default-lazy-init="true">
     *     <!-- no beans will be pre-instantiated... -->
     *   </beans>
     *  懒加载的局部定义:
     *   <bean id="lazy" class="com.something.ExpensiveToCreateBean" lazy-init="true"/>
     *   <bean name="not.lazy" class="com.something.AnotherBean"/>
     *
     * 与多例对象一样, 懒加载的单例对象(为多例对象设置懒加载好像没有什么意义),如果被其他 非懒加载的单例对象依赖,
     *   那么, 该懒加载机制将自动失效, 因为 该懒加载 bean 被其他 立即加载的 bean 所依赖
     */
    /**
     * bean 标签的 depends-on 属性: (测试结果,不一定完全准确)
     *      1. 一个单例对象 depends-on 另一个 单例对象: 这时, depends-on 属性等价于 ref 属性
     *      2. 一个单例对象 depends-on 一个多例对象: 这时, depends-on 也是等价于 ref,
     *          但是 这个被依赖的 多例对象会被初始化 两次(depends-on 一次, ref 一次)
     *
     *  However, sometimes dependencies between beans are less direct.
     *      An example is when a static initializer in a class needs to be triggered,
     *      such as for database driver registration.
     *      The depends-on attribute can explicitly force one or more beans to be initialized
     *      before the bean using this element is initialized.
     *      The following example uses the depends-on attribute to express a dependency on a single bean:
     *
     *          <bean id="beanOne" class="ExampleBean" depends-on="manager"/>
     *          <bean id="manager" class="ManagerBean" />
     *
     * To express a dependency on multiple beans, supply a list of bean names as
     *   the value of the depends-on attribute (commas, whitespace, and semicolons are valid delimiters):
     *
     *      <bean id="beanOne" class="ExampleBean" depends-on="manager,accountDao">
     *          <property name="manager" ref="manager" />
     *      </bean>
     *
     *      <bean id="manager" class="ManagerBean" />
     *      <bean id="accountDao" class="x.y.jdbc.JdbcAccountDao" />
     *
     *    	The depends-on attribute can specify both an initialization-time dependency and,
     *    	in the case of singleton beans only, a corresponding destruction-time dependency.
     *    	Dependent beans that define a depends-on relationship with a given bean are destroyed first,
     *    	prior to the given bean itself being destroyed. Thus, depends-on can also control shutdown order.
     *
     * mysql 源码: com.mysql.jdbc;
     *  public class Driver extends NonRegisteringDriver implements java.sql.Driver {
     *     public Driver() throws SQLException {
     *     }
     *
     *     static {
     *         try {
     *             DriverManager.registerDriver(new Driver());
     *         } catch (SQLException var1) {
     *             throw new RuntimeException("Can't register driver!");
     *         }
     *     }
     * }
     *
     */
    /** Method Injection 方法注入
     * lifecycle of beans : 生命周期问题
     *   一个单例对象依赖于一个多例对象, 要想每次获取的单例对象中的多例对象都不是相同的, 那么可以让该单例对象实现 ApplicationContextAware 接口
     *   正常来说: 单例对象中注入一个多例对象, 该单例对象只会创建一次, 那么其 多例对象属性也只会是第一次创建的那一个
     *
     * A problem arises when the bean lifecycles are different.
     *  Suppose singleton bean A needs to use non-singleton (prototype) bean B, perhaps on each method invocation on A.
     *  The container creates the singleton bean A only once, and thus only gets one opportunity to set the properties.
     *  The container cannot provide bean A with a new instance of bean B every time one is needed.
     *
     * A solution is to forego some inversion of control.
     * You can make bean A aware of the container by implementing the ApplicationContextAware interface,
     * and by making a getBean("B") call to the container ask for (a typically new) bean B instance every time bean A needs it.
     * The following example shows this approach:
     *
     * // a class that uses a stateful Command-style class to perform some processing
     * package fiona.apple;
     *
     * // Spring-API imports
     * import org.springframework.beans.BeansException;
     * import org.springframework.context.ApplicationContext;
     * import org.springframework.context.ApplicationContextAware;
     *
     * public class CommandManager implements ApplicationContextAware {  //基于接口的动态代理(method injection): 对应下面的 基于类的动态代理(lookup method injection)
     *     private ApplicationContext applicationContext;
     *     public Object process(Map commandState) {
     *         // grab a new instance of the appropriate Command
     *         Command command = createCommand();
     *         // set the state on the (hopefully brand new) Command instance
     *         command.setState(commandState);
     *         return command.execute();
     *     }
     *
     *  //单例对象依赖于多例对象, 这里使用 getBean() 方法, 保证单例对象的每次使用 都会注入一个不同的 多例对象
     *     //In the case of the CommandManager class in the previous code snippet,
     *     //the Spring container dynamically overrides the implementation of the createCommand() method.
     *     protected Command createCommand() {
     *         // notice the Spring API dependency!
     *         return this.applicationContext.getBean("command", Command.class); //
     *     }
     *     public void setApplicationContext(
     *             ApplicationContext applicationContext) throws BeansException {
     *         this.applicationContext = applicationContext;
     *     }
     * }
     *
     * //基于类的动态代理: Lookup Method Injection
     *  类 不能 final
     *  类中的方法 不能 final (被代理的方法)
     *
     *  package fiona.apple;
     *
     * // no more Spring imports!
     *
     * public abstract class CommandManager {
     *     public Object process(Object commandState) {
     *         // grab a new instance of the appropriate Command interface
     *         Command command = createCommand();
     *         // set the state on the (hopefully brand new) Command instance
     *         command.setState(commandState);
     *         return command.execute();
     *     }
     *     // okay... but where is the implementation of this method?
     *     protected abstract Command createCommand();
     * }
     *
     * In the client class that contains the method to be injected (the CommandManager in this case),
     * the method to be injected requires a signature of the following form:
     *           <public|protected> [abstract] <return-type> theMethodName(no-arguments);
     *
     * If the method is abstract, the dynamically-generated subclass implements the method.
     * Otherwise, the dynamically-generated subclass overrides the concrete method defined in the original class.
     * Consider the following example:
     * <!-- a stateful bean deployed as a prototype (non-singleton) -->
     * <bean id="myCommand" class="fiona.apple.AsyncCommand" scope="prototype">
     *     <!-- inject dependencies here as required -->
     * </bean>
     * <!-- commandProcessor uses statefulCommandHelper -->
     * <bean id="commandManager" class="fiona.apple.CommandManager">
     *     <lookup-method name="createCommand" bean="myCommand"/>
     * </bean>
     * The bean identified as commandManager calls its own createCommand() method
     *   whenever it needs a new instance of the myCommand bean.
     *    You must be careful to deploy the myCommand bean as a prototype if that is actually what is needed.
     *    If it is a singleton, the same instance of the myCommand bean is returned each time.
     *
     * 注解:
     * Alternatively, within the annotation-based component model,
     *    you can declare a lookup method through the @Lookup annotation, as the following example shows:
     * public abstract class CommandManager {
     *     public Object process(Object commandState) {
     *         Command command = createCommand();
     *         command.setState(commandState);
     *         return command.execute();
     *     }
     *     @Lookup("myCommand") //指定返回值类型
     *     protected abstract Command createCommand();
     * }
     *
     * Or, more idiomatically, you can rely on the target bean
     *    getting resolved against the declared return type of the lookup method:
     *
     * public abstract class CommandManager {
     *     public Object process(Object commandState) {
     *         MyCommand command = createCommand();
     *         command.setState(commandState);
     *         return command.execute();
     *     }
     *     @Lookup //不指定属性值, 默认返回值
     *     protected abstract MyCommand createCommand();
     * }
     *
     * By using Java configuration, you can create a subclass of CommandManager
     *   where the abstract createCommand() method is overridden in such a way
     *      that it looks up a new (prototype) command object. The following example shows how to do so:
     * @Bean
     * @Scope("prototype") //多例
     * public AsyncCommand asyncCommand() {
     *     AsyncCommand command = new AsyncCommand();
     *     // inject dependencies here as required
     *     return command;
     * }
     *
     * @Bean
     * public CommandManager commandManager() {
     *     // return new anonymous implementation of CommandManager with createCommand()
     *     // overridden to return a new prototype Command object
     *     return new CommandManager() {
     *         protected Command createCommand() {
     *             return asyncCommand();
     *         }
     *     }
     * }
     *
     */

    /** ioc: 1.5.2
     * As a rule, you should use the prototype scope for all stateful(有状态的) beans and the singleton scope for stateless(无状态的) beans.
     * A data access object (DAO) is not typically configured as a prototype, because a typical DAO does not hold any conversational state. (会话状态)
     *
     * scopes: singleton, prototype
     *         request, session, application(ServletContext), webSocket   -- web-scoped beans
     *      The request, session, application, and websocket scopes are available
     *          only if you use a web-aware Spring ApplicationContext implementation (such as XmlWebApplicationContext).
     *
     *  request scope:
     *   xml:
     *      <bean id="loginAction" class="com.something.LoginAction" scope="request"/>
     *      The Spring container creates a new instance of the LoginAction bean
     *          by using the loginAction bean definition for each and every HTTP request.
     *          That is, the loginAction bean is scoped at the HTTP request level.
     *         When the request completes processing, the bean that is scoped to the request is discarded.
     *  注解:
     *   @RequestScope
     *   @Component
     *   public class LoginAction {
     *     // ...
     *   }
     *
     *  session scope:
     *      <bean id="userPreferences" class="com.something.UserPreferences" scope="session"/>
     *      @SessionScope
     *      @Component
     *      public class UserPreferences {
     *          // ...
     *      }
     *
     *  application scope: ServletContext - 单例对象: 整个 ioc 只有一个 该对象,
     *                  applicationContext: 一个 ServletContext 只有一个 applicationContext 对象, 整个 ioc 可能有 多个 applicationContext
     *    This is somewhat similar to a Spring singleton bean but differs in two important ways:
     *      It is a singleton per ServletContext, not per Spring 'ApplicationContext'
     *       (for which there may be several in any given web application),
     *       and it is actually exposed and therefore visible as a ServletContext attribute.
     *    xml:
     *      <bean id="appPreferences" class="com.something.AppPreferences" scope="application"/>
     *    注解:
     *      @ApplicationScope
     *      @Component
     *      public class AppPreferences {
     *          // ...
     *      }
     *
     */
    /**
     *  request, session 的依赖问题
     *
     *      <bean id="userPreferences" class="com.something.UserPreferences" scope="session">
     *          <aop:scoped-proxy/>
     *      </bean>
     *      <bean id="userManager" class="com.something.UserManager">
     *          <property name="userPreferences" ref="userPreferences"/>
     *      </bean>
     *
     *   <aop:scoped-proxy/>: 启用 基于 cglib 的动态代理(基于类的动态代理)
     *    如果不加这个代理标签, 则 二者都为 单例对象, 也就意味着 整个容器只有一个 UserPreferences (session)
     *    这并不是我们所希望的,因此设置代理, 这样一来, userManager 中注入的 对象, 实际上是 类的代理对象
     *    CGLIB proxies intercept only public method calls! Do not call non-public methods on such a proxy.
     *        They are not delegated to the actual scoped target object.
     *   如果配代理类有接口, 可以使用 JDK 自带的 interface-based proxy
     *     <!-- DefaultUserPreferences implements the UserPreferences interface -->
     *      <bean id="userPreferences" class="com.stuff.DefaultUserPreferences" scope="session">
     *              <aop:scoped-proxy proxy-target-class="false"/> //false: 不是基于继承的代理(基于类:cglib), 而是基于实现的代理(基于接口:jdk )
     *      </bean>
     *
     *      <bean id="userManager" class="com.stuff.UserManager">
     *          <property name="userPreferences" ref="userPreferences"/>
     *      </bean>
     */
    /**
     * 初始化方法: Initialization Callbacks
     *    1. <bean/> 标签的 init-method 属性
     *    2. 实现 InitializingBean 接口
     *    3. @PostConstructor
     *
     * xml-code
     *      <bean id="exampleInitBean" class="examples.ExampleBean" init-method="init"/>
     * java-code:
     * public class ExampleBean {
     *     public void init() {
     *         // do some initialization work
     *     }
     * }
     *
     * The preceding example has almost exactly the same effect as the following example (which consists of two listings):
     * xml-code:
     *      <bean id="exampleInitBean" class="examples.AnotherExampleBean"/>
     * java-code:
     * public class AnotherExampleBean implements InitializingBean {
     *     public void afterPropertiesSet() {
     *         // do some initialization work
     *     }
     * }
     *
     * 销毁方法: Destruction Callbacks
     *      1. <bean/> 标签的 destroy-method
     *      2. 实现 DisposableBean 接口
     *      3. @PreDestroy
     * xml-code:
     *      <bean id="exampleInitBean" class="examples.ExampleBean" destroy-method="cleanup"/>
     * java-code:
     * public class ExampleBean {
     *     public void cleanup() {
     *         // do some destruction work (like releasing pooled connections)
     *     }
     * }
     * The preceding definition has almost exactly the same effect as the following definition:
     * xml-code:
     *      <bean id="exampleInitBean" class="examples.AnotherExampleBean"/>
     * java-code:
     * public class AnotherExampleBean implements DisposableBean {
     *     public void destroy() {
     *         // do some destruction work (like releasing pooled connections)
     *     }
     * }
     *
     *
     * 如果项目中有多个类需要使用 初始化方法: 可以命名相同的方法(eg: init())
     * 这样,只需在 xml 的 根标签(<beans/>) 中定义属性:
     *   <beans default-init-method="init" default-destroy-method="destroy">
     *  这样, 所有类的 初始化方法 init 都会执行
     *
     * 就近原则:
     *      当 <beans/> 和 <bean/> 都定义了 初始化/销毁方法时, 以 <bean/> 定义的为准
     */
    /**
     * 继承：
     * //声明为 抽象的父类，只是作为一个模板，被子类继承（不会被 ioc 实例化）
     * <bean id="inheritedTestBean" abstract="true" class="org.springframework.beans.TestBean">
     *     <property name="name" value="parent"/>
     *     <property name="age" value="1"/>
     * </bean>
     *
     * <bean id="inheritsWithDifferentClass" class="org.springframework.beans.DerivedTestBean" parent="inheritedTestBean" init-method="initialize">
     *     <property name="name" value="override"/>  //姓名被重写，年龄被继承
     *     <!-- the age property value of 1 will be inherited from parent -->
     * </bean>
     */
    /**
     * bean.xml 中 .properties 文件的引入:
     *
     *  //等价于: <context:property-placeholder location="classpath:jdbc.properties"/>
     * <bean class="org.springframework.context.support.PropertySourcesPlaceholderConfigurer">
     *     <property name="locations" value="classpath:com/something/jdbc.properties"/>
     * </bean>
     *
     * <bean id="dataSource" destroy-method="close"
     *         class="org.apache.commons.dbcp.BasicDataSource">
     *     <property name="driverClassName" value="${jdbc.driverClassName}"/>
     *     <property name="url" value="${jdbc.url}"/>
     *     <property name="username" value="${jdbc.username}"/>
     *     <property name="password" value="${jdbc.password}"/>
     * </bean>
     *
     * PropertySourcesPlaceholderConfigurer 还可以用来引入 .property 文件中定义的 全限定名
     *   (在一个接口有多个实现类时,同样用来解耦 decouple)
     *   <bean class="org.springframework.beans.factory.config.PropertySourcesPlaceholderConfigurer">
     *     <property name="locations">
     *         <value>classpath:com/something/strategy.properties</value>
     *     </property>
     *     <property name="properties">
     *         <value>custom.strategy.class=com.something.DefaultStrategy</value>
     *     </property>
     * </bean>
     *
     * <bean id="serviceStrategy" class="${custom.strategy.class}"/> //动态方式引入 类的全限定名
     */
    /**
     * @Bean  By default, the bean name is the same as the method name.
     *
     *  public class BeanOne {
     *     public void init() {
     *         // initialization logic
     *     }
     * }
     * public class BeanTwo {
     *     public void cleanup() {
     *         // destruction logic
     *     }
     * }
     *
     * @Configuration
     * public class AppConfig {
     *     @Bean(initMethod = "init")
     *     public BeanOne beanOne() {
     *         return new BeanOne();
     *     }
     *     @Bean(destroyMethod = "cleanup")
     *     public BeanTwo beanTwo() {
     *         return new BeanTwo();
     *     }
     * }
     *
     *    @Scope("prototype"): 对象类别
     *
     * injecting inter-bean dependency:
     *
     * @Configuration
     * public class AppConfig {
     *     @Bean
     *     public BeanOne beanOne() {
     *         return new BeanOne(beanTwo());  //直接通过方法调用,获取返回值对应的 bean
     *     }
     *     @Bean
     *     public BeanTwo beanTwo() {
     *         return new BeanTwo();
     *     }
     * }
     * 	This method of declaring inter-bean dependencies works
     * 	    only when the @Bean method is declared within a @Configuration class.
     * 	    You cannot declare inter-bean dependencies by using plain @Component classes.
     *
     * @Lazy creation on first access instead of on startup
     *
     */

     /* @Import(Class[]) : 配置类 中引入其他配置类
     * @ImportResource(): 配置类中 引入 xml 配置文件
     *
     * xml 中引入 配置类:
     *  <beans>
     *     <!-- enable processing of annotations such as @Autowired and @Configuration -->
     *     <context:annotation-config/> //启动 配置类 的注解支持
     *     <context:property-placeholder location="classpath:/com/acme/jdbc.properties"/> //引入 jdbc 配置信息
     *
     *     <bean class="com.acme.AppConfig"/>
     *
     *     <bean class="org.springframework.jdbc.datasource.DriverManagerDataSource"> //在配置类中使用了 @Autowired 注解, 可以不用指定 id
     *         <property name="url" value="${jdbc.url}"/>
     *         <property name="username" value="${jdbc.username}"/>
     *         <property name="password" value="${jdbc.password}"/>
     *     </bean>
     * </beans>
     *
     * 直接使用 注解扫描器: @Configuration 会被当做 @Component, 以同样的方式扫描其中的注解
     *
     * Using <context:component-scan/> to pick up @Configuration classes
     * Because @Configuration is meta-annotated with @Component,
     * @Configuration-annotated classes are automatically candidates for component scanning.
     * Using the same scenario as describe in the previous example,
     * we can redefine system-test-config.xml to take advantage of component-scanning.
     * Note that, in this case, we need not explicitly declare <context:annotation-config/>,
     * because <context:component-scan/> enables the same functionality.
     *
     * The following example shows the modified system-test-config.xml file:
     *
     * <beans>
     *     <!-- picks up and registers AppConfig as a bean definition -->
     *     <context:component-scan base-package="com.acme"/> //开启类 注解扫描器,就不用再 写  <context:annotation-config/> 了
     *     <context:property-placeholder location="classpath:/com/acme/jdbc.properties"/>
     *
     *     <bean class="org.springframework.jdbc.datasource.DriverManagerDataSource">
     *         <property name="url" value="${jdbc.url}"/>
     *         <property name="username" value="${jdbc.username}"/>
     *         <property name="password" value="${jdbc.password}"/>
     *     </bean>
     * </beans>
     *
     */
    /**
     * 配置类中引入 xml 配置文件
     *
     * @ImportResource("classpath:bean.xml")
     *
     */
}

