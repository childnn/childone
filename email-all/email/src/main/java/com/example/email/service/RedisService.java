package com.example.email.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/8/22 15:07
 */
@Service
public class RedisService {

    /**
     * @param receiver
     * @return
     * @see Cacheable  先查缓存, 没有缓存再调方法, 将方法的返回值 存入缓存.
     * @see org.springframework.cache.annotation.CachePut  先调方法, 将目标方法的返回值存入缓存. -- 更新缓存(同 key 更新)
     * 使用 @CachePut 目标方法一定会执行.
     * @see org.springframework.cache.annotation.CacheEvict 清除缓存
     * {@link CacheEvict#allEntries()} 设置为 true, 表示清除所有当前 缓存名称的 缓存.
     * {@link CacheEvict#beforeInvocation()} 缓存是否在方法执行之前清除, 默认方法执行之后(如果方法异常, 不会清除).
     * 如果设置为方法执行之后清除缓存, 则无论方法异常与否, 都会优先清除缓存.
     * @see org.springframework.cache.annotation.Caching # 组合 以上三个注解.
     * @see org.springframework.cache.annotation.CacheConfig # 定义在类上, 指定以上注解的公共配置.
     * @see org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration # 缓存自动配置类
     * {@code org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration.CacheConfigurationImportSelector}
     * 默认生效: {@code org.springframework.boot.autoconfigure.cache.SimpleCacheConfiguration}
     * @see ConcurrentMapCacheManager # 默认缓存管理器
     * {@code org.springframework.cache.concurrent.ConcurrentMapCache#lookup(java.lang.Object)}
     * @see ConcurrentMapCache # 默认缓存对象.
     * <p>
     * 基本思路:
     * 1. 读取注解 {@link Cacheable} 查询 Cache {@link ConcurrentMapCacheManager#getCache(java.lang.String)}
     * 按照 cacheNames 指定的 name 获取. CacheManager 先获取相应的缓存, 第一次获取缓存如果没有 Cache 会自动创建.
     * 2. 去 Cache 查找缓存内容. {@code org.springframework.cache.concurrent.ConcurrentMapCache#lookup(java.lang.Object)}
     * 默认 key 生成类: {@link org.springframework.cache.interceptor.SimpleKeyGenerator}
     * 没有查到缓存, 就查询目标方法(被标注了 @Cacheable 注解的方法),  将目标方法的结果放入缓存中.
     * @see Cacheable#value()
     * @see Cacheable#cacheNames()
     * # 等价, 指定缓存组件的名字. 可以指定多个值.
     * @see Cacheable#key() SPEL 表达式, 缓存数据使用的 key, 默认所有方法参数作为 key
     * key = "#recevier" 等价于 key = "#root.args[0]" 等价于 "#a0", "#p0"
     * @see Cacheable#keyGenerator() key 生成器, 可以指定 key 的生成器组件的 id.
     * key/keyGenerator 二选一
     * @see Cacheable#cacheManager()
     * @see Cacheable#cacheResolver()
     * # 指定缓存管理器/缓存解析器
     * @see Cacheable#condition() 条件满足才会缓存.
     * @see Cacheable#unless() 如果 unless 指定的条件成立(true), 则不缓存.
     * # 支持 SPEL 表达式.
     * @see Cacheable#sync() 是否异步
     */
    @Cacheable(cacheNames = "email"/*, key = "#receiver"*/ /*"#root.methodName + '[' + #receiver + ']'"*/
            // , unless = "#a0 > 1" /*"#result == null"*/  // 除非 表达式不成立, 才会缓存.
            // , condition = "#a0 > 2" // 如果 表达式成立, 会缓存.
            // 自定义 key 生成策略
            /*, keyGenerator = "keyGenerator"*/)
    public String cache(String receiver) {
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println("verifyCode = " + verifyCode);
        // try {
        //     mailUtil.sendSimpleMail(receiver, "注册验证码", verifyCode);
        // } catch (Exception e) {
        //     return "error..";
        // }
        return verifyCode;
    }

}
