package util;

import net.sf.cglib.core.Converter;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ResolvableType;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see S the source type
 * @see T the target type
 * @since 2021/6/29 10:50
 */
public class DefaultConverter<S, T> implements Converter {

    private final S sourceBean;
    private final Class<T> targetClass;
    private final List<String> ignoreProperties;
    private final boolean deepCopy;

    public DefaultConverter(S sourceBean, Class<T> targetClass, String... ignoreProperties) {
        this(sourceBean, targetClass, true, ignoreProperties);
    }

    public DefaultConverter(S sourceBean, Class<T> targetClass, boolean deepCopy, String... ignoreProperties) {
        Assert.notNull(sourceBean, "The source bean must not be null!");
        Assert.notNull(targetClass, "The target class must not be null!");
        Assert.noNullElements(ignoreProperties, "The ignore properties must not contains null element!");
        this.sourceBean = sourceBean;
        this.targetClass = targetClass;
        this.ignoreProperties = Arrays.asList(ignoreProperties);
        this.deepCopy = deepCopy;
    }

    /**
     * @param context setter method name
     * @return the property name
     * @see java.beans.PropertyDescriptor
     * @see Introspector#decapitalize(java.lang.String)
     */
    private static String resolveFiledNameFromSetter(Object context) {
        return StringUtils.uncapitalize(context.toString().replace("set", ""));
    }

    private static Class<?> resolveGenericType(Object source, String fieldName) throws NoSuchFieldException {
        return resolveGenericType(source.getClass(), fieldName);
    }

    @SuppressWarnings("rawtypes")
    private static Class<?> resolveGenericType(Class clazz, String fieldName) throws NoSuchFieldException {
        return resolveGenericType(clazz.getDeclaredField(fieldName));
    }

