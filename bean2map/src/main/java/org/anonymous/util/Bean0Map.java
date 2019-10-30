package org.anonymous.util;

import net.sf.cglib.beans.BeanMap;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author child
 * 2019/6/24 12:03
 */
public class Bean0Map {
    /**
     * //必须要有 get 方法: 需要从给定的 bean 中获取属性
     *
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> bean2Map(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (null != bean) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * //必须要有 set 方法: 否则属性就是 默认值
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static <T> T map2Bean$(Map<String, Object> map, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        T bean = clazz.getConstructor().newInstance();
        map2Bean(map, bean);
//        BeanMap beanMap = BeanMap.create(bean);
//        beanMap.putAll(map);
        return bean;
    }

    public static <T> T map2Bean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objects) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (null != objects && objects.size() > 0) {
            Map<String, Object> map = null;
            for (T bean : objects) {
                map = bean2Map(bean);
                list.add(map);
            }
        }
        return list;
    }

    public static <T> List<T> maps2Objects(List<Map<String, Object>> maps, Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<T> list = new ArrayList<>();
        if (maps != null && maps.size() > 0) {
//            Map<String, Object> map = null;
            for (Map<String, Object> beanMap : maps) {
                T instance = clazz.getConstructor().newInstance();
                T bean = map2Bean(beanMap, instance);
                list.add(bean);
            }
        }
        return list;
    }

}
