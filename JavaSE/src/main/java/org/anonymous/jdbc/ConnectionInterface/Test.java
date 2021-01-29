package org.anonymous.jdbc.ConnectionInterface;

/**
 * 2019年3月9日10:23:43
 * 1.获取执行 sql 的对象
 * Statement createStatement() 创建一个 Statement 对象来将 SQL 语句发送到数据库。 -- 有安全隐患
 * PreparedStatement prepareStatement(String sql) 创建一个 PreparedStatement 对象来将参数化的 SQL 语句发送到数据库。
 * 2.管理事务:
 * void setAutoCommit(boolean autoCommit) 将此连接的自动提交模式设置为给定状态。
 * 设置参数 false: 禁用自动提交//即开启事务
 * void rollback() 取消在当前事务中进行的所有更改，并释放此 Connection 对象当前持有的所有数据库锁。
 * void commit() 使所有上一次提交/回滚后进行的更改成为持久更改，并释放此 Connection 对象当前持有的所有数据库锁。
 */
public class Test {
}
