package org.anonymous;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/5 13:22
 */
@SpringBootApplication
@EnableAdminServer
public class AdminServer {
    public static void main(String[] args) {
        SpringApplication.run(AdminServer.class, args);
    }
}
