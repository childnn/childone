package org.anonymous.web;

import org.anonymous.domain.User1;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author child
 * 2019/4/20 11:35
 */
@Controller("user1")
@RequestMapping("/ctrl1")
public class UserController1 {

    @ModelAttribute("aaa")
    public void getUser(@RequestParam(value = "username", required = false) String username, Model model) {
        User1 user = new User1();
        user.setUsername(username);
        user.setId(1234);
        user.setPassword("abcd");
        model.addAttribute("user", user);
    }

    @RequestMapping("/test1")
    public String test1(ModelMap model) {
        Object user = model.get("user");

        System.out.println(user);

        return "success";
    }

}
