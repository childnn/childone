package org.anonymous.web.servlet;

import org.anonymous.beans.Contact;
import org.anonymous.service.impl.ContactServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author child
 * 2019/3/29 11:13
 */
//本 servlet 专门用来 调用 service, 并将结果响应 给 retrieve.jsp
@WebServlet("/RetrieveServlet")
public class RetrieveServlet extends HttpServlet {
    private static final long serialVersionUID = -9202479589173417164L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取所有的联系人 对象
        List<Contact> list = ContactServiceImpl.getContact();
        request.getSession().setAttribute("list", list);
        //重定向: 添加数据成功后,自动跳转 到 RetrieveServlet 查询所有更新后的数据并显示到页面
        response.sendRedirect(request.getContextPath() + "/retrieve.jsp");
    }
}
