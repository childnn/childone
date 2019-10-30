package com.example.demo.service;

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

    private final ServicePrototype servicePrototype;

    public ServiceSingleton(ServicePrototype servicePrototype) {
        this.servicePrototype = servicePrototype;
    }

    @Lookup // 查找方法, 对于多例对象, 每次获取的不同.
    public abstract ServicePrototype0 getServiceProtoType0();

    // 即使是多例对象, 同一个单列对象中注入的也是同一个对象.
    public ServicePrototype getServicePrototype() {
        return servicePrototype;
    }

}
