package com.example.bootweb.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2019/11/5 10:51
 */
@Data
@Accessors(chain = true, fluent = true)
public class Student implements Serializable {

    private static final long serialVersionUID = 7287502999930983398L;

    private String name;

    private String sex;

    private int id;
}
