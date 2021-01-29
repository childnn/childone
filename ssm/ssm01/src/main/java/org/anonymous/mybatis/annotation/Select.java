package org.anonymous.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author child
 * 2019/4/10 12:06
 * 注解替代 配置文件 UserMapper.xml
 */
@Target(ElementType.METHOD) //哪个方法要被 动态代理对象执行,就在该方法上使用本注解
@Retention(RetentionPolicy.RUNTIME) //该注解保留到运行时
public @interface Select {
    String querySql(); //需要执行的 sql

    String resultType(); //JavaBean 的全限定名//返回值类型
}
