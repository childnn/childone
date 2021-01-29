package org.anonymous.web;

import org.anonymous.domain.User;
import org.anonymous.exception.MyException;
import org.anonymous.service.Service00;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author child
 * 2019/4/21 21:39
 */
@Controller("user")
public class UserController2 {


    @RequestMapping("test1")
    public String test1() throws MyException { //有异常抛出: 自动 跳转 异常解析器
        Service00 service00 = new Service00();
        service00.testS();
        return "success";
    }

    @RequestMapping("test2") //拦截器测试
    public String test2() {
        System.out.println("控制器方法...");
        return "success";
    }

    /**
     * 检测用户是否登录
     *
     * @return
     */
    @RequestMapping("find")
    public String find() {
        System.out.println("去数据库查询用户:检测用户是否登录");
        return "success";
    }

    //登录成功: 将数据存入session
    @RequestMapping("login")
    public String login(User user, HttpSession session) {

        session.setAttribute("user", user);
        System.out.println("登录成功..");
        return "success";
    }
}
