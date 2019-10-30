package com.example.email.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/8/22 10:25
 */
@RestController
@RequestMapping("/redis")
public class MailRedisController {

    private final RedisTemplate redisTemplate; // k-v 类型自定义
    private final StringRedisTemplate stringRedisTemplate; // k-v 字符串

    public MailRedisController(@Qualifier("redisTemplate") RedisTemplate redisTemplate,
                               @Qualifier("stringRedisTemplate") StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @GetMapping("/cache")
    public String send(String receiver) {
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println("verifyCode = " + verifyCode);

        stringRedisTemplate.opsForValue().set(receiver, verifyCode, 1L, TimeUnit.MINUTES);

        /*stringRedisTemplate.opsForValue().set(receiver, verifyCode);
        stringRedisTemplate.expire(receiver, 1L, TimeUnit.MINUTES);*/


        return "ookkkkkkkk";
    }

    @GetMapping("/get")
    public String verify(String receiver) {
        return stringRedisTemplate.opsForValue().get(receiver);
    }

}
