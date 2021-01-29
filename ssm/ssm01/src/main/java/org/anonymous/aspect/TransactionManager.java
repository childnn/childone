package org.anonymous.aspect;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author child
 * 2019/4/17 16:49
 * 事务管理: 切面类 -- spring 生命式事务的 原理
 * 四大基本通知
 */
public class TransactionManager {

    private DataSource dataSource;

    //开启事务: 前置通知
    public void openTransaction() throws SQLException {
        //开启 事务 同步管理器: 将当前 线程 和 获取的 连接 绑定
        TransactionSynchronizationManager.initSynchronization();
        //获取连接
        Connection connection = DataSourceUtils.getConnection(dataSource); //从当前线程中获取连接

        //开启事务
        connection.setAutoCommit(false);

        System.out.println("前置");
    }

    //提交事务: 后置通知
    public void commitTransaction() throws SQLException {
        Connection connection = DataSourceUtils.getConnection(dataSource); // 同一个 连接
        connection.commit();
        System.out.println("后置");
    }

    //回滚事务: 异常通知
    public void transactionException() throws SQLException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        connection.rollback();
        System.out.println("异常");
    }

    //关闭事务: 最终通知
    public void closeTransaction() throws SQLException {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        connection.setAutoCommit(true);
        TransactionSynchronizationManager.clearSynchronization();
        System.out.println("最终");
    }

    //di
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
