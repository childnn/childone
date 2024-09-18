package org.example.aop.config;

import org.aspectj.lang.annotation.Pointcut;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @Before( value = "execution(public * com.jxtech.jxudp.service.BasicBeanService.queryByIds(java.util.List)) && args(pks)",
 * argNames = "jp,pks"
 * )
 * public void before(JoinPoint jp, List<Object> pks) {
 * ...
 * }
 * @since 2024/6/5
 * <a href="https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/pointcuts.html">...</a>
 * <a href="https://eclipse.dev/aspectj/doc/released/progguide/semantics-pointcuts.html">
 * aspectj Pointcuts 语法
 * </a>
 * execution(modifiers-pattern?
 * ret-type-pattern
 * declaring-type-pattern?name-pattern(param-pattern)
 * throws-pattern?)
 */
public class CommonPointcuts {

    // https://docs.spring.io/spring-framework/reference/core/aop/ataspectj/pointcuts.html#aop-common-pointcuts
    // named-pointcut: 具名的 PCD.

    // the method serving as the pointcut signature must have a void return type
    @Pointcut("execution(public * *(..))")
    public void pointcut() {
    }

    @Pointcut("within(org.example.aop.service.*)")
    // @Pointcut("within(org.example.aop..*)") // Error creating bean with name 'myAspect' defined in org.example.aop.config.AopConfig: Failed to instantiate [org.example.aop.config.MyAspect]: Factory method 'myAspect' threw exception with message: Error creating bean with name 'myAspect': Requested bean is currently in creation: Is there an unresolvable circular reference?
    public void pointcutWithin() {
    }

    /**
     * A join point is in the web layer if the method is defined
     * in a type in the com.xyz.web package or any sub-package
     * under that.
     */
    @Pointcut("within(com.xyz.web..*)")
    public void inWebLayer() {
    }

    /**
     * A join point is in the service layer if the method is defined
     * in a type in the com.xyz.service package or any sub-package
     * under that.
     */
    @Pointcut("within(com.xyz.service..*)")
    public void inServiceLayer() {
    }

    /**
     * A join point is in the data access layer if the method is defined
     * in a type in the com.xyz.dao package or any sub-package
     * under that.
     */
    @Pointcut("within(com.xyz.dao..*)")
    public void inDataAccessLayer() {
    }

    /**
     * A business service is the execution of any method defined on a service
     * interface. This definition assumes that interfaces are placed in the
     * "service" package, and that implementation types are in sub-packages.
     * <p>
     * If you group service interfaces by functional area (for example,
     * in packages com.xyz.abc.service and com.xyz.def.service) then
     * the pointcut expression "execution(* com.xyz..service.*.*(..))"
     * could be used instead.
     * <p>
     * Alternatively, you can write the expression using the 'bean'
     * PCD, like so "bean(*Service)". (This assumes that you have
     * named your Spring service beans in a consistent fashion.)
     */
    @Pointcut("execution(* com.xyz..service.*.*(..))")
    public void businessService() {
    }

    /**
     * A data access operation is the execution of any method defined on a
     * DAO interface. This definition assumes that interfaces are placed in the
     * "dao" package, and that implementation types are in sub-packages.
     */
    @Pointcut("execution(* com.xyz.dao.*.*(..))")
    public void dataAccessOperation() {
    }

}
