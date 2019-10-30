package com.example.email.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Set;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/8/21 16:39
 */
@Component //IoC
public class MailUtil {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public MailUtil(@Qualifier("mailSender") JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 发送简单邮件.
     *
     * @param receiver 邮件接受者.
     * @param subject  邮件主题.
     * @param context  邮件内容.
     */
    public void sendSimpleMail(String receiver, String subject, String context) {

        /*Properties props = new Properties();
		props.put("mail.smtp.timeout", 10000);
		props.put("mail.smtp.connectiontimeout", 10000);
		props.put("mail.smtp.writetimeout", 10000);
        props.put("mail.smtp.host", host);
        props.put("mail.debug", "true");
       // Get session
        Session session = Session.getInstance(props);*/
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        System.err.println(sender);
        message.setTo(receiver); // 支持群发: 数组
        message.setSubject(subject);
        message.setText(context);
        JavaMailSenderImpl mailSender = (JavaMailSenderImpl) this.mailSender;
        String host = mailSender.getHost();
        System.out.println("host = " + host);
        int port = mailSender.getPort();
        System.out.println("port = " + port);
        Properties properties = mailSender.getJavaMailProperties();
        System.out.println("properties = " + properties);
        String protocol = mailSender.getProtocol();
        System.out.println("protocol = " + protocol);

        // try {
            this.mailSender.send(message);
            // LOGGER.info("successful mail delivery...");
        // } catch (MailException e) {
        //     LOGGER.error("发送邮件错误..", e);
        // }
    }

    /**
     * 发送 富文本(html) 邮件.
     *
     * @param receiver 邮件接收者.
     * @param subject 主题
     * @param context 内容
     * @see org.springframework.mail.MailSender
     * @see org.springframework.mail.javamail.JavaMailSender
     * @see org.springframework.mail.javamail.JavaMailSenderImpl
     * @see org.springframework.mail.SimpleMailMessage
     * @see org.springframework.mail.javamail.MimeMailMessage
     * @see javax.mail.internet.MimeMessage
     */
    public void sendHtmlMail(String receiver, String subject, String context) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(sender);
            helper.setTo(receiver); // 支持群发: 数组
            helper.setSubject(subject);
            helper.setText(context, true);

            mailSender.send(message);
            LOGGER.info("HTML 邮件发送成功...");
        } catch (MessagingException e) {
            LOGGER.error("HTML 邮件发送失败..", e);
        }
    }

    public void sendInlineResourceMail(String receiver, String subject, String srcPath, String srcId) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(sender);
            helper.setTo(receiver); // 支持群发: 数组
            helper.setSubject(subject);
            // helper.setText(context, true);
            FileSystemResource res = new FileSystemResource(srcPath);
            helper.addInline(srcId, res);

            mailSender.send(message);
            LOGGER.info("静态资源 邮件发送成功...");
        } catch (MessagingException e) {
            LOGGER.error("静态资源 邮件发送失败..", e);
        }
    }

    public static void main(String[] args) {
        Charset utf8 = StandardCharsets.UTF_8;
        String name = utf8.name();
        System.out.println("name = " + name);
        Set<String> set = utf8.aliases();
        System.out.println("set = " + set);

    }

}
