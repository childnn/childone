package org.anonymous.config.mybatis.spring;

import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @SuppressWarnings("unchecked") List<E> list = (List<E>) tcm.getObject(cache, key);
 * if (list == null) {
 * list = delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
 * tcm.putObject(cache, key, list); // issue #578 and #116
 * }
 * return list;
 * }
 * }
 * --
 * private void flushCacheIfRequired(MappedStatement ms) {
 * Cache cache = ms.getCache();
 * if (cache != null && ms.isFlushCacheRequired()) { // 存在二级缓存, 且开启刷新缓存时, 执行刷新操作
 * tcm.clear(cache);
 * }
 * }
 * 二级缓存相关:
 * @see org.mybatis.spring.annotation.MapperScan  // @Import(MapperScannerRegistrar.class)
 * @see org.mybatis.spring.annotation.MapperScannerRegistrar#registerBeanDefinitions(org.springframework.core.type.AnnotationMetadata, org.springframework.beans.factory.support.BeanDefinitionRegistry)
 * {@link org.mybatis.spring.annotation.MapperScannerRegistrar#registerBeanDefinitions(org.springframework.core.type.AnnotationMetadata, org.springframework.core.annotation.AnnotationAttributes, org.springframework.beans.factory.support.BeanDefinitionRegistry, java.lang.String)}
 * java-code:
 * BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
 * @see org.mybatis.spring.mapper.MapperScannerConfigurer#setBasePackage(String)
 * @see org.mybatis.spring.mapper.MapperScannerConfigurer#postProcessBeanDefinitionRegistry(org.springframework.beans.factory.support.BeanDefinitionRegistry)
 * {@code org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanDefinitionRegistryPostProcessors(java.util.Collection, org.springframework.beans.factory.support.BeanDefinitionRegistry)}
 * {@link org.mybatis.spring.mapper.ClassPathMapperScanner#doScan(java.lang.String...)
 * Calls the parent search that will search and register all the candidates.
 * Then the registered objects are post processed to set them as MapperFactoryBeans}
 * 1. 先执行 spring-IoC 的 {@link org.springframework.context.annotation.ClassPathBeanDefinitionScanner#doScan(java.lang.String...)}
 * 2. 之后再 {@link org.mybatis.spring.mapper.ClassPathMapperScanner#processBeanDefinitions(java.util.Set)}
 * java-code:
 * // the mapper interface is the original class of the bean
 * // but, the actual class of the bean is MapperFactoryBean
 * definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName); // issue #59
 * definition.setBeanClass(this.mapperFactoryBeanClass);
 * @see org.mybatis.spring.mapper.MapperFactoryBean#setSqlSessionFactory(org.apache.ibatis.session.SqlSessionFactory)
 * {@link org.mybatis.spring.SqlSessionTemplate}
 * 一个 MapperFactoryBean 生成一个 Mapper, {@link org.mybatis.spring.mapper.MapperFactoryBean#getObject()}
 * 依赖于 SqlSessionTemplate{也即 SqlSession}, SqlSessionTemplate 中的 SqlSession 由动态代理生成
 * @see org.mybatis.spring.support.SqlSessionDaoSupport
 * ==========================================================================================================================
 * -- mybatis
 * @see org.apache.ibatis.annotations.Mapper -- 此注解在 spring-boot 中才有使用(根据 IDEA 中搜索到的引用点)?
 * {@code org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration.AutoConfiguredMapperScannerRegistrar#registerBeanDefinitions(org.springframework.core.type.AnnotationMetadata, org.springframework.beans.factory.support.BeanDefinitionRegistry)}
 * @see org.apache.ibatis.binding.MapperProxy
 * @see org.apache.ibatis.binding.MapperProxyFactory#newInstance(org.apache.ibatis.session.SqlSession)
 * @see org.apache.ibatis.binding.MapperRegistry
 * --- mybatis-spring 一级缓存无事务时执行清空的源码:
 * @see org.mybatis.spring.SqlSessionTemplate.SqlSessionInterceptor
 * java-code:
 * if (!isSqlSessionTransactional(sqlSession, SqlSessionTemplate.this.sqlSessionFactory)) {
 * // force commit even on non-dirty sessions because some databases require
 * // a commit/rollback before calling close()
 * sqlSession.commit(true);
 * }
 * ---
 * mybatis-statement-id 生成策略:
 * annotation-based(@Select...) 的解析:
 * @see org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseStatement(java.lang.reflect.Method)
 * java-code:
 * final String mappedStatementId = type.getName() + "." + method.getName();
 * xml-based(mapper.xml) 的解析:
 * @see org.apache.ibatis.builder.xml.XMLStatementBuilder#parseStatementNode()
 * java-code:
 * String id = context.getStringAttribute("id");
 * {@link org.apache.ibatis.builder.MapperBuilderAssistant#addMappedStatement(java.lang.String, org.apache.ibatis.mapping.SqlSource, org.apache.ibatis.mapping.StatementType, org.apache.ibatis.mapping.SqlCommandType, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Class, java.lang.String, java.lang.Class, org.apache.ibatis.mapping.ResultSetType, boolean, boolean, boolean, org.apache.ibatis.executor.keygen.KeyGenerator, java.lang.String, java.lang.String, java.lang.String, org.apache.ibatis.scripting.LanguageDriver, java.lang.String)}
 * {@link org.apache.ibatis.builder.MapperBuilderAssistant#applyCurrentNamespace(java.lang.String, boolean)}
 * 先判断 base 是否带有 currentNamespace 前缀, 没有则拼接前缀.
 * 关于 currentNamespace, 注解形式就是当前接口全限定名; mapper.xml 形式, 就是 xml 中设置的 namespace 属性名, 不一定为 全限定名
 * @see org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parse()
 * java-code: assistant.setCurrentNamespace(type.getName());
 * @see org.apache.ibatis.builder.xml.XMLMapperBuilder#configurationElement(org.apache.ibatis.parsing.XNode)
 * java-code:
 * String namespace = context.getStringAttribute("namespace");
 * if (namespace == null || namespace.isEmpty()) {
 * throw new BuilderException("Mapper's namespace cannot be empty");
 * }
 * builderAssistant.setCurrentNamespace(namespace);
 * =====================================================
 * mybatis 一级缓存清空条件:
 * @see org.apache.ibatis.executor.BaseExecutor#clearLocalCache() -- 此方法的调用, 就意味着清空一级缓存
 * 1. 更新操作(非查询)
 * @see org.apache.ibatis.executor.BaseExecutor#update(org.apache.ibatis.mapping.MappedStatement, java.lang.Object)
 * 2. 查询且刷新缓存
 * @see org.apache.ibatis.executor.BaseExecutor#query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler, org.apache.ibatis.cache.CacheKey, org.apache.ibatis.mapping.BoundSql)
 * 源码:
 * {@link org.apache.ibatis.mapping.MappedStatement#isFlushCacheRequired()} -- 此方法返回 true, 则会清空非子查询的一级缓存, 和当前 MappedStatement 存在二级缓存时的二级缓存
 * if (queryStack == 0 && ms.isFlushCacheRequired()) { // 非子查询, 且开启刷新时, 执行清空一级缓存操作
 * clearLocalCache();
 * }
 * queryStack 记录子查询深度, 子查询时不会执行清空一级缓存操作
 * 另注, 二级缓存清空判断条件: (详见下方关于二级缓存的解析)
 * {@link org.apache.ibatis.executor.CachingExecutor#flushCacheIfRequired(org.apache.ibatis.mapping.MappedStatement)}
 * 设置 {@link org.apache.ibatis.mapping.MappedStatement#flushCacheRequired} 的点:
 * 2.1. mapper.xml
 * {@link org.apache.ibatis.builder.xml.XMLStatementBuilder#parseStatementNode()}
 * <select flushCache="true">
 * // flushCache = "true" 表示刷新一级缓存, 不设置其值为 "!isSelect"
 * </select>
 * 源码: boolean flushCache = context.getBooleanAttribute("flushCache", !isSelect);
 * 2.2. annotation
 * {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseStatement(java.lang.reflect.Method)}
 * {@link org.apache.ibatis.annotations.Options#flushCache()}
 * 源码:
 * boolean flushCache = !isSelect; // 是否刷新一级缓存
 * boolean useCache = isSelect; // 是否使用二级缓存
 * if (options != null) {
 * if (FlushCachePolicy.TRUE.equals(options.flushCache())) {
 * flushCache = true;
 * } else if (FlushCachePolicy.FALSE.equals(options.flushCache())) {
 * flushCache = false;
 * }
 * ...
 * }
 * 3. 查询, 且一级缓存作用域为 STATEMENT
 * {@link org.apache.ibatis.executor.BaseExecutor#query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler, org.apache.ibatis.cache.CacheKey, org.apache.ibatis.mapping.BoundSql)}
 * {@link org.apache.ibatis.session.LocalCacheScope}
 * 源码:
 * if (configuration.getLocalCacheScope() == LocalCacheScope.STATEMENT) {
 * // issue #482
 * clearLocalCache();
 * }
 * 设置 localCacheScope 的点:
 * 3.1. config.xml
 * {@link org.apache.ibatis.builder.xml.XMLConfigBuilder#settingsElement(java.util.Properties)}
 * 源码:
 * configuration.setLocalCacheScope(LocalCacheScope.valueOf(props.getProperty("localCacheScope", "SESSION")));
 * 3.2. annotation 设置 localCacheScope 暂未找到...
 * 4. commit/rollback
 * @see org.apache.ibatis.executor.BaseExecutor#commit(boolean)
 * @see org.apache.ibatis.executor.BaseExecutor#rollback(boolean)
 * 另, {@link org.apache.ibatis.session.defaults.DefaultSqlSession#clearCache()} 也可以说明 SqlSession 作用域的缓存为一级缓存
 * -----------------------------------------------------------------------------------------------------------------------------
 * 二级缓存:
 * 设置方法与解析点:
 * 1. xml:
 * 1.1. 首先开启全局二级缓存配置
 * config.xml  <setting name="cacheEnabled" value="true"/> 默认 true
 * 解析源码:
 * {@link org.apache.ibatis.builder.xml.XMLConfigBuilder#settingsElement(java.util.Properties)}
 * configuration.setCacheEnabled(booleanValueOf(props.getProperty("cacheEnabled"), true));
 * 1.2. 再在对应的 mapper.xml 中开启当前 mapper 的二级缓存配置
 * 开启: <cache/>
 * 解析:
 * {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#configurationElement(org.apache.ibatis.parsing.XNode)}
 * cacheElement(context.evalNode("cache"));
 * {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#cacheElement(org.apache.ibatis.parsing.XNode)}
 * 1.3. 最后再对应的 sql 中开启二级缓存配置
 * <select useCache="true">
 * ...开启当前 sql 的二级缓存
 * </select>
 * 解析:
 * {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#configurationElement(org.apache.ibatis.parsing.XNode)}
 * ...-> {@link org.apache.ibatis.builder.xml.XMLStatementBuilder#parseStatementNode()}
 * 源码: boolean useCache = context.getBooleanAttribute("useCache", isSelect); // 默认 select-sql 开启二级缓存
 * 2. annotation
 * 2.1. 开启 Mapper.java 类层次的二级缓存
 * 设置: 在 需要使用二级缓存的 mapper.java 接口上标注 {@link org.apache.ibatis.annotations.CacheNamespace}
 * 解析:
 * {@link org.apache.ibatis.binding.MapperRegistry#addMapper(java.lang.Class)}
 * ...-> {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parse()}
 * {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseCache()}
 * 2.2. 开启指定 sql 二级缓存
 * 设置: 在方法上标注 {@link org.apache.ibatis.annotations.Options#useCache()}
 * 解析:
 * {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseStatement(java.lang.reflect.Method)}
 * 源码:
 * boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
 * boolean flushCache = !isSelect; // 是否刷新一级缓存... 根据代码, 实际也跟 二级缓存有关, 查看方法调用点:{@link org.apache.ibatis.mapping.MappedStatement#isFlushCacheRequired()}
 * boolean useCache = isSelect; // 是否开启二级缓存
 * if (options != null) {
 * if (FlushCachePolicy.TRUE.equals(options.flushCache())) {
 * flushCache = true;
 * } else if (FlushCachePolicy.FALSE.equals(options.flushCache())) {
 * flushCache = false;
 * }
 * useCache = options.useCache();
 * ...
 * }
 * 设置之后再 builder
 * 构建二级缓存 Cache 对象, 并将该 Cache 添加到 Configuration
 * {@link org.apache.ibatis.builder.MapperBuilderAssistant#useNewCache(java.lang.Class, java.lang.Class, java.lang.Long, java.lang.Integer, boolean, boolean, java.util.Properties)}
 * 二级缓存存储点: {@link org.apache.ibatis.session.Configuration#caches} --(caches-key 就是 namespace) 一个 SqlSessionFactory 对应一个 Configuration.
 * 一级缓存存储点: {@link org.apache.ibatis.executor.BaseExecutor#localCache} -- 一个 SqlSession 对应一个 Executor. 此 cache 就是{@link org.apache.ibatis.cache.impl.PerpetualCache}
 * 构建 MappedStatment 对象, 并将该对象添加到 Configuration 中
 * {@link org.apache.ibatis.builder.MapperBuilderAssistant#addMappedStatement(java.lang.String, org.apache.ibatis.mapping.SqlSource, org.apache.ibatis.mapping.StatementType, org.apache.ibatis.mapping.SqlCommandType, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Class, java.lang.String, java.lang.Class, org.apache.ibatis.mapping.ResultSetType, boolean, boolean, boolean, org.apache.ibatis.executor.keygen.KeyGenerator, java.lang.String, java.lang.String, java.lang.String, org.apache.ibatis.scripting.LanguageDriver, java.lang.String)}
 * 二级缓存的判断、获取、清空:
 * @see org.apache.ibatis.executor.CachingExecutor#query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler)
 * @see org.apache.ibatis.executor.CachingExecutor#query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler, org.apache.ibatis.cache.CacheKey, org.apache.ibatis.mapping.BoundSql)
 * 源码:
 * Cache cache = ms.getCache(); // 获取上述设置的 MappedStatement 中的 Cache
 * if (cache != null) {
 * flushCacheIfRequired(ms);
 * if (ms.isUseCache() && resultHandler == null) { // 开启当前 Statement 的二级缓存, 且不使用 ResultHandler {@link org.apache.ibatis.executor.Executor#NO_RESULT_HANDLER}
 * ensureNoOutParams(ms, boundSql);
 * @see org.apache.ibatis.cache.TransactionalCacheManager
 * @see org.apache.ibatis.cache.decorators.TransactionalCache
 * @see org.apache.ibatis.builder.MapperBuilderAssistant#useNewCache(java.lang.Class, java.lang.Class, java.lang.Long, java.lang.Integer, boolean, boolean, java.util.Properties)
 * 源码:
 * Cache cache = new CacheBuilder(currentNamespace)
 * .implementation(valueOrDefault(typeClass, PerpetualCache.class)) // 实际的
 * .addDecorator(valueOrDefault(evictionClass, LruCache.class))...build();
 * {@link org.apache.ibatis.mapping.CacheBuilder#build()}
 * {@link org.apache.ibatis.mapping.CacheBuilder#newBaseCacheInstance(java.lang.Class, java.lang.String)}
 * 源码:  the base-cache 必须存在单参数构造(参数类型为 String, 其值表示此 Cache 的 id)
 * 可以参见 {@link org.apache.ibatis.cache.Cache} 官方文档
 * Constructor<? extends Cache> cacheConstructor = getBaseCacheConstructor(cacheClass);
 * try {
 * return cacheConstructor.newInstance(id);
 * } catch (Exception e) {
 * throw new CacheException("Could not instantiate cache implementation (" + cacheClass + "). Cause: " + e, e);
 * }
 * 默认的 base-cache 就是 {@link org.apache.ibatis.cache.impl.PerpetualCache}, 其缓存使用的数据结构就是一个 HashMap
 * 然后再设置相应的 decorator-cache(s).
 * @since 2020/12/15 9:33
 * -- mybatis-spring
 * MapperScannerRegistrar -> MapperScannerConfigurer -> ClassPathMapperScanner -> MapperFactoryBean
 */
// @Configuration // @Import() 引入到主配置类, 可以不需此注解.
@MapperScan(basePackages = "org.anonymous.dao.annotation")
public class MyBatisConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * @see org.apache.ibatis.type.TypeAliasRegistry#registerAlias(java.lang.Class)
     * @see org.apache.ibatis.type.Alias#value()  如果使用了注解, 则在注入时使用注解的 value 值,
     * 否则使用 {@link Class#getSimpleName()}
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() {
        SqlSessionFactoryBean ssf = new SqlSessionFactoryBean();
        ssf.setDataSource(dataSource);
        // ssf.setTypeAliasesPackage("org.anonymous.domain"); // 需要使用别名可以配置, 一般就是需要在 xml 中用到时使用. 可以配置使用 @Alias
        ssf.setFailFast(true); // org.mybatis.spring.SqlSessionFactoryBean.onApplicationEvent: check all statements are completed
        // todo: mybatis 默认解析 DTD 约束 xml 配置, 解析不了 schema 约束 xml 配置, 那么为何会存在 schema 约束? 是为了用于 spring?
        // 默认的 builder: org.apache.ibatis.builder.xml.XMLConfigBuilder
        // org.apache.ibatis.builder.xml.XMLConfigBuilder.parseConfiguration
        // ssf.setConfigLocation(new ClassPathResource("SqlMapConfig.xml"));
        Configuration config = new Configuration();
        config.setLogImpl(Log4jImpl.class);
        // config.setCacheEnabled(); // 二级缓存
        config.setDefaultExecutorType(ExecutorType.REUSE); // ReuseExecutor
        ssf.setConfiguration(config);

        return ssf;
    }


}
