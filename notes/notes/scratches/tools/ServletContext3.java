package org.anonymous.web.servletContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author child
 * 2019/3/28 18:41
 */
/*
request 的方法
    获取 本项目下的 任意文件的真实路径: 没有该文件也会返回一个路径: request.getRealPath("文件名") -- 硬盘地址
        eg: D:\Develop\IDEA_Project\J2EE\out\artifacts\Day22TomcatAndServlet_war_exploded\jsp\ind.jsp
    获取 本 servlet 的 URL : request.getRequestURL() -- 统一资源定位符 --> ip:port/contextPath/servletPath
        eg: http://localhost:8080/Day22TomcatAndServlet_war_exploded/ss3
    获取 本 servlet 的 URL : /Day22TomcatAndServlet_war_exploded/ss3 --> contextPath/servletPath
        eg: /Day22TomcatAndServlet_war_exploded/ss3
    获取 本项目 虚拟路径： request.getContextPath() -- 常用
         jsp  页面,获取项目虚拟路径:  ${pageContext.request.contextPath}
*/

public class ServletContext3 extends HttpServlet {
    private static final long serialVersionUID = -6039035982452492247L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = request.getServletContext();
        String realPath = servletContext.getRealPath("/jsp/ind.jsp");
        System.out.println(realPath); //D:\Develop\IDEA_Project\J2EE\out\artifacts\Day22TomcatAndServlet_war_exploded\index.jsp
        String contextPath = servletContext.getContextPath();
        String path = request.getContextPath();
        System.out.println(contextPath); // /Day22TomcatAndServlet_war_exploded
        System.out.println(path); //当前项目虚拟名: 自带斜杠 /Day22TomcatAndServlet_war_exploded
        String servletPath = request.getServletPath();
        System.out.println(servletPath); //  /ss3
        String requestURI = request.getRequestURI();
        System.out.println(requestURI); // /Day22TomcatAndServlet_war_exploded/ss3
        StringBuffer requestURL = request.getRequestURL();
        System.out.println(requestURL); //http://localhost:8080/Day22TomcatAndServlet_war_exploded/ss3
        String remoteAddr = request.getRemoteAddr();
        System.out.println("remoteAddr = " + remoteAddr); //remoteAddr = 0:0:0:0:0:0:0:1
        String remoteUser = request.getRemoteUser();
        System.out.println("remoteUser = " + remoteUser); //null
        String remoteHost = request.getRemoteHost();
        System.out.println("remoteHost = " + remoteHost); //remoteHost = 0:0:0:0:0:0:0:1
        int remotePort = request.getRemotePort();
        System.out.println("remotePort = " + remotePort); //remotePort = 53244

    }
}
