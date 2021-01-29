package org.anonymous.web.servlet;

import org.anonymous.service.impl.ContactServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author child
 * 2019/3/29 22:24
 */
//删除所选
@WebServlet("/DeleteSelectedServlet")
public class DeleteSelectedServlet extends HttpServlet {
    private static final long serialVersionUID = 4493630187218135535L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接数据: 被选中的 checkbox 的 id
        Enumeration<String> enumeration = request.getParameterNames(); //返回 names: 枚举
        ContactServiceImpl.deleteSelected(enumeration);
        //删除完毕跳转 RetrieveServlet: 显示最新信息 -- 重定向
        //        response.sendRedirect(request.getContextPath() + "/retrieve");
        response.sendRedirect(request.getContextPath() + "/pages");

        //测试
        //        Map<String, String[]> map = request.getParameterMap(); //返回键值对: name == values(数组)
     /*   while (enumeration.hasMoreElements()) { //测试
            System.out.println(enumeration.nextElement());
        }
        String[] values = request.getParameterValues("1"); //返回 values: 数组
        System.out.println(Arrays.toString(values));*/
    }
}
