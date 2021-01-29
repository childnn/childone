package mysql;

/**
 * 2019年3月7日15:24:19
 *  DCL:data control language
 *  查询用户: R
 *      1.切换到 mysql 数据库
 *          use mysql
 *      2.查询 user 表
 *          select * from user;
 *  创建用户: C
 *      CREATE USER '用户名' @ '主机名' IDENTIFIED BY '密码';
 *       若希望在任意电脑上登录本用户, '主机名' 用 '%' 替换
 *  删除用户: D
 *      DROP USER '用户名' @ '主机名';
 *  修改密码: U
 *     UPDATE USER SET PASSWORD = PASSWORD('新密码') WHERE USER = '用户名';
 *     SET PASSWORD FOR '用户名' @ '主机名' = PASSWORD('新密码');
 *
 *   若 root 用户密码忘记
 *      1.cmd --> net stop mysql 停止 mysql 服务
 *      2.启动 mysql 服务: 使用无验证方式
 *          mysqld --skip-grant-tables
 *      3.打开新的 cmd 窗口,直接输入 mysql 命令,回车,可以成功登陆
 *          使用修改密码 sql 语句 : (见上面修改密码)
 *      4.打开任务管理器,手动结束 mysqld.exe 进程
 *      5.启动 mysql 服务,使用新密码登陆
 *
 *  查询指定用户的权限:
 *      SHOW GRANTS FOR '用户名' @ '主机名';
 *  授予权限:
 *      GRANT 权限列表 ON 数据库名.表明 TO '用户名' @ '主机名';
 *          权限类别: SELECT(查询), DELETE, UPDATE...
 *      授予指定用户所有权限: 通配符(all,*)
 *      GRANT ALL ON *.* TO '用户名' @ '主机名';
 * 撤销权限:
 *      REVOKE 权限列表 ON 数据库名.表明 FROM '用户名' @ '主机名';
 *      撤销所有权限:同授权
 */
public class MySQL03 {
    /**
     * MySQL 支持的列类型
     *      tinyint/smallint/mediumint/int(integer)/bigint: 1/2/3/4/8 字节整数, 可分为有符号/无符号. 这些整型区别仅仅是表数范围不同
     *      float/double: 单精度/双精度浮点类型
     *      decimal(dec): 精确小数类型, 相对于 float/double 不会产生精度丢失问题
     *      date: 日期类型, 不能保存时间. 把 java.util.Date 对象保存进 date 列时, 时间部分将会丢失
     *      time: 时间类型, 不能保存日期. 把 java.util.Date 对象保存进 time 列时, 日期部分将丢失
     *      datetime: 日期, 时间类型
     *      timestamp: 时间戳类型
     *      year: 年类型, 仅仅保存时间的年份
     *      char: 定长字符串类型
     *      varchar: 可变长字符串类型
     *      binary: 定长二进制字符串类型, 以二进制形式保存字符串
     *      varbinary: 可变长度的二进制字符串类型, 它以二进制形式保存字符串
     *      tinyblob/blob/mediumblob/longblob: 1/2/3/4 字节的二进制大对象, 可用于存储图片, 音乐等二进制数据, 分别可存储 255B/64KB/16MB/4GB 大小
     *      tinytext/text/mediumtext/longtext: 1/2/3/4 字节的文本对象, 可用于存储超长长度的字符串, 分别可存储: 255B/64KB/16MB/4GB 的文本
     *      enum('value1', 'value2', ...): 枚举类型, 该列的值只能是 enum 后括号里多个值的其中之一
     *      set('value1', 'value2', ...): 集合类型, 该列的值可以是 set 后括号里多个值的其中几个
     *
     */


}
