package util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.*;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.*;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/8/13 10:53
 */
public class FieldMapping {

    // 字段映射索引
    @Repeatable(Index.List.class)
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD})
    public @interface Index {

        // 索引
        int value();

        // 关联类型, 为了方便同一字段映射多次映射到不同类的情形
        Class<?> targetClass();

        @Retention(RetentionPolicy.RUNTIME)
        @Target({ElementType.FIELD})
        @interface List {
            Index[] value();
        }

    }

    @Deprecated
    static class IndexEntity {

        private final int value;

        private final Class<?> targetClass;

        public IndexEntity(int value, Class<?> targetClass) {
            this.value = value;
            this.targetClass = targetClass;
        }


        public int getValue() {
            return value;
        }

        public Class<?> getTargetClass() {
            return targetClass;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            IndexEntity that = (IndexEntity) o;
            return value == that.value && Objects.equals(targetClass, that.targetClass);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, targetClass);
        }
    }

    /**
     *
     * @param sourceBean 数据源实例
     * @param targetBean 目标实例, 有可能存在已经赋值的属性
     */
    public static void mapping(final Object sourceBean, final Object targetBean) {
        Class<?> sourceClass = sourceBean.getClass();
        Class<?> targetClass = targetBean.getClass();
        Map<Integer, Field> sourceIndices = resolveFieldIndex(sourceClass, targetClass);
        Map<Integer, Field> targetIndices = resolveFieldIndex(targetClass, sourceClass);

        if (CollectionUtils.isEmpty(sourceIndices) || CollectionUtils.isEmpty(targetIndices)) {
            return;
        }

        targetIndices.forEach((i, targetField) -> {
            try {
                Field sourceField = sourceIndices.get(i);
                if (sourceField != null) {
                    setAccessible(targetField);
                    setAccessible(sourceField);
                    // target field type isAssignableFrom source field type
                    if (!ClassUtils.isAssignable(targetField.getType(), sourceField.getType())) {
                        throw new RuntimeException(errorMsg(sourceField, targetField));
                    }
                    targetField.set(targetBean, sourceField.get(sourceBean));
                }
            } catch (Exception e) {
                throw new RuntimeException(String.format("[%s ==> %s %s]反射赋值异常: %s",
                        sourceClass.getName(), targetClass.getName(),
                        targetField.getName(), e.getMessage()));
            }
        });

        // return result;
    }

    /**
     *
     * @param sourceBean 数据源实例
     * @param targetClass 目标类 Class
     * @param <S> 数据源类型
     * @param <T> 目标类型
     * @return 目标对象
     */
    public static <S, T> T mapping(final S sourceBean, Class<T> targetClass) {
        if (sourceBean == null || targetClass == null) {
            return null;
        }
        T result = BeanUtils.instantiateClass(targetClass);

        mapping(sourceBean, result);

        return result;
    }

    private static String errorMsg(Field sourceField, Field targetField) {
        return String.format("不兼容的类型映射: 数据源[%s]字段[%s %s]无法转换为目标[%s]字段[%s %s]. 数据源字段类型必须与目标字段同类型或着为其子类型",
                sourceField.getDeclaringClass(), sourceField.getType(), sourceField.getName(),
                targetField.getDeclaringClass(), targetField.getType(), targetField.getName());
    }

    @SuppressWarnings("rawtypes")
    private static Map<Integer, Field> resolveFieldIndex(Class src, Class exceptTargetClass) {
        HashMap<Integer, Field> result = new HashMap<>();
        for (Field sourceField : src.getDeclaredFields()) {
            Index[] indexAnnos = sourceField.getDeclaredAnnotationsByType(Index.class);
            putIfMatch(result, exceptTargetClass, sourceField, indexAnnos);
            // AccessibleObject#getDeclaredAnnotationsByType(java.lang.Class) 可以自行获取 Repeatable 注解, 无需重复处理
            // Index.List listAnno = sourceField.getDeclaredAnnotation(Index.List.class);
            // putIfMatch(result, exceptTargetClass, sourceField, listAnno);
        }

        return result;
    }

    /**
     * @see AccessibleObject#getDeclaredAnnotationsByType(java.lang.Class) 可以自行获取 Repeatable 注解, 无需重复处理
     * @param result key: {@link Index#value()}, value:
     * @param exceptTargetClass {@link Index#targetClass()}
     * @param sourceField the source field
     * @param list {@link Index.List}
     */
    @Deprecated
    @SuppressWarnings("rawtypes")
    private static void putIfMatch(HashMap<Integer, Field> result, Class exceptTargetClass, Field sourceField, Index.List list) {
        if (list == null) {
            return;
        }
        putIfMatch(result, exceptTargetClass, sourceField, list.value());
    }

    /**
     * @param result key: {@link Index#value()}, value:
     * @param exceptTargetClass {@link Index#targetClass()}
     * @param sourceField the source field
     * @param indexAnnos {@link Index}
     */
    @SuppressWarnings("rawtypes")
    private static void putIfMatch(HashMap<Integer, Field> result, Class exceptTargetClass, Field sourceField, Index[] indexAnnos) {
        if (indexAnnos == null) {
            return;
        }
        for (Index indexAnno : indexAnnos) {
            if (indexAnno.targetClass() == exceptTargetClass) {
                Field field = result.get(indexAnno.value());
                if (field != null) {
                    throw new RuntimeException(String.format("[%s]存在相同 Index 映射关系: [%s], [%s]. Index(value = %s, targetClass = %s)",
                            field.getDeclaringClass().getName(), field.getName(), sourceField.getName(),
                            indexAnno.value(), indexAnno.targetClass().getName()));
                }
                result.put(indexAnno.value(), sourceField);
            }
        }
    }

    @Deprecated
    private static IndexEntity newIndexEntity(Index indexAnno) {
        return new IndexEntity(indexAnno.value(), indexAnno.targetClass());
    }

    // key: index, value: the field value
    // private static HashMap<Integer, Object> resolveFieldIndexAndValue(Object obj) {
    //     return Stream.of(obj.getClass().getDeclaredFields())
    //             .collect(HashMap::new, (m, f) -> {
    //                 Index indexAnno = f.getDeclaredAnnotation(Index.class);
    //                 if (indexAnno != null) {
    //                     try {
    //                         setAccessible(f);
    //                         m.put(indexAnno.value(), f.get(obj));
    //                     } catch (IllegalAccessException e) {
    //                         throw new RuntimeException("反射字段异常: " + e.getMessage());
    //                     }
    //                 }
    //             }, HashMap::putAll);
    // }

    private static void setAccessible(Field f) {
        if (!f.isAccessible()) {
            f.setAccessible(true);
        }
    }

    public static void main(String[] args) {
        Source source = Source.builder().a("A").b('B').c("C").cs(new ArrayList<>()).build();
        Target1 target1 = mapping(source, Target1.class);
        System.out.println("target = " + target1);
        Target2 target2 = mapping(source, Target2.class);
        System.out.println("target2 = " + target2);
    }

}

