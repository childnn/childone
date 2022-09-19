package com.example.demo.config;

import com.example.demo.service.ServicePrototype;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/04/23
 */
@Component
public class MyObjectFactory implements ObjectFactory<ServicePrototype>, BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public ServicePrototype getObject() throws BeansException {
        return beanFactory.getBean(ServicePrototype.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
