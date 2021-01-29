package org.jspservlet.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/10/31 10:37
 */
@WebServlet(urlPatterns = "/search", initParams = {
        @WebInitParam(name = "", value = "jdbc:mysql://localhost:3306/bookDB", description = "创建数据库之后的连接信息")
})
public class SearchServlet extends MySqlServlet {

    private static final long serialVersionUID = -1717260702379126316L;

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final String SEARCH_CONDITION_ALL = "all";
    private static final String SEARCH_CONDITION_PRECISION = "precision";
    private static final String SEARCH_CONDITION_KEYWORD = "keyword";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(DEFAULT_CHARSET.name());

        String condition = req.getParameter("cond"); // 请求参数

        if (isEmpty(condition)) {
            resp.sendRedirect("search.html");
            return;
        }

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        ResultSet rs = null;
        try (
                final Connection conn = getConn();
                final Statement stmt = conn.createStatement()
        ) {
            switch (condition) {
                case SEARCH_CONDITION_ALL:
                    rs = stmt.executeQuery("select * from bookinfo");
                    printBookInfo(out, rs);
                    out.close(); // 结果写回页面
                    break;
                case SEARCH_CONDITION_PRECISION: {
                    String title = req.getParameter("title");
                    String author = req.getParameter("author");
                    String bookConcern = req.getParameter("bookConcern");
                    boolean isTitlePresent = isEmpty(title);
                    boolean isAuthorPresent = isEmpty(author);
                    boolean isBookConcernPresent = isEmpty(bookConcern);

                    if (isTitlePresent && isAuthorPresent && isBookConcernPresent) {
                        resp.sendRedirect("search.html");
                        return;
                    }
                    StringBuilder sql = new StringBuilder("select * from bookinfo where 1 = 1");
                    if (!isTitlePresent) {
                        sql.append(" and title = '").append(title).append("'");
                    }
                    if (!isAuthorPresent) {
                        sql.append("and author = '").append(author).append("'");
                    }
                    if (!isBookConcernPresent) {
                        sql.append(" and bookconcern = '").append(bookConcern).append("'");
                    }
                    rs = stmt.executeQuery(sql.toString());
                    printBookInfo(out, rs);
                    out.close();
                    break;
                }
                case SEARCH_CONDITION_KEYWORD: {
                    String keyword = req.getParameter("keyword");
                    if (isEmpty(keyword)) {
                        resp.sendRedirect("search.html");
                        return;
                    }
                    String sql = "select * from bookinfo where title like '%" + keyword + "%'";
                    rs = stmt.executeQuery(sql);
                    printBookInfo(out, rs);
                    out.close();
                    break;
                }
                default:
                    resp.sendRedirect("search.html");
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            release(rs);
        }

    }

    private void printBookInfo(PrintWriter out, ResultSet rs) throws SQLException {
        out.println("<html><head>");
        out.println("<title>图书信息</title>");
        out.println("</head><body>");
        out.println("<table border=1><caption>图书信息</caption>");
        out.println("<tr><th>书名</th><th>作者</th><th>出版社</th><th>价格</th><th>发行日期</th></tr>");

        // ResultSet 对象的起始游标在第一行之前, 所以需要先调用一次 next() 方法
        while (rs.next()) {
            out.println("<tr>");
            out.println("<td>" + rs.getString("title") + "</td>");
            out.println("<td>" + rs.getString("author") + "</td>");
            out.println("<td>" + rs.getString("bookconcern") + "</td>");
            out.println("<td>" + rs.getString("price") + "</td>");
            out.println("<td>" + rs.getString("publish_date") + "</td>");
            out.println("</tr>");
        }
        out.println("</table></body></html>");
    }
}
