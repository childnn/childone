package org.anonymous.config.mybatis;

import org.apache.ibatis.mapping.VendorDatabaseIdProvider;

import javax.sql.DataSource;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/4 11:32
 */
public class OracleDatabaseIdProvider extends VendorDatabaseIdProvider {

    @Override
    public String getDatabaseId(DataSource dataSource) {
        return "Oracle";
    }

}
