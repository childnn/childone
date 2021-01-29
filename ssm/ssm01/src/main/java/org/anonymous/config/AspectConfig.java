package org.anonymous.config;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author child
 * 2019/4/17 14:31
 */
//@Configuration //定义  配置类
//@ComponentScan(basePackages = "org.anonymous") // 注解扫描器
@EnableAspectJAutoProxy //<aop:aspectj-autoproxy/> 开启 aop 注解支持
public class AspectConfig {

}
