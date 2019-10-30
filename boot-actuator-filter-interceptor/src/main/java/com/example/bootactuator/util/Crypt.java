package com.example.bootactuator.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/17 9:44
 */
public class Crypt {
    private static final String ROW = "child1";

    public static void main(String[] args) {
        // 默认就是 org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptPasswordEncoder()
        PasswordEncoder bcrypt = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = bcrypt.encode(ROW);
        System.out.println("encode = " + encode); // 带前缀的密码.
        boolean b = bcrypt.matches(ROW, encode);
        System.out.println("b = " + b);
        //
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String child1 = bCryptPasswordEncoder.encode(ROW);
        System.out.println("child1 = " + child1);
        boolean b1 = bCryptPasswordEncoder.matches(ROW, child1);
        System.out.println("b1 = " + b1);

        System.out.println("==============================");

        // org.bouncycastle.crypto.generators.SCrypt 需要导入依赖.
       /* SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder();
        String encode1 = sCryptPasswordEncoder.encode(ROW);
        System.out.println("encode1 = " + encode1);
        boolean matches = sCryptPasswordEncoder.matches(ROW, child1);
        System.out.println("matches = " + matches);*/
    }
}
