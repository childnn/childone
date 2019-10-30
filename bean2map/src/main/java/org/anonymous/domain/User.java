package org.anonymous.domain;

import lombok.Data;
import lombok.ToString;

/**
 * @author child
 * 2019/6/24 12:41
 */
@Data
@ToString
public class User {
    private int id;
    private String name;
/*
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }*/
}
