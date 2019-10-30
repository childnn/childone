package cn.itheima.mysql;

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
}
