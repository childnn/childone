package org.anonymous.service.impl;

import org.anonymous.service.AccountService;

/**
 * @author child
 * 2019/4/13 20:18
 */
public class AccountServiceImpl0 implements AccountService {

    public AccountServiceImpl0() {
        System.out.println("我是多例对象....");
    }

    @Override
    public void save() {
        System.out.println("proto");
    }

    //初始化方法
    public void ccc() {
        System.out.println("2222222222222多例初始化222222222");
    }

    //销毁方法
    public void ddd() {
        System.out.println("我是多例,我不会执行...");
    }
}
