package org.anonymous.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author child
 * 2019/4/12 20:06
 */
public class User extends SuperUser implements Serializable {
    private Integer id;
    // private String username; // 继承的属性, 也可以赋值成功!
    private String sex;
    private String birthday;
    private String address;

    private List<Account> accounts;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", accounts=" + accounts +
                '}';
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
