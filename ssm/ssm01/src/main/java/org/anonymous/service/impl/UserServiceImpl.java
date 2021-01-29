package org.anonymous.service.impl;

import org.anonymous.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author child
 * 2019/4/17 14:30
 */
@Service("user") //ioc
public class UserServiceImpl implements UserService {

    public void f() {
        System.out.println("原方法。。。");
    }

    @Override
    public void save() {
        //        int i = 1/0;
        System.out.println("original..");
    }

    @Override
    public void delete() {
        System.out.println("delete...");
    }

    @Override
    public void update() {
        System.out.println("update...");
    }

    @Override
    public void find() {
        System.out.println("find...");
    }
}
