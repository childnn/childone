package org.jspservlet.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see WebServlet#value() url-pattern 必须以 / 开头
 * @since 2020/10/31 17:28
 */
@WebServlet(value = "/createTable", initParams = {
        @WebInitParam(
                name = "url",
                value = "jdbc:mysql://localhost:3306/bookDB",
                description = "数据库连接信息"
        )
})
public class CreateTableServlet extends MySqlServlet {
    private static final long serialVersionUID = 2705567561393191150L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //ServletConfig config = getServletConfig();
        String url = getInitParameter("url");
        System.out.println("url = " + url);

        ResourceBundle ddl = ResourceBundle.getBundle("DDL");
        try (
                Connection conn = getConn();
                Statement stmt = conn.createStatement()

        ) {
            String string = ddl.getString("create.bookinfo");
            System.out.println("string = " + string);
            stmt.execute(string);
            String insert = ddl.getString("insert.bookinfo");
            stmt.execute(insert);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

}
