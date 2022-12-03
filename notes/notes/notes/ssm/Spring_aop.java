package org.ssm.spring;

/**
 * @author child
 * 2019/4/29 11:22
 *
 * web 服务器: Tomcat, JBoss/WildFly, IBM WebSphere Application Server, Oracle WebLogic Server
 *
 * Normal Form: 范式(标准形式)
 * One of the central tenents(信条) of the Spring Framework is that of non-invasiveness(非侵入性).
 *
 * Aspect-oriented Programming(AOP) complements Object-oriented Programming(OOP) by providing another way of
 *  thinking about program structrue. The key unit of modularity in OOP is the class, whereas in AOP the unit of
 *  modularity is the aspect. Aspects ebable the modularization of concerns(such as transaction management) that cut
 *  across multiple types and objects. (Such concerns are often termed "crosscutting" concerns in AOP literature.)
 *
 * AOP is used in the Spring Framework to:
 *   · Provide declarative enterprise services. The most inportment such service is declarative transaction management. //声明式事务管理
 *   · let users implement custom aspects, complementing their use of OOP with AOP.
 *
 * AOP concepts and terminology: 概念, 术语
 *   · Aspect: a modularization of a concern that cuts across multiple classes. (切面)
 *   · Join point(连接点): a point during the execution of a program, such as the execution of a method or the handing of
 *          an exception. In Spring AOP, a join point always represents a method execution.
 *   · Advice(通知): Action taken by an aspect at a particular join point. Different types advice include "around",
 *          "before" and "after" advice.
 *   · Pointcut(切入点): A predicate(谓语) that matches join points. Advice is associated with a pointcut expression and runs at
 *          any join point matched by the pointcut.
 *   · Introduction: Declaring addtional methods or fields on behalf of a type. Spring AOP lets you introduce new interfaces
 *          (and a corresponding implementation) to any advised object.
 *   · Target object: An object being adviced by one or more aspects. Also referred to as the "advised object".
 *          Since Spring AOP is implements by using runtime proxies, this object is always a proxied object.
 *   · AOP proxy: An object created by the AOP framework in order to implements the aspect contracts(advise method
 *          executions and so on). In the Spring Framework, an AOP proxy is a JDK dynamic proxy or a CGLIB proxy.
 *   · Weaving: Linking aspects with other application types or objects to create an advised object. This can be done at
 *          compile time(using the AspectJ compiler, for example), load time, or at runtime. Spring AOP, like other pure
 *          Java AOP frameworks, performs weaving at runtime.
 *
 * Spring AOP includes the follwing types of advice:
 *    · Before advice: Advice that runs before a join point but that does not have the ability to prevent execution flow
 *          proceeding to the join point(unless it throws an exception)
 *    · After returning advice: Adivce to be run after a join point comletes normally(for example, if a method returns without
 *          throwing an exception).
 *    · After throwing advice: Advice to be executed if a method exits by throwing an exception.
 *    · After (finally) advice: Advice to be executed regardless of the means by which a join point exits (normal or
 *          exceptional return).
 *    · Around advice: Advice that surrounds a join point such as a method invocation. This is the most powerful
 *          kind of advice. Around advice can perform custom behavior before and after the method invocation. It is
 *          alse responsible for choosing whether to proceed to the join point or to shortcut the advised method
 *          execution by returning its own return value or throwing an exception.
 *
 * Pointcut Designators : 切入点 选择器(指示器)  -- PCD
 *    Spring AOP supports the following AspectJ pointcut designators (PCD) for use in pointcut expressions:
 *      · execution: For matching method execution join points. This is the primary pointcut designators to use
 *           when working with Spring AOP.
 *      · within: Limits matching to join points within certain types(the execution of a method declared within a matching type
 *           with Spring AOP)
 *      · this: Limits matching to join points (the execution of methods when using Spring AOP) where the bean reference
 *          (Spring AOP proxy) is an instance of the given type.
 *      · target: Limits matching to join points (the execution of methods when using Spring AOP) where the target object
 *          (application object being proxied) is an instance of the given type.
 *      · args: Limits matching to join points (the execution of methods when using Spring AOP) where the arguments are instances of
 *          the given types.
 *      · @target: Limits matching to join points (the execution of methods when using Spring AOP) where the class of the
 *          executing object has an annotation of the given type.
 *      · @args: Limits matching to join points(the execution of methods when using Spring AOP) where the runtime type
 *          of the actual arguments passed have annotation of the given types.
 *      · @within: Limits matching to join points within types that have the given annotation (the execution of methods declared
 *           in types with the given annotation when using Spring AOP).
 *      · @annotation: Limits matching to join points where the subject of the join point (the method being excuted in Spring AOP)
 *          has the given annotation.
 *
 * PCD 的示例: 都是 method execution -- 以每一个方法为基本单位
 *   The following examples show some common pointcut expressions:
 *       execution(public * *(..)) :                            The execution of any public method
 *       execution(* set*(..)):                                 The execution of any method with a name that begins with set
 *       execution(* com.xyz.service.AccountService.*(..)):     The execution of any method defined by the AccountService interface
 *       execution(* com.xyz.service.*.*(..)):                  The execution of any method defined in the service package
 *       execution(* com.xyz.service..*.*(..)):                 The execution of any method defined in the service package or one of its sub-packages
 *       within(com.xyz.service.*):                             Any join point (method execution only in Spring AOP) within the service package
 *       within(com.xyz.service..*):                            Any join point (method execution only in Spring AOP) within the service package or one of its sub-packages
 *       this(com.xyz.service.AccountService):                  Any join point (method execution only in Spring AOP) where the proxy implements the AccountService interface
 *       target(com.xyz.service.AccountService):                Any join point (method execution only in Spring AOP) where the target object implements the AccountService interface
 *       args(java.io.Serializable):                            Any join point (method execution only in Spring AOP) that takes a single parameter and where the argument passed at runtime is Serializable
 *         注: Note that the pointcut given in this example is different from execution(* *(java.io.Serializable)). (只有一个参数, 且参数是 Serializable 类型)
 *              The args version matches if the argument passed at runtime is Serializable,
 *              and the execution version matches if the method signature declares a single parameter of type Serializable.
 *       @target( org.springframework.transaction.annotation.Transactional): Any join point (method execution only in Spring AOP) where the target object has a @Transactional annotation
 *       @within( org.springframework.transaction.annotation.Transactional): Any join point (method execution only in Spring AOP) where the declared type of the target object has an @Transactional annotation
 *       @annotation( org.springframework.transaction.annotation.Transactional): Any join point (method execution only in Spring AOP) where the executing method has an @Transactional annotation
 *       @args( com.xyz.security.Classified): Any join point (method execution only in Spring AOP) which takes a single parameter, and where the runtime type of the argument passed has the @Classified annotation
 *       bean(tradeService): Any join point (method execution only in Spring AOP) on a Spring bean named tradeService
 *       bean(*Service): Any join point (method execution only in Spring AOP) on Spring beans having names that match the wildcard expression *Service
 *
 * JDK dynamic proxy or CGLIB:
 *        If the target object to be proxied implements at least one interface, a JDK dynamic proxy is used.
 *        All of the interfaces implemented by the target type are proxied.
 *        If the target object does not implement any interfaces, a CGLIB proxy is created.
 *  if you want to force the use of CGLIB proxying(for example, to proxy every method defined for the target object,
 *      not only those implemented by its interfaces), you can do so. However, you should consider the following issues:
 *         · With CGLIB, final methods cannot be advised, as they cannot be overridden in runtime-generated subclasses.
 *         · As of Spring 4.0, the constructor of your proxied object is NOT called twice anymore,
 *              since the CGLIB proxy instance is created through Objenesis.
 *              Only if your JVM does not allow for constructor bypassing,
 *              you might see double invocations and corresponding debug log entries from Spring’s AOP support.
 *
 * To force the use of CGLIB proxies, set the value of the proxy-target-class attribute
 *      of the <aop:config> element to true, as follows:
 *
 *      <aop:config proxy-target-class="true"> //强制使用 CGLIB 的动态代理： 基于类的动态代理
 *          <!-- other beans defined here... -->
 *      </aop:config>
 *
 * To force CGLIB proxying when you use the @AspectJ auto-proxy support,
 *      set the proxy-target-class attribute of the <aop:aspectj-autoproxy> element to true, as follows:
 *      <aop:aspectj-autoproxy proxy-target-class="true"/> //使用 @AspectJ 时
 *
 * To be clear, using proxy-target-class="true" on <tx:annotation-driven/>,
 *   <aop:aspectj-autoproxy/>, or <aop:config/> elements forces the use of CGLIB proxies for all three of them.
 *
 *
 */
