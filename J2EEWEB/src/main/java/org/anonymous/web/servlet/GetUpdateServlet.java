package org.anonymous.web.servlet;

import org.anonymous.beans.Contact;
import org.anonymous.service.impl.ContactServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author child
 * 2019/3/29 13:20
 */
//接收 待 修改 contact 的 id : 到数据库中查找,返回给 update 页面 -- 回显
@WebServlet("/GetUpdateServlet")
public class GetUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = -5720570890198983731L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接数据: update 按钮 提交的 id
        String id = request.getParameter("id");
        Contact contact = ContactServiceImpl.getId(id);
        //把对象存入 session, 转到 修改页面: update.jsp
        request.getSession().setAttribute("contact", contact);
        response.sendRedirect(request.getContextPath() + "/update.jsp");
    }
}
