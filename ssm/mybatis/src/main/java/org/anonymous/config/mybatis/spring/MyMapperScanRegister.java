package org.anonymous.config.mybatis.spring;

import org.anonymous.config.mybatis.spring.annotation.MyMapperScan;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/5 8:55
 * @see org.mybatis.spring.annotation.MapperScannerRegistrar
 */
public class MyMapperScanRegister implements ImportBeanDefinitionRegistrar {

    /**
     *
     * @see org.springframework.core.type.StandardAnnotationMetadata
     * @param importingClassMetadata 此 metadata 可以认为就是 当前类(MyMapperScanRegister) 对应注解(MyMapperScan)标注的 Configuration 类的信息, 以及类上
     *                               定义的各种注解的相关信息
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println("importingClassMetadata = " + importingClassMetadata);
        // importingClassMetadata.get
        final Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(MyMapperScan.class.getName());
        final String basePackage = (String) annotationAttributes.get("value");
        final BeanDefinitionBuilder bdb = BeanDefinitionBuilder.genericBeanDefinition(MyMapperScanConfigurer.class);
        bdb.addPropertyValue("basePackage", basePackage);

        System.out.println("basePackage = " + basePackage);

        registry.registerBeanDefinition(getClass().getName(), bdb.getBeanDefinition());
    }

}
