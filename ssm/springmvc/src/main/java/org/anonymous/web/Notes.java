package org.anonymous.web;

import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.HandlerMethodReturnValueHandlerComposite;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.handler.AbstractHandlerMethodMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.ViewNameMethodReturnValueHandler;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @see org.springframework.web.servlet.HandlerMapping#getHandler(HttpServletRequest) 解析得到
 * {@link HandlerExecutionChain}
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
 * @see org.springframework.web.servlet.HandlerAdapter#handle(HttpServletRequest, HttpServletResponse, Object)
 * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
 * handler-mapping 用来
 * ----
 * DispatcherServlet 相关调用链
 * @see org.springframework.web.servlet.FrameworkServlet#doGet(HttpServletRequest, HttpServletResponse)
 * @see org.springframework.web.servlet.FrameworkServlet#processRequest(HttpServletRequest, HttpServletResponse)
 * @see org.springframework.web.servlet.DispatcherServlet#doService(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 * @see DispatcherServlet#doDispatch(HttpServletRequest, HttpServletResponse)
 * @see DispatcherServlet#getHandler(javax.servlet.http.HttpServletRequest)
 * {@link AbstractHandlerMapping#getHandler(javax.servlet.http.HttpServletRequest)}
 * {@link org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping#getHandlerInternal}
 * {@link AbstractHandlerMapping#getHandlerExecutionChain(java.lang.Object, javax.servlet.http.HttpServletRequest)}
 * @see DispatcherServlet#getHandlerAdapter(java.lang.Object)
 * @see HandlerAdapter#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
 * {@link RequestMappingHandlerAdapter#handleInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.web.method.HandlerMethod)}
 * {@link RequestMappingHandlerAdapter#invokeHandlerMethod(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.web.method.HandlerMethod)}
 * - invoke handler(invoke the method)
 * @see ServletInvocableHandlerMethod#invokeAndHandle(org.springframework.web.context.request.ServletWebRequest, org.springframework.web.method.support.ModelAndViewContainer, java.lang.Object...)
 * {@link InvocableHandlerMethod#invokeForRequest(org.springframework.web.context.request.NativeWebRequest, org.springframework.web.method.support.ModelAndViewContainer, java.lang.Object...)}
 * {@link InvocableHandlerMethod#doInvoke(java.lang.Object...)}
 * 处理返回值: 不同的返回值类型不同的 return-value handler
 * @see HandlerMethodReturnValueHandlerComposite#handleReturnValue(java.lang.Object, org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest)
 * @see HandlerMethodReturnValueHandler
 * eg: 处理 {@link org.springframework.web.bind.annotation.ResponseBody} 注解 的处理器 {@link RequestResponseBodyMethodProcessor#supportsReturnType}
 * 处理视图 {@link ViewNameMethodReturnValueHandler} 方法返回值为 void 或者 String {@link ViewNameMethodReturnValueHandler#handleReturnValue(java.lang.Object, org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest)}
 * 校验是否为重定向 view {@link ViewNameMethodReturnValueHandler#isRedirectViewName(java.lang.String)}
 * --- return ModelAndView
 * @see RequestMappingHandlerAdapter#getModelAndView(org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.method.annotation.ModelFactory, org.springframework.web.context.request.NativeWebRequest)
 * @see DispatcherServlet#processDispatchResult(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.web.servlet.HandlerExecutionChain, org.springframework.web.servlet.ModelAndView, java.lang.Exception)
 * --- 视图解析器
 * @see ViewResolver
 * @see DispatcherServlet#viewResolvers
 * ---
 * 存储 path-handlerMethod 的映射 {@link AbstractHandlerMethodMapping#mappingRegistry}
 * 日志:
 * @see FrameworkServlet#initServletBean()
 * @since 2020/12/6 15:50
 * handler: 标注了 {@link org.springframework.web.bind.annotation.RequestMapping} 等 web 注解的方法 (实际是其中的方法)
 * 实际的 handler 就是 {@link HandlerExecutionChain#handler} 为 Object 类型
 * 在 spring-mvc 中就是 {@link org.springframework.web.method.HandlerMethod}
 */
public class Notes {
}
