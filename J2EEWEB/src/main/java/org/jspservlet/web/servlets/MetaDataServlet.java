package org.jspservlet.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jspservlet.entity.TableMetaAndBooks;
import org.jspservlet.entity.TableMetaData;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/10/31 16:43
 * 用于描述数据据库及其各个组成部分的数据称为元数据
 * MySQL 连接参数:
 * nullCatalogMeansCurrent=true
 * 该参数表示在使用 {@link DatabaseMetaData#getTables(java.lang.String, java.lang.String, java.lang.String, java.lang.String[])}
 * 方法查询表信息时, 如果参数一 catalog 实参为 null, 则表示当前数据库(即 catalog)
 * 可以去掉此参数看变化(在不指定 catalog 值时, 会返回其他数据库的表). MySQL-5 该参数值默认为 true.
 */
@WebServlet(urlPatterns = "/meta", initParams = {
        @WebInitParam(name = "url", value = "jdbc:mysql://localhost:3306/bookdb?nullCatalogMeansCurrent=true", description = "数据库连接信息")
})
public class MetaDataServlet extends MySqlServlet {
    public static final ObjectMapper om = new ObjectMapper();
    private static final long serialVersionUID = -1512206709370191692L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        ResultSet rs = null;
        Statement stmt = null;
        try (
                final Connection conn = getConn()
        ) {

            String tableName = req.getParameter("tableName");

            // 列出所有表
            if (StringUtils.isEmpty(tableName)) {
                DatabaseMetaData dbMeta = conn.getMetaData();
                /*
                    参数一: 数据库名
                    参数二:
                    参数三: 表明
                    参数四: 表类型. 如 视图等, 详见 API 文档.
                 */
                rs = dbMeta.getTables(null/*"bookdb"*/, null, null, new String[]{"TABLE"});
                List<String> tableNames = new ArrayList<>();

                while (rs.next()) {
                    String tName = rs.getString("TABLE_NAME");
                    System.out.println("tName = " + tName);
                    String tableType = rs.getString("table_type"); // 不区分大小写
                    System.out.println("table_type = " + tableType);
                    // String tableSchem = rs.getString("TABLE_SCHEM");
                    // System.out.println("tableSchem = " + tableSchem);
                    // ResultSetMetaData metaData = rs.getMetaData();
                    // System.out.println("metaData = " + metaData);
                    tableNames.add(tName);
                }
                String s = om.writeValueAsString(tableNames);
                System.out.println("s = " + s);
                out.println(om.writeValueAsString(tableNames));
                out.close();
            } else { // 查询指定表信息
                System.out.println("查询表信息: " + tableName);
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM " + tableName);

                // 获取表的结构信息: metadata
                ResultSetMetaData rsMetaData = rs.getMetaData();
                int columnCount = rsMetaData.getColumnCount(); // 字段数量
                System.out.println("columnCount = " + columnCount);
                List<TableMetaData> tableMetaDataList = new ArrayList<>(); // 每一个字段的信息
                for (int i = 1; i <= columnCount; i++) {
                    TableMetaData tableMetaData = new TableMetaData();
                    tableMetaData.setColumnName(rsMetaData.getColumnName(i)); // column 从 1 开始
                    tableMetaData.setColumnTypeName(rsMetaData.getColumnTypeName(i));
                    tableMetaData.setColumnClassName(rsMetaData.getColumnClassName(i));
                    tableMetaData.setColumnDisplaySize(rsMetaData.getColumnDisplaySize(i));
                    tableMetaDataList.add(tableMetaData);
                }
                // 获取表中的数据
                List<Map<String, Object>> books = new ArrayList<>();

                while (rs.next()) { // 每次循环是一条记录
                    Map<String, Object> abook = new HashMap<>(); // 一本书的信息
                    // column 从 1 开始
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = rsMetaData.getColumnName(i); // 数据库字段名
                        Object columnValue = rs.getObject(columnName); // 值
                        abook.put(columnName, columnValue); // 此处可以改为对象形式, 不使用 map
                    }
                    books.add(abook);
                }
                System.out.println(books);
                // om.writeValueAsString(tableMetaDataList);
                // om.writeValue(out, tableMetaDataList);
                // om.writeValue(out, books);
                TableMetaAndBooks tableMetaAndBooks = new TableMetaAndBooks();
                tableMetaAndBooks.setBooks(books);
                tableMetaAndBooks.setTableMetaDatum(tableMetaDataList);

                om.writeValue(out, tableMetaAndBooks);

                out.flush();
                out.close();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            release(rs);
            release(stmt);
        }
    }
}
