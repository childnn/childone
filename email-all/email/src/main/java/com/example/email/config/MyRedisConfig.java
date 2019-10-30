package com.example.email.config;

import com.example.email.pojo.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/8/22 14:34
 */
@Configuration
public class MyRedisConfig {

    /**
     * @param redisConnectionFactory
     * @return
     *
     * @see JdkSerializationRedisSerializer  # redis 默认的 序列化方式: java -- value 对象必须实现 {@link java.io.Serializable} 接口
     * 可以使用其他的序列化方式: json 形式
     */
    @Bean
    public RedisTemplate<Object, User> myRedisTemplate(@Qualifier("redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, User> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<User> serializer = new Jackson2JsonRedisSerializer<>(User.class);
        template.setDefaultSerializer(serializer);
        return template;
    }

    // @Bean
    public RedisCacheManager myRedisCacheManager(@Qualifier("myRedisTemplate") RedisTemplate<Object, User> myRedisTemplate) {
        // RedisCacheConfiguration redisCacheConfiguration = new RedisCacheConfiguration();
        // RedisCacheManager cacheManager = new RedisCacheManager(myRedisTemplate);
        return null;
    }
}
