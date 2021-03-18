package org.anonymous.test.core;

import org.apache.ibatis.executor.BatchExecutor;
import org.apache.ibatis.executor.CachingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ReuseExecutor;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.apache.ibatis.executor.Executor
 * @see org.apache.ibatis.executor.BaseExecutor 一级缓存:  protected PerpetualCache localCache;
 * ---
 * @see org.apache.ibatis.executor.SimpleExecutor
 * @see org.apache.ibatis.executor.ReuseExecutor
 * Simple 与 Reuse 的区别:
 * 重用预编译: org.apache.ibatis.executor.ReuseExecutor#statementMap
 * org.apache.ibatis.executor.ReuseExecutor#prepareStatement(org.apache.ibatis.executor.statement.StatementHandler, org.apache.ibatis.logging.Log)
 * @see org.apache.ibatis.executor.BatchExecutor
 * --
 * @see org.apache.ibatis.executor.CachingExecutor 二级缓存
 * @see org.apache.ibatis.executor.BaseExecutor#query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler)
 * @see org.apache.ibatis.executor.BaseExecutor#doQuery(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler, org.apache.ibatis.mapping.BoundSql)
 * query 缓存的是结果本身, doQuery 缓存的是连接 {@link org.apache.ibatis.executor.ReuseExecutor#prepareStatement(org.apache.ibatis.executor.statement.StatementHandler, org.apache.ibatis.logging.Log)}
 * 或者说是 statement 对象(减少预编译的过程, 节省资源)
 * ---
 * @see org.anonymous.dao.annotation.UserDao#findById(int)
 * @since 2020/12/12 19:43
 * <p>
 * query 方法与 doQuery 方法的区别
 */
public class ExecutorTest {

    private Configuration config;
    private Transaction tx;

    /**
     * @see org.apache.ibatis.builder.xml.XMLConfigBuilder#environmentsElement(org.apache.ibatis.parsing.XNode)
     */
    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
        SqlSessionFactory ssf = ssfb.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
        config = ssf.getConfiguration();
        // SqlSession sqlSession = ssf.openSession();
        // 非连接池方式
        /*Connection conn = DriverManager.getConnection(url, usernme, pwd);
        tx = new JdbcTransaction(conn);*/

        // mybatis 自带连接池
        // org.apache.ibatis.builder.xml.XMLConfigBuilder.environmentsElement
        // org.apache.ibatis.builder.xml.XMLConfigBuilder.transactionManagerElement
        tx = config.getEnvironment().getTransactionFactory()
                .newTransaction(config.getEnvironment().getDataSource(),
                        TransactionIsolationLevel.READ_COMMITTED,
                        false); // autoCommit 默认 false
    }

    @Test
    public void config() {

        Map<String, ParameterMap> parameterMaps = new HashMap<>();

        final Collection<String> parameterMapNames = config.getParameterMapNames();
        final Collection<ParameterMap> maps = config.getParameterMaps();

        System.out.println("ParameterMaps....");
        parameterMapNames.forEach(id -> {
            maps.forEach(m -> {
                if (m.getId().equals(id)) {
                    parameterMaps.put(id, m);
                }
            });
        });
        parameterMaps.forEach((k, v) -> System.out.println(k + ": " + v.getParameterMappings()));

        System.out.println("ResultMap...");


        Map<String, ResultMap> resultMapMap = new HashMap<>();
        final Collection<String> resultMapNames = config.getResultMapNames();
        final Collection<ResultMap> resultMaps = config.getResultMaps();
        resultMapNames.forEach(k -> resultMaps.stream().filter(v -> v.getId().equals(k)).findAny().ifPresent(v -> resultMapMap.put(k, v)));

        resultMapMap.forEach((k, v) -> {
            System.out.println("k = " + k);
            System.out.println("v.getIdResultMappings() = " + v.getIdResultMappings());
            System.out.println("v.getConstructorResultMappings() = " + v.getConstructorResultMappings());
            System.out.println("v.getResultMappings() = " + v.getResultMappings());
            System.out.println("v.getPropertyResultMappings() = " + v.getPropertyResultMappings());
            System.out.println();
        });

        System.out.println("TypeHandler...");
        final TypeHandlerRegistry typeHandlerRegistry = config.getTypeHandlerRegistry();
        typeHandlerRegistry.getTypeHandlers().forEach(System.out::println);

        System.out.println("TypeAlias...");
        final TypeAliasRegistry typeAliasRegistry = config.getTypeAliasRegistry();
        typeAliasRegistry.getTypeAliases().forEach((k, v) -> System.out.println(k + ": " + v));

    }

    /**
     * @see #reuseExecutor()
     */
    @Test
    public void simpleExecutor() throws SQLException {
        SimpleExecutor se = new SimpleExecutor(config, tx);
        // ExecutorType executorType = ExecutorType.SIMPLE;
        // BaseExecutor se = config.newExecutor(tx, executorType);

        MappedStatement ms = config.getMappedStatement("org.anonymous.dao.annotation.UserDao.findById");
        Object param = 1;
        RowBounds rb = RowBounds.DEFAULT;
        @SuppressWarnings("unchecked")
        ResultHandler<Object> rh = SimpleExecutor.NO_RESULT_HANDLER;
        BoundSql bs = ms.getBoundSql(10);
        System.out.println("bs.getParameterMappings() = " + bs.getParameterMappings());
        System.out.println("bs.getParameterObject() = " + bs.getParameterObject());

        List<Object> list = se.doQuery(ms, param, rb, rh, bs);
        System.out.println("list = " + list);
        List<Object> list1 = se.doQuery(ms, param, rb, rh, bs);
        System.out.println(list.get(0) == list1.get(0));
    }

    /**
     * @see #simpleExecutor()
     * 相同的 sql, 单次预编译
     *
     * @see org.apache.ibatis.executor.ReuseExecutor#prepareStatement(org.apache.ibatis.executor.statement.StatementHandler, org.apache.ibatis.logging.Log)
     * 缓存的是 Statement 而不是查询结果
     */
    @Test
    public void reuseExecutor() throws SQLException {
        ReuseExecutor re = new ReuseExecutor(config, tx);

        MappedStatement ms = config.getMappedStatement("org.anonymous.dao.annotation.UserDao.findById");
        Object param = 1;
        RowBounds rb = RowBounds.DEFAULT;
        @SuppressWarnings("unchecked")
        ResultHandler<Object> rh = SimpleExecutor.NO_RESULT_HANDLER;
        BoundSql bs = ms.getBoundSql(10);
        System.out.println("bs.getParameterMappings() = " + bs.getParameterMappings());
        System.out.println("bs.getParameterObject() = " + bs.getParameterObject());

        // sql 重用. 但查询结果非同一个对象. 并没有缓存
        List<Object> list = re.doQuery(ms, param, rb, rh, bs);
        List<Object> list1 = re.doQuery(ms, param, rb, rh, bs);
        System.out.println(list.get(0) == list1.get(0));
    }

    /**
     * 1. 批处理操作只针对修改重用 sql, 查询等同于 simple executor
     * 2. 必须手动刷新: flush/或者设置自动提交
     */
    @Test
    public void batchExecutor() throws SQLException {
        BatchExecutor be = new BatchExecutor(config, tx);

        MappedStatement msQuery = config.getMappedStatement("org.anonymous.dao.annotation.UserDao.findById");
        Object param = 1;
        RowBounds rb = RowBounds.DEFAULT;
        @SuppressWarnings("unchecked")
        ResultHandler<Object> rh = Executor.NO_RESULT_HANDLER;
        BoundSql bs = msQuery.getBoundSql(10);
        System.out.println("bs.getParameterMappings() = " + bs.getParameterMappings());
        System.out.println("bs.getParameterObject() = " + bs.getParameterObject());

        List<Object> list = be.doQuery(msQuery, param, rb, rh, bs);
        List<Object> list1 = be.doQuery(msQuery, param, rb, rh, bs);
        System.out.println(list.get(0) == list1.get(0));

        // 更新
        MappedStatement msUpdate = config.getMappedStatement("org.anonymous.dao.annotation.UserDao.updateUser");
        HashMap<String, Object> params = new HashMap<>();
        params.put("username", "张三");
        params.put("id", 1);

        int i = be.doUpdate(msUpdate, params);
        // 要么设置 autoCommit = true, 要么手动 flush(参数 false 表示不回滚), 二者选其一, 可以实现更新成功
        // 否则会回滚
        // be.flushStatements(false);
        // be.doFlushStatements(false);
        int i1 = be.doUpdate(msUpdate, params);

        // 如果不刷新, (当前连接)读到的虽然是修改后的值, 但是数据库实际没有修改成功?!! -- 隔离级别为 读已提交(针对不同的事务, 不同的连接对象)
        be.doQuery(msQuery, param, rb, rh, bs);
    }

    /**
     * @see org.apache.ibatis.executor.BaseExecutor#localCache -- 缓存的是查询结果, 是 BaseExecutor 带有的, 子类继承此功能
     * @see org.apache.ibatis.executor.BaseExecutor#query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler)
     * query 方法自带一级缓存(session 级别缓存, 当前线程的本地缓存)
     * ---
     * @see org.anonymous.dao.annotation.UserDao#findById(int)
     * 关闭当前 Select-sql 的一级缓存: 即当前 session 的缓存.
     * 源码:
     * @see org.apache.ibatis.executor.BaseExecutor#query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler, org.apache.ibatis.cache.CacheKey, org.apache.ibatis.mapping.BoundSql)
     * if (queryStack == 0 && ms.isFlushCacheRequired()) {
     * clearLocalCache();
     * }
     * <p>
     * 方法一: @see org.apache.ibatis.annotations.Options.FlushCachePolicy#TRUE
     * 方法二: mapper: <select flushCache="true"/>
     * 方法三: config: <setting name="localCacheScope" value="STATEMENT"/>
     * 一级缓存的 key 生成策略:
     * @see org.apache.ibatis.executor.BaseExecutor#createCacheKey(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.mapping.BoundSql)
     */
    @Test
    public void firstCache() throws SQLException {
        // 在禁用一级缓存的情况下, 可以查看 simple/reuse 的区别: reuse 是重用预编译, 即重用 connection.preparedStatement(sql);
        // Executor se = new SimpleExecutor(config, tx);
        Executor se = new ReuseExecutor(config, tx);

        MappedStatement ms = config.getMappedStatement("org.anonymous.dao.annotation.UserDao.findById");
        Object param = 1;
        RowBounds rb = RowBounds.DEFAULT;
        @SuppressWarnings("unchecked")
        ResultHandler<Object> rh = Executor.NO_RESULT_HANDLER;

        List<Object> list = se.query(ms, param, rb, rh);
        System.out.println("list = " + list);
        List<Object> list1 = se.query(ms, param, rb, rh);
        System.out.println(list.get(0) == list1.get(0));
        System.out.println(list == list1);

        se.query(ms, param, rb, rh);
    }

    /**
     * 二级缓存的对象必须可序列化
     * 日志中的 "Cache Hit Ratio xxxx" 即表示二级缓存开启, 计算的二级缓存命中率
     *
     * @see org.anonymous.test.Demo02_cache#test2()
     * 二级缓存相关
     * 1. 全局配置: SqlMapConfig.xml- <setting name="cacheEnabled" value="true"/> 默认 true, 即二级缓存全局配置默认开启
     * @see org.apache.ibatis.cache.decorators.TransactionalCache
     * 注解:
     * @see org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseCache()
     * @see org.apache.ibatis.annotations.CacheNamespace Mapper 级别的设置
     * @see org.apache.ibatis.annotations.Options#useCache() 方法级别的设置, 默认为 true, Mapper 级别不设置则方法级别无效
     * xml:
     * @see org.apache.ibatis.builder.xml.XMLMapperBuilder#cacheElement(org.apache.ibatis.parsing.XNode)
     * ---
     * commit 操作清空一级缓存
     * @see org.apache.ibatis.session.defaults.DefaultSqlSession#commit(boolean)
     * @see org.apache.ibatis.executor.BaseExecutor#clearLocalCache()
     */
    @Test
    public void secondCache() throws SQLException {
        SimpleExecutor se = new SimpleExecutor(config, tx);
        // 装饰者模式
        CachingExecutor ce = new CachingExecutor(se);

        MappedStatement ms = config.getMappedStatement("org.anonymous.dao.annotation.UserDao.findById");
        Object param = 1;
        RowBounds rb = RowBounds.DEFAULT;
        @SuppressWarnings("unchecked")
        ResultHandler<Object> rh = SimpleExecutor.NO_RESULT_HANDLER;
        List<Object> list = ce.query(ms, param, rb, rh);

        // 如果不提交, 此后的两次查询使用的都是一级缓存
        ce.commit(true); // commit 会刷新一级缓存, 此后才会使用二级缓存

        List<Object> list1 = ce.query(ms, param, rb, rh); // 二级缓存命中率: 1/2

        List<Object> list2 = ce.query(ms, param, rb, rh); // 二级缓存命中率: 2/3

    }

}
