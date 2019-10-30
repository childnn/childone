package com.example.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/8/22 11:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheTest {

    @Autowired
    @Qualifier("cacheManager")
    private CacheManager cacheManager;
    
    @Autowired
    private ApplicationContext ctx;

    @Test
    public void cache() {
        String[] beanNames = ctx.getBeanNamesForType(CacheManager.class);
        String s = Arrays.toString(beanNames);
        System.out.println("s = " + s);
    }
}
