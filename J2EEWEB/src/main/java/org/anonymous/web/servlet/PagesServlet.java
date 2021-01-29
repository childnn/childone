package org.anonymous.web.servlet;

import org.anonymous.beans.PageBean;
import org.anonymous.service.impl.ContactServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * @author child
 * 2019/3/31 11:12
 * 分页:
 * 物理分页: (常用)
 * 特点: 每次点击下一页都会去数据库做查询
 * 优点: 能够获取到数据库最新的数据
 * 缺点: 频繁和数据库交互,效率不高 -- redis (重点:非关系型数据库 -- 没有表,存储键值对)
 * 逻辑分页:
 * 特点: 当点击下一页的数据, 将数据全部查询出来, 放在一个集合中
 * 在次点击下一页的时候, 并不是去数据库查询是去集合中查询
 * 优点: 不会频繁个数据库交互 效率高点
 * 缺点: 会延时获取数据库最新数据 (数据缺乏即时性)
 */
//检索,分页
@WebServlet(urlPatterns = "/pagesServlet")
public class PagesServlet extends HttpServlet {
    private static final long serialVersionUID = -27260936378239997L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    //得到 检索数据 的 map
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //当前页页码
        int pageNumber = 0;
        if (null != request.getParameter("pageNumber")) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        //每页显示的条数
        int pageSize = 3;
        //接收检索数据
        Map<String, String[]> map = request.getParameterMap();
        map.forEach((key, value) -> System.out.println(key + ":" + Arrays.toString(value)));
        PageBean pageBean = ContactServiceImpl.pages(pageNumber, pageSize, map);
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("map", map);
        //请求转发: 展示
        request.getRequestDispatcher("pages_search.jsp").forward(request, response);
    }
    //得到每个检索数据
   /* protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //解中文乱码
        request.setCharacterEncoding("utf-8");

        String name = request.getParameter("name");
        String qq = request.getParameter("qq");
        //当前页码
        //传过来的数据中 可能没有 pageNumber
//        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        String pageNumber1 = request.getParameter("pageNumber");
        System.out.println(pageNumber1);
        int pageNumber = 1;
        if (pageNumber1 != null) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        //每页数据条数
        int pageSize = 3;
        //传数据,获取 bean
        PageBean pageBean = ContactService.page(pageNumber, pageSize, name, qq);
        //传递数据
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("name", name);
        request.setAttribute("qq", qq);
        request.getRequestDispatcher("/pages_search.jsp").forward(request,response);
    }*/
}
