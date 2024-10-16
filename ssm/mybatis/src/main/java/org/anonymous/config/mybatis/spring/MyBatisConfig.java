package org.anonymous.config.mybatis.spring;

import org.anonymous.config.mybatis.spring.annotation.MyMapperScan;
import org.apache.ibatis.logging.log4j.Log4jImpl;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * todo: MyBatis 自身如何完全不依赖 xml 配置?
 * MyBatis 最主要的几个类
 * @see org.apache.ibatis.session.Configuration 核心配置, 包括所有
 * @see org.apache.ibatis.builder.xml.XMLConfigBuilder 解析主配置文件
 * @see org.apache.ibatis.builder.xml.XMLMapperBuilder 解析 mapper.xml
 * @see org.apache.ibatis.builder.xml.XMLStatementBuilder 解析 mapper.xml 中的各种 sql 标签
 * @see org.apache.ibatis.builder.annotation.MapperAnnotationBuilder 解析 mapper.class 注解, 包括 sql 注解在内的各种注解
 * @see org.apache.ibatis.builder.MapperBuilderAssistant 解析每个 mapper.xml 时都有一个对应的此对象, 以 currentNamespace 区分
 * @see org.apache.ibatis.binding.MapperRegistry
 * @see org.apache.ibatis.mapping.MappedStatement 每个 Sql 对应一个此对象.
 * -- 执行器
 * @see org.apache.ibatis.executor.BaseExecutor 一级缓存
 * @see org.apache.ibatis.executor.CachingExecutor 二级缓存
 * @see org.apache.ibatis.executor.ReuseExecutor 重用 {@link java.sql.Statement}
 * @see org.apache.ibatis.executor.BatchExecutor
 * @see org.apache.ibatis.executor.SimpleExecutor 非重用 Statement
 * ---------------------------------------------------------------------------------------------------------
 * 各种 id
 * 1. 1st-cache-id: {@link org.apache.ibatis.executor.BaseExecutor#createCacheKey(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.mapping.BoundSql)}
 * 1. 2nd-cache-id: currentNamespace {@link org.apache.ibatis.builder.MapperBuilderAssistant#useNewCache
 * 2. statement-id:
 *      注解形式:  className.methodName, 此时 namespace 就是 className.
 *              {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parse()}  assistant.setCurrentNamespace(type.getName());
 *              {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseStatement}  final String mappedStatementId = type.getName() + "." + method.getName();
 *              {@link org.apache.ibatis.builder.MapperBuilderAssistant#addMappedStatement}  id = applyCurrentNamespace(id, false);
 *                  先判断 base 是否带有 currentNamespace 前缀, 没有则拼接前缀.
 *      xml 形式: namespace.id, 此时 namespace 是 mapper.xml 中 mapper 标签的 namespace 属性, id 是各个 sql 标签的 id 属性
 *              {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#configurationElement(org.apache.ibatis.parsing.XNode)} String namespace = context.getStringAttribute("namespace");... builderAssistant.setCurrentNamespace(namespace);
 *              {@link org.apache.ibatis.builder.xml.XMLStatementBuilder#parseStatementNode()}
 * 关于 currentNamespace, 注解形式就是当前接口全限定名; mapper.xml 形式, 就是 xml 中设置的 namespace 属性名, 不一定为 全限定名
 *  1. annotation-mapper:
 *     {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parse()}
 *     java-code:
 *              assistant.setCurrentNamespace(type.getName());
 *  2. xml-mapper:
 *     {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#configurationElement}
 *     java-code:
 *          String namespace = context.getStringAttribute("namespace");
 *          if (namespace == null || namespace.isEmpty()) {
 *              throw new BuilderException("Mapper's namespace cannot be empty");
 *          }
 *          builderAssistant.setCurrentNamespace(namespace);
 * ---------------------------------------------------------------------------------------------------------
 * 别名映射:
 *   1. {@link org.apache.ibatis.type.TypeAliasRegistry} {@link org.apache.ibatis.type.Alias}
 *          {@link org.apache.ibatis.type.TypeAliasRegistry#registerAlias(java.lang.Class)}
 *   2. {@link org.apache.ibatis.session.Configuration#Configuration()}
 *   在 {@link org.apache.ibatis.builder.xml.XMLConfigBuilder#XMLConfigBuilder(org.apache.ibatis.parsing.XPathParser, java.lang.String, java.util.Properties)}
 *   中初始化 Configuration, 会构建 TypeAliasRegistry, 注册一些默认的 type-alias
 * TypeHandler: 转换 JDBC-type 的数据类型到 Java-type
 *    {@link org.apache.ibatis.type.TypeHandlerRegistry#register(java.lang.String)}
 *    {@link org.apache.ibatis.type.MappedTypes} {@link org.apache.ibatis.type.MappedJdbcTypes}
 *    {@link org.apache.ibatis.type.TypeHandlerRegistry#TypeHandlerRegistry(org.apache.ibatis.session.Configuration)}
 *    在 Configuration 中初始化
 *    java-code:
 *       protected final TypeHandlerRegistry typeHandlerRegistry = new TypeHandlerRegistry(this);
 *
 * =====================================================================================================================================
 * 注: 以下对于 XMLConfig, mapper.xml, mapper.class 的分析均基于源码.
 * ---------------------------------------------------------------------------------------------------------
 *                          解析主配置类 XMLConfig
 * ---------------------------------------------------------------------------------------------------------
 *    {@link org.apache.ibatis.builder.xml.XMLConfigBuilder#parse() // 从 SqlSessionFactoryBuilder/SqlSessionFactoryBean 进入
 *    1. properties: 设置自定属性, 如 jdbc 连接信息等, 可直接引入 .properties 文件 {@link org.apache.ibatis.session.Configuration#variables}
 *    2. settings: 这些 setting 的属性名就是 Configuration 中的成员变量.
 *          {@link org.apache.ibatis.builder.xml.XMLConfigBuilder#settingsAsProperties(org.apache.ibatis.parsing.XNode)}
 *          该方法中会对 xml-config 中的 settings 属性名进行校验, 校验是否属于 Configuration 的成员.
 *          logImpl, vfsImpl...
 *         java-code:
 *              Properties props = context.getChildrenAsProperties();
 *              // Check that all settings are known to the configuration class
 *              MetaClass metaConfig = MetaClass.forClass(Configuration.class, localReflectorFactory);
 *              for (Object key : props.keySet()) {
 *                  if (!metaConfig.hasSetter(String.valueOf(key))) {
 *                      throw new BuilderException("The setting " + key + " is not known.
 *                      Make sure you spelled it correctly (case sensitive).");
 *                  }
 *              }
 *          {@link org.apache.ibatis.builder.xml.XMLConfigBuilder#settingsElement(java.util.Properties)}
 *          该方法设置了 Configuration 中的各种属性值, 如果 XMLConfig 未配置, 则给定默认值.
 *     3. typeAliases: 设置别名类所在的 package, 或者别名类本身
 *     4. plugins: 插件, 比如分页插件
 *     5. objectFactory: 构建对象的 factory, 一般不需实现, 使用默认即可
 *     6. objectWrapperFactory: 暂时不清楚干嘛的?
 *     7. reflectorFactory: 获取类的反射信息; {@link org.apache.ibatis.reflection.Reflector}, 一般无需实现, MyBatis 内部使用这些信息
 *     8. environments: 可以设置各种环境, 但实际被解析的只是 default-id 对应的环境
 *         解析规则参见: {@link org.apache.ibatis.builder.xml.XMLConfigBuilder#environmentsElement(org.apache.ibatis.parsing.XNode)}
 *     9. databaseIdProvider: 数据库类型 {@link org.apache.ibatis.mapping.DatabaseIdProvider}
 *     10. typeHandlers: TypeHandler 所在的 package, 或者 TypeHandler 本身
 *     11. mappers: mapper 所在的包或者 mapper 本身.
 *        解析 mapper.xml/mapper.class {@link org.apache.ibatis.builder.xml.XMLConfigBuilder#mapperElement(org.apache.ibatis.parsing.XNode)}
 * ------------------------------------------------------------------------------------------------------------------------------------------------------
 *                                              解析 mapper.xml
 * ------------------------------------------------------------------------------------------------------------------------------------------------------
 *   {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#parse()}
 *   1. cache-ref: 当前 mapper.xml 引用的 cache-namespace. 唯一.
 *      {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#cacheRefElement(org.apache.ibatis.parsing.XNode)}
 *      {@link org.apache.ibatis.session.Configuration#cacheRefMap}
 *   2. cache: 解析当前 mapper.xml 的二级缓存配置. 唯一. 不设置则表示当前 mapper 不开启二级缓存.
 *      {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#cacheElement(org.apache.ibatis.parsing.XNode)}
 *         映射语句文件中的所有 select 语句的结果将会被缓存。
 *         映射语句文件中的所有 insert、update 和 delete 语句会刷新缓存。
 *         缓存会使用最近最少使用算法（LRU, Least Recently Used）算法来清除不需要的缓存。
 *         缓存不会定时进行刷新（也就是说，没有刷新间隔）。
 *         缓存会保存列表或对象（无论查询方法返回哪种）的 1024 个引用。
 *         缓存会被视为读/写缓存，这意味着获取到的对象并不是共享的，可以安全地被调用者修改，而不干扰其他调用者或线程所做的潜在修改。
 *         提示 缓存只作用于 cache 标签所在的映射文件中的语句。如果你混合使用 Java API 和 XML 映射文件，
 *         在共用接口中的语句将不会被默认缓存。你需要使用 {@link org.apache.ibatis.annotations.CacheNamespaceRef} 注解指定缓存作用域。
 *      以下属性均可在 {@link org.apache.ibatis.annotations.CacheNamespace} 中找到相对应的.
 *      cache 标签属性:
 *      2.1. type: (base-cache) 默认实现 {@link org.apache.ibatis.cache.impl.PerpetualCache}, 底层缓存实现数据结构就是一个 map
 *          The cache implementation must have a constructor that receives the cache id as an String parameter.
 *          此 cache 是包装器最内层的实现, 必须设置 参数为 id 的 constructor.
 *          此 id 在 xml 中表示当前 xml 的 namespace, 若为注解 {@link org.apache.ibatis.annotations.CacheNamespace} 配置二级缓存,
 *          则此 id 为 注解所在类名 参见源码: {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parse()}
 *          java-code:
 *              assistant.setCurrentNamespace(type.getName());
 *      2.2. eviction: 过期策略(cache 的包装器), 构造方法以 Cache 最为参数
 *             decorator:
 *             LRU – 最近最少使用：移除最长时间不被使用的对象 {@link org.apache.ibatis.cache.decorators.LruCache}
 *             FIFO – 先进先出：按对象进入缓存的顺序来移除它们 {@link org.apache.ibatis.cache.decorators.FifoCache}
 *             SOFT – 软引用：基于垃圾回收器状态和软引用规则移除对象 {@link org.apache.ibatis.cache.decorators.SoftCache}
 *             WEAK – 弱引用：更积极地基于垃圾收集器状态和弱引用规则移除对象 {@link org.apache.ibatis.cache.decorators.WeakCache}
 *             默认的清除策略是 LRU.
 *             另一个 decorator 是 {@link org.apache.ibatis.cache.decorators.TransactionalCache}, 二级缓存相关
 *      2.3. flushInterval: 刷新间隔, 毫秒. 可被设置为任意的正整数，设置的值应该是一个以毫秒为单位的合理时间量。
 *             默认情况是不设置，也就是没有刷新间隔，缓存仅仅会在调用语句时刷新
 *             decorator: {@link org.apache.ibatis.cache.decorators.ScheduledCache}
 *             {@link org.apache.ibatis.mapping.CacheBuilder#setStandardDecorators(org.apache.ibatis.cache.Cache)}
 *      2.4. size: 引用数目. 可以被设置为任意正整数, 要注意欲缓存对象的大小和运行环境中可用的内存资源, 默认值是 1024
 *             {@link org.apache.ibatis.mapping.CacheBuilder#setStandardDecorators(org.apache.ibatis.cache.Cache)}
 *      2.5. readOnly: 是否只读. 可以被设置为 true 或 false。只读的缓存会给所有调用者返回缓存对象的相同实例。
 *          因此这些对象不能被修改。这就提供了可观的性能提升。而可读写的缓存会（通过序列化）返回缓存对象的拷贝。
 *          速度上会慢一些，但是更安全，因此默认值是 false
 *          {@link org.apache.ibatis.mapping.CacheBuilder#setStandardDecorators(org.apache.ibatis.cache.Cache)}
 *          java-code:
 *               if (readWrite) {
 *                  cache = new SerializedCache(cache);
 *               }
 *      2.6. blocking: 是否阻塞. decorator {@link org.apache.ibatis.cache.decorators.BlockingCache}
 *          详细参考官方文档.
 *   3. parameterMap: 可多个. {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#parameterMapElement(java.util.List)}
 *          存储点: {@link org.apache.ibatis.session.Configuration#parameterMaps}
 *        属性:
 *        3.1. id: 标识符
 *        3.2. type: parameterMap 代表的类型
 *        子标签:
 *        3.3. parameter {@link org.apache.ibatis.mapping.ParameterMapping}
 *           属性:
 *           3.3.1. property: 对象的属性
 *           3.3.2. javaType: 别名或全限定名
 *           3.3.3. jdbcType: {@link org.apache.ibatis.type.JdbcType}
 *           3.3.4. resultMap:  Parameters of type java.sql.ResultSet require a resultmap.
 *           3.3.5. mode: {@link org.apache.ibatis.mapping.ParameterMode}
 *           3.3.6. typeHandler: {@link org.apache.ibatis.type.TypeHandler}
 *           3.3.7. numericScale: todo: dtd 中的约束为什么是 scale?
 *   4. resultMap: 可多个. {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#resultMapElements(java.util.List)}
 *      子标签:
 *       4.1. constructor
 *       4.2. discriminator
 *       4.3. id
 *       4.4. result
 *   5. sql: 可多个 {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#sqlElement(java.util.List)}
 *
 *   6. select|insert|update|delete: 可多个 {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#buildStatementFromContext(java.util.List)}
 *          {@link org.apache.ibatis.builder.xml.XMLStatementBuilder#parseStatementNode()}
 *     属性:
 *      {@link org.apache.ibatis.annotations.Options}
 *      id: parentId
 *      databaseId: Configuration {@link org.apache.ibatis.mapping.DatabaseIdProvider} 默认: {@link java.sql.DatabaseMetaData#getDatabaseProductName()}
 *      flushCache: 在一定条件下, 是否刷新 一, 二级缓存. 默认 查询为 false, 非查询为 true.
 *      userCache: 是否在此 sql 使用二级缓存(前提是开启二级缓存全局配置与当前 mapper 的二级缓存配置). 只有 select 标签由此属性. 查询为 true, 其他为 false
 *      resultOrdered: select 专属. 结果排序, 默认 false
 *      parameterType: 参数类型别名或全限定名
 *      lang: {@link org.apache.ibatis.scripting.LanguageDriver}
 *      useGeneratedKeys: insert/update.
 *      statementType: {@link org.apache.ibatis.mapping.StatementType} 默认 PREPARED
 *      fetchSize: select.
 *      timeout:
 *      parameterMap:
 *      resultType:
 *      resultMap
 *      resultSetType: {@link org.apache.ibatis.mapping.ResultSetType}
 *      keyProperty:
 *      keyColumn
 *      resultSets
 *     子标签:
 *      selectKey: update/insert {@link org.apache.ibatis.annotations.SelectKey}
 *      set
 *      choose
 *      foreach
 *      include
 *
 *   --------------------------------------------------------------------------------------------------------------------------
 *                                      二级缓存总结
 *   --------------------------------------------------------------------------------------------------------------------------
     二级缓存总结:
          设置方法与解析点:
          1. xml-configuration:
            1.1. 首先开启全局二级缓存配置: 二级缓存全局配置默认开启
              config.xml  <setting name="cacheEnabled" value="true"/> 默认 true
              源码:
                {@link org.apache.ibatis.builder.xml.XMLConfigBuilder#settingsElement(java.util.Properties)}
                    configuration.setCacheEnabled(booleanValueOf(props.getProperty("cacheEnabled"), true));
            1.2. 再在对应的 mapper.xml 中开启当前 mapper 的二级缓存配置
                开启: <cache/> // 可设置该标签的各种属性
                解析:
                    {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#configurationElement(org.apache.ibatis.parsing.XNode)}
                        cacheElement(context.evalNode("cache"));
                    {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#cacheElement(org.apache.ibatis.parsing.XNode)}
            1.3. 最后再对应的 sql 中开启二级缓存配置
                <select useCache="true">
                    ...开启当前 sql 的二级缓存
                </select>
                解析:
                    {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#configurationElement(org.apache.ibatis.parsing.XNode)}
                            {@link org.apache.ibatis.builder.xml.XMLMapperBuilder#buildStatementFromContext(java.util.List)}
                      ...-> {@link org.apache.ibatis.builder.xml.XMLStatementBuilder#parseStatementNode()}
                    源码: boolean useCache = context.getBooleanAttribute("useCache", isSelect); // 默认 select-sql(DQL) 开启二级缓存
          2. annotation-configuration
             2.1. 开启 Mapper.java 类层次的二级缓存
                设置: 在需要使用二级缓存的 mapper.java 接口上标注 {@link org.apache.ibatis.annotations.CacheNamespace}
                解析:
                    {@link org.apache.ibatis.binding.MapperRegistry#addMapper(java.lang.Class)}
                    ...-> {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parse()}
                    {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseCache()}
                    在此方法中调用重要的 二级缓存构建方法: {@link org.apache.ibatis.builder.MapperBuilderAssistant#useNewCache
                    源码: MapperAnnotationBuilder#parseCache() 中, 解析 @CacheNamespace 注解.
                        assistant.useNewCache(cacheDomain.implementation(), cacheDomain.eviction(), flushInterval, size, cacheDomain.readWrite(), cacheDomain.blocking(), props);
                    在 MapperBuilderAssistant#useNewCache 方法中构建 二级 cache, 并将该 cache 添加到 Configuration.
                    源码:
                        // 一个 SqlSessionFactory 对应一个 Configuration, 二级缓存属于 Configuration, 所以二级缓存又称为 SSF 级别的缓存.
                        // 且 二级缓存属于当前 namespace, 所以在其他存在 namespaceRef/cacheRef.
                        configuration.addCache(cache); // 此处即说明了 二级缓存的源点: protected final Map<String, Cache> caches = new StrictMap<>("Caches collection");
                        currentCache = cache; // 这里的 currentCache 在其后构建 MappedStatement 时用到. 也说明一个 mapper 对应一个 2nd-cache.
             2.2. 开启指定 sql 二级缓存
                设置: 在方法上标注 {@link org.apache.ibatis.annotations.Options#useCache()} 默认 true
                  说明: 在 parse() 方法中调用完 {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseCache()}
                  之后, 会调用 {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseStatement(java.lang.reflect.Method)}
                解析 Sql: 配置了注解的方法
                    {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseStatement(java.lang.reflect.Method)}
                    构建 MappedStatment 对象, 并将该对象添加到 Configuration 中
                    {@link org.apache.ibatis.builder.MapperBuilderAssistant#addMappedStatement
                      mappedStatementId: className.methodName;
                        java-code: id = applyCurrentNamespace(id, false);
                    源码: MapperAnnotationBuilder#parseStatement 方法.
                        boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
                        boolean flushCache = !isSelect; // 是否刷新一级缓存... 根据代码, 实际也跟 二级缓存有关, 查看方法调用点:{@link org.apache.ibatis.mapping.MappedStatement#isFlushCacheRequired()}
                        boolean useCache = isSelect; // 是否开启二级缓存
                        if (options != null) {
                            if (FlushCachePolicy.TRUE.equals(options.flushCache())) {
                                flushCache = true;
                            } else if (FlushCachePolicy.FALSE.equals(options.flushCache())) {
                                flushCache = false;
                            }
                            useCache = options.useCache();
                            ...
                        }

            一级缓存存储点: {@link org.apache.ibatis.executor.BaseExecutor#localCache} -- 一个 SqlSession 对应一个 Executor. 此 cache 就是{@link org.apache.ibatis.cache.impl.PerpetualCache}
            二级缓存存储点: 错误...{@link org.apache.ibatis.session.Configuration#caches} --(caches-key 就是 namespace) 一个 SqlSessionFactory 对应一个 Configuration.
                实际上, 上述所说二级缓存 caches 集合只是用来解析 caseRef 的, 可以通过查看其访问信息得到.
                真正与二级缓存相关联的是 {@link org.apache.ibatis.mapping.MappedStatement#cache}, 而 MappedStatement 存储在
                {@link org.apache.ibatis.session.Configuration#mappedStatements} 中, 也就是与当前 SqlSessionFactory 相关联的 Configuration.
        二级缓存的判断、获取、清空:
          {@link org.apache.ibatis.executor.CachingExecutor#query}
          源码:
          Cache cache = ms.getCache(); // 获取上述设置的 MappedStatement 中的 Cache
          if (cache != null) {
               flushCacheIfRequired(ms);
               if (ms.isUseCache() && resultHandler == null) { // 开启当前 Statement 的二级缓存, 且不使用 ResultHandler {@link org.apache.ibatis.executor.Executor#NO_RESULT_HANDLER}
                   ensureNoOutParams(ms, boundSql);
              // ...
          @see org.apache.ibatis.cache.TransactionalCacheManager
          @see org.apache.ibatis.cache.decorators.TransactionalCache
          @see org.apache.ibatis.builder.MapperBuilderAssistant#useNewCache(java.lang.Class, java.lang.Class, java.lang.Long, java.lang.Integer, boolean, boolean, java.util.Properties)
          源码:
                Cache cache = new CacheBuilder(currentNamespace)
                            .implementation(valueOrDefault(typeClass, PerpetualCache.class)) // cache 实现
                            .addDecorator(valueOrDefault(evictionClass, LruCache.class))...build();
          {@link org.apache.ibatis.mapping.CacheBuilder#build()}
          {@link org.apache.ibatis.mapping.CacheBuilder#newBaseCacheInstance(java.lang.Class, java.lang.String)}
          源码:  the base-cache 必须存在单参数构造(参数类型为 String, 其值表示此 Cache 的 id)
                可以参见 {@link org.apache.ibatis.cache.Cache} 官方文档
                  Constructor<? extends Cache> cacheConstructor = getBaseCacheConstructor(cacheClass);
                  try {
                       return cacheConstructor.newInstance(id);
                  } catch (Exception e) {
                       throw new CacheException("Could not instantiate cache implementation (" + cacheClass + "). Cause: " + e, e);
                  }
          默认的 base-cache 就是 {@link org.apache.ibatis.cache.impl.PerpetualCache}, 其缓存使用的数据结构就是一个 HashMap
          然后再设置相应的 decorator-cache(s).

     --------------------------------------------------------------------------------------------------------------------------
                            解析 mapper.class
     --------------------------------------------------------------------------------------------------------------------------
 * 解析 mapper.class
 *   1. {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parse()}
 *      注意查看源码, 子类实际上会同时加载 mapper.xml 和 mapper.class. {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#loadXmlResource()}
 *          assistant.setCurrentNamespace(type.getName());
 *   2. {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseCache()}
 *      {@link org.apache.ibatis.annotations.CacheNamespace} 当前 mapper 的二级缓存设置
 *   3. {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseCacheRef()}
 *      {@link org.apache.ibatis.annotations.CacheNamespaceRef} 引用其他 mapper 的二级缓存
 *         {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseCacheRef()}
 *         有可能获取 CacheNamespaceRef 时, 对应的 cache 还未注册到 Configuration 中, 此时会将此信息添加到
 *         {@link org.apache.ibatis.session.Configuration#incompleteCacheRefs} 中
 *         源码:
 *             try {
 *                 assistant.useCacheRef(namespace);
 *             } catch (IncompleteElementException e) {
 *                 configuration.addIncompleteCacheRef(new CacheRefResolver(assistant, namespace));
 *             }
 *         这种未处理的信息, 会统一在 {@link org.apache.ibatis.session.Configuration#buildAllStatements()} 处理.
 *   4. {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseResultMap(java.lang.reflect.Method)}
 *         方法标注 @Select/@SelectProvider 且没有标注 @ResultMap 才会调用 parseResultMap 方法.
 *          {@link org.apache.ibatis.annotations.ResultMap} 参见官方文档示例. 一般用来引用 mapper.xml 中定义的 resultMap 标签的 id. 实际也可以引用 @Results 注解的 id 属性.
 *          {@link org.apache.ibatis.annotations.TypeDiscriminator} 参见官方文档示例. 将数据库的标识字段值(枚举)映射为对应的 POJO
 *          {@link org.apache.ibatis.annotations.Results} 参见官方文档示例. java 形式的 result-map 映射
 *          {@link org.apache.ibatis.annotations.Result} 一个 Result 的数据构建为一个 {@link org.apache.ibatis.mapping.ResultMapping}
 *          {@link org.apache.ibatis.annotations.ConstructorArgs} 参见官方文档示例. {@link org.apache.ibatis.annotations.Arg}
 *          {@link org.apache.ibatis.annotations.ResultType} 参见官方文档示例.
 *          {@link org.apache.ibatis.annotations.MapKey} 参见官方文档示例.
 *   5. {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseStatement(java.lang.reflect.Method)}
 *          {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#statementAnnotationTypes} DML, DQL 注解.
 *          {@link org.apache.ibatis.annotations.Options} 配合 DML, DQL 注解使用.
 *          {@link org.apache.ibatis.annotations.SelectKey} 参见官方文档, 暂不清楚如何使用 todo
 *             参见 mapper.xml-selectKey 标签, 此注解应配合 @Insert/@Update 使用, 生成数据库相关的主键(一般就是自增id)
 *
 *   6. {@link org.apache.ibatis.builder.MapperBuilderAssistant#addMappedStatement}
 *          java-code: configuration.addMappedStatement(statement);
 *  --------------------------------------------------------------------------------------------------------------------------
 *
 *
 *
 *
 *
 *      @SuppressWarnings("unchecked")
 *      List<E> list = (List<E>) tcm.getObject(cache, key);
 *      if (list == null) {
 *      list = delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
 *      tcm.putObject(cache, key, list); // issue #578 and #116
 *      }
 *      return list;
 *      }
 *      }
 * --
 * private void flushCacheIfRequired(MappedStatement ms) {
 *      Cache cache = ms.getCache();
 *      if (cache != null && ms.isFlushCacheRequired()) { // 存在二级缓存, 且开启刷新缓存时, 执行刷新操作
 *          tcm.clear(cache);
 *      }
 * }
 * ------------------------------------------------------------------------------------------------------------------------------
 *                          MyBatis-Spring
 * ------------------------------------------------------------------------------------------------------------------------------
 * {@link org.mybatis.spring.annotation.MapperScan}  // @Import(MapperScannerRegistrar.class)
 * {@link org.mybatis.spring.annotation.MapperScannerRegistrar#registerBeanDefinitions}
 * {@link org.mybatis.spring.annotation.MapperScannerRegistrar#registerBeanDefinitions(org.springframework.core.type.AnnotationMetadata, org.springframework.core.annotation.AnnotationAttributes, org.springframework.beans.factory.support.BeanDefinitionRegistry, java.lang.String)}
 * java-code: 构建 {@link org.mybatis.spring.mapper.MapperScannerConfigurer}, 后续会有后置处理器执行
 *      BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);
 *   {@link org.mybatis.spring.mapper.MapperScannerConfigurer#setBasePackage(String)}
 *   {@link org.mybatis.spring.mapper.MapperScannerConfigurer#postProcessBeanDefinitionRegistry}
 *   {@link org.springframework.context.support.PostProcessorRegistrationDelegate#invokeBeanDefinitionRegistryPostProcessors(java.util.Collection, org.springframework.beans.factory.support.BeanDefinitionRegistry)}
 *   {@link org.mybatis.spring.mapper.ClassPathMapperScanner#doScan(java.lang.String...)
 *      Calls the parent search that will search and register all the candidates.
 *      Then the registered objects are post processed to set them as MapperFactoryBeans}
 *      1. 先执行 spring-IoC 的 {@link org.springframework.context.annotation.ClassPathBeanDefinitionScanner#doScan(java.lang.String...)}
 *      2. 之后再 {@link org.mybatis.spring.mapper.ClassPathMapperScanner#processBeanDefinitions(java.util.Set)}
 *   java-code:
 *      // the mapper interface is the original class of the bean
 *      // but, the actual class of the bean is MapperFactoryBean
 *      definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName); // issue #59
 *      definition.setBeanClass(this.mapperFactoryBeanClass);
 *   {@link org.mybatis.spring.mapper.MapperFactoryBean#setSqlSessionFactory(org.apache.ibatis.session.SqlSessionFactory)}
 *   {@link org.mybatis.spring.SqlSessionTemplate}, {@link org.mybatis.spring.support.SqlSessionDaoSupport}
 *      一个 MapperFactoryBean 生成一个 Mapper, {@link org.mybatis.spring.mapper.MapperFactoryBean#getObject()}
 *       依赖于 SqlSessionTemplate{也即 SqlSession}, SqlSessionTemplate 中的 SqlSession 由动态代理生成
 *       {@link org.mybatis.spring.SqlSessionTemplate#getMapper(java.lang.Class)}
 *
 * ==========================================================================================================================
 * -- mybatis
 * {@link org.apache.ibatis.annotations.Mapper} -- 此注解在 spring-boot 中才有使用(根据 IDEA 中搜索到的引用点)?
 * {@link org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration.AutoConfiguredMapperScannerRegistrar#registerBeanDefinitions(org.springframework.core.type.AnnotationMetadata, org.springframework.beans.factory.support.BeanDefinitionRegistry)}
 * {@link org.apache.ibatis.binding.MapperProxy}
 * {@link org.apache.ibatis.binding.MapperProxyFactory#newInstance(org.apache.ibatis.session.SqlSession)}
 * {@link org.apache.ibatis.binding.MapperRegistry} 注册 mapper 接口, 开始注册的实际上是 MapperProxyFactory 对象,
 *      在 {@link org.apache.ibatis.session.SqlSession#getMapper(java.lang.Class)} 调用时, 才会真正创建 mapper 对象.
 * --- mybatis-spring 一级缓存无事务时执行清空的源码:
 * {@link org.mybatis.spring.SqlSessionTemplate.SqlSessionInterceptor}
 * java-code:
 *      // 无事务则将当前 SqlSession commit
 *      if (!isSqlSessionTransactional(sqlSession, SqlSessionTemplate.this.sqlSessionFactory)) {
 *          // force commit even on non-dirty sessions because some databases require
 *          // a commit/rollback before calling close()
 *          sqlSession.commit(true);
 *      }
 *  ==========================================================================================================
 *                              1st/2nd-cache 清空
 *  ==========================================================================================================
 * mybatis 一级缓存清空条件:
 * {@link org.apache.ibatis.executor.BaseExecutor#clearLocalCache()}  -- 此方法的调用, 就意味着清空一级缓存
 * 1. 更新操作(非查询): 无条件清空
 *    {@link org.apache.ibatis.executor.BaseExecutor#update(org.apache.ibatis.mapping.MappedStatement, java.lang.Object)}
 * 2. 查询, 未开启二级缓存, 且刷新缓存
 *    {@link org.apache.ibatis.executor.BaseExecutor#query}
 *   源码:
 *      {@link org.apache.ibatis.mapping.MappedStatement#isFlushCacheRequired()} -- 此方法返回 true, 则会清空非子查询的一级缓存, 和当前 MappedStatement 存在二级缓存时的二级缓存
 *      if (queryStack == 0 && ms.isFlushCacheRequired()) { // 非子查询, 且开启刷新时, 执行清空一级缓存操作
 *          clearLocalCache();
 *      }
 *      queryStack 记录子查询深度, 子查询时不会执行清空一级缓存操作
 * 另注, 二级缓存清空判断条件: (详见下方关于二级缓存的解析)
 *      {@link org.apache.ibatis.executor.CachingExecutor#flushCacheIfRequired(org.apache.ibatis.mapping.MappedStatement)}
 * 设置 {@link org.apache.ibatis.mapping.MappedStatement#flushCacheRequired} 的点:
 *      2.1. mapper.xml
 *          {@link org.apache.ibatis.builder.xml.XMLStatementBuilder#parseStatementNode()}
 *          <select flushCache="true">
 *          // flushCache = "true" 表示刷新一级缓存, 不设置其值为 "!isSelect"
 *          </select>
 *      源码: boolean flushCache = context.getBooleanAttribute("flushCache", !isSelect);
 *      2.2. annotation
 *          {@link org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parseStatement(java.lang.reflect.Method)}
 *          {@link org.apache.ibatis.annotations.Options#flushCache()}
 * 3. 查询, 且一级缓存作用域为 STATEMENT
 *      {@link org.apache.ibatis.executor.BaseExecutor#query
 *      {@link org.apache.ibatis.session.LocalCacheScope}
 *   源码:
 *      if (configuration.getLocalCacheScope() == LocalCacheScope.STATEMENT) {
 *          // issue #482
 *          clearLocalCache();
 *      }
 *   设置 localCacheScope 的点:
 *    3.1. config.xml
 *      {@link org.apache.ibatis.builder.xml.XMLConfigBuilder#settingsElement(java.util.Properties)}
 *    源码:
 *      configuration.setLocalCacheScope(LocalCacheScope.valueOf(props.getProperty("localCacheScope", "SESSION")));
 *    3.2. annotation 设置 localCacheScope 暂未找到...
 * 4. commit/rollback
 *     {@link org.apache.ibatis.executor.BaseExecutor#commit(boolean)}
 *     {@link org.apache.ibatis.executor.BaseExecutor#rollback(boolean)}
 * 另, {@link org.apache.ibatis.session.defaults.DefaultSqlSession#clearCache()} 也可以说明 SqlSession 作用域的缓存为一级缓存
 * -----------------------------------------------------------------------------------------------------------------------------
 * -- mybatis-spring
 *   MapperScan -> MapperScannerRegistrar -> MapperScannerConfigurer -> ClassPathMapperScanner -> MapperFactoryBean
 * @since 2020/12/15 9:33
 */
// @Configuration // @Import() 引入到主配置类, 可以不需此注解.
@MyMapperScan(value = "org.anonymous.dao.annotation")
// @MapperScan(basePackages = "org.anonymous.dao.annotation")
public class MyBatisConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * 可以完全不依赖 MyBatis-XMLConfig, 直接构建 Configuration 实例.
     * @see org.mybatis.spring.SqlSessionFactoryBean#afterPropertiesSet()
     * @see org.apache.ibatis.type.TypeAliasRegistry#registerAlias(java.lang.Class)
     * @see org.apache.ibatis.type.Alias#value()  如果使用了注解, 则在注入时使用注解的 value 值,
     * 否则使用 {@link Class#getSimpleName()}
     * --
     * 另, 可参照 Spring-boot 实现方式: {@link org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration#sqlSessionFactory(javax.sql.DataSource)}
     */
    @Bean
    public SqlSessionFactory/*Bean*/ sqlSessionFactory() throws Exception {
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

        // 两种返回方式都可以
        // return ssf;
        return ssf.getObject();
    }

}
