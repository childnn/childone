package org.anonymous.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

/**
 * @author child
 * 2019/3/29 11:40
 */
public abstract class JdbcUtils {
    private static final Properties properties = new Properties();
    private static DataSource dataSource;

    static {
        try {
            properties.load(Objects.requireNonNull(JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties")));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}
