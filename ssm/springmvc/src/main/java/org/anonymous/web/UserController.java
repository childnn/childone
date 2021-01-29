package org.anonymous.web;

import org.anonymous.domain.User;
import org.anonymous.domain.User1;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author child
 * 2019/4/20 11:35
 */
@Controller("user")
//@RequestMapping("ctrl") //父路径
//@RestController //直接将 方法 return 的结果 返回浏览器显示，而不是返回指定的页面（比如： 直接在页面 显示 “success” 而不是 跳转 "success.jsp 页面"）
public class UserController {

    /**
     * 浏览器访问的初始化方法：
     * 当浏览器访问 该类的 @RequestMapping("${path}") 时，会优先执行 @ModelAttribute 注解的方法
     * 该方法可以用来执行 修改用户名/密码 的相关业务： 接受页面数据
     * 1. 拿着页面提交的 id，先去数据库查询到 此id 对应的对象数据(user)
     * 2. 将页面的 username/password 设置给该 对象
     * 3. 将 该对象 作为返回值 返回: 该返回值 会作为 参数往下传递(类似于 request.setAttribute(key, obj))
     * 4. 扩展: 如果不使用 返回值的方式传递参数,可以使用 map 集合的方式传递(祥见 org.anonymous.controller.UserController0)
     */
    //    @RequestMapping("test1")
    @ModelAttribute //@Before
    public User init(String username, String password) {
        //从数据库 查询对象: select * from user where id = ? - 略
        //给页面传过来的对象 设置属性值(比如,修改的属性值)
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        System.out.println(user);
        user.setUsername("white");
        System.out.println(user);
        return user; //将改变了属性的 对象，传递到 浏览器实际访问的地址： test1
    }

    @RequestMapping(path = "test1")
    public String test1(User user) {
        System.out.println("修改后:" + user);

        return "success";
    }

    //去数据库查数据
    private User1 findUserById(int id) {
        User1 user = new User1();
        user.setId(id);
        user.setUsername("jack");

        return user;
    }
}
