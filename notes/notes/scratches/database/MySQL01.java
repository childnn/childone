package cn.itheima.mysql;

/**
 * 2019年3月5日11:16:52
 * show variables like 'char%'; 查看当前数据库的相关编码集。
 * set names gbk; 设置当前数据库的相关编码集。
 * 数据的存储: 个体(事物)的存储,个体之间的关系的存储
 * 1. 数据库是如何存储数据的
 *      字段  记录  表  约束(主键 外键 唯一键 非空 check default 触发器)
 *      主键: 一个表中个元素之间相互区别的 key (通常是 编号) : 唯一的标识一个事物
 *      外键: 连接两个表的数据: 一个表的外键通常是另一个表的主键. 表明具有该外键的事物来自哪个表的主键
 *      字段: 列,属性,field : 一个字段是一个事物的一种静态特征(各个字段即各种不同特征)
 *      记录: 行, 元组 : 字段的组合, 表示的是一个具体的事物 (表中一个元素/个体的各种属性/字段集)
 *      表: 记录 的组合 同一类型事物的集合
 *
 *      约束:
 *          对一个表的属性操作的限制叫做约束
 *          分类:
 *          主键约束: 不允许重复元素,避免了数据冗余
 *          外键约束: 通过外键约束从语法上保证了本事物所关联的其他事物一定是存在的
 *                   事物之间的联系通过 外键 来体现
 *          CHECK约束: 保证事物属性的取值在合法范围内
 *              格式: 字段名 数据类型 check(字段名>=100 and 字段名<=200)
 *          default约束:
 *              字段名 字段类型 default (默认值) -- 括号可以省略
 *          NOT NULL: 非空
 *          UNIQUE: 唯一, mysql 数据库对 NULL 无效, 即 设置属性的唯一约束后,该属性可以有多个 Null
 *
 * 案例:
 * 员工(表)                                    (主键)     (外键.相当于java中的引用/指针:指向另一张表)
 * (字段1)    (字段2)    (字段3)...
 * 员工姓名   员工工资    员工性别    员工年龄    员工编号    部门编号
 * 张三        1000       男         20         10000       1        (一条记录)
 * 李四        2000       男         23         10001       1        (一条记录)
 * 王五        3000       男         30         10002       1        (一条记录)
 * 小娟        2300       女         18         10003       2        (一条记录)
 * <p>
 * 部门(表)                          (主键)
 * 部门名       部门人数   部门地址   部门编号
 * 外研部        1000      西安        1
 * 市场部        200       北京        2
 *
 * 2. 数据库是如何操作数据的
 *      insert update delete T-SQL 存储过程 函数 触发器
 * 3. 数据库是如何显示数据的
 *      select (重点中的重点)
 *
 * 关系型数据库: //java程序语言是面向对象, 数据库语言是关系/非关系. 这是不同的思想
 *          表和表之间存在关联(不是独立的), 通过  外键 相连
 *
 * SQL : structured query language 结构化查询语言
 *        mySQL 数据库 的 SQL 语句不区分大小写, 关键字建议大写
 * 3 种注释:
 *      单行注释: -- 注释 或 #注释
 *      多行注释: 同 java
 *
 * 开启 mysql 服务:
 *      方法一.手动: 我的电脑 --> 管理 --> 服务和应用程序 --> 服务 --> mysql
 *      方法二: cmd -->命令 services.msc 打开 "管理" 窗口
 *      方法三: cmd -->命令
 *              net start mysql : 启动 mysql 服务
 *              net stop mysql : 关闭 mysql 服务
 * 登录 mysql :
 *      cmd 命令:
 *          方法一: mysql -u用户名 -p密码
 *          方法二: mysql -h访问服务器的ip -u用户名 -p密码
 *          方法三: mysql --host=ip --user=用户名 --password=密码
 * 退出 mysql:
 *      cmd 命令:
 *          exit 或 \q
 *          quit 或 \q
 * 备份与还原 mysql 中的数据库:
 *      备份 cmd 命令: mysqldump -u用户名 -p密码 数据库名 > 保存路径
 *      还原 cmd 命令: 登录mysql-->创建数据库-->使用数据库-->执行文件
 * sql 语句分类:
 *      DDL: data definition language 数据定义语言 ---增删改查 数据库/表
 *          用来定义数据库对象: 数据库,表,列等   //关键字 : create, drop, alter, show， desc(表)
 *  (重点)   DML: data manipulation language 数据操作语言 ---增删改 表中的数据
 *          用来对数据库中表的数据进行增删改  //关键字: insert, delete, update
 * (重点中的重点)  DQL: data query language 数据查询语言 (关键) ---查 表中的数据
 *          用来查询数据库中表的 记录(行/元组)(数据)  //关键字: select, where
 *      DCL: data control language 数据控制语言 -- 管理用户,授权
 *          用来定义数据库的访问权限,安全级别,创建用户  //关键字: GRANT, REVOKE
 *
 * 操作【数据库】: CRUD
 *      C: Create
 *          CREATE DATABASE 数据库名称; : 创建指定名称 数据库(如果以存在同名数据库,会报错)
 *          CREATE DATABASE 数据库名称 CHARACTER SET GBK/UTF8
 *          CREATE DATABASE IF NOT EXISTS 数据库名称; : 如果指定数据库名的数据库不存在则创建数据库
 *          CREATE DATABASE IF NOT EXISTS 数据库名称 CHARACTER SET GBK/UTF8; : 创建指定名称数据库并 指定字符集格式
 *      R: Retrieve
 *          SHOW DATABASES; : 查看所有数据库目录
 *          SHOW CREATE DATABASE 数据库名称; : 查看"数据库名称"指定数据库的 字符集
 *      U: Update
 *          ALTER DATABASE 数据库名 CHARACTER SET GBK/UTF8; : 修改指定数据库的字符集编码格式
 *      D: Delete
 *          DROP DATABASE 数据库名; : 删除指定数据库 数据库不存在则 error
 *          DROP DATABASE IF EXISTS 数据库名; : 如果数据库存在则删除 数据库不存在也不error
 * 使用数据库:
 *      查询当前正在使用的数据库名称:
 *          SELECT DATABASE(); 如果当前没有进入任何数据库 返回 NULL
 *      进入指定数据库:
 *          USE 数据库名;
 *
 * 操作【表】: （重点**********************重点）
 *      对表的 字段(列) 的操作,依附于表
 *      C: CREATE
 *          CREATE TABLE 表名称(
 *              列名1(字段名)  数据类型1,
 *              列名2         数据类型2,
 *              列名n         数据类型n
 *          );
 *          (字段之间加逗号, 最后一列末尾不要加逗号)
 *          一个列,就是一个字段(field)
 *          数据类型:
 *              int : 整型类型
 *              double : 小数
 *                     double(5, 2): 长度为5 保留2位小数
 *              date: 年月日 yyyy-MM-dd
 *              datetime: 年与日时分秒 yyyy-MM-dd HH-mm-ss --- 若不赋值则为 NULL    8 字节
 *              timestamp: 时间戳类型 年月日时分秒 ----- 若不给这个数据类型对应的字段赋值,则默认为系统当前时间。 4 字节 可以表示 1970-2038
 *              varchar: 字符串 --- 数据库中没有字符的概念,都是字符串
 *              char: 字符串 --- 也是字符串
 *                  varchar(长度) : 可变长度 --- 长度范围内,用多少算多少 (节省资源)
 *                  char(长度) : 固定长度 --- 长度范围内,不管写多少长度的字符串,占用的长度都为 固定长度
 *         复制表:
 *          CREATE TABLE 表名称 LIKE 被复制的表明
 *      R: RETRIEVE (找回,恢复)
 *          查询所在数据库中所有表的目录:
 *              SHOW TABLES;
 *          查询表结构: (字段 数据类型 约束 主键 外键)
 *              DESC 表明称;
 *          查看表的字符集:(与查看数据库的字符集类型相同)
 *              SHOW CREATE TABLE 表名称;
 *      U: UPDATE
 *          修改表明
 *              ALTER TABLE 表明 RENAME TO 新表明;
 *          修改表字符集 (了解)
 *              ALTER TABLE 表明 CHARACTER SET 字符集名称(gbk/utf8)
 *          添加列 (字段) (重点******重点)
 *              ALTER TABLE 表明 ADD 列名 数据类型
 *          修改列名称 类型
 *              ALTER TABLE 表明 CHANGE 列名 新列名 新数据类型;
 *              ALTER TABLE 表明 MODIFY 列名 新数据类型;  ---> 这也是 删除 NOT NULL 约束的方法  -- 见 273 行
 *          删除列 (删除字段):
 *              ALTER TABLE 表明 DROP 列名;
 *              删除到最后一列时,不能使用该sql语句:  (You can't delete all columns with ALTER TABLE; use DROP TABLE instead)
 *              只能直接删除表(删除最后一个字段,就是删除表)
 *          大表ALTER TABLE非常耗时，MySQL执行大部分修改表结果操作的方法是用新的结构创建一个张空表，从旧表中查出所有的数据插入新表，然后再删除旧表。
 *
 *      D: DELETE
 *          DROP TABLE 表名称; 有则删,没有则 error
 *          DROP TABLE IF EXISTS 表名称; 有则删,没有无 error
 *
 * 操作表中数据: (记录) (重点*******************重点)
 *   DML : 增删改 表中数据 data manipulation language
 *      C: CREATE
 *          INSERT INTO 表明(列一, 列二..) values(值一, 值二), (值一, 值二)...; 可以给全部字段赋值,也可以选择部分
 *          INSERT INTO 表明 values(值一, 值二); //给所有列(字段)赋值,且一一对应
 *          除了数字类型,其他类型需要引号(单引号双引号都可)
 *      U: UPDATE and set
 *          UPDATE 表明 SET 列名1 = 值1, 列名2 = 值2  WHERE 条件;
 *          如果不加任何where条件,则会改变表中 指定列的所有值
 *      D: DELETE (删除记录,行)  **************************************重点************************
 *          DELETE FROM 表明 WHERE 条件;
 *                如果不加 WHERE ,则删除所有记录(有多少条记录就执行删除语句多少次,不推荐使用,效率低)
 *                delete 删除所有数据后, 再次添加数据的主键索引 会从上次删除的最大值开始算(区别于 truncate)
 *          TRUNCATE TABLE 表明; ---删除表,并创建一个空表
 *           两个删除的区别:
 *                  DELETE FROM 表明称 [where 条件]:
 *                          删除的数据可以找回 ----> 安全性高
 *                          删除数据,主键的索引不会跟随删除的元素变小(保持在删除之前表的最大索引)
 *                          可以加 where: 删除指定条件的 记录 //不加条件就把数据库中 记录 逐条删除
 *                  TRUNCATE TABLE 表明
 *                          删除不可找回 ---> 效率高
 *                          删除数据,索引回到原点
 *                          不可以加 where: 只能删除整张表
 *              共同点:
 *                    都不会删除 字段 信息,只会删除 记录
 *
 *   DQL : 查询表中数据 (重点中的重点*******************************!!!!) data query language
 *      SELECT * FROM 表明; (* 表示所有字段名) ---> 显示指定表的完整信息
 *       若只需要显示一部分信息,则 将※换成 对应的字段名,字段之间用逗号分隔
 *      多个
 *          SELECT
 *              字段1,
 *             字段2
 *          FROM
 *              表明1,
 *              表明2
 *          WHERE
 *              条件列表
 *          GROUP BY (后面只能接 having)
 *              分组条件
 *          HAVING
 *              分组之后的条件
 *          ORDER BY
 *              排序字段1 ASC,排序字段2 DESC
 *              ASC: 升序  (不写排序方式默认升序)
 *              DESC:降序
 *              如果有多个排序条件,只有当前面的条件值相同时,才会使用后面的排序规则
 *          LIMIT
 *              分页限定 : 每页开始索引 = (当前页 - 1) * 每页显示条数
 *   基础查询:
 *      1.多个字段
 *      2.去重:
 *          SELECT DISTINCT 字段名(列名)1, 字段2 From 表明: 去除 同字段 FILED 的重复值 VALUE  并 显示剩余的值
 *      3.计算多个列的数学运算值(和差积商)
 *          SELECT 字段1, 字段2, 字段1+字段2 FROM 表明; 求两个字段值的和
 *          有null 参与的运算,结果都为 null : ifnull(字段名, 0) 将 null 换为 0
 *          SELECT 字段1, 字段2, 字段1 + IFNULL(字段2, 0) FROM 表明; 如果未赋值(默认 null),将值替换为 0
 *      4.起别名: (可以给表和列起别名)
 *          AS : 可以省略 :
 *          字段别名:
 *                  SELECT 字段1 别名1, 字段2 别名2... FROM 表明;
 *          多表查询: 别名配合指定字段查询 不同表之间同字段数据
 *              SELECT * FROM 表1 别名1, 表2 别名2 WHERE 别名1.字段 = 别名2.字段
 *   条件查询:
 *       WHERE 配合运算符
 *       运算符:
 *             >, <, >=, <=, =, <>(不等于), !=(不等于)
 *             并且: AND,   &&,   BETWEEN...AND
 *             或: OR,  ||
 *             非: NOT,  !
 *             指定数值范围: IN(值1, 值2...)  等价于: 值1 OR 值2 OR..
 *          注意:
 *              NULL 不能使用普通数学运算符判断 (IS / IS NOT)
 *              必须使用 WHERE 字段1 IS NULL; 或者 IS NOT NULL; 的格式
 *   模糊查询:
 *        LIKE:
 *          语法: where 字段 like '%.. _'
 *            占位符: 下划线 _  表示任意单个字符
 *                    百分号 %  表示任意多个字符(0 或 多个)
 *
 *  DQL查询语句: DATA QUERY LANGUAGE ---------------- 查询语句的详细介绍
 *      排序查询:
 *          oder by 字段1 排序方式1,字段2 排序方式2
 *          排序方式:
 *                  ASC: 升序
 *                  DESC: 降序
 *           当且仅当 前面字段的值相同时,才会使用后面的排序方式
 *      聚合函数
 *          函数:
 *              count(字段) max(字段) min(字段) avg(字段) sum(字段)
 *                例: SELECT COUNT(IFNULL(字段,0)) FROM 表名  //排除 NULL 值
 *             在聚合函数后面可以起别名
 *      分组查询
 *          GROUP BY 字段
 *          分组之后的查询字段,只能是聚合函数
 *           例: SELECT 分组字段,聚合函数(字段1),聚合函数(字段2) FROM 表明 GROUP BY 分组字段;
 *           where 和 having 的区别:
 *              where 在分组之前限定,如果不满足条件,则不参与分组.
 *                  having 在分组之后进行限定,如果不满足结果,则不会被查询
 *              where 后不可以跟聚合函数,
 *                  having 后可跟聚合函数的判断
 *      分页查询
 *          LIMIT 开始的索引,一页显示的记录条数
 *          开始索引 = (当前页-1) * 每页显示的记录条数
 *          (limit 语法只适用于 mysql)
 *
 * 约束: constraint
 *      对表中数据进行限定,保证数据的正确性、有效性、完整性
 *      在数据类型后添加约束
 *      分类：
 *          主键： PRIMARY KEY
 *          非空： NOT NULL
 *          唯一： UNIQUE
 *          外键： FOREIGN KEY
 *    1.NOT NULL
 *       1.1 在创建表时添加约束:
 *          create table 表明(
 *                字段名 数据类型,
 *                字段名 数据类型 约束(NOT NULL)
 *           );
 *       1.2 创建表之后,添加约束:
 *           ALTER TABLE 表明 MODIFY 字段名 数据类型 NOT NULL;
 *       1.3 删除字段的非空约束:
 *           ALTER TABLE 表明 MODIFY 字段名 数据类型; (相当于修改字段的数据类型) --- 见141行
 *    2.UNIQUE (对 null 值无效)
 *       2.1 可以有 NULL 值: 可以出现多个 null --- mysql (不同数据库有差异,有的只允许一个 null)
 *       2.2 在创建表时添加约束:
 *           create table 表明(
 *               字段名 数据类型,
 *               字段名 数据类型 约束(UNIQUE)
 *           );
 *       2.3 在创建表之后,添加约束
 *          ALTER TABLE 表明 MODIFY 字段名 数据类型 UNIQUE;
 *       2.4 删除字段的唯一约束:(区别于 NOT NULL)
 *          ALTER TABLE 表明 DROP INDEX 字段名; (索引)
 *    3.PRIMARY KEY : 一般不要使用业务逻辑当主键,应该单独创建一个主键字段
 *          非空且唯一 //一个记录区别于其他记录的唯一标识
 *          一张表有且只能有一个主键:
 *                  一个字段可以作为一个主键: 该字段不能null不能重复
 *                  注意: 多个字段可以联合为一个主键: 要求多个字段的值联合起来不能为 null 不能重复
 *                  格式: PRIMARY KEY(字段1,字段2...)
 *                       注意:不是数学上的加起来,是(字段1-字段2..),类似键值对的表示,只不过这里是 多个字段联合为一个键
 *
 *          3.1 创建表时添加主键约束
 *              create table 表明(
 *                  字段名 数据类型 primary key [auto-increment],
 *                  字段名 数据类型
 *              );
 *          3.2 创建表后添加主键
 *              alter table 表明 modify 字段名 数据类型 primary key;
 *          3.3 删除主键
 *              alter table 表明 DROP PRIMARY KEY;
 *          3.4 自动增长:(可以认为 auto-increment 是一种数据类型,所以可以通过修改字段数据类型的方式 删除自动增长)
 *              mysql中, auto_increment 只能修饰主键???
 *              cmd 提示:  there can be only one auto column and it must be defined as a key
 *              如果某一字段的是数字类型的,使用 auto_increment 可以来完成值的自动增长
 *              通常配合 primary key 使用,以保证 primary key 的自动增长并 保证唯一
 *          3.5 添加自动增长
 *              1) 在创建表时在 primary key 后 直接加 auto_increment;
 *              2) 在创建表后添加: alter table 表明 modify 字段名 数据类型 auto-increment;
 *              当主键对应的字段设置了 auto-increment 时, 可以 在:
 *                  insert into 表明(字段1,字段2) values(值1,值2),(值1,值2); 给主键的字段赋值时 直接写 NULL,其值会自动根据当前表的最大主键值 加一
 *          3.6 删除自动增长:
 *              alter table 表明 modify 字段名 数据类型;
 *              因为这种方法无法删除主键,所以可以用来删除自动增长
 *          当主键和自增同时存在时,想要删除主键,必须先删除自增
 *
 *    UNIQUE 和 PRIMARY KEY 的区别: (重点!)
 *          一般主键是没有实际含义整型数字
 *          唯一可以是空,且空可以不唯一
 *          主键必须非空
 *        相同点: 二者都要求唯一
 *
 *    4.FOREIGN KEY : 用来维护 表之间的关系. 一个表的外键一般是另一个表的主键
 *          4.1 在创建表时,添加外键
 *              create table 表明(
 *                  外键字段 数据类型,
 *                  constraint 外键别名 foreign key(外键字段名) references 主表名(主表主键字段名)
 *              );
 *              constraint 外键别名 就是用来删除外键的
 *         4.2 创建表之后,添加外键 -- 外键别名必须要,否则 无法删除
 *              alter table 外键表明 add constraint 外键别名 foreign key (外键字段名) references 主表名(主表主键字段名);
 *         4.3 删除外键
 *              alter table 外键表明 drop foreign key 外键别名;
 *     级联更新/删除:(少用)
 *          主表主键更改,引起从表外键更改
 *          更新和删除可以同时使用,也可分开使用
 *            ON UPDATE CASCADE
 *            ON DELETE CASCADE
 *
 *    表间的关系:
 *         1. 一对多的关系: (主表与从表)
 *                  部门表和员工表: 一个部门有多个员工,一个员工只属于一个部门
 *         2. 多对多的关系:
 *                  学生表和课程表: 一个学生可以选多个课程,一个课程可以被多个学生选
 *                  此时需要一个中间表: (实际上就是创建了两个一对多的关系)
 *                      这张表至少有两个字段,这两个字段分别指向 两张主要表的主键----即中间表的两个外键
 *                      且这两个字段都不能是中间表的主键(这两个字段都可能有重复数据)
 *        student table            中间表                   course table
 *        主键1                    外键1   外键2           主键2
 *        sid     sname           stu    cour             cid     cname
 *         1      小明              1      1               1       java
 *         2      小李              1      2               2        c
 *         3      小娟              2      2               3       python
 *                                 3       3
 *                                 3       1
 *        3. 一对一 : (了解)
 *          身份证和人: 完全可以使用一张表,
 *          一对一关系的出现,一般是因为 数据表的前期设计不完美,后期添加的一对一
 *           3.1 方式一 让任意表的主键 作为 另一张表的 外键
 *           3.2 方式二 在任意一方创建一个字段,作为该表的外键(此外键必须 unique 约束),指向另一张表的主键
 *
 *    数据库范式:
 *          设计数据库时,需要遵循的规范
 *          设计 关系数据库 时,遵循不同的规范要求,设计出合理的关系型数据库,这些不同的规范要求 被称为不同的范式,各种范式呈递次规范
 *              越高的范式数据库冗余越小
 *          关系数据库的六种范式:
 *              1NF: 基本要求,每一个字段下对应的记录只能有一个数据
 *              2NF: 在 1NF 的基础上, 一张表只能描述一个事物,且一张表必须有一个主键(其他字段必须依赖主键)
 *                    函数依赖:
 *                          A --> B, 如果通过属性 A 的值,可以唯一确定 属性 B 的值,则称 B 依赖于 A (如: 学号 --> 学生姓名)
 *              3NF: 在 2NF 的基础上, 一张表的任何非主键属性不依赖于其他非主键属性
 *                   从表的外键必须是主表的主键
 *              BCNF:巴斯-科德范式.
 *              4NF:
 *              5NF: 完美范式
 *
 */
public class MySQL01 {
    /*
        引导学生思考
    * DQL: data query language
    *  查询的核心在于顺序! -- 每个子语句的执行顺序
    *
    *
    * */
}
