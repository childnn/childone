package org.anonymous.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author child
 * 2019/4/19 14:57
 */
@Controller("hello")
public class HelloSpringMVC {
    /*
     * 方法要求：
     *    有 String 类型的返回值
     *        返回值,就是 访问该方法后的 跳转地址(访问路径)
     *
     *
     * */
    @RequestMapping("/hello") //配置浏览器访问该方法的路径
    public String hello1() {
        System.out.println("hello");

        return "demo";
    }

}
