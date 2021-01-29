package org.anonymous.beans;

/**
 * @author child
 * 2019/4/9 21:40
 * sql 映射配置文件对象: UserMapper.xml
 * 1. sql 语句
 * 2. 返回类型的全限定名
 */
public class Mapper {
    private String querySql; //查询语句
    private String resultType; //返回值类型: 需要返回的 JavaBean 的全限定名

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }
}
