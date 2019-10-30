package com.example.another;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :)
 *
 * @author MiaoOne
 * @since 2019/10/25 11:32
 */
public class SendEmail {
    public static Properties props = new Properties();
    static {
        try {
            props.load(new FileInputStream("mail.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // props.setProperty("mail.host", )
    }

    private static Session getSession(String sender, String password) {
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        };

        return Session.getDefaultInstance(props, authenticator);
    }

    public static void send(String content, String to) {
        String host = props.getProperty("mail.host");
        String protocol = props.getProperty("mail.protocol");
        String port = props.getProperty("mail.port");
        String encoding = props.getProperty("mail.default-encoding");

    }
}
