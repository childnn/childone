package org.anonymous.config.mybatis.spring;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/5 8:50
 * @see org.mybatis.spring.mapper.MapperFactoryBean
 */
public class MyMapperFactoryBean<T> implements FactoryBean<T> {

    private Class<T> mapper;

    public MyMapperFactoryBean(Class<T> mapper) {
        this.mapper = mapper;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getObject() throws Exception {
        final Object mapperProxy = Proxy.newProxyInstance(MyMapperFactoryBean.class.getClassLoader(), new Class[]{mapper},
                (proxy, method, args) -> {
                    System.out.println(method.getName() + ": " + Arrays.toString(args));
                    return null;
        });
        return (T) mapperProxy;
    }

    @Override
    public Class<?> getObjectType() {
        return mapper;
    }
}
