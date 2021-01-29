package org.anonymous.web.servlet;

import org.anonymous.beans.Admin;
import org.anonymous.service.impl.ContactServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author child
 * 2019/3/29 11:11
 */
//本 servlet 专门用来 校验 验证码/用户名,密码 --  首页登录 index.jsp
@WebServlet("/WebServlet0")
public class WebServlet0 extends HttpServlet {
    private static final long serialVersionUID = -7541949900324547540L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解post 请求乱码
        request.setCharacterEncoding("utf-8");
        //接数据
        //先校验 验证码
        //页面提交的验证码
        String checkCode = request.getParameter("checkCode");
        //系统生成的验证码
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        if (checkCode != null && checkCode.equalsIgnoreCase(code)) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Admin admin = ContactServiceImpl.check(username, password);
            if (null != admin) {
                //查询到用户,登陆成功: 跳转 欢迎页面 -- welcome.jsp
                //把 登录用户的数据带到 welcome.jsp 页面
                session.setAttribute("admin", admin);
                response.sendRedirect(request.getContextPath() + "/welcome.jsp");
            } else {
                session.setAttribute("error", "用户名或密码错误!");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
        } else {
            session.setAttribute("error", "验证码错误!");
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
