package org.anonymous.config.mybatis.spring;

import org.anonymous.config.mybatis.spring.annotation.MyMapper;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/3/5 9:11
 * @see org.mybatis.spring.mapper.ClassPathMapperScanner
 */
public class MyClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

    public MyClassPathMapperScanner(BeanDefinitionRegistry registry) {
        super(registry, true); // MyBatis 这里设置为 false, 不使用 default filters.
    }

    /**
     * @see org.mybatis.spring.mapper.MapperScannerConfigurer#postProcessBeanDefinitionRegistry(org.springframework.beans.factory.support.BeanDefinitionRegistry)
     * MyBatis 是在 scan 之前先调用 scanner.registerFilters();
     * @see org.mybatis.spring.mapper.ClassPathMapperScanner#registerFilters()
     * register default filters.
     * 必须标注 @MyMapper 才会被加载.
     * @see org.springframework.core.type.filter.AnnotationTypeFilter
     * @see org.mybatis.spring.mapper.ClassPathMapperScanner#annotationClass
     */
    @Override
    protected void registerDefaultFilters() {
        // super.registerDefaultFilters();
        addIncludeFilter(new AnnotationTypeFilter(MyMapper.class));
    }

    /**
     * @see org.mybatis.spring.mapper.ClassPathMapperScanner#isCandidateComponent(org.springframework.beans.factory.annotation.AnnotatedBeanDefinition)
     * @see org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider#scanCandidateComponents(java.lang.String)
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        // return super.isCandidateComponent(beanDefinition);
        return beanDefinition.getMetadata().isInterface() &&
                beanDefinition.getMetadata().isIndependent();
    }

    /**
     * @see #isCandidateComponent(org.springframework.beans.factory.annotation.AnnotatedBeanDefinition)
     */
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        final Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        processMapperBeanDefinitions(beanDefinitionHolders);

        return beanDefinitionHolders;
    }

    private void processMapperBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        beanDefinitionHolders.forEach(bdh -> {
            final GenericBeanDefinition beanDefinition = ((GenericBeanDefinition) bdh.getBeanDefinition());
            final String beanClassName = beanDefinition.getBeanClassName();

            System.out.println("beanClassName = " + beanClassName);
            // MyMapperFactoryBean 构造方法参数
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
            beanDefinition.setBeanClass(MyMapperFactoryBean.class);

        });
    }
}
