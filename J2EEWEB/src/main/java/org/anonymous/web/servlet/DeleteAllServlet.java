package org.anonymous.web.servlet;

import org.anonymous.service.impl.ContactServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author child
 * 2019/3/29 19:07
 */
//删除所有数据:不需要接收数据
@WebServlet("/DeleteAllServlet")
public class DeleteAllServlet extends HttpServlet {
    private static final long serialVersionUID = 4285934225980958161L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ContactServiceImpl.deleteAll();
        //重定向: 删除完毕,返回 查询页面,显示 联系人为空的信息 -- 略过 跳转 RetrieveServlet 去数据库查询的环节,提高效率
        request.getSession().setAttribute("list", null); //直接传入空: 配合 RetrieveServlet 传的 list
        //        response.sendRedirect(request.getContextPath() + "/retrieve.jsp"); //原展示页面
        response.sendRedirect(request.getContextPath() + "/pages");
    }
}
