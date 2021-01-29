package org.anonymous.web;

import org.anonymous.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author child
 * 2019/4/20 11:35
 */
@Controller("user0")
@RequestMapping("/ctrl0")
//@RestController
public class UserController0 {

    /**
     * 上接 org.anonymous.controller.UserController
     * 不使用返回值传递参数, 使用 map 传递: 再参数上定义 map
     * key: 随意
     * value: 对象
     *
     * @param user
     */
    @ModelAttribute("aaa") //@Before
    public void init(User user, Map<String, User> map) {
        user.setUsername("white");
        System.out.println(user);
        map.put("aaa", user);
    }

    @RequestMapping(path = "/test1")
    public String test1(@ModelAttribute("aaa") User user) {
        System.out.println(user);

        return "success";
    }
}
