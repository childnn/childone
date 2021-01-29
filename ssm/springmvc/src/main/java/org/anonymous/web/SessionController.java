package org.anonymous.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;

/**
 * @author child
 * 2019/4/20 16:28
 */
@Controller("session")
@SessionAttributes({"username", "password"}) //指定 session 域中可以存储的 key值类型
public class SessionController {
    /*
     * 1. 往 session 中存数据
     * 2. 从 session 中取数据
     * 3. 删除 session 中数据
     *
     * */
    @RequestMapping("save")
    public String saveSession(Model model) {
        //把 数据 存在 session 域中: key 值必须为指定的 key
        model.addAttribute("username", "adcb");
        return "success";
    }

    @RequestMapping("find")
    public String findSession(ModelMap modelMap) { //等价于 HttpSession
        Object username = modelMap.get("username");
        System.out.println("modelMap:" + username);

        return "success";
    }

    @RequestMapping("find0")
    public String findSession0(HttpSession session) {
        Object username = session.getAttribute("username");
        System.out.println("session" + username);
        return "success";
    }

    @RequestMapping("delete")
    //    @RequestBody
    public String deleteSession(SessionStatus status) {
        //清空
        status.setComplete();

        return "success";
    }
}
