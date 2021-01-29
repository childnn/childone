package org.anonymous.xml.factory;

import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.Properties;

/**
 * shadow
 * 2019/3/19 16:16
 * 工厂类: 只用来 生产 对象
 */
public class BeanFactory {
    public static Object getObject(String className) throws Exception {
        Properties pro = new Properties();
        pro.load(Objects.requireNonNull(BeanFactory.class.getClassLoader().getResourceAsStream("prop.properties")));
        Constructor<?> obj = Class.forName(pro.getProperty(className)).getDeclaredConstructor();
        obj.setAccessible(true); //暴力反射, 防止 类的默认构造非 public(一般不会这样...)
        return obj.newInstance();
    }
}
