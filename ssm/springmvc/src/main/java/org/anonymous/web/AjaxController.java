package org.anonymous.web;

import org.anonymous.domain.User;
import org.anonymous.domain.User1;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author child
 * 2019/4/21 9:44
 */
@Controller("ajax")
public class AjaxController {

    /*
      封装 ajax: jackson
    * @RequestBody: 专门用来接收 页面 ajax 的数据 -- 将 json 数据转化为 Java 对象
    * @ResponseBody: 封装返回值,返回给页面 (就是 ajax 回调函数的 参数 d) -- 将 Java 对象转化为 json 数据
    *
    *  页面 ajax 参数设置:
    *   contentType(服务端返回给页面的参数类型): "application/json;charset=utf8"  -- 固定写法
    *   url:请求地址
    *   data:请求数据(json格式) -- {"key":"value", "key":"value"..}
    *   type:请求方式 --- "post/get"
    *   dataType:数据类型 -- 固定写法 -- "json"
    *   sync:true 是否异步
    *   success:function(d){响应成功回调函数}
    *   error:function(){响应失败回调函数}
    * */
    @RequestMapping("/ajax")
    public @ResponseBody
    User1 test1(@RequestBody User user) {

        System.out.println(user);

        //模拟 service->dao: 查数据
        User1 user1 = new User1();
        user1.setId(1);
        user1.setUsername("rose");
        user1.setPassword("abcd");

        //注意: 这里 user 对象,对应的 javabean 必须要有 get 方法: 底层调用 get 方法,将对象转为 Jason 数据
        return user1;
    }
}