    private static Class<?> resolveGenericType(Field field) {
        return ResolvableType.forField(field).resolveGeneric(0);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected Object resolveValue(
            S sourceBean, Class<T> targetClass,
            Object value, Class currentInCopyFiledType,
            /*Object context, */String filedName, boolean deepCopy) {

        // 处理集合泛型类型不同的情形, 如 Collection<FooVo> list -> Collection<FooBo> list
        // value: 正在处理字段的值, target: 正在处理的目标字段类型, context: setter 方法名称
        // 属性为 Collection 类型, 检测集合泛型在 source 和 target 中是否一致,
        // 不一致则执行 copy list 转换.
        if (Collection.class.isAssignableFrom(currentInCopyFiledType)) {
            try {
                Field sourceField = sourceBean.getClass().getDeclaredField(filedName);
                Field targetField = targetClass.getDeclaredField(filedName);
                Class<?> sourceFieldType = sourceField.getType();
                Class<?> targetFieldType = targetField.getType();
                if (sourceFieldType != targetFieldType) {
                    throw new IllegalStateException(
                            String.format("Can't convert different type from [%s#%s] with type [%s] to [%s#%s] with type [%s]",
                                    sourceField.getDeclaringClass(), sourceField.getName(), sourceFieldType.getName(),
                                    targetField.getDeclaringClass(), targetField.getName(), targetFieldType.getName()));
                }

                // Method targetMethod = targetClass.getDeclaredMethod(context.toString(), currentInCopyFiledType);
                // source/currentInCopyFiledType 属性名称必须完全一致才能 copy
                // 属性字段名称
                // String filedName = StringUtils.uncapitalize(context.toString().replace("set", ""));
                // Field filed = targetClass.getDeclaredField(filedName);

                // Spring 工具类: 获取集合泛型类型
                // ResolvableType fT = ResolvableType.forField(filed);
                // Class<?> targetGeneric = fT.resolveGeneric(0);
                Class<?> targetGeneric = resolveGenericType(targetField/*targetClass, filedName*/);
                boolean isAssignableFromSet = Set.class.isAssignableFrom(currentInCopyFiledType);
                // 集合泛型为简单类型或简单类型集合
                // 这里使用 ArrayList/HashSet, 在实际中可能会有问题, 比如实际的类型不是 ArrayList/HashSet
                if (BeanUtils.isSimpleProperty(targetGeneric) && deepCopy) {
                    // 具体类型: set/list 具体实现
                    if (!currentInCopyFiledType.isInterface() && !Modifier.isAbstract(currentInCopyFiledType.getModifiers())) {
                        return ((Collection) currentInCopyFiledType.getDeclaredConstructor().newInstance()).addAll((Collection) value);
                    }
                    if (isAssignableFromSet) {
                        // Set valSet = (Set) value;
                        return new HashSet<>((Set) value);
                    } else /*
                    if (List.class.isAssignableFrom(currentInCopyFiledType)
                     || Collection.) */ {
                        List valList = (List) value;
                        return new ArrayList<>((List) value);
                    }
                }

                // Field sourceField = sourceBean.getClass().getDeclaredField(filedName);
                // Class<?> sourceGeneric = ResolvableType.forField(sourceField).resolveGeneric(0);
                Class<?> sourceGeneric = resolveGenericType(sourceField/*sourceBean, filedName*/);

                // 如果泛型同类型, 直接返回 value
                // 如果不同类型泛型, 需要 deep-copy
                // since 2021年6月29日
                // 不能直接返回 value, 如果直接返回, 是集合的引用, 不能实现深拷贝
                return deepCopy || targetGeneric != sourceGeneric // 如果集合泛型类型不同, 则强制 deep-copy
                        ?
                        (isAssignableFromSet
                                ? BeanUtil.copyBeanSet((Collection) value, targetGeneric, deepCopy)
                                : BeanUtil.copyBeanList((Collection) value, targetGeneric, deepCopy)
                        ) : value; // otherwise: !deepCopy && targetClass == sourceGeneric
                // return/* sourceGeneric == targetGeneric ?
                //         value : */BeanUtil.copyBeanList(new ArrayList<>((Collection) value), targetGeneric);
                // Object sourceValue = sourceField.get(sourceBean);
                // targetClass.getField(StringUtils.uncapitalize(context.toString().replace("set", "")))
                // TypeVariable[] typeParameters = currentInCopyFiledType.getTypeParameters();
                // System.out.println("typeParameters = " + typeParameters);
            } catch (NoSuchFieldException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }

        // if (currentInCopyFiledType.isArray()) {
        //     Class componentType = currentInCopyFiledType.getComponentType(); // 获取数组的组件类型
        //     Array.newInstance(componentType);
        //     // componentType.cast()
        //     Object[] arrVal = (Object[]) value;
        //     // return Array.newInstance(currentInCopyFiledType);
        //     return Arrays.copyOf(arrVal, arrVal.length);
        // }

        // if (BigDecimal.class.isAssignableFrom(currentInCopyFiledType)) {
        //     return new BigDecimal(value.toString());
        // }

        // 非简单类型, 继续执行拷贝
        // 默认简单类型: 基本数据类型 ({@code boolean}, {@code byte},
        //      {@code char}, {@code short}, {@code int},
        //      {@code long}, {@code float}, and {@code double}.)
        // {@code java.lang.String}, {@code java.lang.Number}
        if (deepCopy && !BeanUtils.isSimpleProperty(currentInCopyFiledType)
            /*!currentInCopyFiledType.isPrimitive()
                && String.class != currentInCopyFiledType
                && !Number.class.isAssignableFrom(currentInCopyFiledType)*/) {

            return BeanUtil.copy(value, currentInCopyFiledType, deepCopy);
        }
        // 子类根据需要实现
        return customiseValue(sourceBean, targetClass, value, currentInCopyFiledType, filedName, deepCopy);
    }

    /**
     * 默认直接返回 value, 子类可根据需要设置如 date <-> string 的转换.
     *
     * @param sourceBean             the source bean
     * @param targetClass            the target class
     * @param value                  the copying value of the field
     * @param currentInCopyFiledType the type of the value. 当前正在执行 copy 的属性类型
     * @param filedName              the filed name that current in copying
     * @param deepCopy               是否深拷贝
     * @return the copied value
     */
    @SuppressWarnings({"rawtypes"})
    protected Object customiseValue(
            S sourceBean, Class<T> targetClass, Object value, Class currentInCopyFiledType, String filedName, boolean deepCopy) {
        return value;
    }

    /**
     * @param value                  正在处理的字段值
     * @param currentInCopyFiledType 正在处理的目标字段类型
     * @param setterMethodName       setter 方法名称, String 类型
     */
    @Override
    public Object convert(Object value, Class currentInCopyFiledType, Object setterMethodName) {
        // System.out.println("setterMethodName.getClass() = " + setterMethodName.getClass()); // String
        String filedName = resolveFiledNameFromSetter(setterMethodName);

        return ignoreProperties.contains(filedName) ?
                null : resolveValue(sourceBean, targetClass, value, currentInCopyFiledType, /*setterMethodName, */filedName, deepCopy);
    }

}
