package org.anonymous.se;

/**
 * @author child
 * 2019/5/4 14:14
 * day01
 * 1. 并行与并发
 * 并行 parallel: 多个事件(任务)同一时刻执行
 * 并发 concurrent: 一个时间段内,多个任务交替执行
 * 2. jsp 和 servlet 区别
 * jsp 本质上就是一个 servlet, jsp 会在接到请求之后,编程 .java 文件,进而编译得到 .class 文件, 保存在 work 目录下
 * jsp 具有 servlet 中的所有域对象
 * jsp 用于页面展示数据,servlet 用于接收页面数据和向页面传递数据
 * jsp 中可以写 HTML/js 标签
 * 3. jsp 内置对象
 * out: Javax.servlet.jsp.JspWriter                    -- 用来向页面输出数据
 * request: Javax.servlet.http.HttpServletRequest      -- 请求对象
 * response: Javax.servlet.http.HttpServletResponse    -- 响应对象
 * session: Javax.servlet.http.HttpSession             -- 服务端的会话域对象
 * pageContext: javax.servlet.jsp.PageContext          -- jsp 特有,可以获取其他域对象
 * application: javax.servlet.ServletContext           -- 服务器对象
 * config: javax.servlet.ServletConfig                 -- 当前 jsp 页面的配置对象
 * page: java.lang.Object                              -- 当前 jsp 页面本身 (this)
 * exception: java.lang.Exception                      -- 异常对象: 404. 500 etc
 * 4. session 工作原理
 * 服务端的会话技术,可以将数据保存在 session 域中
 * session 依赖于 cookie
 * 在同一次会话中(浏览器不关闭),当第一次调用 request.getSession() (第一次请求)时, 创建 session 对象,
 * 此后,浏览器的请求都会 携带 JSESSIONID, 再次调用 getSession() 方法,得到的还是第一次创建的 session 对象
 * 若浏览器关闭,意味着本次会话结束,session 并没有立即销毁,只是无法再通过 JSESSIONID 获取到 相同的 session
 * session 域中的数据可以被 一次会话中的 多次请求(多个 request 对象) 共享, 可以存储任意类型数据
 * session 的销毁:
 * 1). 服务器非正常关闭
 * 2). session.invalidate()
 * 3). 超时失效: 默认 30 分钟
 * 5. final, finally, finalize
 * final: 用来修饰 类, 方法, 变量
 * 被 final 修饰的 类 不能被继承
 * 被 final 修饰的 方法 不能被重写
 * 被 final 修饰的 变量(成员/局部) 不可变, 且必须被初始化
 * finally: try..finally 或 try..catch..finally
 * finally 代码块中的代码一定会执行,无论有无异常发生
 * finalize: Object 类的方法
 * /@Deprecated(since="9")
 * protected void finalize() throws Throwable {}
 * 与垃圾回收(garbage collector)有关
 * 6. 设计模式
 * 工厂模式: factory
 * 构建者模式: builder
 * 代理模式: proxy
 * 单例模式: singleton
 * 原型(多例)模式: prototype
 * 7. spring 事务管理方式
 * 基于 aspectj 的事务: 自定义事务管理器(切面类)
 * 声明式事务: spring 的事务管理器:  DataSourceTransactionManager
 * 8. mybatis #{} 与 ${} 区别
 * #{}: ognl 表达式, 会被解析成占位符"?", 底层使用 preparedStatement, 防止 sql 注入, 预加载,提高效率
 * '${}': 非 占位符形式, 使用 sql 拼接, 底层是 statement, 会有 sql 注入风险
 * 9. mybatis 和 hibernate
 * mybatis: 可以进行更为细致的 sql 优化
 * hibernate: ORM 框架, 功能强大, 数据库无关性好
 * 10. 乐观锁,悲观锁
 * 悲观锁: 在整个数据修改过程中,数据将处于锁定状态
 * 乐观锁: 相对于悲观锁而言, 大多是基于数据版本（ Version ）记录机制实现。
 * 即为数据增加一个版本标识，在基于数据库表的版本解决方案中，一般是通过为数据库表增加一个 “version” 字段来实现。
 * 读取出数据时，将此版本号一同读出，之后更新时，对此版本号加一。
 * 此时，将提交数据的版本数据与数据库表对应记录的当前版本信息进行比对，
 * 如果提交的数据版本号大于数据库表当前版本号，则予以更新，否则认为是过期数据。
 * sql:
 * 1. 查询 '005' 课程的 学生学号, 成绩: 学生表, 课程表, 成绩表
 * select s.sid, sc.score from student s, course c, sc sc where s.sid = sc.sid and c.cid = sc.cid and c.cid = '005';
 * 查询 '005' 课程比 '006' 课程成绩高的学生学号
 * select c5.sid
 * from (select s.sid, sc.score from student s, course c, sc sc where s.sid = sc.sid and c.cid = sc.cid and c.cid = '005') c5,
 * (select s.sid, sc.score from student s, course c, sc sc where s.sid = sc.sid and c.cid = sc.cid and c.cid = '006') c6
 * where c5.sid = c6.sid and c5.score > c6.score;
 * 2. 查询 '8888' 编号同学的课程编号: 学生表, 课程表, 成绩表
 * select c.cid from student s, course c, sc sc where s.sid = sc.sid and c.cid = sc.cid and s.sid = '8888';
 * 查询学生的 课程编号, 学生编号, 学生姓名: 学生表, 课程表, 成绩表// 按学号分组
 * select c.cid, s.sid, s.sname from student s, course c, sc sc where s.sid = sc.sid and c.cid = sc.cid group by s.sid;
 * 查询 至少有一门课 与 学号 '8888' 同学所学 相同的 学生学号,姓名
 * select s1.sid, s1.sname
 * from (select c.cid, s.sid, s.sname from student s, course c, sc sc where s.sid = sc.sid and c.cid = sc.cid group by s.sid) s1
 * where s1.cid in (select c.cid from student s, course c, sc sc where s.sid = sc.sid and c.cid = sc.cid and s.sid = '8888');
 * 3. 查询 学生学习课程编号,学生姓名: student, course, sc
 * select c.cid, s.sname from student s, course c, sc sc where s.sid = sc.sid and c.cid = sc.cid;
 * 查询 '陈奕迅' 老师讲授的课程编号: teacher, course
 * select c.cid from teacher t, course c where t.tid = c.tid and t.tname='eason';
 * 查询没学过 'eason' 讲授任一门 课程的学生姓名
 * select s1.sname
 * from (select c.cid, s.sname from student s, course c, sc sc where s.sid = sc.sid and c.cid = sc.cid) s1
 * where s1.cid not in (select c.cid from teacher t, course c where t.tid = c.tid and t.tname='eason');
 * 4. 查询 课程编号 '002' 的学生成绩,学号,姓名: course, student, sc
 * select s.sid, s.sname, sc.score from student s, course c, sc sc where s.sid = sc.sid and c.cid = sc.cid and c.cid = '002';
 * 查询 '002' 课程成绩 低于 '001' 课程成绩的 学生姓名,学号
 * select s1.sid, s1.sname
 * from (select s.sid, s.sname, sc.score from student s, course c, sc sc where s.sid = sc.sid and c.cid = sc.cid and c.cid = '001') s1,
 * (select s.sid, s.sname, sc.score from student s, course c, sc sc where s.sid = sc.sid and c.cid = sc.cid and c.cid = '002') s2
 * where s1.sid = s2.sid and s1.score > s2.score;
 * day02-sql:
 * 5. 查询平均成绩大于 85 的 学生学号,姓名,平均成绩: 学生表, 成绩表
 * select s.sid, s.sname, avg(sc.score)
 * from student s, sc sc
 * where s.sid = sc.sid group by s.sid;
 * 6. 查询 '语文1' 低于 60 的学生姓名,分数: 学生表,课程表,成绩表
 * select s.sname, sc.score
 * from student s, course c, sc sc
 * where s.sid = sc.sid and c.cid = sc.cid
 * and sc.score < 60;
 * 7. 查询所有学生选课
 * select * from student s left outer join sc sc on s.sid = sc.sid left outer join course c on sc.cid = c.cid;
 * 8. 查询任意一门课程成绩在 70 分以上的 学生姓名,课程名称,分数
 * select s.sname, c.cname, sc.score
 * from student s, course c, sc sc
 * where s.sid = sc.sid and c.cid = sc.cid
 * and sc.score > 70;
 * day03-sql
 * 9. 查询学生平均成绩及其名次: 学生表,成绩表
 * select avg(sc.score) a
 * from student s, sc sc
 * where s.sid = sc.sid
 * group by s.sid
 * order by a desc;
 * 10. 查询各科成绩前三名记录????
 * select sc.score o
 * from student s, sc sc
 * where s.sid = sc.sid
 * group by s.sid
 * order by o;
 * 11. 查询每门课程被选修的学生数: 课程表,学生表,成绩表
 * select c.cid, count(s.sid)
 * from student s, course c, sc sc
 * where s.sid = sc.sid and c.cid = sc.cid
 * group by c.cid;
 * 12. 查询只选修了一门课的学生学号,姓名: ???
 * 查询每个学生选课
 * select * from student s left outer join sc sc on s.sid = sc.sid left outer join course c on sc.cid = c.cid;
 * day04-sql
 * 13. 查询男/女生人数
 * select count(*) from student s group by s.ssex;
 * 14. 名字带有 '蓝' 的学生名单
 * select s.sname from student s where s.sname like '%蓝%';
 * 15. 同名同姓学生名单
 * select s.sname from student s, (select * from student) s1 where s.sname = s1.sname;
 * 同名人数: ..
 * 16. 每门课平均成绩, 升序; 成绩相同, 按课程编号降序
 * <p>
 * <p>
 * day02
 * 1. String 不是基础数据类型,
 * 基础数据类型: byte, short, int, long, float, double, char, boolean
 * 2. springMVC 执行流程
 * 浏览器发送请求,前端控制器(DispatcherServlet) 接受请求,通过 处理器映射器(HandlerMapping) 和 处理器适配器(HandlerAdapter),
 * 根据指定映射关系, 将页面数据发送到 指定的控制器 controller.
 * 前端控制器再将控制器的数据与视图解析器结合,将结果返回 浏览器
 * 3. day01-7
 * 4. spring 依赖注入方式
 * xml: constructor/set
 * 注解: @Autowired
 * 5. Math.round(-11.5) 结果: 四舍五入
 * -11
 * Math.round(11.5) == 12
 * 6. == 与 equals
 * == 比较逻辑地址值
 * equals 比较内容 (前提是重写 equals 方法)
 * 7. final: day01-5
 * 8. String 常用方法
 * charAt()
 * concat()
 * contains()
 * endsWith()
 * equals()
 * equalsIgnoreCase()
 * getBytes()
 * indexof()
 * isEmpty()
 * lastIndexOf()
 * length()
 * replace()
 * repalceAll()
 * split()
 * startsWith()
 * subString()
 * toCharArray()
 * toUpperCase()
 * toLowerCase()
 * valueOf()
 * 9. 接口与抽象类
 * 接口与子类之间是实现关系(接口与子接口之间是继承关系), 抽象类与子类之间是继承关系
 * 一个类可以实现多个接口, 一个类只能继承一个类, 一个接口可以继承多个接口
 * 接口中没有构造方法,抽象类中有构造方法, 二者都不能被实例化
 * 抽象类是对类抽象,接口是对行为抽象.抽象类是对整个类整体进行抽象,包括属性、行为,但接口是对类局部（行为）进行抽象。
 * 抽象类是自底向上抽象而来，接口是自顶向下设计而来
 * 对抽象类而言，父类和派生类之间的概念在本质上是相同的 -- 属于同类
 * 对接口而言，父接口与实现类在概念上不要求一致 -- 不要求同类，而只是实现功能
 * 抽象类中可以有: 成员变量,常量,静态方法,抽象方法(必须显式生命 public abstract),构造方法
 * 接口中可以有: private/private static/public static/public default/public abstract(不写默认就有)
 * 常量(public static final 修饰, 没有普通成员变更)
 * 10. 同 7
 * <p>
 * day03
 * 1. list, set, map
 * 单列集合: 顶层接口 collection
 * list: 元素有序,可重复,有索引
 * set: 元素不重复,无索引 // LinkedHashSet 有序, HashSet 无序
 * 双列集合: 顶层接口 map
 * 键值对集合: k-v
 * key: 本质上是 一个 set 集合, 唯一
 * value: 本质上是一个 普通单列集合, 可重复
 * 2. HashMap 与 Hashtable
 * 二者都实现了 map 接口
 * HashMap 继承自 AbstractMap 类, Hashtable 继承自 Dictionary 类(已被遗弃)
 * 二者几乎等价, HashMap 非同步, 接受 null 值的 k/v, - Collections.synchronizedMap(hashMap) 实现同步
 * Hashtable 同步, 不接受 null 值的 k/v - 同步方面被 ConcurrentHashMap 取代
 * 另, 迭代方式不同, Hashtable 使用 Enumeration 方式; HashMap 使用 fail-fast 迭代器(存在并发修改异常)
 * Hashtable 在 jdk1.8 之后加入 fail-fast 机制
 * 初始容量与扩容: Hashtable 默认初始大小 11, 此后每次扩容变为 2n+1; HashMap 默认初始大小 16, 此后每次扩容为原来 2 倍
 * 3. ArrayList 与 LinkedList
 * ArrayList: 可以认为是动态数组, 增删慢,查询快
 * LinkedList: 基于链表, 增删快,查询慢
 * 4. mysql 与 oracle
 * mysql 中小型数据库, oracle 大型数据库
 * mysql 主键可以使用 auto_increment, oracle 没有自动增长,主键一般使用 序列
 * mysql 单双引号等价, oracle 只有别名使用双引号,其他均使用单引号
 * mysql 分页语法简单 limit , oralce 分页 rownum, 只能写 rownum < n, 不能写大于
 * mysql 以数据库为管理表的基本单位, oracle 以 用户 作为管理表的基本单位
 * 5. day01-8
 * 6. mysql 数据库优化: 5 大范式
 * 7. redis 与 mysql 事务
 * 8. redis 数据类型
 * key: 字符串
 * value: 5 种, 字符串, 散列(hash), 列表(list), 集合(set), 有序集合(zset)
 * 9. redis 特点(非关系型数据库与关系型数据库比较)
 * 关系型数据库: mysql, oracle, db2
 * 特点: 数据库数据都在硬盘上,数据与数据之间存在关系,即表于表之间的关系(一对一,一对多,多对多)
 * 优点: 数据安全性,完整性高
 * 缺点: 查询慢(读取硬盘文件,查询数据: io)
 * 非关系型数据库: redis, mongodb
 * 特点: 数据都在内存中, 数据和数据之间不存在关系, 都是 k-v 形式 (key 字符串,value 5 种数据类型), 一般来说,依赖于 关系型数据库的数据
 * 优点: 查询快
 * 缺点: 数据安全性,完整性相对较低
 * 10. redis 与 memecache
 * <p>
 * day04
 * 1. 时间复杂度与空间复杂度
 * 时间复杂度 time complexity: 算法运行时间, 分为常数,指数,线性
 * 空间复杂度 space complexity: 算法运行过程中占用 存储空间(内存)大小
 * 2. 二叉树 及其遍历
 * 最多只有两个 子树的树结构称为 二叉树
 * 遍历方式: 先序/中序/后序遍历
 * 3. 队列与栈
 * 队列: 先进先出 FIFO, 入口出口各处一侧
 * 栈: 先进后出 FILO, 入口出口同一侧
 * 4. OSI 分层
 * 5. 进程与线程
 * 一个进程可以包含多个线程
 * 线程是资源分配的最小单位
 * 6. 同步与异步, ajax 请求 get/post 区别
 * 同步: 浏览器请求之后,必须等待服务器的响应完成才能进行接下来的操作
 * 异步: 浏览器的请求与服务器的响应互不影响,浏览器在服务器响应过程中,可以自由执行其他操作
 * 7. day03-1
 * 8. sesssionFactory 是线程安全的, session 是 非线程安全的
 * 9. servlet 生命周期
 * 默认第一次访问时,创建 servlet(单例); 也可以配置,在服务器启动时创建(load-on-startup)
 * 关闭服务器(结束项目部署),servlet 销毁
 * 10. jsp 常用指令
 */
public class Demo {

    public static void main(String[] args) {
        long round = Math.round(-11.51);
        long round0 = Math.round(-11.5);
        System.out.println(round); //-12
        System.out.println(round0); //-11
        long round3 = Math.round(11.5);
        System.out.println(round3); //12
        long round1 = Math.round(0.49);
        long round2 = Math.round(0.51);
        System.out.println(round1 + ":" + round2);
    }

}
