package org.jspservlet.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Enumeration;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/10/31 23:21
 */
public class DynamicServlet extends HttpServlet {
    private static final long serialVersionUID = -1971070953557381453L;

    @Override
    public void init() throws ServletException {
        System.out.println("测试非 web.xml, 非 @WebServlet 的注册");
        Enumeration<String> names = getInitParameterNames();
        while (names.hasMoreElements()) {
            String key = names.nextElement();
            System.out.println(key + ": " + getInitParameter(key));
        }
    }
}
