package org.anonymous.web;

import org.anonymous.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author child
 * 2019/4/19 19:44
 * <p>
 * 数据的封装: 简单数据,list,map
 */
@Controller
@RequestMapping("user")
public class Demo {

    /*
     * 有自动类型转换: 页面 String -> Integer/int
     *   如果 方法参数是 Integer/int..: 则必须是 String 可以转换的
     *        Integer.parseInt(String s) 否则就异常
     *
     * 简单数据的接收:
     *   方法参数配置什么名称,页面的 name 属性就必须一致
     *
     * */
    @RequestMapping("test")
    public String test(String username, Integer password) { //接受来自 index.jsp 页面的数据
        System.out.println(username + ":" + password);

        return "success";
    }

    /*
     * 对象数据的封装
     *   方法的参数: 对象
     *   页面: name 属性就必须是 该对象的 属性名(成员变量名)
     *   ps: JavaBean 必须要有 set 方法(否则无法赋值:底层就是调用 set方法)
     *
     * */
    @RequestMapping("test1")
    public String test1(User user) {
        System.out.println(user);

        return "success";
    }

    /**
     * 对象list集合属性的封装
     * 方法的参数: 就是 对象
     * name 属性值 == 对象的集合属性名[索引].属性名
     * 此时,先调用 get 方法, 获取 对象中的 对象集合属性
     * 再调用 set 方法: 为 集合属性赋值
     * ps:必须要有 list 集合属性对应的 get 方法
     */
    @RequestMapping("test2")
    public String test2(User user) {

        System.out.println(user);
        return "success";
    }

    /**
     * map 集合属性:  页面 name 属性值 == 集合属性名[key值].属性名
     *
     * @param user
     * @return
     */
    @RequestMapping("test3")
    public String test3(User user) {
        System.out.println(user);
        return "success";
    }

    /**
     * 获取 servlet 相关 api
     *
     * @param request
     * @param response
     * @param session
     * @return
     */

    @RequestMapping("test4")
    public String test4(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        System.out.println(request); //org.apache.catalina.connector.RequestFacade
        System.out.println(response); //org.apache.catalina.connector.ResponseFacade
        System.out.println(session); //org.apache.catalina.session.StandardSessionFacade
        return "success";
    }


    /*
     * 获取请求头数据
     * @RequestHeader:
     *    value: 请求头key
     *    required: 请求头是否必须携带 该参数(如果没有就报错)
     * 获取 cookie 信息
     * */
    @RequestMapping("test5")
    public String test5(@RequestHeader(value = "User-Agent", required = true) String value1,
                        @RequestHeader(value = "Connection", required = false) String value2,
                        @CookieValue("JSESSIONID") String value3) {

        System.out.println(value1); //Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.140 Safari/537.36 Edge/18.17763
        System.out.println(value2); //Keep-Alive
        System.out.println(value3); //DE2AD4FD61448B85F3BD684C2577CBB8

        return "success";
    }
}
