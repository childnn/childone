package com.example.bootweb.mail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/25 18:45
 */
public class MailUtilPlus {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailUtilPlus.class);

    /**
     * @param receiver 收件人邮箱
     * @param subject  邮件主题
     * @param content  邮件正文
     */
    public static boolean sendMail(String receiver, String subject, String content, String from, String username, String pwd) {

        // feiyu13522190985@163.com
        //
        try {
            // Create the email message
            HtmlEmail email = new HtmlEmail();
            // Session session = Session.getDefaultInstance(PropertyUtil.getProperties(),
            //         new DefaultAuthenticator(PropertyUtil.getProperty("mail.smtp.user"),
            //                 PropertyUtil.getProperty("mail.smtp.password")));
            // email.setMailSession(session);
            email.setCharset(StandardCharsets.UTF_8.name());
            email.setFrom(from); // 179090436
            String hostName = null;
            int port = 0;
            if (from.contains("qq")) {
                hostName = "smtp.qq.com";
                port = 465;
            } else if (from.contains("163")) {
                hostName = "smtp.163.com";
                port = 994;
            }
            email.setHostName(hostName);
            email.setSslSmtpPort(String.valueOf(port)); // 994  -- SSL 端口.  // 非 SSL 协议: 25 端口.
//            email.setSmtpPort(); // 非 SSL 协议端口.
            email.setDebug(true);
//            email.setAuthentication("179090436@qq.com", "nmamtfyunw8279"); // nmamtfyunw8279 // srbmefytrszddhai
            email.setAuthentication(username, pwd); // nmamtfyunw8279 // srbmefytrszddhai
            //email.setTLS(true);		// 是否TLS校验..
//            email.setSSL(true);
            email.addTo(receiver);
            email.setSubject(subject);
            email.setMsg(content);
            email.setSSLOnConnect(true);

            //email.attach(attachment);	// add the attachment
            email.send();                // send the email
            return true;
        } catch (EmailException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }
}
