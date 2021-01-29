package org.anonymous.web.servlet;

import org.anonymous.beans.Contact;
import org.anonymous.service.impl.ContactServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author child
 * 2019/3/29 17:17
 */
//把 修改后的 信息 传到数据库: 来自 update.jsp 页面
@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = -5720570890198983731L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解 post 接收中文乱码
        request.setCharacterEncoding("utf-8");
        //接收数据
        Map<String, String[]> map = request.getParameterMap();
        //数据封装对象
        Contact contact = new Contact();
        try {
            BeanUtils.populate(contact, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //转 service 层
        ContactServiceImpl.update(contact);
        //响应: 重定向到 RetrieveServlet, 显示新数据
        //        response.sendRedirect(request.getContextPath() + "/retrieve");
        response.sendRedirect(request.getContextPath() + "/pages");
    }
}
