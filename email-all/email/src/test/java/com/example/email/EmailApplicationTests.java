package com.example.email;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailApplicationTests {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ApplicationContext ctx;

    @Value("${spring.mail.username}")
    private String sender;

    @Test
    public void contextLoads() {
        MailProperties bean = ctx.getBean(MailProperties.class);
        System.out.println("bean = " + bean);
        String username = bean.getUsername();
        System.out.println("username = " + username);
        System.out.println("sender = " + sender);
        Class<? extends JavaMailSender> klass = javaMailSender.getClass();
        System.out.println("klass = " + klass);
        String[] beanNamesForType = ctx.getBeanNamesForType(JavaMailSender.class);
        System.out.println("Arrays.toString(beanNamesForType) = " + Arrays.toString(beanNamesForType));
    }

    @Test
    public void sendMail() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        System.err.println(sender);
        helper.setFrom(sender);
        helper.setTo("274979673@qq.com");
        helper.setSubject("月色真美");
        helper.setText("月の光が美しい");
        javaMailSender.send(mimeMessage);
    }
}
