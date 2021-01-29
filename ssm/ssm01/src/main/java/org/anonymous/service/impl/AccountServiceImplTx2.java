package org.anonymous.service.impl;

import org.anonymous.dao.AccountDao;
import org.anonymous.service.AccountService;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author child
 * 2019/4/17 15:26
 * 在 转账过程中, 必然要使用事务
 * 原始方式: 硬编码事务
 * 方式二: aop- 切面类 + 四大通知/环绕通知 -- AccountServiceImpl0
 */
public class AccountServiceImplTx2 implements AccountService {
    //di
    private AccountDao accountDao;
    //di 连接池
    private DataSource dataSource;

    @Override
    public void transfer() {
        //开启事务管理器: 就是为了 将获取的 连接 和 当前线程绑定 (ThreadLocal)
        TransactionSynchronizationManager.initSynchronization();
        //获取连接: 从 ioc 中 取
        Connection connection = DataSourceUtils.getConnection(dataSource);
        try {
            //开启事务: 关闭自动提交
            connection.setAutoCommit(false);

            System.out.println("前置");

            accountDao.outMoney();
            int i = 1 / 0; //如果在 此处报错, 数据库数据就会异常
            accountDao.inMoney();

            //没有异常 则提交事务
            connection.commit();

            System.out.println("后置");

            //这里不能 抛 SQLException, 否则 catch 不到 java.lang.ArithmeticException: / by zero,
            //事务 也就不会回滚了!!! 注意
        } catch (Exception e) {
            System.out.println("异常");
            e.printStackTrace();
            try {
                //有异常, 就回滚
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            System.out.println("最终");
            try {
                //将事务改为 自动提交
                connection.setAutoCommit(true);
                //关闭连接
                connection.close();
                //关闭事务管理器
                TransactionSynchronizationManager.clearSynchronization();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //di
    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
