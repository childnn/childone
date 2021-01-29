package org.anonymous.util;

import org.anonymous.service.AccountService;
import org.anonymous.service.impl.AccountServiceImpl1;

/**
 * @author child
 * 2019/4/13 21:37
 * 实例工厂(非静态工厂方法)： instance factory method
 * 工厂的非静态方法创建对像
 */
public class BeanFactory1 {
    public AccountService getBean() {
        return new AccountServiceImpl1();
    }
}
