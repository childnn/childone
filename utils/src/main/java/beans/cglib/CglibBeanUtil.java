package beans.cglib;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.beans.BeanMap;
import net.sf.cglib.core.Converter;
import org.apache.commons.collections4.CollectionUtils;

import java.beans.Introspector;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author MiaoOne
 * 2019/8/7 10:35
 * bean 和 map 的相互转换
 * 1. copy 属性实体类必须要有 setter 方法; bean 转 map 必须要有 getter 方法
 * 2. 使用 lombok 不可以开启 链式 set 模式: 即不能使用 @Accessors(chain = true)
 * 3. 对于属性类型为 Collection, 属性名相同, 泛型类型不同但是泛型中的属性相同
 * (如 Collection<DO><->Collection<DTO>)的情况, 此工具类已经做了处理
 * 4. 如果需要处理其他特殊类型, 如 {@link Map}, {@link String} <-> {@link Date}
 * 等相互转换, 请自行实现 {@link DefaultConverter#customiseValue}
 */
public final class CglibBeanUtil {

    //-------------------------------------------------------
    // bean copy.
    //-------------------------------------------------------

    /**
     * 创建 target class 实例, 将 source bean 的属性, 默认执行深拷贝
     *
     * @param sourceBean       the source bean
     * @param targetClass      the target bean
     * @param ignoreProperties array of property names to ignore
     * @param <S>              the source type
     * @param <T>              the target type
     * @return the target instance of the target class
     */
    public static <S, T> T copy(S sourceBean, Class<T> targetClass, final String... ignoreProperties) {
        return copy(sourceBean, targetClass, true, ignoreProperties);
    }

    /**
     * 创建 target class 实例, 将 source bean 的属性
     *
     * @param sourceBean       the source bean
     * @param targetClass      the target bean
     * @param deepCopy         whether need deep clone
     * @param ignoreProperties array of property names to ignore
     * @param <S>              the source type
     * @param <T>              the target type
     * @return the target instance of the target class
     */
    public static <S, T> T copy(S sourceBean, Class<T> targetClass, boolean deepCopy, final String... ignoreProperties) {
        if (sourceBean == null || targetClass == null) {
            return null;
        }

        BeanCopier beanCopier = BeanCopier.create(sourceBean.getClass(), targetClass, true);
        T targetBean = newInstance(targetClass);

        beanCopier.copy(sourceBean, targetBean,
                new DefaultConverter<>(sourceBean, targetClass, deepCopy, ignoreProperties)
                /*(value, target, context) -> {
                    String filedName = resolveFiledNameFromSetter(context);

                    return Arrays.asList(ignoreProperties).contains(filedName) ?
                            null : resolveValue(sourceBean, targetClass, value, target, context, filedName);
                }*/);

        return targetBean;
    }

    // public static <S, T> T copy(S sourceBean, Class<T> targetClass, boolean deepCopy, Converter converter, final String... ignoreProperties) {
    //     BeanCopier beanCopier = BeanCopier.create(sourceBean.getClass(), targetClass, true);
    //     T targetBean = newInstance(targetClass);
    //
    //     beanCopier.copy(sourceBean, targetBean, converter);
    //
    //     return targetBean;
    // }

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
    /*public static <S, T> T copy(S sourceBean, Class<T> targetClass) {
        BeanCopier beanCopier = BeanCopier.create(sourceBean.getClass(), targetClass, true);
        T targetBean = newInstance(targetClass);

        beanCopier.copy(sourceBean, targetBean,
                // 处理集合泛型类型不同的情形, 如 Collection<FooVo> list -> Collection<FooBo> list
                // value: 正在处理字段的值, target: 正在处理的目标字段类型, context: setter 方法名称
                (value, target, context) -> {
                    System.out.printf("value: %s, target: %s, context: %s%n", value, target, context);
                    String filedName = resolveFiledNameFromSetter(context);

                    return resolveValue(sourceBean, targetClass, value, target, context, filedName);
                });
        return targetBean;
    }*/

    // @SuppressWarnings({"rawtypes", "unchecked"})
    // private static <S, T> Object resolveValue(S sourceBean, Class<T> targetClass, Object value, Class target, Object context, String filedName) {
    //     if (Collection.class.isAssignableFrom(target)) {
    //         try {
    //             // Method targetMethod = targetClass.getDeclaredMethod(context.toString(), target);
    //             // source/target 属性名称必须完全一致才能 copy
    //             // 属性字段名称
    //             // String filedName = StringUtils.uncapitalize(context.toString().replace("set", ""));
    //             // Field filed = targetClass.getDeclaredField(filedName);
    //
    //             // Spring 工具类: 获取集合泛型类型
    //             // ResolvableType fT = ResolvableType.forField(filed);
    //             // Class<?> targetGeneric = fT.resolveGeneric(0);
    //             Class<?> targetGeneric = resolveGenericType(targetClass, filedName);
    //
    //             // Field sourceField = sourceBean.getClass().getDeclaredField(filedName);
    //             // Class<?> sourceGeneric = ResolvableType.forField(sourceField).resolveGeneric(0);
    //             Class<?> sourceGeneric = resolveGenericType(sourceBean, filedName);
    //
    //             // 如果泛型同类型, 直接返回 value
    //             // 如果不同类型泛型, 需要 deep-copy
    //             return sourceGeneric == targetGeneric ? value : copyBeanList(new ArrayList<>((Collection) value), targetGeneric);
    //             // Object sourceValue = sourceField.get(sourceBean);
    //             // targetClass.getField(StringUtils.uncapitalize(context.toString().replace("set", "")))
    //             // TypeVariable[] typeParameters = target.getTypeParameters();
    //             // System.out.println("typeParameters = " + typeParameters);
    //         } catch (NoSuchFieldException e) {
    //             throw new RuntimeException(e);
    //         }
    //     }
    //     return value;
    // }

    /**
     * @see java.beans.PropertyDescriptor
     * @see Introspector#decapitalize(java.lang.String)
     * @param context setter method name
     * @return the property name
     */
    // private static String resolveFiledNameFromSetter(Object context) {
    //     return StringUtils.uncapitalize(context.toString().replace("set", ""));
    // }
    //
    // private static Class<?> resolveGenericType(Object source, String fieldName) throws NoSuchFieldException {
    //     return resolveGenericType(source.getClass(), fieldName);
    // }
    //
    // @SuppressWarnings("rawtypes")
    // private static Class<?> resolveGenericType(Class clazz, String fieldName) throws NoSuchFieldException {
    //     return resolveGenericType(clazz.getDeclaredField(fieldName));
    // }
    //
    // private static Class<?> resolveGenericType(Field field) {
    //     return ResolvableType.forField(field).resolveGeneric(0);
    // }

    /**
     * bean copy: 将集合中每个对象的属性封装到另一个类的对象的属性
     *
     * @param sourceList  数据源集合
     * @param targetClass 目标类的 Class 对象
     * @param <S>         source 泛型
     * @param <T>         target 泛型
     * @return target bean list
     */
    public static <S, T> List<T> copyBeanCollection(Collection<S> sourceList, Class<T> targetClass, final String... ignoreProperties) {
        return copyBeanList(sourceList, targetClass, true, ignoreProperties);
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
    public static <S, T> Collection<T> copyBeanCollection(Collection<S> sourceList, final Class<T> targetClass, final boolean deepCopy,
                                                    Supplier<Collection<T>> collectionFactory, final String... ignoreProperties) {
        return CollectionUtils.isEmpty(sourceList) || targetClass == null ?
                Collections.emptyList()
                : sourceList.stream().map(s -> copy(s, targetClass, deepCopy, ignoreProperties)).collect(Collectors.toCollection(collectionFactory));
     /*   List<T> destList = new ArrayList<>();
        for (S sourceBean : sourceList) {
            T targetBean = copy(sourceBean, targetClass);
            destList.add(targetBean);
        }
        return destList;*/
    }

    public static <S, T> List<T> copyBeanList(Collection<S> sourceList, final Class<T> targetClass,
                                              final boolean deepCopy, final String... ignoreProperties) {
        return (List<T>) copyBeanCollection(sourceList, targetClass, deepCopy, ArrayList::new, ignoreProperties);
    }

    public static <S, T> Set<T> copyBeanSet(Collection<S> sourceList, final Class<T> targetClass,
                                            final boolean deepCopy, final String... ignoreProperties) {
        return (Set<T>) copyBeanCollection(sourceList, targetClass, deepCopy, HashSet::new, ignoreProperties);
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
        return null != bean ? BeanMap.create(bean) : null;
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
        return CollectionUtils.isEmpty(objects) ?
                Collections.emptyList()
                : objects.stream().map(CglibBeanUtil::bean2Map).collect(Collectors.toList());
    }

    public static <T> List<T> maps2Objects(List<Map<String, Object>> maps, final Class<T> clazz) {
        return CollectionUtils.isEmpty(maps) || clazz == null ?
                Collections.emptyList()
                : maps.stream().map(map -> map2Bean(map, clazz)).collect(Collectors.toList());
    }

    private static <T> T newInstance(Class<T> clazz) {
        try {
            Constructor<T> cons = clazz.getDeclaredConstructor();
            if (!cons.isAccessible()) {
                cons.setAccessible(true);
            }
            return cons.newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

}
