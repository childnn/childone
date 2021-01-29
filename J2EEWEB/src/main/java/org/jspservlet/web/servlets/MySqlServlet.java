package org.jspservlet.web.servlets;

import org.jspservlet.config.InitUrl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see WebServlet#urlPatterns() 没有该参数, 不会被注册
 * <p>
 * 一个 Statement 对象在同一时刻只有一个打开的 ResultSet 对象, 在 Statement 接口中定义的所有
 * executeXxx() 方法都隐含的关闭 Statement 当前的 ResultSet 对象. 如果要执行多个查询语句,
 * 并且需要同时对它们的结果进行操作, 那么必须使用多个 Statement 对象.
 * -------------------------
 * 可滚动的结果集: {@link Connection#createStatement()} 返回的 Statement 对象创建的 ResultSet 只能向前滚动,
 * 即只能调用 {@link ResultSet#next()} 向前得到数据行, 当达到最后一条记录时, next() 方法将返回 false, 无法向后读取数据行.
 * @see Connection#createStatement(int, int) 可以获取一个 可滚动的结果集
 * 参数一: resultSetType
 * 1. {@linkplain ResultSet#TYPE_FORWARD_ONLY} 该结果集只能向前移动. 这是调用{@linkplain Connection#createStatement()} 默认类型
 * 2. {@linkplain ResultSet#TYPE_SCROLL_INSENSITIVE} 结果集可滚动, 但对数据库的变化不敏感
 * 3. {@linkplain ResultSet#TYPE_SCROLL_SENSITIVE} 结果集可滚动, 对数据库变化敏感.
 * 例如, 在程序中查询返回 10 条数据, 如果另一个程序删除其中 2 行, 则此结果集中就只有 8 行数据了.
 * 参数二: resultSetConcurrency 指定并发性类型
 * 1. {@linkplain ResultSet#CONCUR_READ_ONLY} 结果集不能用于更新数据库. 默认类型
 * 2. {@linkplain ResultSet#CONCUR_UPDATABLE} 结果集可用于更新数据库. 使用这个选项, 就可以在结果集中插入、删除或更新数据行,
 * 而这种改变将反映到数据库中.
 * @see Connection#prepareStatement(String, int, int) 同理
 * 有的 JDBC 驱动无法支持可滚动和可更新的结果集. 使用
 * {@linkplain DatabaseMetaData#supportsResultSetType(int)}
 * {@linkplain DatabaseMetaData#supportsResultSetConcurrency(int, int)}
 * 通过 {@linkplain Connection#getMetaData()} 获取 {@linkplain DatabaseMetaData} 实例.
 * -------------
 * @see ResultSet#isBeforeFirst()
 * @see ResultSet#isAfterLast()
 * @see ResultSet#isFirst()
 * @see ResultSet#isLast()
 * -- 判断游标是否位于第一行之前, 尾行之后, 第一行, 最后一行
 * @see ResultSet#beforeFirst() 移动游标到结果集第一行之前
 * @see ResultSet#afterLast() 移动游标到结果集最后一行
 * @see ResultSet#first() 移动游标到结果集第一行
 * @see ResultSet#last() 移动游标到结果集最后一样
 * @see ResultSet#absolute(int) 移动游标到结果集指定行
 * -- 参数可正可负. 正数相对于结果集 开始, 1 表示游标移动到第一行.
 * 负数表示相对于结果集 终点, -1 表示移动游标到最后一样
 * 0 表示移动游标到 首行 之前.
 * absolute(1) 等价于 first()
 * absolute(-1) 等价于 last()
 * @see ResultSet#previous() 当前行的前一行
 * @see ResultSet#relative(int) 相对当前行的位置. 可正可负, 0 表示当前行-不移动
 * ----------------
 * 可更新的结果集
 * 创建 Statement 对象时, 指定 {@linkplain ResultSet#CONCUR_UPDATABLE}
 * 这样, 对结果集中的数据进行编辑, 这种改变会影响数据库中的原始数据.
 * --
 * 更新一行: updateXxx() 方法
 * @see ResultSet#updateInt(String, int)  或
 * @see ResultSet#updateInt(int, int)
 * 只能修改当前行, 并不能修改数据库的实际数据, 在此后还要调用
 * @see ResultSet#updateRow() 将当前行的新数据更新到数据库.
 * 如果将游标移动到另一行而没有调用 updateRow 方法, 那么所有的更新将从结果集中被删除, 且这些更新不会被传到数据库中.
 * @see ResultSet#cancelRowUpdates() 放弃当前行的修改
 * @see ResultSet#rowUpdated() 判断当前行是否被更新
 * --
 * 插入一行: 在结果集插入一行, 并将这个新行提交到数据库中.
 * @see ResultSet#moveToInsertRow() 移动游标到 插入行, 插入行 是一个与可更新的结果集相联系的特殊的缓存行.
 * 当游标被防止到插入行时, 当前游标的位置被记录下来. 将游标移动到插入行后, 接下来调用 updateXxx() 方法, 设置行中的数据.
 * 最后, 在行数据设置完成后, 调用 {@linkplain ResultSet#insertRow()} 将新行传递给数据库, 从而在数据库中真正插入一行数据
 * 当游标在插入行时, 只有 updateXxx(), getXxx(), insertRow() 方法可被调用, 且在一个列上调用 getXxx() 方法之前,
 * 必须先调用 updateXxx() 方法. 可以调用 {@linkplain ResultSet#rowInserted()} 方法判断当前行是否是插入行.
 * --
 * 删除一行
 * 可以调用 {@linkplain ResultSet#deleteRow()} 从结果集和数据库删除一行, 当游标指向插入行的时候, 不能调用这个方法.
 * 一个被删除的行可能在结果集中留下一个空的位置, 可以调用 {@linkplain ResultSet#rowDeleted()} 方法判断一行是否被删除
 * 可更新结果集的使用必须满足以下 3 个条件:
 * 1. 只能针对数据库中单张表的查询
 * 2. 查询语句不能包含任何 join 操作
 * 3. 查询操作的表中必须有主键, 而且在查询的结果集中必须包含作为主键的字段.
 * 此外, 如果在结果集上执行插入操作, 那么 SQL 查询还应该满足以下了两个条件:
 * 1. 查询操作必须喧杂数据库表中所有不能为空的列
 * 2. 查询操作必须选择所有没有默认值的列.
 * @since 2020/10/31 16:44
 */
@WebServlet(urlPatterns = "/init", initParams = {
        @WebInitParam(name = "url", value = "jdbc:mysql://localhost:3306", description = "创建数据库之前的连接信息"),
}, loadOnStartup = 0)
@WebListener
public class MySqlServlet extends HttpServlet implements InitUrl, ServletContextListener {
    private static final long serialVersionUID = -854917044778028983L;

    private String username;
    private String password;
    private String driverClass;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext ctx = sce.getServletContext();
        //driverClass = ctx.getInitParameter("driverClass");
        //username = ctx.getInitParameter("username");
        //password = ctx.getInitParameter("password");
        System.out.println("ctx = " + ctx);
        //System.out.println("driverClass = " + driverClass);
        System.out.println(this); // 这是作为 listener 创建的实例
    }

    @Override
    public void init() throws ServletException {
        System.out.println(this); // 这是作为 servlet 创建的实例

        String contextPath = getServletContext().getContextPath();
        System.out.println("contextPath = " + contextPath);

        try (
                Connection conn = getConn();
                Statement stmt = conn.createStatement()

        ) {
            // mysql 不区分大小写
            stmt.execute("CREATE DATABASE IF NOT EXISTS bookDB"); // executeUpdate() 方法 也可以
            System.out.println("数据库连接成功!");
        } catch (ClassNotFoundException e) {
            throw new ServletException("数据库驱动加载失败!", e);
        } catch (SQLException e) {
            throw new ServletException("数据库错误!", e);
        }
    }

    protected Connection getConn() throws SQLException, ClassNotFoundException {
        // context-init-params
        ServletContext ctx = getServletContext();
        driverClass = ctx.getInitParameter("driverClass");
        username = ctx.getInitParameter("username");
        password = ctx.getInitParameter("password");

        // servlet-init-params
        String url = getInitParameter("url");

        // register driver
        Class.forName(driverClass);

        return DriverManager.getConnection(url, username, password);
    }

    protected void release(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ignore) {
            }
        }
    }

    protected void release(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ignore) {
            }
        }
    }

    protected void release(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ignore) {
            }
        }
    }


    @Override
    public String onStartup(String url) {
        System.out.println("url = " + url);
        return url;
    }
}
