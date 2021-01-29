package org.anonymous.test.datasource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author child
 * 2019/4/14 10:33
 */
public class Demo_spring_druid {
    public static void main(String[] args) throws SQLException {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("bean7.xml");
        DataSource druid = (DataSource) applicationContext.getBean("druid");
        Connection connection = druid.getConnection();
        System.out.println(connection);
    }
}
