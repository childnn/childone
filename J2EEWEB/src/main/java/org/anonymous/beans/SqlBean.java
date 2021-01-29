package org.anonymous.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author child
 * 2019/4/1 21:53
 */
public class SqlBean {
    private String sql;
    private List<Object> list = new ArrayList<>();

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }
}
