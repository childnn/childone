package org.anonymous.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author child
 * 2019/4/19 14:57
 */
@Controller("hello0")
@RequestMapping(path = "/user") //方法的父路径: 用于区分模块
public class HelloSpringMVC0 {
    /*
     * 方法要求：
     *    有 String 类型的返回值
     *        返回值,就是 访问该方法后的 跳转地址(访问路径)
     * @RequestMapping: 可以在类上/方法上
     *     属性
     *        value==path: 配置请求路径(二者等价)
     *        method: 请求方式(get/post)
     *        params: 请求参数
     *            若配置 请求参数,代表页面必须要传递指定的参数(否则就报错)
     *
     * */
    @RequestMapping(value = "/hello0", method = RequestMethod.POST, params = {"username", "password"}) //配置浏览器访问该方法的路径
    public String hello1() {
        System.out.println("hello");

        return "demo"; //jsp/demo.jsp 的简写: 配置了 视图解析器
    }
}
