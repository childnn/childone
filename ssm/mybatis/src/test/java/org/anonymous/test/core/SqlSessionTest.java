package org.anonymous.test.core;

import org.anonymous.dao.annotation.UserDao;
import org.anonymous.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.apache.ibatis.executor.BaseExecutor#query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler)
 * 一级缓存 org.apache.ibatis.executor.BaseExecutor#localCache
 * 缓存的是相同 sql 的结果集
 * 一级缓存的 key: {@link org.apache.ibatis.cache.CacheKey}
 * {@link org.apache.ibatis.executor.BaseExecutor#createCacheKey(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.mapping.BoundSql)}
 * 要看哪些操作会清空 一级缓存, 就看哪里调用了 {@link org.apache.ibatis.executor.BaseExecutor#clearLocalCache()}
 * 所有更新操作都会清空一级缓存. {@link org.apache.ibatis.executor.BaseExecutor#update(org.apache.ibatis.mapping.MappedStatement, java.lang.Object)}
 * ---
 * @see org.anonymous.dao.annotation.UserDao#findById(int)
 * @since 2020/12/13 10:42
 * SqlSession 实际上是封装了 Executor
 * SqlSession -> CachingExecutor(二级 cache) -> BaseExecutor(query/doQuery, update/doUpdate)
 */
public class SqlSessionTest {

    private SqlSessionFactory ssf;

    @Before
    public void init() throws IOException {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        ssf = builder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
    }

    /**
     * @see ExecutorTest#secondCache()
     * SqlSession 底层使用的就是 Executor
     * @see org.apache.ibatis.session.defaults.DefaultSqlSession#DefaultSqlSession(org.apache.ibatis.session.Configuration, org.apache.ibatis.executor.Executor, boolean)
     * @see org.apache.ibatis.session.defaults.DefaultSqlSessionFactory#openSessionFromDataSource
     * 在二级缓存开启的情况下(默认: org.apache.ibatis.session.Configuration#cacheEnabled=true)
     * 使用的就是 {@link org.apache.ibatis.executor.CachingExecutor}
     * @see org.apache.ibatis.session.Configuration#newExecutor(org.apache.ibatis.transaction.Transaction, org.apache.ibatis.session.ExecutorType)
     * 源码:
     * if (cacheEnabled) {
     * executor = new CachingExecutor(executor);
     * }
     * 二级缓存的 cache 作用域
     * @see org.apache.ibatis.mapping.MappedStatement#getCache() -- 这就是二级缓存
     * @see org.apache.ibatis.builder.xml.XMLMapperBuilder#cacheElement(org.apache.ibatis.parsing.XNode)
     * @see org.apache.ibatis.builder.MapperBuilderAssistant#useNewCache(java.lang.Class, java.lang.Class, java.lang.Long, java.lang.Integer, boolean, boolean, java.util.Properties)
     * currentCache = cache;
     * @see org.apache.ibatis.builder.MapperBuilderAssistant#addMappedStatement(java.lang.String, org.apache.ibatis.mapping.SqlSource, org.apache.ibatis.mapping.StatementType, org.apache.ibatis.mapping.SqlCommandType, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Class, java.lang.String, java.lang.Class, org.apache.ibatis.mapping.ResultSetType, boolean, boolean, boolean, org.apache.ibatis.executor.keygen.KeyGenerator, java.lang.String, java.lang.String, java.lang.String, org.apache.ibatis.scripting.LanguageDriver, java.lang.String)
     * xxx.cache(currentCache); -- 将 currentCache 设置给 MappedStatement
     */
    @Test
    public void sqlSession() {
        SqlSession sqlSession = ssf.openSession(/*ExecutorType.REUSE,*/ true); // 开启自动提交
        User user = sqlSession.selectOne("org.anonymous.dao.annotation.UserDao.findById", 1);
        System.out.println("user = " + user);

        // 注释此行代码, 查看日志: 此行代码是决定使用哪一级缓存的关键. 不提交, 则为一级缓存(session 级别), 提交后则使用二级缓存
        // 提交之后(清空一级缓存), 方可使用二级缓存
        // sqlSession.commit();
        // sqlSession.rollback();
        // 或者关闭当前 session, 但是后面的查询不再可以使用此 session 需要重新获取新的 session
        // sqlSession.close(); // 关闭之后重新获取 session: SqlSession sqlSession = ssf.openSession(true); // 开启自动提交

        UserDao mapper = sqlSession.getMapper(UserDao.class);
        User u1 = mapper.findById(1);
        System.out.println(u1 == user); // 使用一级缓存时为 true, 使用二级缓存时为 false

        mapper.findById(1);
    }

    /**
     * @see org.apache.ibatis.builder.xml.XMLConfigBuilder#mapperElement(org.apache.ibatis.parsing.XNode)
     * @see org.apache.ibatis.binding.MapperRegistry#addMapper(java.lang.Class) -- 每一个 mapper 对应一个 org.apache.ibatis.builder.annotation.MapperAnnotationBuilder
     * @see org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parse()
     * loadXmlResource(); -- 先尝试加载对应的 .xml 文件, 如果没有 .xml 的 mapper 文件也不影响
     */
    public void builder() {

    }

}
