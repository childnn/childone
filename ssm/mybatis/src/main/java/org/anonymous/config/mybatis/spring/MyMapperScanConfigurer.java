package org.anonymous.config.mybatis.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/5 9:07
 * @see org.mybatis.spring.mapper.MapperScannerConfigurer
 * @see org.apache.ibatis.binding.MapperProxyFactory
 */
public class MyMapperScanConfigurer implements BeanDefinitionRegistryPostProcessor {

    private String basePackage;

    /**
     * 属性必须要有 setter 方法
     * @see MyMapperScanRegister#registerBeanDefinitions(org.springframework.core.type.AnnotationMetadata, org.springframework.beans.factory.support.BeanDefinitionRegistry)
     * @param basePackage
     */
    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        final MyClassPathMapperScanner scanner = new MyClassPathMapperScanner(registry);
        // ...
        System.out.println("Do-scan................");
        scanner.doScan(basePackage);

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // NOOP.
    }
}
