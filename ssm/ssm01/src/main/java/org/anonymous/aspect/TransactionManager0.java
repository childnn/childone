package org.anonymous.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author child
 * 2019/4/17 16:57
 * 环绕通知
 */
public class TransactionManager0 {
    private DataSource dataSource;

    //环绕
    public Object round(ProceedingJoinPoint pj) {
        //开启 事务 同步管理器: 将当前 线程 和 获取的 连接 绑定
        TransactionSynchronizationManager.initSynchronization();
        //获取连接
        Connection connection = DataSourceUtils.getConnection(dataSource); //从当前线程中获取连接
        try {
            /*前置通知*/

            //开启事务
            connection.setAutoCommit(false);
            System.out.println("前置");

            pj.proceed(); //invoke

            /*后置通知*/
            connection.commit();

            System.out.println("后置");

        } catch (Throwable throwable) {
            //异常通知
            System.out.println("异常");
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            throwable.printStackTrace();

        } finally {
            //最终通知
            System.out.println("最终");
            try {
                connection.setAutoCommit(true);
                connection.close();
                TransactionSynchronizationManager.clearSynchronization();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
