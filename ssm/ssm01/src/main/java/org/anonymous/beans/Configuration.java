package org.anonymous.beans;

import java.util.Map;

/**
 * @author child
 * 2019/4/9 21:39
 * 核心配置文件对象: SqlMapConfig.xml
 * 以 mysql 数据库为例:
 * 1.驱动: driver -- com.mysql.jdbc.Driver
 * 2.地址: url -- jdbc:mysql://localhost:3306/数据库名?characterEncoding=utf8
 * 3.用户名: (数据库的用户名) username -- root
 * 4.密码: (数据库密码) password -- root
 * <p>
 * 5.Mapper 的 map 集合: sql 映射文件的对象的集合
 * key: namespace.id
 * -- namespace: dao 层接口的 全限定名
 * -- id: namespace 对应接口的 方法名
 * value: Mapper 对象
 */
public class Configuration {
    private String driver;
    private String url;
    private String username;
    private String password;
    private Map<String, Mapper> mappers;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Mapper> getMappers() {
        return mappers;
    }

    public void setMappers(Map<String, Mapper> mappers) {
        this.mappers = mappers;
    }
}
