package org.anonymous.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author child
 * 2019/3/30 10:21
 */
//测试 checkbox 提交时的内容
// name == value
//不写 name 不会提交，不写 value 默认 为 “on”
//写了 value 但时 不赋值：value="", 则表示 value 为空字符串
@WebServlet("/DemoServlet")
public class DemoServlet extends HttpServlet {
    private static final long serialVersionUID = -4553966477543757923L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Map<String, String[]> map = request.getParameterMap();
        map.forEach((key, value) -> System.out.println(key + Arrays.toString(value)));
        System.out.println(request.getParameter("female"));*/
        Map<String, String[]> map = new HashMap<String, String[]>() {
            private static final long serialVersionUID = 2220565296790957355L;

            {
                put("name", new String[]{"1"});
            }
        };
        request.getSession().setAttribute("map", map);
        response.sendRedirect(request.getContextPath() + "/test.jsp");
        //        request.setAttribute("map", map);
        //        request.getRequestDispatcher("/test.jsp").forward(request,response);
    }
}
