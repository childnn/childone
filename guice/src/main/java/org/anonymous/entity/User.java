package org.anonymous.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import lombok.Data;
import org.anonymous.dao.UserMapper;
import org.anonymous.service.FooService;
import org.anonymous.service.impl.FooServiceImpl;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.mybatis.guice.MyBatisModule;
import org.mybatis.guice.datasource.builtin.PooledDataSourceProvider;
import org.mybatis.guice.datasource.helper.JdbcHelper;

import java.util.Properties;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/12/6 15:43
 */
@Data
public class User {

    private int id;

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new MyBatisModule() {
            @Override
            protected void initialize() {
                install(JdbcHelper.HSQLDB_Embedded);

                bindDataSourceProviderType(PooledDataSourceProvider.class);
                bindTransactionFactoryType(JdbcTransactionFactory.class);
                addMapperClass(UserMapper.class);

                Names.bindProperties(binder(), createTestProperties());
                bind(FooService.class).to(FooServiceImpl.class);
            }
            Properties createTestProperties() {
                Properties myBatisProperties = new Properties();
                myBatisProperties.setProperty("mybatis.environment.id", "test");
                myBatisProperties.setProperty("JDBC.schema", "mybatis-guice_TEST");
                myBatisProperties.setProperty("derby.create", "true");
                myBatisProperties.setProperty("JDBC.username", "sa");
                myBatisProperties.setProperty("JDBC.password", "");
                myBatisProperties.setProperty("JDBC.autoCommit", "false");
                return myBatisProperties;
            }
        });

        FooService fooService = injector.getInstance(FooService.class);
        User user = fooService.doSomeBusinessStuff(1);
        System.err.println(user);
    }

}
