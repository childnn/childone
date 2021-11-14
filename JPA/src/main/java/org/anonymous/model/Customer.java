package org.anonymous.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/30 23:43
 * @see Entity  实体(具备了与表的映射关系), 如果不加 @Table 注解, 则表明就是类名
 * @see Table 表明
 * @see Id 主键, 配合 {@link GeneratedValue}
 * @see GeneratedValue
 * @see javax.persistence.Basic 表示一个简单的属性到数据库表的字段的映射, 对于没有任何标注的属性, 默认等同于 @Basic
 * @see Column 比 @Basic 更具体的设置, 列明, 长度, 约束等, 详见属性
 * @see javax.persistence.Transient 对应的属性无需映射到表字段
 * @see javax.persistence.Temporal 时间类型
 * @see javax.persistence.TableGenerator 当 {@link GeneratedValue#strategy()} 值为 {@link GenerationType#TABLE}
 *      时, 使用此注解关联到生成主键的表
 */
// @Data
@Entity
@Table(name = "Jpa_customer")
public class Customer {

    public static void main(String[] args) {
        Integer i = null;
        System.out.println(i < 0);

    }

    private Integer id;
    private String lastName;
    private String email;
    private int age;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
