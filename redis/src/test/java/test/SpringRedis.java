package test;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2022/8/19 21:46
 */
public class SpringRedis {

    /**
     * @see org.springframework.data.redis.connection.jedis.JedisConnectionFactory
     * @see org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
     */
    @Test
    public void test() {
        RedisTemplate<Object, Object> rt = new RedisTemplate<>();

        // lettuce 依赖 netty, bi jedis 性能更好
        // jedis 需要依赖 redis-pool
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(getStandaloneConfig()/*, getLettuceClientConfiguration()*/);
        connectionFactory.afterPropertiesSet();

        rt.setConnectionFactory(connectionFactory);

        Jackson2JsonRedisSerializer<Objects> ser = new Jackson2JsonRedisSerializer<>(Objects.class);
        ObjectMapper om = new ObjectMapper()
                .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
                .enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        ser.setObjectMapper(om);


        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key -- 都是 string
        rt.setKeySerializer(stringRedisSerializer);
        // hash key 都是 string
        rt.setHashKeySerializer(stringRedisSerializer);
        // value 是 string
        rt.setValueSerializer(stringRedisSerializer);
        // hash value 都是 json
        rt.setHashValueSerializer(ser);


        rt.afterPropertiesSet();

        rt.opsForValue().set("key1", "value");
        Object v = rt.opsForValue().get("k");
        System.out.println("v = " + v);
    }

    protected final RedisStandaloneConfiguration getStandaloneConfig() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        config.setHostName("localhost");
        config.setPort(6379);
        // config.setPassword(RedisPassword.of(this.properties.getPassword()));
        config.setDatabase(0);
        return config;
    }

    // private LettuceClientConfiguration getLettuceClientConfiguration(
    //         ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers,
    //         ClientResources clientResources, Pool pool) {
    //     LettuceClientConfigurationBuilder builder = createBuilder(pool);
    //     applyProperties(builder);
    //     if (StringUtils.hasText(getProperties().getUrl())) {
    //         customizeConfigurationFromUrl(builder);
    //     }
    //     builder.clientResources(clientResources);
    //     builderCustomizers.orderedStream().forEach((customizer) -> customizer.customize(builder));
    //     return builder.build();
    // }

}
