package org.anonymous.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author child
 * 2019/4/21 8:57
 * 使用 restFul 编程风格
 */
@Controller("restFul")
@RequestMapping("/restFul/user")
public class RestFulController {
    /**
     * 核心:
     * 1. 使用地址参数
     * 2. 根据不同的 提交方式 执行不同的操作
     * 三个点：
     * 页面参数的路径： 地址参数
     * 格式： {aaa} -- 随便写,但是 获取参数时必须一致
     * 如何获取这个参数
     * 注解： @PathVariable("aaa")
     * 如何设置提交方式
     *
     * @RequestMapping 的 method 属性
     */

    //查
    @RequestMapping(value = "/{id}", method = RequestMethod.GET) //get 方式,获取 - 只接受指定的提交方式: 使用不匹配的提交方式请求会报错
    public String find(@PathVariable("id") Integer value) {

        System.out.println("findById:" + value);

        return "success";
    }

    //删
    @RequestMapping(value = "/{aaa}", method = RequestMethod.DELETE) //delete, 删除
    public String delete(@PathVariable("aaa") Integer value) {
        System.out.println("deleteById:" + value);

        return "success";
    }

    //改
    @RequestMapping(value = "/{up}", method = RequestMethod.PUT) //put, 改
    public String update(@PathVariable("up") Integer value) {
        System.out.println("updateById:" + value);
        return "success";
    }

    //曾: 不需要 id
    @RequestMapping(value = "/{id}", method = RequestMethod.POST) //post, 曾
    public String create() {
        System.out.println("create: + 对象数据");
        return "success";
    }
}
