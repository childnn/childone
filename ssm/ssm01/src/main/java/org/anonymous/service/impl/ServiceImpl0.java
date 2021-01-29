package org.anonymous.service.impl;

import org.anonymous.service.AccountService;

/**
 * @author child
 * 2019/4/13 22:15
 * 依赖注入: 方式二 -- bean1.xml
 * set 属性方式:
 * 为了证明 配置文件中 标签属性的设置 与 变量名无关, 而是与方法有关,
 * 这里把变量名 改为与方法名不对应的模式
 */
public class ServiceImpl0 implements AccountService {
    private String aname;
    private Integer aage;

    public void setName(String name) {
        this.aname = name;
    }

    public void setAge(Integer age) {
        this.aage = age;
    }

    @Override
    public void save() {
        System.out.println(aname + ":" + aage);
    }
}
