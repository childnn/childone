package com.example.demo.service;

import com.example.demo.config.MyObjectFactory;
import com.example.demo.config.ProtoFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/24 16:07
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public abstract class ServiceSingleton {

    @Autowired
    private ProtoFactory protoFactory;

    @Autowired
    private MyObjectFactory myObjectFactory;

    private final ServicePrototype servicePrototype;

    public ServiceSingleton(ServicePrototype servicePrototype) {
        this.servicePrototype = servicePrototype;
    }

    // lookup-method 的定义格式: <public|protected> [abstract] <return-type> theMethodName(no-arguments);
    // the container will generate
    // runtime subclasses of the method's containing class via CGLIB
    // the Spring Framework implements this method injection by using bytecode
    // generation from the CGLIB library to dynamically generate a subclass that overrides the method.
    @Lookup // 查找方法, 对于多例对象, 每次获取的不同.
    public abstract ServicePrototype0 getServiceProtoType0();

    // 即使是多例对象, 同一个单列对象中注入的也是同一个对象.
    public ServicePrototype getServicePrototype() {
        return servicePrototype;
    }

    public void method() {
        ServicePrototype0 prototype0 = getServiceProtoType0();
        System.out.println("prototype0 = " + prototype0);

        ServicePrototype servicePrototype1 = protoFactory.getServicePrototype();
        System.out.println("servicePrototype1 = " + servicePrototype1);

        ServiceSingleton serviceSingleton = protoFactory.getServiceSingleton();
        System.out.println("serviceSingleton = " + serviceSingleton);
        // Object singletonTarget = AopProxyUtils.getSingletonTarget(serviceSingleton);
        // System.out.println("singletonTarget = " + singletonTarget);
        // System.out.println("serviceSingleton.getClass() = " + serviceSingleton.getClass());
        System.out.println("AopUtils.getTargetClass(serviceSingleton) = " + AopUtils.getTargetClass(serviceSingleton));

        myObjectFactory.
    }

}
