package com.example.email.controller;

import com.example.email.service.RedisService;
import com.example.email.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Random;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/8/21 21:37
 * 使用 session 存储 邮箱验证码.
 */
@RestController
@RequestMapping("/mail")
public class MailController {
    private final MailUtil mailUtil;
    private final CacheManager cacheManager;
    private final RedisService redisService;

    @Autowired
    public MailController(@Qualifier("mailUtil") MailUtil mailUtil,
                          @Qualifier("cacheManager") CacheManager cacheManager,
                          @Qualifier("redisService") RedisService redisService) {
        this.mailUtil = mailUtil;
        this.cacheManager = cacheManager;
        this.redisService = redisService;
    }

    // public static void main(String[] args) {
    //     // BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //     // String encode = passwordEncoder.encode("123456");
    //     // System.out.println("encode = " + encode);
    // }

    @GetMapping("/send")
    public String sendEmail(String receiver/*, HttpSession session*/) {
        // String id = session.getId();
        // System.out.println("id = " + id);
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        System.out.println("verifyCode = " + verifyCode);
        // session.setAttribute(receiver, verifyCode);
        // session.setMaxInactiveInterval(60); // 超时时间
        try {
            mailUtil.sendSimpleMail(receiver, "注册验证码", verifyCode);
        } catch (Exception e) {
            return "error..." + e.getMessage();
        }
        return "发送成功...";
    }

    @GetMapping("/verify")
    public String verify(String receiver, String verifyCode, HttpSession session) {
        // String id = session.getId();
        // System.err.println("id = " + id);
        Object code = session.getAttribute(receiver);
        // session.invalidate();
        System.err.println("code = " + code);
        if (verifyCode != null && !verifyCode.isEmpty() && verifyCode.equals(code)) {
            return "okkkkkkkkk";
        }
        return "nonono";
    }

    @GetMapping("/cache")
    public String send(String receiver) {
        String cache = redisService.cache(receiver);
        if ("error..".equals(cache)) {
            return cache;
        }
        return "success";

    }

    @GetMapping("/get")
    public String get(String receiver, String code) {
        Cache email = cacheManager.getCache("email");
        System.out.println("email = " + email);
        Collection<String> cacheNames = cacheManager.getCacheNames();
        cacheNames.forEach(System.out::println);
        if (email != null) {
            // String name = email.getName();
            // System.out.println("name = " + name);
            Cache.ValueWrapper valueWrapper = email.get(receiver);
            System.out.println("valueWrapper = " + valueWrapper);
            // valueWrapper.get()
            if (valueWrapper != null) {
                Object o = valueWrapper.get();
                System.out.println("o = " + o);
            } else {
                return "error..";
            }
        } else {
            return "error...";
        }
        return "okkkkkkkkkkkk..";
    }

    /**
     * @see org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
     * {@code org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration}
     */
    public void redis() {

    }

}
