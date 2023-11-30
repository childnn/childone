package util;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/01/06
 */
public class AutoPackUtil {

    private static final Map<Class<?>, Map<String, Field>> classFieldCache = new HashMap<>();

    /**
     * @param data                       数据源
     * @param parentIdFieldName          父分类ID字段名
     * @param idFieldName                子分类ID字段名
     * @param childrenFieldName          子分类列表字段名
     * @param topLevelParentIdConditions 顶层父分类过滤条件
     * @param comparator                 同级排序, 可空 空则不排序
     * @param <T>                        数据源类型
     *                                   .@param <Id> ID 类型
     * @return 封装层级结构的数据
     */
    public static <T> List<T> autoPack(
            Iterable<T> data, String parentIdFieldName, String idFieldName, String childrenFieldName,
            Predicate<T> topLevelParentIdConditions, @Nullable Comparator<T> comparator) {
        if (IterableUtils.isEmpty(data)) {
            return Collections.emptyList();
        }
        List<T> list = StreamSupport.stream(data.spliterator(), false)
                .filter(Objects::nonNull).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        List<T> parent = list.stream().filter(topLevelParentIdConditions).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(parent)) {
            return list;
        }
        // 按需排序
        sortIfNeeded(parent, comparator);

        doAutoPack(parent, list, parentIdFieldName, idFieldName, childrenFieldName, comparator);

        return parent;
    }

    /**
     * @param data                       数据源
     * @param parentIdFieldName          父分类ID字段名
     * @param idFieldName                子分类ID字段名
     * @param childrenFieldName          子分类列表字段名
     * @param topLevelParentIdConditions 顶层父分类过滤条件
     * @param <T>                        数据源类型
     *                                   .@param <Id> ID 类型
     * @return 封装层级结构的数据
     */
    public static <T> List<T> autoPack(
            Iterable<T> data, String parentIdFieldName, String idFieldName,
            String childrenFieldName, Predicate<T> topLevelParentIdConditions) {

        return autoPack(data, parentIdFieldName, idFieldName,
                childrenFieldName, topLevelParentIdConditions, null);
        // List<T> list = CollectorsUtil.filter(data, Objects::nonNull);
        // if (CollectionUtils.isEmpty(list)) {
        //     return Collections.emptyList();
        // }
        // List<T> parent = CollectorsUtil.filter(data, topLevelParentIdConditions);
        // if (CollectionUtils.isEmpty(parent)) {
        //     return list;
        // }
        //
        // doAutoPack(parent, list, parentIdFieldName, idFieldName, childrenFieldName, null);
        //
        // return parent;
    }

    /**
     * 递归封装子级结构
     *
     * @see #autoPack
     */
    private static <T> void doAutoPack(
            List<T> parent, List<T> data, String parentIdFieldName,
            String idFieldName, String childrenFieldName, @Nullable Comparator<T> comparator) {
        if (CollectionUtils.isEmpty(parent) || CollectionUtils.isEmpty(data)) {
            return;
        }

        List<Object> parentIds = parent.stream().map(x -> getFiledValue(idFieldName, x)).collect(Collectors.toList());
        data.removeIf(x -> parentIds.contains(getFiledValue(idFieldName, x)));

        List<T> children = data.stream()
                .filter(x -> parentIds.contains(getFiledValue(parentIdFieldName, x)))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(children)) {
            return;
        }
        Map<Object, List<T>> childrenGroup = children.stream()
                .collect(Collectors.groupingBy(x ->
                        ObjectUtils.defaultIfNull(getFiledValue(parentIdFieldName, x), "")));

        parent.forEach(x -> populateChildren(x, childrenGroup, idFieldName, childrenFieldName, comparator));

        doAutoPack(children, data, parentIdFieldName, idFieldName, childrenFieldName, comparator);
    }

    private static <T> Field doGetDeclaredField(String fieldName, Class<?> clazz) {
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(String.format("类`%s`字段`%s`不存在",
                    clazz.getName(), fieldName));
        }
        ReflectionUtils.makeAccessible(field);
        return field;
    }

    /**
     * 获取指定字段的 Field 实例
     *
     * @param fieldName 字段名
     * @param x         实例
     * @param <T>       实例类型 Field 实例
     * @return fieldName 对应的
     */
    private static <T> Field getDeclaredField(String fieldName, T x) {
        Map<String, Field> fieldNameMap = classFieldCache.getOrDefault(x.getClass(), new HashMap<>());

        Field field;
        if ((field = fieldNameMap.get(fieldName)) == null) {
            field = doGetDeclaredField(fieldName, x.getClass());
            fieldNameMap.put(fieldName, field);
            classFieldCache.put(x.getClass(), fieldNameMap);
        }

        return field;
    }

    /**
     * 获取指定对象的指定字段的字段值
     *
     * @param fieldName 字段名
     * @param x         实例
     * @param <T>       实例类型
     * @return fieldName 对应的字段值
     */
    private static <T> Object getFiledValue(String fieldName, T x) {
        Field field = getDeclaredField(fieldName, x);
        try {
            return field.get(x);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("类`%s`反射取`%s`字段值异常",
                    x.getClass().getName(), fieldName));
        }
    }

    /**
     * @param childrenFieldName 子类集合字段名
     * @param childIdFieldName  子级字段名
     * @param childrenGroup     子集按父分类值分组
     * @param x                 result
     * @param <T>               结果类型
     */
    private static <T> void populateChildren(T x, Map<Object, List<T>> childrenGroup,
            String childIdFieldName, String childrenFieldName,
            @Nullable Comparator<T> comparator) {
        Field f = getDeclaredField(childrenFieldName, x);
        try {
            List<T> children = childrenGroup.get(getFiledValue(childIdFieldName, x));
            sortIfNeeded(children, comparator);
            f.set(x, children);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(String.format("类`%s`反射赋值`%s`异常",
                    x.getClass().getName(), childrenFieldName));
        }
    }

    private static <T> void sortIfNeeded(List<T> data, Comparator<T> comparator) {
        if (comparator != null && data != null) {
            data.sort(comparator);
        }
    }

}
