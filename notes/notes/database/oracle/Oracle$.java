package org.oracle;

import org.junit.Test;

/**
 * @author child
 * 2019/5/5 18:33
 * 连接查询
 *   定义: 将两个或两个以上的表以一定的连接条件
 *   (select 后面跟的就是结果要显示的 字段/列数)
 *   mysql 的 别名不能加 引号
 *   分类:
 *     一:内连接
 *        理解下面每一个 sql 的含义
 *          1. select .. from A, B                      -- 笛卡尔积: 没有使用条件筛选,结果没有实际意义
 *                把 A 表的所有记录 分别与 B 表的每一行记录匹配(得到结果: A 的记录数 * B 的记录数)
 *                A 表的任何记录都会与 B 表的每一条记录 连接.
 *          2. select .. from A, B where ..            -- 把 笛卡尔积 用 where 进行过滤
 *             eg: select * from emp, dept where deptno = 7369;
 *          3. select .. from A [inner] join B on ..
 *              eg: select * from emp e join dept d on 1 = 1;       -- 笛卡尔积: on 1=1, 表示连接条件不存在
 *                  select * from emp e join dept d on e.deptno = d.deptno;  -- 条件连接
 *                等价于: select * from emp e, dept d where e.deptno = d.deptno;
 *              三表:
 *              SELECT * FROM emp e JOIN dept d ON 1=1 JOIN salgrade s ON 1=1; -- 笛卡尔积: 行数是 各表行数之积, 列数是 各表列数之和
 *              SELECT *
 *                  FROM emp "e"
 *                  JOIN dept "d"
 *                      ON "e".deptno = "d".deptno
 *                  JOIN salgrade "s"
 *                      ON "e".sal > "s".losal
 *                      AND "e".sal < "s".hisal
 *                  WHERE "e".sal > 2000;   -- where 必须写在 join 后面
 *              等价于:
 *              SELECT *
 *                  FROM emp e, dept d, salgrade s
 *                  WHERE e.deptno = d.deptno
 *                  AND (e.sal > s.losal AND e.sal < s.hisal)  -- 也可以不加括号: 加了逻辑上更容易理解
 *                  AND e.sal > 2000;
 *              推荐使用 join..on 的语法, 不使用 where
 *             区别以下两个 sql
 *              select * from emp, dept where dept.deptno = 10;  -- 部门表中 部门编号为 10 的记录与 员工表中 每一行记录组合
 *                      对部门表来说,部门编号是主键, 则 上述 sql 得到的结果为 员工表的记录数 (emp 行数 * 1)
 *                  等价: select * from emp join dept on 1=1 where dept.deptno = 10;
 *              select * from emp, dept where emp.deptno = 10;  -- 员工表中 部门编号为 10 的记录 与部门表的每一行记录的组合
 *                      对员工表来说, 部门编号是 外键(多的一方), 得到结果 (员工的部门编号为 10 的记录数 * 部门表总记录数(部门总数))
 *            另: having 不能替代 where: having 是对分组之后的数据过滤,必须配合 group by 使用
 * 基于 oracle 的语法:
 *   习题(查询):
 *      1) 每个员工姓名, 部门编号, 薪水, 薪水等级
 *      2) 每个部门编号, 部门所有员工平均工资, 平均工资等级
 *      3) 每个部门编号, 部门名称, 部门所有员工平均工资, 平均工资等级
 *      4) emp 表所有领导的姓名
 *      5) 平均工资最高的部门编号,部门平均工资
 *      6) 工资大于所有员工
 * ---------------------------------------------------------------------------------------------------
 *          -- 输出部门名称不包含 C 的所有员工中工资最高的前三名的每个员工的姓名,工资,工资等级,部门名称
 *           语法: 内连接(join..on), 模糊(like), 分页(rownum), 排序(order by)
 *         oracle-sql:
 *           select "e".ename, "e".sal, "s".grade, "d".dname
 *              from emp "e"
 *              join dept "d"
 *              on "e".deptno = "d".deptno
 *              join salgrade "s"
 *              on "e".sal between "s".losal and "s".hisal
 *              where "d".dname not like '%C%'   -- 必须单引号
 *              and rownum < 4              -- oracle 专有: < 4 表示 前 1,2,3 列数据
 *              order by "e".sal desc;
 * ---------------------------------------------------------------------------------------------------
 *         -- 每个员工姓名,部门编号,薪水,薪水等级(降序): 员工表(其中有部门编号),工资等级表
 *            语法: join, between..and, order by .. desc (降序)
 *            select "e".ename, "e".deptno, "e".sal, "s".grade
 *              from scott.emp "e"
 *              join scott.salgrade "s"
 *              on "e".sal between "s".losal and "s".hisal      -- on "e".sal >= "s".losal and "e".sal <= "s".hisal
 *              order by "e".sal desc;  -- 降序
 * ---------------------------------------------------------------------------------------------------
 *         --查找每个部门的编号, 该部门所有员工的平均工资,平均工资等级: 员工表, 工资表
 *              -- 首先查询 部门编号,部门平均工资
 *              select deptno, avg(sal) as "avg_sal"
 *                  from scott.emp
 *                  group by deptno;
 *              -- 把上述结果作为临时表, 结合工资等级
 *              select "t".*, "s".grade
 *                  from (
 *                      select deptno, avg(sal) as "avg_sal"
 *                          from scott.emp
 *                          group by deptno) "t"
 *                  join scott.salgrade "s"
 *                  on "t"."avg_sal" between "s".losal and "s".hisal;  -- 别名 "avg_sal" 必须加双引号
 *              --等价于
 *              select "t".*, "s".grade
 *                  from (
 *                      select deptno, avg(sal) as "avg_sal"
 *                          from scott.emp
 *                          group by deptno) "t", scott.salgrade "s"
 *                  where "t"."avg_sal" between "s".losal and "s".hisal;
 * ---------------------------------------------------------------------------------------------------
 *         -- 查找每个部门的编号, 部门名称, 部门所有员工平均工资, 平均工资等级: 员工表, 部门表, 工资表
 *              -- 先查单表: 部门编号，部门平均工资
 *                  select deptno, avg(sal) "avg_sal"
 *                      from scott.emp
 *                      group by deptno;
 *              -- 把单表的结果作为临时表: 结合 部门名称，工资等级
 *                  select "t".deptno "部门编号", "d".dname "部门名称", "t"."avg_sal" "部门平均工资", "s".grade "平均工资等级"
 *                      from (select deptno, avg(sal) "avg_sal"
 *                                  from scott.emp
 *                                  group by deptno) "t"
 *                      join scott.dept "d"
 *                      on "t".deptno = "d".deptno
 *                      join scott.salgrade "s"
 *                      on "t"."avg_sal" between "s".losal and "s".hisal;
 * ---------------------------------------------------------------------------------------------------
 *        -- emp 表所有领导的编号(领导同时也是员工), 姓名, 职位: 员工表(子查询)
 *         方法一:
 *          select "e".empno, "e".ename,"e".job
 *              from emp "e"
 *              join (select distinct mgr from emp)"m"   -- 临时表: 领导编号表(包括最高领导: 其领导编号为 null)
 *              on "e".empno = "m".mgr          -- 如果员工编号在 上述临时表中: 表示该员工就是领导
 *              order by empno asc;   --排序可选: 便于查看
 *            -- 写不写 distinct 不影响结果: 只是为了更好区分
 *          方法二:
 *          select "e".empno, "e".ename, "e".job
 *              from emp "e"        -- 员工表
 *              where "e".empno         -- 只要 员工编号 在 领导编号表中,就说明 该员工是领导
 *                  in (select mgr from emp);   -- 临时表: 领导编号表
 *          注: 上述两个 sql 结果完全一样.
 *              同一个需求,可以有 n 多种实现方式,只要结果一致即可,暂时不用考虑 sql 优化等问题.
 * ---------------------------------------------------------------------------------------------------
 *         -- emp 表所有 不是领导 的员工的信息
 *           错误的写法: 直接在在上面的 sql 加以个 not in
 *           select "e".empno, "e".ename, "e".job
 *              from emp "e"
 *              where "e".empno
 *              not in (select mgr from emp);   -- 临时表中有 null: not in (a, b, null)  -- 恒为 false
 *         注: in(..) 的方式: 本质上可以理解为  .. or .. or..,  or 运算在处理 null 时,会有问题
 *           not in(a, b, null) --> not (x = a or x = b or x = null)  --> not (false or false or null) --> false
 *         正确的写法: 先排除 null, 在使用 not in(...)
 *           select "e".empno, "e".ename, "e".job
 *              from emp "e"
 *              where "e".empno
 *              not in (select nvl(mgr, 0) from emp where mgr != 0);  -- 这里临时表的 where 条件加不加都行.
 * ---------------------------------------------------------------------------------------------------
 *          -- 平均薪水最高的部门的编号, 部门的平均工资: 分组,排序,分页
 *              -- 部门平均薪水, 部门编号
 *               select deptno, avg(sal)
 *                  from emp group by deptno;
 *              -- 平均工资最高的部门编号, 部门平均工资
 *               select "t".deptno, "t"."avg_sal"
 *                  from (select deptno, avg(sal) "avg_sal" from emp group by deptno order by "avg_sal" desc)"t"
 *                  where rownum < 2;
 *                  -- 上面临时表中的 order by 子句也可以不加
 *              -- 第二种写法:
 *               select "t".*
 *                  from (
 *                      select deptno, avg(sal) "avg_sal"
 *                          from emp group by deptno
 *                          order by "avg_sal" desc
 *                      )"t"
 *                  where "t"."avg_sal" = (
 *                      select max("avg_sal") from (
 *                              select deptno, avg(sal) "avg_sal"
 *                                  from emp group by deptno
 *                                  order by "avg_sal" desc)
 *                 );
 * ---------------------------------------------------------------------------------------------------
 *         -- 工资大于所有员工中工资最低的前三名的姓名, 工资, 部门编号, 部门名称, 工资等级
 *           -- 工资最低的前三名, 姓名, 部门编号, 工资
 *            select "t".deptno, "t".ename, "t".sal
 *              from (select deptno, ename, sal from emp order by sal) "t"
 *              where rownum < 4;
 *           -- 结合 部门名称(部门表), 工资等级(工资表): 还是工资最低的 前三名信息
 *              select "t1".ename, "t1".sal, "d".deptno, "d".dname, "s".grade
 *                  from (select "t".deptno, "t".ename, "t".sal
 *                              from (select deptno, ename, sal from emp order by sal) "t"  -- emp 表 按工资排序
 *                              where rownum < 4) "t1"      -- 获取排序后的 前三(最低三个)
 *                  join dept "d"
 *                  on "t1".deptno = "d".deptno
 *                  join salgrade "s"
 *                  on "t1".sal between "s".losal and "s".hisal;
 *           -- 工资大于 上述 3 个人的信息: 上述三个人工资最低, 则工资不在上述 三个人之中, 必定就是大于
 *             select "e".ename, "e".sal, "e".deptno, "d".dname, "s".grade
 *                  from emp "e"
 *                  join dept "d"
 *                  on "e".deptno = "d".deptno
 *                  join salgrade "s"
 *                  on "e".sal between "s".losal and "s".hisal
 *                  where "e".sal not in (
 *                      select "t1".sal
 *                          from (select "t".deptno, "t".sal
 *                                      from (select deptno, sal from emp order by sal) "t"
 *                                      where rownum < 4) "t1"
 *                  );
 *           -- 上述题目可能有歧义： 另一种理解
 *              去掉工资最低的人, 剩下的人中工资最低的 3 个(工资升序后前三个)
 *              -- 思路: 先得到 工资最低的 前 4 个, 再排除 最低的一个
 *              -- emp 表: 员工姓名, 工资, 部门编号
 *            select "t2".*
 *                  from (
 *                      select "t1".*
 *                              from (
 *                                     select "t".*
 *                                          from (select ename, sal, deptno from emp order by sal) "t"  -- 工资升序
 *                                          where rownum < 5) "t1"       -- 前 4 位
 *                              order by "t1".sal desc) "t2"        -- 工资降序
 *                 where rownum < 4;         -- 取前三: 即排除工资最低后的 最低前三位
 *            -- 上述的结果结合 部门表(部门姓名) 工资表(工资等级)
 *            select "t3".*, "d".dname, "s".grade
 *                  from (
 *                          select "t2".*
 *                                  from (
 *                                      select "t1".*
 *                                              from (
 *                                                  select "t".*
 *                                                      from (select ename, sal, deptno from emp order by sal) "t"
 *                                              where rownum < 5) "t1"
 *                                  order by "t1".sal desc) "t2"
 *                  where rownum < 4) "t3"
 *                  join dept "d"
 *                  on "t3".deptno = "d".deptno
 *                  join salgrade "s"
 *                  on "t3".sal between "s".losal and "s".hisal;
 *        -- 方式二:
 *          -- 先过滤最低工资, 再得到剩下员工的所有信息, 再升序, 再获取前三个
 *        select *          --"t1".ename, "t1".sal, "t1".deptno, "t1".dname, "t1".grade
 *                from (
 *                     select "t".ename, "t".sal, "t".deptno, "d".dname, "s".grade
 *                            from (
 *                                select *
 *                                    from emp
 *                                    where sal > (select min(sal) from emp)
 *                            ) "t"
 *                            join dept "d"
 *                            on "t".deptno = "d".deptno
 *                           join salgrade "s"
 *                            on "t".sal between "s".losal and "s".hisal
 *                            order by "t".sal)     -- "t1"
 *                 where rownum < 4;
 * ---------------------------------------------------------------------------------------------------
 *   内连接总结: 三表 emp, dept, salgrade
 *    select * from emp, dept;                          -- 结果: 行数 = emp 行数 (乘以) dept 行数, 列数 = emp 列数 + dept 列数
 *     等价于 select * from dept, emp;
 *    select * from emp, dept where ...                 -- 本语句的 where 是对 两表合成的临时表的过滤
 *    select * from emp, dept where empno = 7369;
 *      -- 两种理解方式:
 *          -- 1. emp 表的一行记录(一个员工编号一定只有员工)与 dept 表的所有记录连接
 *          -- 2. 先把 emp 和 dept 的所有记录连接, 再把 empno = 7369 的记录选出
 *     严格来讲: 第二种解释更准确, 先得到两个表的连接, 再进行 where 过滤
 *     select * from emp, dept where 1=1;                       -- 笛卡尔积: 等价于没有过滤条件
 *     select * from emp, dept where deptno = 10;               -- error: 两表得到的临时表中有两个 deptno 字段
 *     select * from emp, dept where emp.deptno = 10;           -- emp 表中 部门编号为 10 的记录数 (乘以) dept 表中所有记录数
 *     select * from emp, dept where dept.deptno = 10;          -- dept 表中 部门编号为 10 的记录数(1 条记录) (乘以) emp 的记录数 - 即 emp 表的记录数
 *     等价于: select * from emp join dept on 1=1 where dept.deptno = 10;  -- on 1=1 就相当于没有过滤条件
 *
 *     考虑下面的语句的实际含义,以及这样写是否合适: (基于 oracle)
 *      select * from emp
 *          join dept
 *          on emp.deptno = dept.deptno and emp.sal > 2000;  -- 这里直接在 on 后写 and 而不是用 where
 *          -- on 后面是连接条件, and 后面是过滤条件 -- 但是这样不推荐: 一般就是用 where 来表示过滤
 *      等价于:
 *        select * from emp, dept
 *              where emp.deptno = dept.detpno
 *              and emp.sal > 2000;
 *      重点在于: 有 join 则必须要有 on
 *
 *      -- 工资大于 1500 所有员工按部门分组, 把 部门平均工资大于 2000 的 最高的前两个部门的 部门编号, 部门名称, 平均工资, 工资等级 升序输出
 *          -- 工资大于 1500 的员工
 *            select * from emp where sal > 1500;  -- 临时表
 *          -- 分组, 降序(为后面获取 最高的两个做准备)
 *            select avg(sal) "avg_sal", "t".deptno from (select * from emp where sal > 1500) "t" group by "t".deptno order by "avg_sal" desc;
 *          -- 连接 3 表
 *            select "d".deptno, "d".dname, "t1"."avg_sal", "s".grade
 *              from (select avg(sal) "avg_sal", "t".deptno
 *                       from (select * from emp where sal > 1500) "t"
 *                       group by "t".deptno order by "avg_sal" desc
 *                   ) "t1"
 *              join dept "d"
 *              on "t1".deptno = "d".deptno
 *              join salgrade "s"
 *              on "t1"."avg_sal" between "s".losal and "s".hisal
 *              where rownum < 3;
 *          -- 方式二: 加个 having
 *             select * from (
 *                  select "d".deptno, dname, "avg_sal", grade from (
 *                      select deptno, avg(sal) "avg_sal"
 *                          from (
 *                              select deptno, sal
 *                                  from emp
 *                                  where sal > 1500)          -- 先得到 工资大于 1500 的员工
 *                                  group by deptno           -- 再分组
 *                                  having avg(sal) > 2000
 *                                  order by avg(sal) desc) "t"
 *                          join dept "d"
 *                          on "t".deptno = "d".deptno
 *                          join salgrade "s"
 *                          on "t"."avg_sal" between "s".losal and "s".hisal)
 *              where rownum < 3;
 *          -- 方式三： 类似方式二
 *              select * from (
 *                             select "d".deptno ,dname, "avg_sal", grade
 *                                  from (
 *                                      select deptno, avg(sal) "avg_sal"
 *                                              from emp
 *                                              where sal > 1500
 *                                              group by deptno
 *                                              having avg(sal) > 2000
 *                                              order by "avg_sal" desc
 *                                       ) "t"
 *                                join dept "d"
 *                                on "t".deptno = "d".deptno
 *                                join salgrade "s"
 *                                on "t"."avg_sal" between "s".losal and "s".hisal)
 *                      where rownum < 3;
 *    注: 关于 having 的注意事项: having 后面不能通过 别名参数 过滤
 *       错误的写法: select deptno, avg(sal) "avg_sal" from emp where sal > 1500 group by deptno having "avg_sal" > 2000;
 *       正确的写法: select deptno, avg(sal) from emp where sal > 1500 group by deptno having avg(sal) > 2000;
 *
 *
 * 基本语法:
 * mysql-sql:
 *    select
 *      from
 *      join
 *      on
 *      join
 *      on
 *      ....
 *      where
 *      group by
 *      having
 *      order by
 *      limit
 *
 * oracle-sql:
 *      select
 *          from
 *          join
 *          on
 *          join
 *          on
 *          ...
 *          where
 *              (rownum < n) -- 分页
 *          group by
 *          having
 *          order by
 *
 *
 * 二:外连接
 *   1. 左外:
 *     基本语法:
 *       select * from emp "e"
 *          left [outer] join dept "d"
 *          on "e".deptno = "d".deptno;
 *     说明:
 *       1) 用 左表 的第一行分别和 右表 的所有行进行连接, 如果有匹配的行, 则一起输出;
 *          如果右表有多行匹配,则结果集输出多行; 如果没有匹配行,则结果集中只输出一行,
 *          该输出行左边为左表第一行内容, 右边全部输出 null.
 *       2) 然后, 再用 左表的第二行 和右表的所有行进行连接, 如果有匹配的行,则一起输出;
 *          如果右表有多行匹配,则结果集输出多行;如果没有匹配行,则结果集中只输出一行,
 *          该输出行左边为坐标第二行内容, 右边全部输出 null.
 *       3) 以此类推, 直至左表的所有行 连接完毕
 *       4) 因为 右表 很可能出现有多行和左表的某一行匹配, 所以 左连接产生的结果集的行数很可能
 *          大于 left join 左表的记录总数. (出现类似于笛卡尔积的效果)
 *       5) 总结: 左外连接 的结果集包括 left outer 子句中指定的 左表的所有行, 而不仅仅是与右表 有关系的行.
 *          如果左表的某行在右表中没有匹配行,则在相关的结果集中,右表的所有查询列的值均为 null 值.
 *          (可以这样理解: 如果左表 没有匹配到 右表的任何记录, 就默认匹配到 一个 null 行)
 *       另: 左外连接得到的结果记录数:
 *                  最少 -- 左表的行数
 *                  最多 -- 笛卡尔积
 *        实际上, 左连接产生的结果集的行数很可能大于 left join 左表的记录总数; 不能理解为: 左连接 left join
 *          左表的记录有 n 行,则连接的结果也是 n 行/n 行的倍数. 左连接产生的结果集的行数很可能 大于 left join 左表的记录总数,
 *          但是 实际的结果是无法确定的, 根据实际的连接情况而定(而不能下结论: 左外连接的结果记录数一定是 左表的倍数 之类).
 *     左外连接的实际意义:
 *        返回一个事物及该事物的相关信息, 如果该事物没有相关的信息, 则输出 null.
 *       eg:
 *        已知条件: product 货物表,  orderForm 订单表.
 *        需求: 输出 货物的订单信息, 如果某货物没有订单信息, 也要显示货物信息.
 *          -- 要显示 谁的全部信息, 就以谁为左外连接的左表(主表)
 *         select * from product "p" left [outer] join orderForm "o" on "p".pid = "o".pid;
 *   所以, 以下两个 sql 的结果是不一样的.
 *      select *
 *        from dept "d"
 *        left join emp "e"
 *        on "e".deptno = "d".deptno;
 *      select *
 *        from emp "e"
 *        left join dept "d"
 *        on "e".deptno = "d".deptno;
 *   2. 右外: 左外相反.. 略
 *   3. 完全连接: 关键字 full join..on
 *      select *
 *        from emp "e"
 *        full join dept "d"
 *        on "e".deptno = "d".deptno;
 *     解释: 结果集中包含三部分内容
 *      1. 两个表中相匹配的所有记录
 *      2. 左表中那些在右表没有匹配的记录,这些记录的 右边字段全部为 null
 *      3. 右表中那些在左表没有匹配的记录,这些记录的 左边字段全部为 null
 *    一句话: full join = left join + right join
 *   4. 交叉连接: 可以不加 on 的 join
 *      select * from emp cross join dept;
 *      等价于: select * from emp, dept;
 *   5. 自连接
 *      定义: 在同一张表上做连接查询.
 *      eg: 不使用 聚合函数, 求薪水最高的员工信息
 *        -- 先得到 工资不是最高的员工编号
 *            -- 过滤条件: "e1"表 中工资低于 "e2"表 中工资的 员工编号 (结果一定包含 除了工资最高员工 的所有人)
 *         select distinct "e1".empno from emp "e1" join emp "e2" on "e1".sal < "e2".sal;
 *        -- 进而得到 工资最高的员工信息
 *        select * from emp where empno not in (select distinct "e1".empno from emp "e1" join emp "e2" on "e1".sal < "e2".sal);
 *        注: 如果 要求工资最低工资, 将小于改为大于即可.
 *      查询最高薪水的方式:
 *       1) select * from emp where sal = (select max(sal) from emp "e");
 *       2) select * from (select * from emp order by sal desc) where rownum < 2;
 *  6. 联合: 关键字 union
 *     eg: 输出每个员工的姓名, 工资, 上司姓名  -- 最高领导没有领导, 要使用外连接才能得到所有结果
 *      一般方式:
 *      select "e2".ename "员工姓名", "e2".sal "工资", "e1".ename "领导姓名"
 *          from emp "e1"
 *          right join (select ename, sal, mgr from emp) "e2"  -- 临时表: 员工姓名, 工资, 领导编号
 *          on "e1".empno = "e2".mgr        -- 把临时表作为 员工表: 员工的领导编号 = 领导表中的员工编号(领导也是员工)
 *          order by "工资";
 *      联合查询的方式:
 *       select "e2".ename "员工姓名", "e2".sal "员工工资", "e1".ename "领导姓名"
 *            from emp "e1"  -- 领导表
 *            join emp "e2"  -- 员工表
 *            on "e1".empno = "e2".mgr
 *       union
 *       select ename, sal, '最大领导'    -- 联合表的查询字段必须与 主表的查询字段完全一致
 *            from emp                       -- 另: 这里的 '最大领导' 不是别名, 而是 字符串, 必须是单引号(与 ename 的数据类型一致)
 *            where mgr is null
 *       order by "员工工资";
 *     这样理解: 先查出 主表的信息(不包含 最高领导的信息), 再在主表的最下面(最后一行) 追加一行(最高领导的信息)  -- 即 纵向连接
 *
 *     联合的定义: 表和表之间的数据以纵向的方式连接在一起.
 *         之前的连接(内连接/外连接) 都是 横向连接.
 *      注:
 *       若干个 select 子句要联合起来,必须满足的条件(严格遵循 总想链接的定义)
 *        1. 联合表的 查询字段数量 必须与 主表的 查询字段数量完全一致(列数一致): 不能主表查出 3 个字段, 联合表查出 4 个字段 (这样就不满足 纵向连接的目的了)
 *        2. 联合表的 查询字段的数据类型 必须与 主表的 查询字段的数据类型 一一对应 (或者至少是相互兼容的)
 * ---------------------------------------------------------------------------------------------------
 *  分页: oracle
 *    1.-- 查询工资最高的前 3 各员工信息
 *       -- 先把所有工资排序
 *       select * from emp order by sal desc;
 *      -- 再得到前三个
 *       select * from (select * from emp order by sal desc) where rownum < 4;
 *    2.-- 查询工资降序排列后 第 4,5,6 位 员工信息: 排除最高的前三个,再得到 剩下员工中最高工资的前三个
 *       select * from (select * from emp where empno not in (select empno from (select * from emp order by sal desc) where rownum < 4) order by sal desc) where rownum < 4;
 *    3.-- 查询工资降序排列后 第 7,8,9 位 员工信息: 排除最高的 1-6 位
 *       -- 先查询前 6 位
 *      select * from (select * from emp order by sal desc) where rownum < 7;
 *      -- 再排除前 6 位
 *      select * from emp where empno not in (select empno from (select * from emp order by sal desc) where rownum < 7) order by sal desc;
 *      -- 再的到前三个: 7, 8, 9 位
 *      select * from (
 *                  select *
 *                      from emp
 *                      where empno not in (
 *                          select empno
 *                              from (
 *                                  select *
 *                                      from emp
 *                                      order by sal desc)
 *                              where rownum < 7)
 *                       order by sal desc)
 *                where rownum < 4;
 *
 * 基本格式: select * from (
 *                      select *
 *                          from A
 *                          where A_id not in (
 *                              select *
 *                                  from (
 *                                      select *
 *                                          from A
 *                                          [order by A_x desc])  -- 排序: 将原始表的所有记录排序: 可选
 *                                  where rownum < n)   -- 得到(排序后)的前 n 行记录
 *                           [order by A_x desc])   -- 排除前 n 行后, 排序(可选)
 *                    where ronum < m;      -- 最终的结果: 得到 排除 前 n 行后的 接下来的 m 行记录
 * ---------------------------------------------------------------------------------------------------
 *
 *  视图:
 *    一:为什么需要视图
 *       需求: 求出平均工资最高的部门编号,部门平均工资
 *       一般实现:
 *       -- 首先求部门编号, 部门平均工资
 *       select deptno, avg(sal) "avg_sal" from emp group by deptno;
 *       --在上述临时表中 查最高
 *       select * from (select deptno, avg(sal) "avg_sal" from emp group by deptno) where rownum < 2;
 *      视图方式实现:
 *       1. 创建视图
 *        create view v$_emp_1
 *        as
 *           select deptno, avg(sal) "avg_sal"
 *                 from emp
 *                 group by deptno;
 *       2. 查询视图
 *         select * from v$_emp_1;
 *       3. 使用视图: 就是把视图作为一张临时表
 *         select * from v$_emp_1
 *              where "avg_sal" = (select max("avg_sal") from v$_emp_1);
 *        结果等价于上面的sql:(分页的写法)
 *         select * from (select deptno, avg(sal) "avg_sal" from emp group by deptno) where rownum < 2;
 *       4. 删除视图: 就是删除表
 *         drop view v$_emp_1;
 *      作用: 简化查询: 避免了代码的冗余; 视图可以重复使用.
 *
 *    二:什么是视图
 *       视图,从代码上看是一个 select 语句
 *        从 逻辑上看,就是一张表: 临时表,虚拟表(不是实际存在的)
 *      常见的应用场景: 利用视图屏蔽某些字段 -- 视图中只有 主表的部分字段
 *       eg: 隐藏 emp 表的 工资, 雇佣日期字段
 *        1. 创建视图
 *         create view v$_emp_2
 *         as
 *            select empno, ename, job, mgr, comm, deptno from emp;
 *          -- 视图(临时表)中没有 sal, hiredate 字段
 *        2. 给 某个用户授予一定的权限: 只能 访问 视图 v$_emp_2, 而不能访问 主表 emp
 *         select * from v$_emp_2;  -- 通过这种方式就屏蔽了 工资和雇佣日期字段
 *       另: 视图通常只用来做查询, 不会通过视图来做增删改.
 *    三:视图的使用格式
 *       create view 视图名
 *       as
 *          select ...
 *    四:视图的优缺点
 *       优点: 简化查询; 增加数据保密性
 *       缺点: 简化查询,但不能提高查询效率; 同时增加了维护成本
 *    五:使用时注意的问题
 *      1. 创建视图的 select 语句必须为所有的 计算列(非原始字段的列: 如聚合函数)指定 别名
 *         eg:
 *          create view v$_a as select avg(sal) from emp;   -- error
 *          create view v$_b as select avg(sal) "avg_sal" from emp;  -- ok
 *      2. 视图不是物理表, 是虚拟表
 *      3. 不建议通过视图更新视图所依附的原始表的数据或结果.
 * ---------------------------------------------------------------------------------------------------
 * 事务: 重要
 *   一: 为什么需要事务
 *     事务主要用来保证数据的合理性和并发处理的能力.
 *     通俗的说,
 *          事务可以避免数据处于一种不合理的中间状态. (一系列操作 要么全部成功,要么全部失败, 不能处于中间状态)   -- 转账问题
 *          利用事务可以实现多个用户对共享资源的同时访问(并发), 保证数据的合理性.   -- 并发访问
 *   事务和线程的关系
 *      都是通过锁来解决某些问题: 线程同步
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
public class Oracle$ {

    /**
     *  学习一门学科的基础
     * 1. 为什么需要 A: 如果没有 A 会有什么问题, 使用 A 会带来什么好处
     * 2. A 是什么: 定义(自己的理解)
     * 3. 怎么用 A
     * 4. 使用时注意的问题
     * 5. 应用场景/领域
     * 6. 特点(优缺点)
     */
    @Test
    public void test1() {
        int sum = 0;
        for (int i = 0; i < 14; i++) {
            sum += i;
        }
        System.out.println(sum);
    }
}
