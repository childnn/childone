package org.jspservlet.web.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see javax.servlet.ServletContext#log(String) 只记录消息本身
 * @see javax.servlet.GenericServlet#log(String) 日志消息前面会加上 servelt 名字
 * @since 2020/11/2 22:08
 * 两个方法会把日志信息写入到日志文件中哦.
 */
public class ErrorHandlerServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.service(req, resp);
        // 当 HTTP 发生错误时, Servlet 容器会自动将 HTTP 错误代码作为 javax.servlet.error.status_code 属性的值, 保存到请求对象中.
        Integer statusCode = (Integer) req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // 其他逻辑

        try {
            int a = 1 / 0;
        } catch (Exception e) {
            // 统一处理异常
            req.setAttribute(RequestDispatcher.ERROR_EXCEPTION, e);
            req.setAttribute(RequestDispatcher.ERROR_REQUEST_URI, req.getRequestURI());
            req.getRequestDispatcher("xxxHandler").forward(req, resp);
        }
    }
}
