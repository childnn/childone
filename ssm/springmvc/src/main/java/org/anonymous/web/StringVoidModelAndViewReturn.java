package org.anonymous.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author child
 * 2019/4/21 14:13
 * 通过不同的返回值 实现
 * 1. 数据响应: 域对象中存的参数
 * 2. 页面跳转: 请求转发/重定向
 * 三种返回值:
 * String, void, ModelAndView
 */
@Controller("str")
@RequestMapping("/string")
public class StringVoidModelAndViewReturn {

    /**
     * 返回值 string 类型 实现 数据响应/页面跳转(请求转发/重定向)
     * 1. 数据响应： 把数据放在 request 域中
     * 2. 页面跳转：
     * 默认请求转发
     * 返回的 url:
     * 物理地址: 地址全名 --> /jsp/demo.jsp
     * 逻辑地址: 经过视图解析器的配置前后缀的简写形式 --> demo
     * 默认情况下使用 请求转发方式,
     * 如果没有配置 试图解析器 -- 就是 物理地址(全名)
     * 如果配置了 视图解析器 -- 就是 逻辑地址(简写)
     * 自己的方式设置地址: 只能是物理地址
     * return "forward:/jsp/demo.jsp"; -- 请求转发
     * return "redirect:/jsp/demo.jsp"; -- 重定向
     */
    @RequestMapping("/test1")
    public String test1(HttpServletRequest request) {
        request.setAttribute("msg", "我是string返回值...");
        //        return "success"; //逻辑地址:url 的简写
        return "forward:/jsp/success.jsp"; //自定义方式设置地址: 请求转发
        //        return "redirect:/jsp/success.jsp"; //重定向:两次请求, request 域中的数据不会共享 - 浏览器得到 null(jsp格式<%%>)/空字符串(el表达式${})
        //        return "forward:/WEB-INF/jsp/test.jsp"; //请求转发: 访问 web-inf 目录下的资源
        //        return "redirect:/WEB-INF/jsp/test.jsp"; //重定向: 不能访问 web-inf 目录下的资源: 404
    }

    /**
     * void 返回值, 实现 数据响应/页面跳转(请求转发/重定向)
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/test2")
    public void test2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("msg", "我是void返回值...");

        //请求转发:相对路径
        //        request.getRequestDispatcher("/jsp/success.jsp").forward(request, response);
        //重定向
        response.sendRedirect(request.getContextPath() + "/jsp/success.jsp");
    }

    /**
     * ModelAndView 返回值实现 数据响应/页面跳转(重定向/请求转发)
     * 1.数据响应: mv.addObject(key,value) 等价于 request.setAttribute(..)
     * 2.页面跳转:
     * 支持物理地址和逻辑地址: 和 string类型返回值默认的一致
     * mv.setViewName("url");
     * <p>
     * 自己设置: 只能是 物理地址
     * mv.setViewName("request:url");
     * mv.setViewName("redirect:url");
     * <p>
     * 注: modelAndView 的方式,重定向的优点: 一般的 request.sendRedirect(url) 方式在重定向时,得不到 request 域中的数据
     * 而 modelAndView 方式的重定型, 虽然不会直接得到 request域中的数据: 但是 它把 request 域中的数据以 请求行(请求体?)的形式带到了浏览器端
     * 在 浏览器地址栏 中显示.
     */
    @RequestMapping("test3")
    public ModelAndView test3() {
        ModelAndView mv = new ModelAndView();
        //数据响应: request 域
        mv.addObject("msg", "modelAndView");
        //页面跳转
        //        mv.setViewName("success"); //物理地址/逻辑地址都可以 : 默认 请求转发
        //        mv.setViewName("forward:/jsp/success.jsp"); //自定义方式:请求转发
        /*
        * 重定向不会得到 request 域中的数据,但是重定向完成的同时,会携带 request 域中的数据
        * localhost:8080/springMVC_day02_a/jsp/success.jsp?msg=modelAndView
            在地址栏中显示
         * */
        mv.setViewName("redirect:/jsp/success.jsp"); //重定向
        return mv;
    }
}