@Builder
@AllArgsConstructor
@NoArgsConstructor
class Source {
    @FieldMapping.Index.List({
            @FieldMapping.Index(value = 0, targetClass = Target1.class),
            @FieldMapping.Index(value = 4, targetClass = Target2.class)
    })
    private String a;
    @FieldMapping.Index(value = 1, targetClass = Target1.class)
    @FieldMapping.Index(value = 3, targetClass = Target2.class)
    private int b;
    @FieldMapping.Index(value = 2, targetClass = Target1.class)
    @FieldMapping.Index(value = 2, targetClass = Target2.class)
    private Object c;
    @FieldMapping.Index(value = 3, targetClass = Target1.class)
    @FieldMapping.Index(value = 1, targetClass = Target2.class)
    private List<String> cs;

    // 重复映射会报错
    // @PropertyMapping.Index(value = 3, targetClass = Target1.class)
    // @PropertyMapping.Index(value = 1, targetClass = Target2.class)
    private Collection<String> cs1;

    public static void main(String[] args) throws NoSuchFieldException {
        Class<Source> sourceClass = Source.class;

        FieldMapping.Index b = sourceClass.getDeclaredField("c").getDeclaredAnnotation(FieldMapping.Index.class);
        System.out.println("b = " + b);

        FieldMapping.Index[] bs = sourceClass.getDeclaredField("b")
                .getDeclaredAnnotationsByType(FieldMapping.Index.class);
        System.out.println("bs = " + Arrays.asList(bs));

        FieldMapping.Index.List list = sourceClass.getDeclaredField("a")
                .getDeclaredAnnotation(FieldMapping.Index.List.class);
        System.out.println("Arrays.asList(list.value()) = " + Arrays.asList(list.value()));
    }
}

@ToString
class Target1 {
    @FieldMapping.Index(value = 0, targetClass = Source.class)
    private String x;
    @FieldMapping.Index(value = 1, targetClass = Source.class)
    private int y;
    @FieldMapping.Index(value = 2, targetClass = Source.class)
    private Object z;
    @FieldMapping.Index(value = 3, targetClass = Source.class)
    private Collection<String> ls;
}

@ToString
class Target2 {
    @FieldMapping.Index(value = 4, targetClass = Source.class)
    private String x;
    @FieldMapping.Index(value = 3, targetClass = Source.class)
    private int y;
    @FieldMapping.Index(value = 2, targetClass = Source.class)
    private Object z;
    @FieldMapping.Index(value = 1, targetClass = Source.class)
    private List<String> ls;
}