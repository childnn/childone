package com.example.bootactuator.beans;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/4/15 8:45
 */
// @Data // spring-bean wrapper 与 lombok 不兼容
public class Employee {

    private float salary;

    public void setSalary(float salary) { // write
        this.salary = salary;
    }

    public float getSalary() { // read
        return salary;
    }
}
