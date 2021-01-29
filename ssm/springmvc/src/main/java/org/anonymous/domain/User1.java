package org.anonymous.domain;

/**
 * @author child
 * 2019/4/20 11:45
 */
public class User1 {
    private Integer id;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    //必须要 get: 对象 -> jason 数据
    public Integer getId() {
        return id;
    }

    //页面的数据封装对象: 必须要有 set 方法
    public void setId(Integer id) {
        this.id = id;
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
}
