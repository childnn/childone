package org.anonymous.service.impl;

import org.anonymous.service.AccountService;

/**
 * @author child
 * 2019/4/13 21:55
 * 依赖注入: 方式一
 * 构造器方式 -- bean.xml
 */
public class AccountServiceImpl2 implements AccountService {
    /**
     * 依赖注入(DI): 对存在依赖关系的属性自动赋值
     * 属性:
     * 简单属性(int, integer string ...)
     * 复杂属性(array, list, map)
     * 对象属性
     * 注入:
     * 构造器方式, 必须要有有参构造
     * set 属性方式, 必须要有构造方法
     */
    private final String name;
    private final Integer age;

    public AccountServiceImpl2(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void save() {
        System.out.println(name + ":" + age);
    }
}
