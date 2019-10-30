package com.example.email;

import com.example.email.util.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/8/21 17:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Autowired
    private MailUtil mailUtil;

    private final String emailRegx = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    @Test
    public void simpleMail() {
        String receiver = "274979673@qq.com";
        String subject = "test simple email";
        String context = "hello this is simple mail";

        mailUtil.sendSimpleMail(receiver, subject, context);
    }

    @Test
    public void testEmail() {
        String email = "274@qq.com";
        boolean matches = email.matches(emailRegx);
        System.out.println("matches = " + matches);

    }
}
