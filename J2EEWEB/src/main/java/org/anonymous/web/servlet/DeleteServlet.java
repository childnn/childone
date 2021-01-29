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
 * 2019/3/29 17:42
 */
//删除数据
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 5699580682064094803L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收 待删除数据的 id
        String id = request.getParameter("id");
        //调用service
        ContactServiceImpl.delete(id);
        //响应: RetrieveServlet
        //response.sendRedirect(request.getContextPath() + "/retrieve");
        response.sendRedirect(request.getServletContext().getContextPath() + "/retrieve");
    }
}
