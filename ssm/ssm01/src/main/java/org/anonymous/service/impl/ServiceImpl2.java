package org.anonymous.service.impl;

import org.anonymous.service.AccountService;

import java.util.Arrays;

/**
 * @author child
 * 2019/4/13 22:37
 * 依赖注入: 复杂属性
 * 方式二: set 属性方式
 */
public class ServiceImpl2 implements AccountService {
    private String[] arr;

    public void setArr(String[] arr) {
        this.arr = arr;
    }

    @Override
    public void save() {
        System.out.println(Arrays.toString(arr));
    }
}
