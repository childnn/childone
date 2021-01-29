package org.anonymous.service.impl;

import org.anonymous.dao.AccountDao;
import org.anonymous.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author child
 * 2019/4/14 14:07
 * 接口多实现类的 依赖注入:
 * 方式一: @Autowired + 变量名指定的 id
 * 方式二: @Autowired + @Qulifier(value="id 唯一标识")
 */
@Service("accountService6")
public class AccountServiceImpl6 implements AccountService {
    @Autowired //这里变量名改了就会运行报错：变量名 == 变量所属的接口的 实现类的 注解 value (id)
    //变量名就是  ioc 中 对象的 id
    private AccountDao accountDao0; //org.anonymous.daoimpl.AccountDaoImpl0 //要么是 accountDao0,要么是 accountDao1

    @Override
    public void save() {

    }
}
