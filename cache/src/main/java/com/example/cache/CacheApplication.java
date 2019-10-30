package com.example.cache;

import com.example.cache.service.CacheDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collection;

/**
 * @see org.springframework.cache.annotation.Cacheable 有缓存则获取缓存, 没有则执行方法.
 * @see org.springframework.cache.annotation.CachePut 无论有没有缓存都会执行方法, 并更新缓存.
 * @see org.springframework.cache.annotation.CacheEvict
 */
@EnableCaching
@SpringBootApplication
public class CacheApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(CacheApplication.class, args);
        cache(ctx);
    }

    //
    static void cache(ConfigurableApplicationContext ctx) {
        CacheManager bean1 = ctx.getBean(CacheManager.class);
        System.out.println("bean1 = " + bean1);
        Collection<String> cacheNames = bean1.getCacheNames();
        cacheNames.forEach(System.err::println); // 还没有缓存.

        CacheDemo bean = ctx.getBean(CacheDemo.class);

        bean.list("-1"); // 不缓存, 不满足条件.
        bean.list("-1"); // 不缓存.
        bean.list("11");
        bean.list("11"); // 缓存, 不执行方法.
        bean.list("1");

        cacheNames = bean1.getCacheNames();
        cacheNames.forEach(cacheName -> System.err.println("cacheName: " + cacheName)); // 有缓存了 --- 所有缓存执行完毕才会执行.

//        bean.evict(); //
//        bean.evict("11");

        Cache cache = bean1.getCache("test");
//        System.out.println("cache = " + cache);
        Cache.ValueWrapper valueWrapper = cache.get("11");
//        System.out.println("valueWrapper = " + valueWrapper);
        Object value = valueWrapper.get();
        System.out.println("value = " + value);
        // 如果 com.example.cache.service.CacheDemo.evict 指定 key = '1', 则缓存被清除.
        Object value1 = cache.get("1").get();
        System.out.println("value1 = " + value1);
//        Object o = cache.get("2").get(); // NPE
//        System.out.println("o = " + o);

        bean.cacheable("11");
        bean.cacheable("2");
//        Object o = cache.get("2").get(); // 没有返回值, value 就是 null.
//        System.out.println("o = " + o);

        System.out.println("=============================");

        Cache te = bean1.getCache("te");
//        Object o1 = te.get(/*"2"*/CacheDemo.class + "2").get(); // #root.targetClass
//        System.out.println("o1 = " + o1);

//        Object o = te.get(bean + "2").get(); // #root.target
//        System.out.println("o = " + o);

        Object o = te.get("cacheable" + "2").get();
        System.out.println("o = " + o);

    }

}
