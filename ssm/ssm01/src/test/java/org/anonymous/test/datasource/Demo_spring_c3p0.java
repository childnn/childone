package org.anonymous.test.datasource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author child
 * 2019/4/14 10:26
 * spring 与 c3p0
 */
public class Demo_spring_c3p0 {
    public static void main(String[] args) throws SQLException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean7.xml");
        DataSource c3p0 = (DataSource) applicationContext.getBean("c3p0"); //配置文件中的 id
        Connection connection = c3p0.getConnection();
        System.out.println(connection);
    }
}
