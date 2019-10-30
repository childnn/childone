package com.example.demo;

import com.example.demo.service.ServicePrototype;
import com.example.demo.service.ServicePrototype0;
import com.example.demo.service.ServiceSingleton;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LookupMethodApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(LookupMethodApp.class, args);
        ServiceSingleton bean = ctx.getBean(ServiceSingleton.class);
        ServicePrototype prototype = bean.getServicePrototype();
        ServicePrototype prototype1 = bean.getServicePrototype();
        System.out.println(prototype == prototype1); // 非 look-up method 往 singleton 对象中注入 prototype 对象.

        ServicePrototype0 serviceProtoType0 = bean.getServiceProtoType0();
        ServicePrototype0 serviceProtoType01 = bean.getServiceProtoType0();
        System.out.println(serviceProtoType0 == serviceProtoType01); // look-up method

        ServicePrototype bean1 = ctx.getBean(ServicePrototype.class);
        ServicePrototype bean2 = ctx.getBean(ServicePrototype.class);
        System.out.println(bean1 == bean2);

    }

}
