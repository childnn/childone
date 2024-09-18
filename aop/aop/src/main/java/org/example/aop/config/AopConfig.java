package org.example.aop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.springframework.context.annotation.ConfigurationClassBeanDefinitionReader#loadBeanDefinitionsFromRegistrars(java.util.Map)
 * @see EnableAspectJAutoProxy
 * @see org.springframework.context.annotation.AspectJAutoProxyRegistrar
 * @see org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator#setProxyTargetClass(boolean)
 * @see org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator#setExposeProxy(boolean)
 * @see org.springframework.aop.interceptor.ExposeInvocationInterceptor#ADVISOR
 * @see org.springframework.aop.aspectj.annotation.InstantiationModelAwarePointcutAdvisorImpl (advisor 通知者) 每个 advice
 * 方法封装为一个实例
 * @see org.springframework.aop.aspectj.AspectJExpressionPointcut
 * @see org.springframework.aop.aspectj.annotation.ReflectiveAspectJAdvisorFactory#getAdvice(java.lang.reflect.Method, org.springframework.aop.aspectj.AspectJExpressionPointcut, org.springframework.aop.aspectj.annotation.MetadataAwareAspectInstanceFactory, int, String)
 * @see org.aspectj.weaver.tools.PointcutPrimitive  aspectj 的切面表达式类型
 * @see org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor
 * @see org.springframework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor 拦截链
 * @see org.springframework.aop.framework.CglibAopProxy.CglibMethodInvocation
 * @see org.springframework.aop.framework.DefaultAdvisorChainFactory#getInterceptorsAndDynamicInterceptionAdvice(org.springframework.aop.framework.Advised, java.lang.reflect.Method, Class)
 * @see org.springframework.aop.framework.adapter.DefaultAdvisorAdapterRegistry
 * ---
 * --- advice adapter
 * @see org.springframework.aop.framework.adapter.MethodBeforeAdviceAdapter
 * @see org.springframework.aop.framework.adapter.AfterReturningAdviceAdapter
 * @see org.springframework.aop.framework.adapter.ThrowsAdviceAdapter
 * --- advice - 接口
 * @see org.springframework.aop.MethodBeforeAdvice
 * @see org.springframework.aop.AfterReturningAdvice
 * @see org.springframework.aop.ThrowsAdvice
 * --- advice 的 aspectj 的实现
 * @see org.springframework.aop.aspectj.AspectJAroundAdvice
 * @see org.springframework.aop.aspectj.AspectJMethodBeforeAdvice
 * @see org.springframework.aop.aspectj.AspectJAfterAdvice
 * @see org.springframework.aop.aspectj.AspectJAfterThrowingAdvice
 * @see org.springframework.aop.aspectj.AspectJAfterReturningAdvice
 * --- advice interceptor
 * @see org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor
 * @see org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor
 * @see org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor
 * @see org.springframework.aop.interceptor.ExposeInvocationInterceptor
 * ---
 * @see org.aspectj.lang.annotation.Pointcut
 * @see org.springframework.aop.Pointcut
 * @see org.aspectj.lang.reflect.Advice
 * @see org.aopalliance.aop.Advice
 * @see org.springframework.aop.Advisor
 * @see org.springframework.aop.framework.Advised
 * @see org.aspectj.lang.JoinPoint
 * @see org.aspectj.lang.ProceedingJoinPoint
 * ---
 * @see org.springframework.core.ParameterNameDiscoverer
 * @see org.springframework.aop.aspectj.annotation.AbstractAspectJAdvisorFactory.AspectJAnnotationParameterNameDiscoverer
 * @see org.springframework.core.StandardReflectionParameterNameDiscoverer
 * @see org.springframework.aop.aspectj.AspectJAdviceParameterNameDiscoverer
 * ---
 * @see org.springframework.aop.aspectj.annotation.AspectJProxyFactory
 * @see org.springframework.context.annotation.aspectj.EnableSpringConfigured
 * @see EnableLoadTimeWeaving
 * @see org.springframework.beans.factory.aspectj.AnnotationBeanConfigurerAspect
 * @see org.springframework.transaction.aspectj.JtaAnnotationTransactionAspect
 * @since 2024/6/4
 */
@EnableAspectJAutoProxy
@ComponentScan("org.example.aop")
@Configuration(proxyBeanMethods = false)
public class AopConfig {

    // @Order(HIGHEST_PRECEDENCE)
    // @Bean
    MyAspect myAspect() {
        return new MyAspect();
    }

}
