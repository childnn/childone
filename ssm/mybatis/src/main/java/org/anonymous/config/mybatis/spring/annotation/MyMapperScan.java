package org.anonymous.config.mybatis.spring.annotation;

import org.anonymous.config.mybatis.spring.MyMapperScanRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/5 8:54
 * @see org.mybatis.spring.annotation.MapperScan
 * 1. @MapperScan 注解标注的类自己必须是 Spring-IoC 的一个组件
 * 2. 解析 @MypperScan 时, 会解析其上的 @Import 注解中指定的类
 * 3. 此类必须实现 {@link org.springframework.context.annotation.ImportBeanDefinitionRegistrar}
 *    {@link org.springframework.context.annotation.ConfigurationClassParser#processImports(org.springframework.context.annotation.ConfigurationClass, org.springframework.context.annotation.ConfigurationClassParser.SourceClass, java.util.Collection, java.util.function.Predicate, boolean)}
 * 4.
 */
@Import({MyMapperScanRegister.class})
@Retention(RUNTIME)
public @interface MyMapperScan {
    // basePackage
    String value() default "";
}
