package org.anonymous.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;
import net.sf.cglib.core.Converter;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MiaoOne
 * 2019/8/7 10:35
 * * bean 和 map 的相互转换
 * * 1. 实体类必须要有 setter 方法
 * * 2. 使用 lombok 不可以开启 链式 set 模式: 即不能使用 @Accessors(chain = true)
 */
public final class BeanUtil {

    //-------------------------------------------------------
    // bean copy.
    //-------------------------------------------------------

    /**
     * 没有使用 类型转换的 bean copy 方法. 如果需要使用类似 String <--> Date 的转换, 可以自定义转换器.
     *
     * @param sourceBean  源对象(被 copy 的对象).
     * @param targetClass 目标对象(结果对象)的 Class 类型.
     * @param <S>         源对象泛型.
     * @param <T>         结果对象泛型.
     * @return 结果对象.
     * @see Converter
     */
    public static <S, T> T copy(S sourceBean, Class<T> targetClass) {
        BeanCopier beanCopier = BeanCopier.create(sourceBean.getClass(), targetClass, false);
        T targetBean = newInstance(targetClass);
        beanCopier.copy(sourceBean, targetBean, null);
        return targetBean;
    }

    /**
     * bean copy: 将集合中每个对象的属性封装到另一个类的对象的属性
     *
     * @param sourceList  数据源集合
     * @param targetClass 目标类的 Class 对象
     * @param <S>         source 泛型
     * @param <T>         target 泛型
     * @return target bean list
     */
    public static <S, T> List<T> copyBeanList(List<S> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList) || targetClass == null) {
            return Collections.emptyList();
        }
        return sourceList.stream().map(s -> copy(s, targetClass)).collect(Collectors.toList());
     /*   List<T> destList = new ArrayList<>();
        for (S sourceBean : sourceList) {
            T targetBean = copy(sourceBean, targetClass);
            destList.add(targetBean);
        }
        return destList;*/
    }

    //-------------------------------------------------------
    // bean to map / map to bean.
    //-------------------------------------------------------

    /**
     * 必须要有 get 方法: 需要从给定的 bean 中获取属性
     *
     * @param bean object
     * @param <T>  type of the bean
     * @return map of the bean: key -- 属性名, value -- 属性值
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, Object> bean2Map(T bean) {
        if (null != bean) {
            return BeanMap.create(bean);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("============= copy bean ===============");
        Stu child = new Stu(111, "child");
        Person person = copy(child, Person.class);
        System.out.println("person = " + person);
        List<Person> people = copyBeanList(Arrays.asList(child, child, child), Person.class);
        System.out.println("people = " + people);

        System.out.println("============= bean2map =================");
        Stu stu = new Stu();
        stu.setAge(12);
        stu.setName("jack");
        Map<String, Object> map = bean2Map(stu);
        map.forEach((k, v) -> System.out.println(String.format("k: %s -- v: %s", k, v)));

        System.out.println("============= map2bean ===============");
        Stu bean = map2Bean(map, Stu.class);
        System.out.println("bean = " + bean);

        System.out.println("============= objs2maps ===============");
        List<Stu> list = Arrays.asList(new Stu(13, "jack"), new Stu(16, "rose"));
        List<Map<String, Object>> maps = objectsToMaps(list);
        maps.forEach(m -> m.forEach((k, v) -> System.out.println(k + ": " + v)));

        System.out.println("============== maps2objs ==============");
        List<Stu> stus = maps2Objects(maps, Stu.class);
        stus.forEach(System.out::println);
    }

    /**
     * 必须要有 set 方法: 否则属性就是 默认值
     *
     * @param map   key 属性名, value 属性值
     * @param clazz the class of the entity
     * @param <T>   entity type
     * @return bean
     */
    public static <T> T map2Bean(Map<String, Object> map, Class<T> clazz) {
        T bean = newInstance(clazz);
        map2Bean(map, bean);
        return bean;
    }

    private static void map2Bean(Map<String, Object> map, Object bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
    }

    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objects) {
        if (CollectionUtils.isEmpty(objects)) {
            return Collections.emptyList();
        }

        return objects.stream().map(BeanUtil::bean2Map).collect(Collectors.toList());

    }

    public static <T> List<T> maps2Objects(List<Map<String, Object>> maps, final Class<T> clazz) {
        if (CollectionUtils.isEmpty(maps) || clazz == null) {
            return Collections.emptyList();
        }

        return maps.stream().map(map -> map2Bean(map, clazz)).collect(Collectors.toList());
    }

    private static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<T> cons = clazz.getConstructor();
            if (!cons.isAccessible()) {
                cons.setAccessible(true);
            }
            return cons.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Stu {
        private int age;
        private String name;
    }

    @Data
    private static class Person {
        private int age;
        private String name;
    }
}
