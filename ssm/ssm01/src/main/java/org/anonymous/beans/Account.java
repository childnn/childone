package org.anonymous.beans;

/**
 * @author child
 * 2019/4/14 17:40
 */
public class Account {
    private Integer id;
    private String name;
    private Integer money;

    public Account() { //jdbcTemplate: 需要 无参构造 和 set 方法
    }


    public Account(Integer id, String name, Integer money) {
        this.id = id;
        this.name = name;
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
