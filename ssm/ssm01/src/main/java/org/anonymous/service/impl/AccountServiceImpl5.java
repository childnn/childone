package org.anonymous.service.impl;

import org.anonymous.dao.AccountDao;
import org.anonymous.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author child
 * 2019/4/14 12:10
 */
//@Component("accountService") : 通用
@Service("accountService") //： service
//@Controller("accountService") ： web
//@Repository("accountService") ： dao
//注解的 value 不赋值：默认为类名- 首字母小写
public class AccountServiceImpl5 implements AccountService {
    @Autowired //替代 set 方法：找其接口类型 -- 当一个接口有多个实现类时，注入的 变量名 必须 设置为 对应的 实现类 设置的 value 值
    // 注入的 变量名就是 ioc 中 对象 对应的 id
    // 可以加上 @Qualifier("id 唯一值") : 指定自定义 id
    private AccountDao accountDao1; // 想注入接口的哪个实现类,就使用 哪个实现类的 注解 value 作为 变量名

    @Override
    public void save() {
        //调用 dao
        accountDao1.save(); //调用 id="accountDao1"
    }
}
