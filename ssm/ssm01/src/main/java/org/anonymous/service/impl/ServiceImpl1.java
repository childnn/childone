package org.anonymous.service.impl;

import org.anonymous.service.AccountService;

import java.util.Arrays;

/**
 * @author child
 * 2019/4/13 22:29
 * 依赖注入: 复杂属性 : 数组/List/Set
 * 方式一: 构造器方式
 */
public class ServiceImpl1 implements AccountService {
    private final String[] arr;

    public ServiceImpl1(String[] arr) {
        this.arr = arr;
    }

    @Override
    public void save() {
        System.out.println(Arrays.toString(arr));
    }
}
