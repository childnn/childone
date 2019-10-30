package com.example.cache.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 * <p>
 * 名称	            位置	        描述	                                                示例
 * methodName	    root对象	当前被调用的方法名	                                    #root.methodname
 * method	        root对象	当前被调用的方法	                                    #root.method.name
 * target	        root对象	当前被调用的目标对象实例	                            #root.target
 * targetClass	    root对象	当前被调用的目标对象的类	                            #root.targetClass
 * args	            root对象	当前被调用的方法的参数列表	                            #root.args[0]
 * caches	        root对象	当前方法调用使用的缓存列表	                            #root.caches[0].name
 * Argument Name	执行上下文	当前被调用的方法的参数，                                #artsian.id
 * 如findArtisan(Artisan artisan),可以通过#artsian.id获得参数
 * result	        执行上下文	方法执行后的返回值（仅当方法执行后的判断有效，          #result
 * 如 unless cacheEvict的beforeInvocation=false）
 *
 * @author MiaoOne
 * @since 2020/1/6 10:41
 */
@CacheConfig(cacheNames = {"test"})
@Service
public class CacheDemo {

    //    @Cacheable(value = "test", key = "#p0"/*"#id"*/, condition = "#id > '0'")
    @CachePut(/*value = "test", */key = "#p0"/*"#id"*/, condition = "#id > '0'") // 无论如何, 都会更新缓存并执行方法.
    public List<String> list(String id) {
        System.out.println("方法执行了..." + id);
        return Collections.singletonList(id);
    }

    // 这里 key 必须同类型, 缓存的是字符串, 这里写 int 就无效.
    @CacheEvict(/*value = "test", */key = "'1'", beforeInvocation = true)
    public void evict() {
        System.out.println("清除缓存...");
    }

    // 按条件清除缓存.
    @CacheEvict(cacheNames = {"test"}, condition = "#a0 eq '11'")
    public void evict(String id) {
        System.out.println("条件清除缓存...");
    }

    // 缓存有则从缓存获取(不执行方法), 没有就执行方法.
    // targetClass 前不能加 # ?
    // 可以去掉 #root., 直接写 targetClass  -- Class 对象
    // target  -- 目标类的实例, 直接从 IoC 中获取.
    // method
    @Cacheable(/*value = "test"*/value = "te", key = "#root.methodName + #p0"/*"#root.targetClass + #p0"*//*"target + #p0"*/)
    // 类与方法都设置了 缓存名称, 则以方法为准.
    public String cacheable(String id) {
        System.out.println("获取缓存, 如果没有我就会执行..." + id);
        return id + ": 新的缓存";
    }
}
