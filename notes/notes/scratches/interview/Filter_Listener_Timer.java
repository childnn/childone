package org.anonymous.filter_listener_timer;

/**
 * @author child
 * 2019/4/1 9:16
 *
 * web 服务器三大技术: servlet, filter, listener
 * servlet: (运行在 web 服务器上的 Java 程序) 本质上是一个 Java 类, 需要实现 servlet 规范. 作用: 接收 浏览器 的请求,对浏览器的请求做出响应
 *
 * filter 过滤器 -- Great Fire Wall
 *      filter 本质上也是一个 Java 类,需要实现 filter 规范
 *      作用: 对浏览器访问服务器资源时的一种过滤,符合条件才放行,不符合条件不放行
 *      服务器资源: html jsp 图片 servlet
 *      ps: 服务器给浏览器响应的时候, filter 也可以拦截 -- 一般不会这么做(没有意义)
 *    细节:
 *      可以对指定资源拦截, 也可以对所有资源进行拦截 -- 配置
 *      当有多个 filter 对同一资源生效时, 这多个 filter 都会执行
 *      配置文件中: 多个 filter 的执行顺序,按 web.xml 中 的配置顺序
 *      注解中: 多个 filter 的执行顺序,按 文件名 自然排序 执行
 *    filter 生命周期:
 *      服务器启动 创建 filter 对象: 执行 init 方法  -- init -- 一次
 *      浏览器访问对应的资源时,执行: 访问一次,执行一次 (doFilter方法) -- doFilter --多次
 *      服务器关闭时,执行destroy 方法,销毁 filter  -- destory -- 一次
 *      init(FilterConfig config):
 *           FilterConfig: filter 配置对象
 *                 getFilterName(): 当前 filter 的 filterName
 *                 getServletContext() : 全局管理者
 *      doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
 *           基本同 servlet 的参数
 *              servlet 中的参数是 HttpServletRequest: 在使用 filter 时 可以强转为 HttpServletRequest: 获得子类的方法
 *           FilterChain : doFilter(req,resp): 传递 请求/响应对象
 *         放行后,req/resp 会走向下一个 filter(如果有),进而找 servlet
 *   filterChain: 过滤器链,链接多个(两个以上的过滤器)
 *      用来维护 多个过滤器的执行顺序  -- filterChain.doFilter(req,resp)
 *      filterChain 就是一个双向链条, 请求正方向拦截, 响应反方向拦截 (请求中先拦截的filter,在响应中后执行)
 *
 *   Filter 的配置:
 *      1.filter 资源路径配置 (面试题)
 *          1) 完全路径匹配: /资源名   -- 拦截单一资源
 *              如果希望一个过滤器拦截多个资源, 直接在 配置文件中的<filter-mapping></filter-mapping> 中 写多个 url-pattern
 *          2) 目录匹配: /* -- 所有资源  /a/b/*  --对应目录下的所有资源
 *          3) 扩展名匹配(拦截一类资源): 不以/开始,  *.扩展名 -- 如: *.jsp  -- 拦截项目中所有 jsp 资源
 *      2. filter 拦截方式:
 *          默认情况下: filter 只拦截 浏览器 过来的请求(如 重定向),不拦截 服务器内部请求(如 请求转发)
 *          配置 filter 拦截 内部资源:
 *                 在配置文件中 <filter-mapping></filter-mapping>
 *                      加上 <dispatcher>FORWARD</dispacher> : 【只拦截】 服务器内部请求转发
 *                    如果希望既拦截 浏览器 的请求,又拦截 服务器内部 请求转发:
 *                          加上 <dispatcher>REQUEST</dispatcher>
 *                    要么都不写，要写就都写
 *                 注解:
 *                    @WebFilter( dispatcherTypes = {DispatcherType.FORWARD, Dispatcher.REQUEST})
 *
 * 案例：
 *      登录拦截：
 *          1.判断浏览器方法的是否是登录相关资源 : .jsp/servlet(登录/验证码)/css/js/fonts/...
 *              (HttpServletRequest)req.getRequestURI().contains("....") : 是否包含...
 *              是： 放行 （不拦截）
 *              不是：拦截 --> 进入登录页面
 *          2.判断当前用户是否登录
 *              已登录的用户信息 会 存在 session 中,在 filter 中从 session 取数据,判断
 *              req.getSession().getAttribute("user");
 *              没有登录: 拦截 --> 进入登录页面
 *              登录: 放行
 *
 * listener 监听器: 本质上也是一个 Java 类,需要实现指定的 listener 规范
 *      作用: 监听 3 个域对象(servletContext,request,session) 的状态变化
 *   事件监听机制
 *     事件 : 一件事情
 *     事件源 : 事件发生的地方
 *     监听器 : 一个对象
 *     注册监听 : 将事件,时间源,监听器绑定在一起, 当事件源上发生某个事件后,执行监听代码
 *
 *  !!!! interface ServletContextListener 监听器-- 后期框架要用 -- 后期封装,自动加载配置文件 --------重点---------
 *           void contextInitialized(ServletContextEvent sce) : servletContext 对象创建时执行 -- 服务器开启(项目部署)创建
 *           void contextDestroyed(ServletContextEvent sce) : servletContext 对象销毁时执行  -- 服务器关闭(项目撤销部署)销毁
 *                  -- 用来在 项目加载时, 将整个 项目的所有配置文件加载
 *          注: 后期的框架项目,有大量的配置文件,希望项目启动时,项目相关框架的配置文件加载完成
 *              利用 servletContextListener 监听器,只要服务器已启动,就加载项目的配置文件,后面需要使用相关的配置直接用
 *      步骤:
 *          1.定义一个类,implements ServletContextListener
 *          2.配置 项目启动时 需要加载的文件:
 *              web.xml:
 *                      <listener>
 *                          <listener-class>MyFilter01</listener-class>
 *                      </listener>
 *                      <!--指定初始化参数: 后期写的是 spring 框架的键值对-->
 *                      <context-param>
 *                          <param-name>contextConfigLocation</param-name>
 *                          <param-value>/WEB-INF/classes/application/xxx.xml</param-value>
 *                      </context-param>
 *              注解: @WebListener
 *
 *     接口 ServletRequestListener 监听器: 监听 request 对象的创建和销毁 -- 请求一次,创建一次;响应一次,销毁一次
 *     接口 HttpSessionListener 监听器: 监听 session 对象的创建和销毁 -- 一次会话(浏览器不关闭),一个对象
 *
 * 动态代理(dynamic proxy) : 对一个接口的方法进行增强  -- org.anonymous.domain.ProxyDemo -- 在运行过程中,动态创建一个接口的实现类对象 -- 并不需要显式的 impl.java 文件和 .class 文件
 *   Using Java Reflection to create dynamic implementations of interfaces at runtime
 *   没有实现类的源码 (.java) 和实现类编译后的字节码 (.class), 只有在运行时(runtime) 才能实现接口
 *      框架中的类多数都是 Java 源码接口的实现类 :
 *          如 doGet(HttpServletRequest request, ..) request 实际上是由 tomcat 提供的 HttpServletRequest 实现类对象(多态)
 *      在利用外来框架时,如果我们希望扩展框架的某个类的功能, 但是我们并不知道 框架的这个实现类是谁 (如 tomcat 的 HttpServletRequest 实现类)
 *      此时,就需要用到 动态代理
 *      jdk 提供的动态代理(java) : 不需要导包 // cglib 提供的动态代理(Spring) : 需要导包
 *      术语:
 *          代理与被代理
 *              被代理对象: 接口 -- 被增强对象
 *              代理对象: 实现类 -- 增强对象 -- 由 Proxy 的静态方法 newProxyInstance(...) 得到
 *          增强与被增强
 *              被增强/被代理对象: 目标对象(接口的实现类)
 *              增强对象: 代理对象
 *       !!!想要有 代理对象,首先必须要有 目标对象
 *       !!!想要做动态代理,必须明确目标对象 -- 找不到就做不了动态代理
 *       !!!想要对目标对象的方法进行增强, 目标对象必须要有接口(为了让代理对象也实现这个接口具有和目标对象一样的方法)
 *              -- jdk 的动态代理, 没有接口,无法实现动态代理 (cglib 的动态代理可以没有接口)
 *              -- 以后 的开发就是 面向接口编程
 *    java.lang.reflect Class Proxy  动态代理工具类 : 自动实现动态代理
 *          static Object newProxyInstance​(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
 *              返回指定接口的代理实例，该代理实例将方法调用分派给指定的调用处理程序。
 *           此方法相当于：
 *           Proxy.getProxyClass(loader, interfaces).getConstructor(new Class[] { InvocationHandler.class }).newInstance(new Object[] { handler });
 *              --ClassLoader loader : 需要有一个和目标对象一样的类加载器 -- Class.forName("类名").getClassLoader // 类名.class.getClassLoader()//对象.getClass().getClassLoader()
 *              --Class<?>[] interfaces : 目标对象的所有接口字节码对象的数组
 *              --InvocationHandler h : (调用处理器) 处理增强业务的实现类(InvocationHandler 是接口) -- 其 invoke 方法就是用来处理增强业务的(内部写增强业务的逻辑)
 *           返回值用对应的接口接收: 返回值实际上就是 接口的实现类
 *           只要代理对象调用任何方法, 增强业务的 invoke 方法就会执行, invoke 执行的就是当前调用方法的增强
 *          java.lang.reflect 接口 InvocationHandler
 *             Object invoke(Object proxy, Method method, Object[] args) 在代理实例上处理方法调用并返回结果。
 *                Object proxy: 内部使用,程序员不用 --
 *                Method method: 代理对象调用谁,method 代表谁 -- 即要被增强的方法的字节码对象
 *                Object[] args: 就是被增强方法的参数(传入的实参),没有就是 null
 *
 * timer 定时器 和 邮件 : org.anonymous.listener.timer.TimerDemo
 *      new Timer()
 *      void schedule​(TimerTask task, Date firstTime, long period)
 *      void schedule​(TimerTask task, long delay, long period)
 *   邮件: 导入 jar 包 -- mail.jar/activation.jar
 *
 */
public class Filter_Listener_Timer {
}
