
import com.df.cbhis.utility.annotation.As;
import com.df.cbhis.utility.comm.CollectorsUtil;
import com.df.cbhis.utility.dto.DTO_PageData;
import com.df.cbhis.utility.exceptions.BusinessException;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.*;
import com.querydsl.jpa.impl.JPAQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.querydsl.core.types.Ops.ALIAS;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/10/25 13:58
 */
public class QuerydslDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuerydslDelegate.class);

    public static <V> Optional<V> doFetchOne(JPAQuery<Tuple> query, Class<V> resultType) {
       /* // 获取查询字段
        List<QuerydslMetaData> pmList = resolvePathMetadata(query);
        // 结果实体所有字段
        Map<String, Field> allFields = resolveAllFields(resultType);
        // 获取实体标注有 @As 的字段:
        Map<String, Field> asFields = resolveAsFields(resultType);
        // root name --> field name
        Map<String, List<String>> rootNameFieldNames = resolveRootNameFieldNames(asFields);*/

        return Optional.ofNullable(query.fetchOne())
                .map(t -> doConvert(t, resultType, new QueryDsl(query, resultType)));
    }

    public static <V> Optional<V> doFetchFirst(JPAQuery<Tuple> query, Class<V> resultType) {

        return Optional.ofNullable(query.fetchFirst())
                .map(t -> doConvert(t, resultType, new QueryDsl(query, resultType)));
    }

    /**
     * @see com.df.jpa.dbbase.repository.BaseRepository#queryPageData
     */
    public static <V> DTO_PageData<V> doQueryPage(JPAQuery<Tuple> query, Class<V> resultType,
                                                  @Nullable Integer currentPage, @Nullable Integer pageSize) {

        currentPage = currentPage == null ? 1 : currentPage;
        pageSize = pageSize == null ? 99 : pageSize;
        List<V> data = doQuery(query.offset((long) pageSize * (currentPage - 1)).limit(pageSize), resultType);

        long totalRows = query.fetchCount();

        DTO_PageData<V> pageData = new DTO_PageData<>();
        pageData.setList(data);
        pageData.setTotalRows(totalRows);
        pageData.setPageSize(pageSize);
        pageData.setPageIndex(currentPage);
        pageData.setPageCount(CollectorsUtil.getTotalPage((int) totalRows,  pageSize));

        return pageData;
    }

    public static <V> List<V> doQuery(JPAQuery<Tuple> query, Class<V> resultType) {

        return query.fetch()
                .stream()
                .map(tuple -> doConvert(tuple, resultType, new QueryDsl(query, resultType)))
                .collect(Collectors.toList());
    }

    //----------------------------------------------------
    // Private methods
    //----------------------------------------------------

    private static <V> V doConvert(Tuple tuple,
                                   // List<QuerydslMetaData> pmList,
                                   // Map<String, Field> allFields,
                                   // Map<String, Field> asFields,
                                   // Map<String, List<String>> rootNameFieldNames,
                                   Class<V> resultType,
                                   QueryDsl queryDsl) {
        Object[] arr = tuple.toArray(); // 每一个 tuple array 对应一个结果集的实例
        V resultInstance;
        try {
            resultInstance = resultType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BusinessException(e, String.format("SQL查询结果封装失败: 类[%s]实例化失败", resultType.getName()));
        }

        // 这里的反转排序, 是为了解决多个表有相同的字段时, 后面的表字段值覆盖前面表字段值的情形
        // 这里按习惯, 以排在前面的表为主表
        // 这里的前后关系是指 com.querydsl.jpa.impl.JPAQueryFactory.select(com.querydsl.core.types.Expression<?>...)
        // 中 Expression 的顺序
        // 参见 #resolvePathMetadata
        List<Object> tupleLst = CollectorsUtil.arrayToList(arr);
        Collections.reverse(tupleLst);

        for (int i = 0; i < tupleLst.size(); i++) {
            Object value = tupleLst.get(i);
            // 忽略空值
            if (value == null) {
                continue;
            }

            QuerydslMetaData querydslMetaData = queryDsl.pmList().get(i);
            // 字段名(别名)
            PathMetadata pm = querydslMetaData.getPathMetadata();
            // fixme: 由于目前暂未支持所有 Querydsl 相关类型, 这里可能为空, 空暂时不处理
            // 请参考 com.df.jpa.dbbase.support.QuerydslDelegate.QueryDsl.resolveMetaData
            // 中的日志, 增加相关类型的支持
            if (pm == null) {
                continue;
            }
            String fieldName = pm.getElement().toString();
            // String root = querydslMetaData.getRoot();
            // 简单类型
            if (BeanUtils.isSimpleValueType(value.getClass())) {
                setSimpleValue(resultType, resultInstance, value, /*pm, */fieldName, queryDsl.asFields());
            } else { // 对象类型
                setPojoValue(resultType, resultInstance, value,/* pm,*/ fieldName, queryDsl.rootNameFieldNames(), queryDsl.asFields());
            }
        }
        // 以下逻辑的目的: 结果对象中的一个非查询字段在标注了 @As 注解时,
        // 可以被附上关联字段的值, 可以实现同一个实体的不同字段使用同一个值
        // 标注了 @As 注解,但是没有在查询字段中声明
        setAliasNonQueryField(resultType, resultInstance, queryDsl.allFields(), queryDsl.asFields());

        return resultInstance;
    }

    private static <V> void setAliasNonQueryField(Class<V> resultType,
                                                  V resultInstance,
                                                  Map<String, Field> allFields,
                                                  Map<String, Field> asFields) {
        asFields.forEach((alias, f) -> {
            Field field = allFields.get(alias);
            if (field != null) {
                setAccessibleIfNecessary(f, field);
                try {
                    f.set(resultInstance, field.get(resultInstance));
                } catch (IllegalAccessException e) {
                    throw new BusinessException(e, String.format(
                            "SQL查询结果封装失败: 属性[%s#%s]反射赋值异常", resultType.getSimpleName(), f.getName()));
                }
            }
        });
    }

    private static void setAccessibleIfNecessary(AccessibleObject... accessibleObjects) {
        for (AccessibleObject accessibleObject : accessibleObjects) {
            if (!accessibleObject.isAccessible()) {
                accessibleObject.setAccessible(true);
            }
        }
    }

    /**
     * 拷贝复杂属性, value 的类型是对象类型
     * @param <V> 整体查询结果类型
     * @param resultType 结果类型
     * @param resultInstance 结果实例
     * @param value 查询的字段值
     * .@param pm querydsl 查询字段的配置: 包含别名信息 此参数可以去掉, 这里暂时做校验用
     * @param fieldName 别名
     * @param asFields {@code resultType} 中标注 {@link As} 注解属性
     */
    private static <V> void setPojoValue(Class<V> resultType, V resultInstance,
                                         Object value, /*PathMetadata pm,*/
                                         String fieldName,
                                         Map<String, List<String>> rootNameFieldNames,
                                         Map<String, Field> asFields) {
        // Assert.isTrue(pm.getPathType() == PathType.VARIABLE,
        //         String.format("SQL查询结果封装失败,实体属性[%s#%s]类型校验失败",
        //                 resultType.getName(), fieldName));

        // 首先无条件尝试对象之间的属性拷贝
        List<String> ignoreFields = new ArrayList<>();
        // 对象实例的 filedName 和 @As#root 属性不一致的
        // 说明这些字段指定了 root 属性, 防止被其他的结果实例覆盖
        if (null == rootNameFieldNames.get(fieldName)) {
            ignoreFields.addAll(CollectorsUtil.flatMapping(rootNameFieldNames.values()/*, Function.identity()*/)
                    /*rootNameFieldNames.values().stream()
                    .flatMap(Collection::stream).collect(Collectors.toList())*/);
        }
        BeanUtils.copyProperties(value, resultInstance, ignoreFields.toArray(new String[0]));
        // 尝试赋值结果实体中的对象属性
        Field asField;
        if (null != (asField = asFields.get(fieldName))) {
            Object asFieldInstance;
            Class<?> asFieldType = asField.getType();
            // 在这种情况下, @As 标注的字段必须为非简单类型对象(就是普通 POJO),
            // 才能保证下面的属性拷贝有效
            Assert.isTrue(!BeanUtils.isSimpleValueType(asFieldType),
                    String.format("SQL查询结果封装失败(结果映射失败): [%s#%s]类型为[%s],其别名映射类型为[%s]," +
                                    "请确认`%s`注解是否正确配置",
                            resultType.getName(), asField.getName(),
                            asField.getType().getName(), value.getClass().getName(), As.class.getName()));
            // 对象类型, 拷贝属性
            try {
                asFieldInstance = asFieldType.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new BusinessException(e, String.format("SQL查询结果封装失败: 类[%s]实例化失败", asFieldType.getName()));
            }
            // 把结果属性拷贝至该对象字段
            BeanUtils.copyProperties(value, asFieldInstance);
            // 将 asFieldInstance 赋值到 resultInstance
            setFieldValue(resultType, resultInstance, asField, asFieldInstance);
        }
    }

    private static <V> void setFieldValue(Class<V> resultType, V result,
                                          Field asField, Object asFieldInstance) {
        try {
            setAccessibleIfNecessary(asField);
            asField.set(result, asFieldInstance);
        } catch (IllegalAccessException e) {
            throw new BusinessException(e, String.format(
                    "SQL查询结果封装失败: 属性[%s#%s]反射赋值异常", resultType.getSimpleName(), asField.getName()));
        }
    }

    // as-value --> field
    private static <V> Map<String, Field> resolveAsFields(Class<V> resultType) {
        return CollectorsUtil.filterThenToMap(
                f -> f.isAnnotationPresent(As.class), // filter
                f -> {
                    As as = f.getAnnotation(As.class); // key
                    String value = as.value();
                    String root = as.root();
                    return StringUtils.hasText(root) ?
                            root + "." + value : value;
                },
                Function.identity(), // value: Field
                (field1, field2) -> {
                    throw new BusinessException(
                            String.format("SQL查询结果封装失败: 实体[%s]属性[%s,%s]别名重复",
                                    resultType.getName(), field1.getName(), field2.getName()));
                },
                resultType.getDeclaredFields());

                // Arrays.stream(resultType.getDeclaredFields())
                // .filter(f -> f.isAnnotationPresent(As.class))
                // .collect(Collectors.toMap(
                //         f -> f.getAnnotation(As.class).value(),
                //         Function.identity()));
    }

    /**
     * 简单类型拷贝
     * @see BeanUtils#isSimpleValueType(java.lang.Class)
     * @param resultType 结果类型
     * @param resultInstance 结果实例
     * @param value 查询字段结果--tuple array 中的元素
     * @param fieldName querydsl 别名
     * @param asFields 实体中标注有 @As 注解的属性集
     */
    private static <V> void setSimpleValue(Class<V> resultType, V resultInstance, Object value,/* PathMetadata pm, */
                                           String fieldName, Map<String, Field> asFields) {
        // path.as("别名") 这种生成的是 PathType.VARIABLE
        // Assert.isTrue(pm.getPathType() == PathType.PROPERTY,
        //         String.format("SQL查询结果封装失败: 实体属性[%s#%s]类型校验失败",
        //                 resultType.getName(), fieldName));
        try {
            Field f = resultType.getDeclaredField(fieldName);
            // 简单类型赋值, @As 标注的字段必须为简单类型
            // 才能保证下面的属性拷贝有效
            Assert.isTrue(BeanUtils.isSimpleValueType(f.getType()),
                    String.format("SQL查询结果封装失败(结果映射失败): [%s#%s]类型为[%s],其别名映射类型为[%s]," +
                                    "请确认@com.df.cbhis.utility.annotation.As注解是否正确配置",
                            resultType.getName(), f.getName(), f.getType().getName(), value.getClass().getName()));

            setAccessibleIfNecessary(f);
            f.set(resultInstance, value);
        } catch (NoSuchFieldException e) {
            // 没有找到 fieldName 对应的字段
            // 尝试解析注解别名
            // alias -> field
            setSimpleAsFiledValue(resultType, resultInstance, value, fieldName, asFields);

        } catch (IllegalAccessException e) {
            throw new BusinessException(e, String.format(
                    "SQL查询结果封装失败: 属性[%s#%s]反射赋值异常", resultType.getSimpleName(), fieldName));
        }
    }

    // 处理标注有 @As 的字段: 简单属性
    private static <V> void setSimpleAsFiledValue(Class<V> resultType, V result, Object value,
                                                  String fieldName, Map<String, Field> asFields) {
        Field asField;
        if (null != (asField = asFields.get(fieldName))) {
            // 目标字段必须为简单类型
            Assert.isTrue(BeanUtils.isSimpleValueType(asField.getType()),
                    String.format("SQL查询结果封装失败(结果映射失败): [%s#%s]类型为[%s],其别名映射类型为[%s]," +
                                    "请确认@com.df.cbhis.utility.annotation.As注解是否正确配置",
                            resultType.getName(), asField.getName(), asField.getType().getName(), value.getClass().getName()));
            setAccessibleIfNecessary(asField);
            try {
                asField.set(result, value);
            } catch (IllegalAccessException ex) {
                throw new BusinessException(ex, String.format(
                        "SQL查询结果封装失败: 属性[%s#%s]反射赋值异常",
                        resultType.getName(), asField.getName()));
            }
        }
        // else
        // 如果没有找到 field name 对应的字段
        // 说明 SQL 结果集的该字段在实体中没有定义
        // 直接忽略, 不抛异常
    }

    static class QueryDsl {

        // 查询字段
        private final List<QuerydslMetaData> pmList;
        // 结果实例的所有字段: field name --> field
        private final Map<String, Field> allFields;
        // 结果实例的 @As 字段
        private final Map<String, Field> asFields;
        // root name --> field name
        // 标注 @As 注解, 且设置了 @As#root 属性的字段
        private final Map<String, List<String>> rootNameFieldNames;

        QueryDsl(JPAQuery<Tuple> query, Class<?> resultType) {
            // 获取查询字段
            pmList = resolvePathMetadata(query);
            // 结果实体所有字段
            allFields = resolveAllFields(resultType);
            // 获取实体标注有 @As 的字段:
            asFields = resolveAsFields(resultType);
            // root name --> field name
            rootNameFieldNames = resolveRootNameFieldNames(asFields);
        }

        public List<QuerydslMetaData> pmList() {
            return pmList;
        }

        public Map<String, Field> allFields() {
            return allFields;
        }

        public Map<String, Field> asFields() {
            return asFields;
        }

        public Map<String, List<String>> rootNameFieldNames() {
            return rootNameFieldNames;
        }

        // root name --> field name
        private static Map<String, List<String>> resolveRootNameFieldNames(Map<String, Field> asFields) {
            return CollectorsUtil.filterThenToMap(
                    asFields.keySet(), // data
                    n -> n.contains("."), // filter
                    n -> n.split("\\.")[0], // key: root
                    n -> { // value: field name
                        List<String> flds = new ArrayList<>();
                        flds.add(n.split("\\.")[1]);
                        return flds;
                    },
                    (f1, f2) -> {
                        f1.addAll(f2);
                        return f1;
                    });
        }

        // filed name --> field
        private static <V> Map<String, Field> resolveAllFields(Class<V> resultType) {
            return CollectorsUtil.toMap(
                    Field::getName, // key field name
                    Function.identity(), // value: Field
                    resultType.getDeclaredFields());
        }

        /**
         * @see com.df.cbhis.utility.annotation.As#root()
         * 注: 这里获取 root 属性是为了解决不同表同名字段值覆盖的问题
         */
        private static List<QuerydslMetaData> resolvePathMetadata(JPAQuery<Tuple> query) {
            Expression<?> proj = query.getMetadata().getProjection();
            Assert.notNull(proj, "SQL查询结果封装失败: Querydsl-projection为空,请联系服务端技术人员处理");

            List<QuerydslMetaData> pmList = ((FactoryExpression<?>) proj).getArgs()
                    .stream()
                    .map(x -> resolveMetaData(x, query))
                    .collect(Collectors.toList());

            // 这里的反转排序, 是为了解决多个表有相同的字段时, 后面的表字段值覆盖前面表字段值的情形
            // 这里按习惯, 以排在前面的表为主表
            // 这里的前后关系是指 com.querydsl.jpa.impl.JPAQueryFactory.select(com.querydsl.core.types.Expression<?>...)
            // 中 Expression 的顺序
            Collections.reverse(pmList);

            return pmList;
        }

        // todo: 这里的 MetaData 并没覆盖到 QueryDsl 的所有类型
        // 目前只有 Path (表), Operation (字段别名)
        // 其他有未涉及到的类型, 后续根据需要调整
        private static QuerydslMetaData resolveMetaData(Expression<?> a, JPAQuery<Tuple> query) {
            if (a instanceof Operation/* && ((Operation<?>) a).getOperator() == ALIAS*/) { // as 别名
                Operation<?> op = (Operation<?>) a;
                // Operator operator = op.getOperator(); // == com.querydsl.core.types.Ops.ALIAS
                // 正常来说, args 为长度为 2 的集合, 一个是 root, 一个是别名字段
                List<Expression<?>> args = op.getArgs();
                Map<Boolean, Expression<?>> rootPartition = args.stream()
                        .collect(Collectors.toMap(x -> x.toString().contains("."), Function.identity()));

                if (op.getOperator() == ALIAS) { // as 别名
                    return asMetaData(rootPartition, query);
                } else { // 没有加 as 别名: eg. xx.sum("xx.price")
                    // rootPartition.size() == 1
                    Expression<?> expr = rootPartition.get(true);
                    if (expr != null) {
                        if (expr instanceof Path) {
                            Path<?> p = (Path<?>) expr;
                            return new QuerydslMetaData(p.getMetadata(), Objects.toString(p.getRoot()));
                        } else {
                            // fixme
                            LOGGER.info("[QuerydslDelegate]暂未支持的Querydsl类型(Operation-not-alias-not-path): [{}({})], [{}]",
                                    expr, expr.getClass().getName(), query);
                            return new QuerydslMetaData(null, null);
                        }
                    } else {
                        // fixme
                        LOGGER.info("[QuerydslDelegate]暂未支持的Querydsl类型(rootPartition-null): [{}({})], [{}]",
                                op, rootPartition, query);
                        return new QuerydslMetaData(null, null);
                    }
                }
            }
            // 1. Qxxx 文件生成的  Path 字段
            // 2. EntityPathBase: Qxx 文件的实例
            else if (a instanceof Path) {
                Path<?> path = (Path<?>) a;
                return new QuerydslMetaData(path.getMetadata(), path.getRoot().toString());
                // return ((Path<?>) a).getMetadata();
            } else if (a instanceof TemplateExpression) {
                TemplateExpression<?> te = (TemplateExpression<?>) a;

            }

            throw new BusinessException(String.format("SQL查询结果封装失败: 不支持的查询字段类型[%s]", a.getClass().getName()));
        }

        private static QuerydslMetaData asMetaData(Map<Boolean, Expression<?>> rootPartition, JPAQuery<Tuple> query) {
            String root;
            Expression<?> expr = rootPartition.get(true);
            // assert not null
            if (expr instanceof Path) {
                Path<?> path = (Path<?>) expr;
                root = Objects.toString(path.getRoot());
            } else if (expr instanceof Operation) {
                // example: xx.sum("price").as("totalPrice")
                Operation<?> operation = (Operation<?>) expr;
                List<Expression<?>> opArgs = operation.getArgs();
                // assert size == 1
                Expression<?> argExp = opArgs.get(0);
                if (argExp instanceof Path) {
                    Path<?> argPath = (Path<?>) argExp;
                    root = Objects.toString(argPath.getRoot());
                } else {
                    // fixme
                    root = null;
                    LOGGER.info("[QuerydslDelegate]暂未支持的Querydsl类型(Root-Operation): `{}`, Query: [{}]",
                            argExp.getClass().getName(), query);
                    // throw new BusinessException(String.format("暂未支持的Querydsl类型: `%s`", argExp.getClass().getName()));
                }
            } else {
                // fixme
                root = null;
                LOGGER.info("[QuerydslDelegate]暂未支持的Querydsl类型(Root): {}, Query: [{}]",
                        expr.getClass().getName(), query);
            }

            // 带 . 号的是父属性
            Expression<?> metaExpre = rootPartition.get(false);
            PathMetadata pathMetadata;
            // assert not null
            if (metaExpre instanceof Path) {
                pathMetadata = ((Path<?>) metaExpre).getMetadata();
            } else {
                pathMetadata = null;
                LOGGER.info("[QuerydslDelegate]暂未支持的Querydsl类型(PathMetadata): {}, Query: [{}]",
                        metaExpre.getClass().getName(), query);
            }
            return new QuerydslMetaData(pathMetadata, root);
        }

    }

    static class QuerydslMetaData {

        @Nullable
        private final PathMetadata pathMetadata;

        @Nullable
        private final String root;

        public QuerydslMetaData(PathMetadata pathMetadata, String root) {
            this.pathMetadata = pathMetadata;
            this.root = root;
        }

        public PathMetadata getPathMetadata() {
            return pathMetadata;
        }

        public String getRoot() {
            return root;
        }

    }
}
