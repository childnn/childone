package com.example.querydsl.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/16 19:16
 */
// hibernate 动态代理, 可以不加此注解, 运行 com.example.querydsl.service.impl.UserServiceImpl.detail3 方法. 暂不清楚具体机制.
@JsonIgnoreProperties(value = { "hibernateLazyInitializer"})
@Data
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "sex")
    private String sex;


}


