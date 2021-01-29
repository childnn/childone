package org.anonymous.web.servlet;

import org.anonymous.beans.Admin;
import org.anonymous.service.ContactService;
import org.anonymous.util.ContactIServiceUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author child
 * 2019/4/1 18:47
 * 登录校验专用
 */
@WebServlet(urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = -5812241265161544328L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解 post 提交中文乱码
        request.setCharacterEncoding("utf-8");
        //接收登录用户的输入的数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String checkCode = request.getParameter("checkCode");
        //获取系统生成的 验证码
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        //移除前一次的 验证码: 一次性
        session.removeAttribute("code");
        //判断用户输入的信息
        //先判断验证码: 校验用户名密码 需要和数据库交互,先校验验证码可以提高效率,节省资源
        if (checkCode != null && checkCode.length() == 4 && code.equalsIgnoreCase(checkCode)) {
            //验证码正确,校验用户名和密码
            //非空校验
            if (username == null || password == null || "".equals(username) || "".equals(password)) {
                //请求转发
                request.setAttribute("errorMessage", "用户名或密码错误!");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                return;
            }
            //封装用户输入的信息为对象
            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password);
            //便于接口的扩展,使用接口
            ContactService contactService = ContactIServiceUtils.getContactService();
            Admin checkAdmin = contactService.checkAdmin(admin);
            if (checkAdmin != null) { //登录成功!跳转欢迎界面
                //重定向: 把 用户信息(对象) 放入 session 域中: 欢迎页面/filter 拦截 需要使用
                session.setAttribute("admin", admin);
                response.sendRedirect(request.getContextPath() + "/welcome.jsp");
            } else {
                //请求转发
                request.setAttribute("errorMessage", "用户名或密码错误!");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } else {
            //验证码错误,跳回登录页面.请求转发
            request.setAttribute("errorMessage", "验证码错误!");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            //            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
