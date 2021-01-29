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
 * 2019/3/29 12:57
 */
//接收 create.jsp 页面的表单数据,将数据存入数据库
@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {
    private static final long serialVersionUID = -2093536678466010399L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解乱码
        request.setCharacterEncoding("utf-8");
        //接数据
        Map<String, String[]> map = request.getParameterMap();
        Contact contact = new Contact();
        try {
            BeanUtils.populate(contact, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        //        System.out.println(contact); //测试
        //传数据: 到 service 层
        ContactServiceImpl.create(contact);
        //添加成功后,自动跳转 RetrieveServlet 显示新的 数据
        //        response.sendRedirect(request.getContextPath() + "/retrieve"); //展示
        response.sendRedirect(request.getContextPath() + "/pages"); //分页
    }
}