public class Spring_aop {
   /* @Aspect : 示例: 定义一个切面类(@Aspect), 专门用来定义 各个层 (web/service/dao) 的 切入点

    public class SystemArchitecture {*/
        /**
         * A join point is in the web layer if the method is defined
         * in a type in the com.xyz.someapp.web package or any sub-package
         * under that.
         */
//        @Pointcut("within(com.xyz.someapp.web..*)")
//        public void inWebLayer() {}

        /**
         * A join point is in the service layer if the method is defined
         * in a type in the com.xyz.someapp.service package or any sub-package
         * under that.
         */
//        @Pointcut("within(com.xyz.someapp.service..*)")
//        public void inServiceLayer() {}

        /**
         * A join point is in the data access layer if the method is defined
         * in a type in the com.xyz.someapp.dao package or any sub-package
         * under that.
         */
//        @Pointcut("within(com.xyz.someapp.dao..*)")
//        public void inDataAccessLayer() {}

        /**
         * A business service is the execution of any method defined on a service
         * interface. This definition assumes that interfaces are placed in the
         * "service" package, and that implementation types are in sub-packages.
         *
         * If you group service interfaces by functional area (for example,
         * in packages com.xyz.someapp.abc.service and com.xyz.someapp.def.service) then
         * the pointcut expression "execution(* com.xyz.someapp..service.*.*(..))"
         * could be used instead.
         *
         * Alternatively, you can write the expression using the 'bean'
         * PCD, like so "bean(*Service)". (This assumes that you have
         * named your Spring service beans in a consistent fashion.)
         */
//        @Pointcut("execution(* com.xyz.someapp..service.*.*(..))")
//        public void businessService() {}

        /**
         * A data access operation is the execution of any method defined on a
         * dao interface. This definition assumes that interfaces are placed in the
         * "dao" package, and that implementation types are in sub-packages.
         */
//        @Pointcut("execution(* com.xyz.someapp.dao.*.*(..))")
//        public void dataAccessOperation() {}

//    }
}
