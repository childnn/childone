package com.example.bootactuator;

import com.example.bootactuator.filters.demo2.Filter2;
import com.example.bootactuator.servlet.MyServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.beans.BeansEndpoint;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * http://localhost:9000/actuator
 *
 * @see ServletComponentScan 扫描 {@link WebFilter} {@link WebServlet} {@link WebListener}
 */
@ServletComponentScan(basePackageClasses = {Filter2.class, MyServlet.class})
@RestController
@SpringBootApplication
public class BootActuatorApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(BootActuatorApplication.class, args);
        BeansEndpoint endpoint = ctx.getBean(BeansEndpoint.class);
        BeansEndpoint.ApplicationBeans beans = endpoint.beans();
        Map<String, BeansEndpoint.ContextBeans> contexts = beans.getContexts();
       /* contexts.forEach((k, v) -> {
            System.err.println(k);
            Map<String, BeansEndpoint.BeanDescriptor> bs = v.getBeans();
            bs.forEach((k1, v1) -> System.err.println(k1 + ": " + v1.getType()));
        });*/
    }


    /**
     * @api {get} / 这里配置 method 和 url
     * @apiDescription 这是测试的描述信息
     * @apiName hello
     * @apiGroup 这是接口 Group
     * @apiVersion 1.0.0
     * @apiParam {String} code 行政编码
     * @apiSampleRequest /
     * @apiUse token_msg
     * @apiUse success_msg
     * @apiSuccess (success 2000) {String}   res.id    标识码
     * @apiSuccess (success 2000) {String}   res.name    行政地区名称
     * @apiSuccess (success 2000) {String}   res.code    行政编码
     * @apiSuccess (success 2000) {String}   res.prevCode    上级行政编码
     * @apiSuccess (success 2000) {String}   res.allName    全称
     */
    @RequestMapping("/")
    public String hello(HttpServletRequest req) {
        System.out.println(req.getParameter("name"));
        return "hello";
    }

}
