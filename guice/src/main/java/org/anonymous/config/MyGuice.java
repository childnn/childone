package org.anonymous.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import java.util.Properties;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/6 15:36
 */
public class MyGuice {

    private static final Injector INJECTOR;

    static {
        Properties myBatisProperties = new Properties();
        myBatisProperties.setProperty("mybatis.environment.id", "test");
        myBatisProperties.setProperty("JDBC.schema", "mybatis-guice_TEST");
        myBatisProperties.setProperty("derby.create", "true");
        myBatisProperties.setProperty("JDBC.username", "sa");
        myBatisProperties.setProperty("JDBC.password", "");
        myBatisProperties.setProperty("JDBC.autoCommit", "false");

        INJECTOR = Guice.createInjector(
                JdbcHelper.HSQLDB_Embedded,
                binder -> Names.bindProperties(binder, myBatisProperties)
        );
    }

    public static Injector getInject() {
        return INJECTOR;
    }
}
