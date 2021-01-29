package org.anonymous.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author child
 * 2019/4/13 18:43
 * 单例工厂 ： 每次调用方法，返回的都是同一个对象
 * 思想： 静态代码块中，先加载配置文件，拿到所有 value（全限定名），
 * 通过 value 创建对象，放入 map 中， 在从 map 中的到 对象
 */
//@SuppressWarnings("deprecation")
public abstract class BeanFactory0 {
    private static Map<String, Object> map;

    //配置文件加载完成，即创建对象： 一个类永远都只有一个对象
    static {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("bean");
            Enumeration<String> keys = resourceBundle.getKeys();
            map = new HashMap<>();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                String value = resourceBundle.getString(key);
                Class<?> clazz = Class.forName(value);
                Object obj = clazz.newInstance();
                //把创建好的对象放入 map
                map.put(key, obj);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //通过 key 返回对象： 同一个对象
    public static Object getBean(String key) {
        return map.get(key);
    }
}
