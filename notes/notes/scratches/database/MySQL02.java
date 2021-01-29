package mysql;

/**
 * 2019年3月7日09:14:15
 * 多表查询
 * 1.明确要查几张表
 * 2.明确表间关联关系
 * 3.明确其他条件
 * 4.明确显示内容
 *  交叉查询: 查询的是没有意义的数据,应该避免
 *           错误的查询:  SELECT * FROM 表A,表B; --> 得到的是 2 张表的乘积: 笛卡尔积
 *          想要得到多表有关系的数据,必须使用  条件筛选!
 *  内连接查询: 可以查询多表之间有关系的数据
 *       隐式内连接 : SELECT * FROM 表A,表B WHERE 关联条件(从表.外键=主表.主键) and 筛选条件;
 *       显式内连接 : select * from 表A [inner] join 表B on 关联条件 join 表C on 关联条件 where 筛选条件;
 *       隐式/显式查询结果完全一样
 *  外连接查询 : 除了显示多表有关系的数据,也可以显示无关但有意义的信息(比如: 主表的主键没有对应的外键)
 *      左外连接: select * from 表A(主表) left [outer] join 表B(从表) on 关联条件 where 筛选条件;
 *              查询多表有关系数据,以及 A表 所有数据
 *      右外连接: select * from 表A right [outer] join 表B on 关联条件 where 筛选条件;
 *              查询多表有关系数据,以及 B表 所有数据
 *
 *  子查询:
 *      from 后: 数据表
 *      where 后: 过滤条件
 *      把子查询当成过滤条件时,将子查询放在比较运算符的右边, 可增强 SQL 可读性.
 *
 *      1.将一条 sql 语句的查询结果作为另一条 sql 语句的条件
 *          select * from 表明 where(子查询结果)
 *       单列单值:
 *              eg: select * from 表名 字段=(select count(字段) from 表名);
 *       单列多值:
 *              eg: select * from 表名 字段 in (select 字段 from 表名);
 *      2.一条 sql 执行的结果作为一张临时表存在,适用于多列多值
 *        select * from 表A,(select * from 表B where 条件) 别名 where 条件;
 *        eg:
 *          子查询: SELECT * FROM dept,(SELECT * FROM emp WHERE join_date > '2011-1-1') temp WHERE dept.id = temp.dept_id;
 *          内连接: SELECT * FROM emp e, dept d WHERE e.dept_id=d.id AND e.join_date > "2011-1-1";
 *  集合运算:
 *     1. union: select 语句 union select 语句;
 *        eg: select * from table_1
 *            union
 *            select * from table_2;
 *           -- 以上两个表的结果集的数据列必须一一对应
 *     2. minus: select 语句 minus select 语句;
 *        -- mysql 不支持
 *        select stu_id, stu_name from student_table
 *        minus
 *        -- 两个结果集的数据列的数量相等, 数据类型一一对应.
 *        select teach_id, teach_name from teacher_table;
 *      -- mysql 实现 minus
 *        select stu_id, stu_name from student_table
 *        where (stu_id, stu_name)
 *        not in
 *        (select teach_id, teach_name from teacher_table);
 *      3. intersect
 *        select 语句 intersect select 语句;
 *        -- mysql 不支持
 *        select stu_id, stu_name from student_table
 *        intersect
 *        -- 两个结果集的数据列的数量相等, 数据类型一一对应
 *        select teach_id, teach_name from teacher_table;
 *        -- mysql 实现 intersect
 *        select stu_id, stu_name from student_table
 *        join
 *        teacher_table
 *        on (stu_id = teach_id and stu_name = teach_name);
 *        ------------
 *        select stu_id, stu_name from student_table where student_id < 4
 *        intersect
 *        select teach_id, teach_name from teacher_table where teach_name like '李%';
 *        -- mysql 改写上述 sql
 *        select stu_id, stu_name from student_table
 *        join
 *        teacher_table
 *        on (stu_id = teach_id and stu_name = teach_name)
 *        where stu_id < 4 and teach_name like '李%';
 *
 *
 *
 * 事务: 当一个业务需要多条 sql 语句才能完成时,需要用到事物 -- 如:转账业务 (sql1: A 账户减钱 sql2: B 账户加钱)
 *      如果一个包含多个步骤的业务操作被 事务 管理,那么这些操作要么同时成功,要么同时失败.
 *      把 mysql 语句的自动提交 变为 手动提交
 * 事务的四大特征: ACID
 *      1.原子性(atomicity): 事务是不可分割的最小操作单位,要么同时成功,要么同时失败（失败则回滚）
 *      2.一致性(Consistency): 事务操作前后,数据总量不变
 *            事务的执行结果, 必须使数据库从一个一致性状态, 变到另一个一致性状态. 当数据库只包含事务成功提交的结果时, 数据库处于一致性状态.
 *            如果系统运行发生中断, 某个事务尚未完成而被迫终端, 而该未完成的事务对数据库所做的修改已被写入数据库, 此时, 数据库就处于一种不正确
 *            的状态. 比如银行转账, 只修改 A 账户金额而未修改 B 账户金额, 则数据库就处于不一致性状态.
 *            因此, 一致性是通过原子性来保证的.
 *      3.隔离性(Isolation): 多个事务之间.相互独立 -- 多个用户并发访问数据库时，数据库为每个用户开启一个事务，事务和事务之间相互隔离，互不干扰
 *      4.持久性(Durability): 当事务 提交后,数据库会持久化的保持数据 -- 事务一旦被提交，则数据库中数据的改变就是永久性的
 *   数据库的事务由下列语句组成:
 *     1. 一组 DML 语句
 *     2. 一条 DDL 语句
 *     3. 一条 DCL 语句
 *
 *   事务的隔离性:
 *      多个事务之间相互独立. 多个事务操作同一批数据,会引发一些问题,设置不同的隔离级别就可以解决这些问题
 *      存在的问题:
 *          1.脏读: 一个事务,读取到另一个事务中没有提交的数据
 *          2.不可重复读(强调 改操作): 在同一个事务中, 两次读取到的数据不一样 -- 一个事务读取了另一事务提交的数据
 *          3.幻读(虚读: 强调 增删操作): 一个事务操作(DML)数据表中所有记录,另一个事务添加了一条数据,则第一个事务查询不到这条新增数据的修改
 *              事务T1对一个表中所有的行的某个数据项做了从“1”修改为“2”的操作，这时事务T2又对这个表中插入了一行数据项，
 *              而这个数据项的数值还是为“1”并且提交给数据库。而操作事务T1的用户如果再查看刚刚修改的数据，会发现还有一行没有修改，
 *              其实这行是从事务T2中添加的，就好像产生幻觉一样，这就是发生了幻读。
 *       隔离级别:
 *          1. read uncommitted: 读未提交
 *              产生的问题: 脏读, 不可重复读, 幻读
 *          2. read committed : 读已提交 (oracle)
 *              产生的问题: 不可重复读, 幻读
 *          3. repeatable read : 可重复读 (mysql 默认)
 *              产生的问题: 幻读
 *          4. serializable : 串行化
 *              可以解决所有的问题
 *          注意: 隔离级别从小到大安全性越来越高,但是效率越来越低
 *
 *         注意: 隔离级别从小到大安全性越来越高,但是效率越来越低
 *          数据库查询隔离级别:
 *              select @@tx_isolation;
 *          数据库设置隔离级别:
 *              set global transaction isolation level 级别字符串（上述四种）;
 *              set tx_isolation='(上述四种)'
 *
 * MySQL 事务关键字:
 *   begin/start transaction;
 *   ...
 *   commit/rollback;
 *   --
 *   savepoint a;
 *   ...
 *   rollback to a;
 *
 *
 *  开启事务: start transaction; 手动提交数据
 *  回滚事务: rollback; 事务还原并关闭当前事务
 *  提交事务: commit; 提交数据并关闭当前事务
 *  开启事务后 必须 使用 rollback 或 commit 关闭事务,否则后续的语句不会自动提交
 *
 *  jdbc 操作数据库事务的隔离级别
 *      Connection 类的 static 字段： 5 种
 *      ps：隔离级别的设置只对当前事务有效 -- 事务回滚/提交 后 隔离级别即 还原
 *      connection ： setAutoCommit(boolean b) -- 默认 true 自动提交 -- 设置 connection.setAutoCommit(false) 即表示开启事务
 *       如果设置 setAutoCommit(false), 则此后必须 调用 commit()
 *
 *
 *  mySQL 数据库: 默认自动提交  -- 执行一条增删改语句,就自动提交一条
 *    SELECT @@autocommit; -- 1 代表自动提交, 0 代表手动提交
 *    SET @@autocommit = 0; -- 设置为手动提交
 *  oracle 数据库 必须手动提交
 *
 *  表:部门表,员工表
 *  关联:部门.id=员工.部门id
 *  条件:人数
 *  显示: 部门编号,部门名称,部门位置,部门人数
 *  select d.id,d.dname,d.loc,count(e.id) from dept d,emp e where d.id = e.dept_id GROUP BY d.dname;
 */
public class MySQL02 {
}
