package org.oracle;

/**
 * @author child
 * 2019/4/30 9:18
 *
 * 端口： 1521
 * 数据库： orcl
 * oracle : 管理表的基本单位是 用户 -- mysql 管理表的基本单位是 数据库
 *  数据库(database) > 实例(database instance) > [表空间(tablespace) >] 用户(user) > 表(table) -- 一个数据库下可以有多个数据库实例下,有 n 个表空间, 一个表空间下有 n 个用户, 一个用户下有 n 张表
 *  数据文件： dbf， ora
 *   数据库:
 *      oracle 数据库是数据的物理存储. 包括(数据文件 ORA 或者 DBF,控制文件,联机日志,参数文件).
 *      其实 oracle 数据库的概念和其他数据库不一样,这里的数据库是一个操作系统只有一个库, 可以认为 oracle 就只有一个大的数据库.
 *      一个数据库可以有 n 个实例(oracle instance)
 *  1.数据库实例:
 *      实例是访问 oracle 数据库所需的一部分计算机和辅助处理后台进程,是由进程(background processes)
 *      和这些进程所使用的内存(memory structures)所构成.
 *      其实就是用来访问和使用数据库的一块进程,它只存在于 内存中, 就像 Java 中 new 出来的实例对象一样
 *      我们访问 Oracle 都是访问一个实例,但这个实例如果关联了数据库文件,就是可以访问的,如果没有,就会得到实例不可用的错误
 *      实例名 指的是用于响应某个数据库操作的数据库管理系统的名称, 称为 SID. 实例名由参数 instance_name 决定
 *      查询当前数据库实例名:
 *          select instance_name from v$instance;
 *      数据库实例名(instance_name) 用于对外部连接. 在操作系统中要取得与数据库的联系,必须使用数据库实例名
 *      比如使用 java 代码连接 oracle, 写的 url = "jdbc:oracle:thin:@localhost:1521:orcl"
 *      其中 orcl 就是数据库实例名
 *      一个数据库可以有多个实例,在作数据库服务集群时可以用到.
 *   2.表空间
 *      oracle 数据库是通过表空间来存储物理表的. 一个数据库实例可以有 n 个表空间, 一个表空间下可以有 n 张表
 *      表空间是用来管理数据存储的逻辑概念, 表空间只是和数据文件(ora/dbf文件)有关,数据文件是物理的,
 *      一个表空间可以包含多个数据文件, 而一个数据文件只能隶属一个表空间.
 *      有了数据库,就可以创建表空间
 *      表空间(tablespace) 是 数据库的逻辑划分,每个数据库至少由一个表空间(system表空间).
 *      为了便于管理和提高运行效率,可以使用一些附加表空间来划分用户和应用程序.
 *   创建表空间语法:
 *      create tablespace 表空间名
 *      datafile 表空间数据文件路径(绝对路径,以盘符开始)
 *      size 表空间初始大小
 *      autoextend on next 扩展大小;
 *    查看已经创建好的表空间:
 *      select default_tablespace, temporary_tablespace, d.username
 *      from dba_users d;
 *  3. 用户
 *      从当前用户切换到其他用户: conn 用户名
 *      查看当前用户: show user;
 *      查看当前登录用户下的表: select table_name from user_tables;
 *      从所有表中查指定用户下的表: select * from all_tabels where owner='SCOTT';  -- 用户名必须大写
 *
 *      oracle 数据库创建好后,要想在数据库里建表, 必须先为数据库建立用户, 并为用户指定表空间.
 *      不同的 实例,可以建立相同名称的用户
 *      创建新用户: (在 数据库实例(orcl) 的基础之上)
 *      create user 用户名
 *          identified by 密码
 *          default tablespace 表空间名(默认 users)
 *          temporary tablespace 临时表空间(默认 temp);
 *    有了用户, 想要使用用户账号管理自己的表空间,还得 为用户分配权限:
 *      grant connect to 用户名;
 *      grant resource to 用户名;
 *      grant dba to 用户名;  -- dba 为最高权限,可以创建数据库,表等
 *    查看数据库用户:
 *      select * from dba_users;
 *   用户可以在自己所属的表空间上创建表.
 *   不同的用户可以在同一个表空间建立同名的表.
 *
 * 基本流程: 安装好 oracle 后会默认有 数据库实例 orcl,在该默认实例下创建表空间, 创建用户并指定用户的表空间,
 *
 *
 *  数据类型：
 *      vachar, varchar2 : 一个字符串 - 常用 varchar2
 *      number: number(n) 长度为 n 的整数
 *              number(m,n) 长度为 m 的小数, 小数位数为 n
 *      date: 日期类型
 *      clob: 大对象, 文本数据  character large object
 *      blob: 大对象, 二进制数据 binary large object
 *
 * 创建表空间:
 *   create tablespace 空间名 datafile 'd:\表空间存储目录' size 100m autoextend on next 10m;
 *      创建表空间,指定存储位置,空间大小
 *
 * 删除:
 *  delete from 表    -- 删除表中全部记录, 保留表结构
 *  drop table 表     -- 删除表结构
 *  truncate table 表
 *      -- 先删除表,再创建空表(也就是说保留表结构)
 *       效果等同于删除表中全部记录(delete)
 *       但是,再数据量大的情况下,尤其是在带有索引的情况下,
 *       该操作效率高.
 *       索引可以提高查询效率,但是会影响增删改效率
 *
 * 一次插入多条数据:
 *  insert all
 *     into 表明 (字段列表)
 *         values (参数列表)
 *     into 表明 (字段列表)
 *         values (参数列表)
 *     into ....
 *    select * from dual;
 *
 *
 *
 * 序列: 不属于任何一张表,但是可以逻辑上和表做绑定
 *      默认从 1 开始,依次递增,主要用来给主键赋值使用
 *   dual: 虚表,只是为了补全语法,没有实际意义
 * create sequence 序列名 [increment by n] [start with n];
 * select 序列名 nextval form dual;
 * select 序列名 currval form dual;
 *
 * scott 用户, 密码 tiger
 *
 * 函数:
 *   单行函数:
 *      作用于一行,返回一个值
 *     一:字符函数:
 *        select upper('yes') from dual; -- 小写转大写
 *        select lower('YES') from dual; -- 大写转小写
 *     二: 数值函数
 *        select round(23.14, n) from dual; -- n 表示保留的小数位数,四舍五入
 *        select trunc(3.14, n) from dual; -- 直接截取,非四舍五入
 *        select mod(10, 3) from dual; -- 余数
 *     三: 日期函数: sysdate: 系统当前时间
 *        --查询 emp 表中所有员工入职当前时间(天数)
 *        select sysdate - e.hiredate from emp e;
 *        --明天此刻: 当前时间加一
 *        select sysdate + 1 from dual;
 *        --查询 emp 表中所有员工入职距离现在几个月
 *        select months_between(sysdate, e.hiredate) from emp e;
 *        -- 查询 emp 所有员工 入职几年: 月份/12
 *        select months_between(sysdate, e.hiredate)/12 from emp e;
 *        --入职 几周 : 天数/7
 *        select round((sysdate - e.hiredate)/7) from emp e;
 *     四: 转换函数
 *          --日期转字符串
 *        select to_char(sysdate, 'fm yyyy-mm-dd hh24:mi:ss') from dual;
 *         --字符串转日期
 *        select to_date('9013-12-12 24:24:24', 'fm yyyy-mm-dd hh24:mi:ss') from dual;
 *     五: 通用函数
 *       --emp 表中所有员工的年薪: nvl(string1, replace_with) 等价于 mysql 中 ifnull(.., 0) -- 如果是 null, 就转为 0
 *       select e.sal*12 + nvl(e.com, 0) from emp e;
 *   多行函数:(聚合函数)
 *      作用于多行,返回一个值
 *      select count(1) from emp;  -- 查询总数量
 *      select sum(sal) from emp;  -- 工资总和
 *      select max(sal) from emp;  -- 最高工资
 *      select min(sal) from emp;  -- 最低工资
 *      select avg(sal) from emp;  -- 平均工资
 *
 * 条件表达式:
 *  -- oracle 和 mysql 通用:
 *   -- 等职判断:
 *   -- 给 emp 表中员工起中文名
 *   select e.ename,
 *          case e.ename
 *            when 'SMITH' then '曹贼'
 *              when 'ALLEN' then '诸葛'
 *                ..
 *                else '无名'       -- else 可以省略(不写 else, 则不满足条件的就是 null)
 *                  end             -- end 一定要写
 *   from emp e;
 *
 *   -- 范围判断:
 *   -- 判断 emp 表中员工工资, 如果高于 3000 显示 高收入,.
 *      select e.sal,
 *          case
 *              when e.sal > 3000 then '高收入'
 *                  when e.sal > 1500 then '低收入'
 *                      else '低收入'
 *                          end
 *      from emp e;
 *
 *  -- oracle 专用条件表达式: Oracle 中除了 起别名用双引号, 都用单引号
 *      select e.ename,
 *          decode(e.ename,
 *              'SMITH' '曹贼',
 *              'ALLEN' '诸葛',
 *                '无名') "中文名"   -- 别名必须用双引号
 *       from emp e;
 *
 * -- 分组查询
 *   --查询每个部门的平均工资
 *    select e.deptno, avg(e.sal)
 *    from emp e
 *    group by e.deptno;
 *  注: 分组查询中,出现在 group by 后面的列, 才能出现在 select 后面
 *      没有出现在 group by 后面的列,想在 select 后面,必须加上聚合函数
 *      聚合函数有一个特性,可以把多行记录变成一个值.
 *    group by 时, 得到的结果是一张新的表
 *  --查询平均工资高于 2000 的部门信息
 *      select e.deptno, avg(e.sal) sal    -- 别名 sal
 *      from emp e
 *      group by e.deptno
 *      having age(e.sal) > 2000;
 *  注: 所有条件语句中都不能使用别名(子查询出现的除外: 子查询会先执行)
 *     eg: select ename, sal s from emp where sal > 3000;  -- select 后的 字段可以起别名, where 后的条件不能起别名
 *
 *   --where 是过滤分组前的数据, having 是过滤分组后的数据.
 *      表现形式: where 必须在 group by 之前, having 是在 group by 之后.
 *
 *  eg: 查询出每个部门工资高于 800 的员工的 平均工资, 然后查询出平均工资高于 2000 的部门
 *    select e.deptno, avg(e.sal) asal
 *    from emp e
 *    where e.sal > 800
 *    group by e.deptno
 *    having age(e.sal) > 2000;
 *
 * 多表查询的一些概念
 *   --笛卡尔积
 *    select * from emp e, dept d;
 *   --等值连接: 隐式内连接
 *     select * from emp e, dept d where e.deptno = d.deptno
 *   --内连接
 *      select * from emp e inner join dept d on e.deptno = d.deptno;
 *   --外连接
 *      -- 查询所有部门, 以及部门下的员工的信息
 *      select * from emp e right join dept d on e.deptno = d.deptno;
 *      -- 查询所有员工,以及员工所属部门
 *      select * from emp e left join dept d on e.deptno = d.deptno;
 *    -- oracle 专用外连接
 *      select * from emp e, dept d where e.deptno = d.deptno(+);   -- 带有加号就表示 主表(对应 左外/右外)
 *
 *  -- 自连接: 站在不同的角度,把一张表看成多张表
 *    -- 查询员工姓名,员工领导姓名
 *    select e1.ename, e2.ename
 *    from emp e1, emp e2
 *    where e1.mgr = e2.empno; -- e1 中的领导,是 e2 中的员工
 *    -- 查询员工姓名,员工部门名称,员工领导姓名,员工领导部门名称
 *     select e1.ename, d1.dname, e2.ename, d2.dname
 *     from emp e1, emp e2, dept d1, dept d2
 *     where e1.mgr = e2.empno
 *     and e1.deptno = d1.deptno
 *     and e2.deptno = d2.deptno;
 *
 *  -- 子查询
 *    --子查询返回一个值
 *      -- 查询出工资和 scott 一样的员工信息
 *        select * from emp where sal in
 *          (select sal from emp where ename = 'scott'); -- 姓名为 scott 的不一定只有一个, 所以写 in 而不写 等号
 *    --子查询返回一个集合
 *       -- 查询 工资和 10 号部门任意员工一样的员工信息
 *         select * from emp where sal in
 *          (select sal from emp where deptno = 10);
 *    --子查询返回一张表
 *      -- 查询每个部门最低工资,和最低工资员工姓名,和该员工所在部门姓名
 *       1. 先查询每个部门最低工资
 *          select deptno, min(sal) msal
 *          from emp
 *          group by deptno;
 *       2. 三表联查, 得到最终结果
 *         select t.deptno, t.msal, e.ename, d.dname
 *         from (select deptno, min(sal) msal
 *               from emp
 *               group by deptno) t, emp e, dept d
 *         where t.deptno = e.deptno
 *         and t.msal = e.sal
 *         and e.deptno = d.deptno;
 *
 * 分页:(重点!!!)
 *   rownum行号: 在做 select 操作时, 每查询出一行记录,就会在该行上加上一个行号
 *    rownum 不属于任何一张表, 不能使用 表.rownum 这样表示, 只能单独使用
 *       行号从 1 开始,依次递增, 不能跳着走
 *   -- emp 表工资倒叙排列后, 每页五条记录, 查询 第二页
 *     -- 排序操作会影响 rownum 执行:
 *       eg: select rownum, e.* from emp e order by e.sal desc
 *     --如果涉及到排序,但是要使用 rownum 的话,可以再次嵌套查询
 *      select rownum, t.* from (select rownum, e.* from emp e order by e.sal desc) t;
 *     -- 最后: emp 表工资倒叙后, 每页五条, 查询第二页(>5)
 *       -- rownum 行号不能写上大于一个正数: 可以给 rownum 起别名,然后再写大于
 *      select * from (
 *          select rownum rn, e.* from (
 *              select * from emp order by sal desc
 *          ) e where rownum < 11
 *      ) where rn > 5;
 *    不能写: select rownum, e.* from (select * from emp order by sal desc) e where rownum < 11 and rownum > 5;  -- 错误的写法: rownum 后不能跟大于号
 *
 * 视图：
 *   视图就是提供一个查询窗口，所有数据来源于原表 (可以认为是一张临时表)
 *   创建视图： 必须要有 dba 权限
 *
 *   查询语句创建表
 *    create table emp as select * from scott.emp;
 *   创建视图
 *    create view v_emp as select ename, job from emp;
 *   查询视图
 *     select * from v_emp;
 *   修改视图: 修改的是原表的数据，不推荐通过视图修改
 *      update v_emp set job = 'CLERK' where ename = 'ALLEN';
 *      commit;
 *    创建只读视图: 不能通过视图修改数据
 *      create view v_emp1 as select ename, job from emp with read only;
 *  视图的作用：
 *    1. 视图可以屏蔽某些字段
 *    2. 保证总部和分部数据实时统一
 *
 * 索引:
 *    在表的列上构建一个二叉树,大幅度提高查询效率, 但是会影响 增删改效率.
 *   单例索引:
 *      create index idx_ename on emp(ename); -- 为 emp 表的 ename 字段创建索引
 *      单列索引触发规则: 查询条件必须是索引列中的原始值(不能是模糊查询,单行函数等)
 *   复合索引:
 *      create index idx_enamejob on emp(ename, job);
 *      复合索引中第一列为优先检索列, 如果要触发复合索引, 必须要包含优先检索列中的原始值.
 *      select * from emp where ename = 'SCOTT' and job = 'xx';  -- 触发复合索引
 *      select * from emp where ename = 'SCOTT' or job = 'xx';  -- 关键字 or 相当于有两个查询语句, 不会触发索引
 *      select * from emp where ename = 'SCOTT'; -- 触发单列索引
 *      select * from emp where job = 'xx'; -- 不是优先检索列,不会触发索引
 *
 *  PL/SQL 编程语言
 *      对 sql 语言的扩展,使得 sql 语言具有过程化编程的特性
 *      比一般的过程化编程语言更加灵活
 *      主要用来编写存储过程和存储函数等
 *    声明方法
 *      declare
 *          i number(2) := 10; -- 使用 := 赋值
 *          s varchar2(10) := '小明';
 *          ena emp.ename%type;  -- 引用型变量: 表.字段%type  -- 引用 指定表的字段的数据类型
 *          emprow emp%rowtype; -- 记录型变量: 一行记录(可以认为是一个对象)
 *      begin
 *          dbms_output.put_line(i); -- 输出
 *          dbms_output.put_line(s);
 *          select ename into ena from emp where empno = 7788; -- 使用查询语句赋值:  into
 *          select * into emprow from emp where empno = 7788; -- 使用查询语句赋值:  into
 *          dbms_output.put_line(emprow.ename || '的工作为:' || emprow.job); -- 输出
 *      end;
 *
 * 一：pl/sql 的 if 判断
 *   declare
 *      i number(3) := &i;  -- &i 表示键盘录入,后面的变量随便写
 *   begin
 *      if i < 18 then
 *          dbms_output.put_line('未成年');
 *      elsif i < 40 then
 *          dbms_output.put_line('中年人');
 *      else
 *          dbms_output.put_line('老年人');
 *      end if;
 *   end;
 *
 * 二：pl/sql 的 loop 循环
 *  1: while 循环
 *   declare
 *      i number(2) := 1;
 *   begin
 *      while i < 11 loop
 *          dbms_output.put_line(1); -- 输出 1~10
 *          i := i+1;  -- i++
 *      end loop;
 *   end;
 *
 * 2: exit 循环 -- 重点
 *   declare
 *   begin
 *      loop
 *          exit when i > 10;
 *          dbms_output.put_line(i);
 *          i := i+1;
 *      end loop;
 *    end;
 *
 * 3: for 循环
 *   declare
 *   begin
 *      for i in 1..10 loop
 *          dbms_output.put_line(i);
 *      end loop;
 *   end;
 *
 * 三：游标
 *   可以存放多个对象,多行记录
 *  declare
 *      cursor c1 is select * from emp;  -- 将每一行记录放入 游标 (就是一个集合)
 *      emprow emp%rowtype;              -- 定义 记录型变量
 *  begin
 *      open c1;  -- 开启游标
 *          loop
 *              fetch c1 into emprow;
 *              exit when c1%notfound;  -- 当没有记录时退出
 *              dbms_output.put_line(emprow.enam);  -- 输出姓名
 *          end loop;
 *      close c1;  -- 关闭游标
 *  end;
 *
 * -- 给指定部门员工涨工资
 * declare
 *      cursor c2(eno emp.deptno%type)
 *      is select empno from emp where deptno = eno;
 *      en emp.empno%type;
 * begin
 *      open c2(10);
 *          loop
 *              fetch c2 into en;
 *              exit when c2%notfound;
 *              update emp set sal = sal + 100 where empno = en;
 *              commit;
 *          end loop;
 *      close c2;
 * end;
 *
 * 四：存储过程:
 *   预编译一段 pl/sql 语言, 放置在数据库端, 可以直接调用
 *   这段 pl/sql 一般都是固定步骤的业务.
 * 基本语法:
 *  create [or replace] procedure 过程名[(参数名 in/out 数据类型)]
 *  as
 *  begin
 *      pl/sql..
 *  end;
 *
 * 示例:
 *   create or replace procedure p1(eno emp.empno%type)  -- 'or replace' 可选, 表示如果存在 同名的 procedure, 则替换
 *   as   -- 相当于 declare
 *   begin
 *      update emp set sal = sal + 100 where empno = eno;
 *      commit;
 *   end;
 * --测试 p1 存储过程: 相当于调用方法
 *  declare
 *  begin
 *      p1(7788);  -- 调用 p1, 传递参数
 *  end;
 *
 * 五：存储函数:
 *   基本语法:
 *      存储过程和存储函数的参数不能带长度
 *      存储函数的返回值类型不能带长度
 *     create or replace function 函数名(参数名 参数类型) return number
 *     as
 *     begin
 *          pl/sql;
 *          return ..;
 *     end;
 *  示例:
 *      create or replace function f_yearsal(eno emp.empno%type) return number
 *      as
 *          s number(10);
 *      begin
 *          select sal*12 + nvl(comm, 0) into s from emp where empno = eno;
 *          return s;
 *      end;
 *   测试 存储函数: 有返回值,则必须接收
 *      declare
 *          s number(10);
 *      begin
 *          s := f_yearsal(7788);   -- 接收返回值
 *          dbms_output.put_line(s);
 *      end;
 *
 * out 类型参数的使用： 弥补 存储过程不能 return 的问题
 *   使用存储过程来计算年薪
 *   create or replace procedure p_yearsal(eno emp.empno%type, yearsal out number)  -- 参数数据类型不能有长度
 *   as
 *      s number(10);
 *      c emp.comm%type;
 *   begin
 *      select sal*12, nvl(comm, 0) into s, c from emp where empno = eno;
 *      yearsal := s + c;
 *   end;
 *  测试存储过程:
 *      declare
 *          yearsal number(10);
 *      begin
 *          p_yearsal(7788, yearsal);
 *          dbms_output.put_line(yearsal);
 *      end;
 *
 * in 和 out 类型参数区别
 *   凡是涉及到 into 查询语句赋值或者 := 赋值操作的参数，都必须使用 out 来修饰。
 *
 * 存储过程和存储函数的区别
 *   语法区别： 关键字不一样  procedure/function
 *             存储函数比存储过程多了两个 return
 *   本质区别： 存储函数有返回值， 而存储过程没有返回值
 *           如果存储过程想实现返回值的业务，我们就必须使用 out 类型的参数
 *           即便是存储过程使用了 out 类型的参数，其本质也不是真的有了返回值
 *           而是在存储过程内部给 out 类型参数赋值，在执行完毕后，我们直接拿到输出类型参数的值。
 * 我们可以使用存储函数有返回值的特性，来自定义函数
 *  而存储过程不能用来自定义函数
 * --需求： 查询员工姓名，员工所在部门名称
 *   create table dept as select * from scott.dept;
 *  -- 使用传统方式实现案例需求
 *      select e.ename, d.dname
 *      from emp e, dept d
 *      where e.deptno = d.deptno;
 *  -- 使用存储函数来实现 提供一个部门编号，输出一个部门名称
 *      create or replace function fdna(dno dept.deptno%type) return dept.dname%type
 *      as
 *          dna dept.dname%type;
 *      begin
 *          select dname into dna from dept where deptno = dno;
 *          return dna;
 *      end;
 *   -- 使用 fdna 存储函数来实现案例需求： 查询员工姓名， 员工所在部门名称
 *      select e.ename, fdna(e.deptno)
 *      from emp e;
 *
 * 六： 触发器: 本质上就是一种更复杂的约束
 *   指定一个规则，在我们做增删改操作时，只要满足规则，自动触发，无需调用
 *  1. 语句级触发器: 不包含 for each row 的触发器
 *  2. 行级触发器: 包含 for each row 的触发器
 *      加 for each row 是为了使用 :old 或者 :new 对象或者一行记录.
 *  3. 示例
 *     语句级触发器:
 *    --插入一条记录, 输出一个新员工入职
 *     create or replace trigger t1
 *     after        -- 之前还是之后触发
 *     insert       -- 曾/删/改 的哪一个操作
 *     on person    -- 指定表
 *     declare
 *     begin
 *          dbms_output.put_line('新员工入职');
 *     end;
 *     -- 触发: 只要 person 执行了 insert 相关操作, 就会触发 t1 触发器
 *     insert into person vales(1, '小红');
 *     commit;
 *
 *    行级触发器:
 *     -- 不能给员工降薪
 *     create or replace trigger t2
 *     before       -- 操作之前
 *     update       -- 更新操作
 *     on emp
 *     for each row
 *     declare
 *     begin
 *          if :old.sal > :new.sal then     -- 新数据 < 旧数据, 则抛出异常
 *              raise_application_error(-20001, '不能给员工降薪!');
 *           end if;
 *     end;
 *   -- 触发 t2
 *   update emp set sal = sal - 1 where empno = 7788;
 *   commit;
 *
 * -- 触发器的应用: 使用触发器实现主键自增
 *   分析: 在用户做插入操作之前,拿到即将插入的数据, 给该数据的主键列赋值
 *   使用 行级触发器
 *   create or replace trigger auid
 *   before
 *   insert
 *   on person
 *   for each row
 *   declare
 *   begin
 *      select s_person.nextval into :new.pid from dual;
 *   end;
 *   -- 使用 auid 触发器实现主键自增
 *     insert into person (pname) values('a');
 *     commit;
 *
 *
 * java 代码操作 oracle
 *      oracle10g  --对应 jar 包 -->   ojdbc14.jar
 *      oracle11g  --对应 jar 包 -->   ojdbc6.jar
 *
 *
 */
public class Oracle {
//    Calendar
    /**
     * net start OracleOraDb10g_home1TNSListener
     * net start OracleServiceORCL
     */
}
