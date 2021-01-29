package org.anonymous.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author child
 * 2019/4/13 18:02
 * 模拟 ioc： 解耦， 反射创建对象
 * 解析配置文件: 避免 方法每次调用都会解析配置文件
 * <p>
 * 多例工厂：调用一次工厂方法，就会创建一个 对象
 * 本质就是 通过全限定名 反射对象
 * 单例工厂: 一个类,永远只会得到一个对象
 * 本质就是 在 调用 工厂方法之前,对象已经被创建好了(放入 map 集合), 以后调用方法,都不是创建对象, 而是从容器中取 对象
 * <p>
 * 考察 工厂在生产一个类的对象时，需要 单例 还是 多例， 需要考虑一下几点
 * 1. 该类（被生产对象的类）是否有 操作共享数据
 * 2. 该类 有无线程危机
 * 3. 多个线程可否使用同一个已经创建好的 对象
 */
//@SuppressWarnings("deprecation")
public abstract class BeanFactory {
    private static final Map<String, String> map = new HashMap<>();

    static {
        //解析配置文件: 只需要 .properties 的文件名，不需要 扩展名
        //只加载 .properties 文件： 通过 key 获取 value
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bean");
        //获取指定的 key
        //        resourceBundle.getString()
        //获取所有 key
        Enumeration<String> keys = resourceBundle.getKeys();
        while (keys.hasMoreElements()) {
            //获取 key
            String key = keys.nextElement();
            //获取 value: 全限定名
            String value = resourceBundle.getString(key);
            //把 key=value 放入集合
            map.put(key, value);
        }
    }

    public static Object getBean(String key) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //使用静态代码块， 让配置文件只解析一次
        //        ResourceBundle bundle = ResourceBundle.getBundle("bean.properties");
        //        bundle.getString("");
        //通过 key 获取 value ： value 即 全限定名
        String value = map.get(key);
        Class<?> clazz = Class.forName(value);
        return clazz.newInstance();
    }
}
