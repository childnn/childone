package org.anonymous.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author child
 * 2019/3/31 10:27
 */
//模糊查询: 条件查询
@WebServlet("/FindServlet")
public class FindServlet extends HttpServlet {
    private static final long serialVersionUID = -4395192818796689814L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解决 post 请求中文乱码
        request.setCharacterEncoding("utf-8");
        //接收数据
        Enumeration<String> names = request.getParameterNames();
        //调用 service
        //        ContactService.find(names);
    }
}
