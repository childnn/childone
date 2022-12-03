package org.ssm;

/**
 * J2EE org.ssm
 *
 * @author child
 * 2019/4/18 15:25
 * springmvc: 封装 servlet
 * day01: 参数封装
 *  web: 控制层(Controller) 接受页面的请求封装数据到 JavaBean 给客户端做响应 jsp/html -- springMVC
 *      web 层: 涉及了 MVC 的设计思想: model, view, controller
 *      model: javabean
 *      view: jsp
 *      controller: servlet
 *   MVC 框架:
 *       struts1, web-work, structs2(是 web-work 的延申), springMVC
 *  service: 业务, 事务  -- spring
 *  dao: 持久层(repository) -- mybatis, jdbcTemplate, dbUtils, hibernate, jpa
 *
 *  springMVC 和 struts2 比较(面试)
 *   同:
 *    它们都是 表现层框架,都是基于 MVC 模型编写
 *    它们的底层都离不开原始 servlet 的 API
 *    它们处理请求的机制都是一个 核心控制器 : filter/servlet
 *   异:
 *     (核心)springMVC 的入口是 servlet, 而 struts2 是 filter -- 前端控制器
 *     spring MVC 是 基于方法设计的, 而 struts2 是基于类, struts2 每次执行都会创建一个动作类
 *          springMVC 的核心控制器 是 单例对象, 是线程安全的类
 *          structs2 的核心控制器 是 多例对象, 访问一次,创建一个对象,是线程不安全的类
 *          所以, springMVC 效率更高
 *     springMVC 使用功能更加简洁, 同时 支持 JSR303,处理 ajax 请求更方便
 *      (JSR303 是一套 JavaBean 参数校验的标准, 它定义了很多常用的校验注解, 我们可以直接将这些注解加在我们 javabean 的
 *      属性上,就可以在需要校验的时候进行校验了)
 *     struts2 的 ognl 表达式使页面的开发效率相比 springMVC 更高,但执行效率并没有比 jstl 提升
 *      尤其是 struts2 的表单标签,远没有 html 执行效率高.
 *
 *
 *  springMVC 是 spring 提供的一个 子框架,是为 web 层提供的一个框架, 实现了 mvc 思想
 *      可以和 spring 无缝连接到一起(ioc 通用)
 *  springMVC 封装了 servlet: 前端控制器
 *      - 在 web.xml 中配置
 *      - 在 springMVC 配置文件指定 浏览器访问地址(类.方法的映射)
 *
 *
 * 执行流程:
 *  一:tomcat (web服务器) --启动--> 加载 web.xml 配置文件 (
 *              1.创建 springMVC 的前端控制器(dispatcherServlet)对象,
 *                      开启 注解扫描器: ioc   <context:component-scan base-package="org.anonymous"/>
 *                      映射器和适配器
 *                      <!--配置映射关系: web 层和 DispatcherServlet 的关系
 *                       映射器:
 *                              加载 方法上的 @RequestMapping 注解. 将浏览器的访问 和 方法上的 @RequestMapping("方法的 url") 相关联
 *                               既可以说 将 @RequestMapping('url') 与 类.方法() 相映射
 *                               也可以说 将 方法地址(@RequestMapping()注解指定的地址) 与 跳转地址(方法返回值指定的 url) 相映射
 *                               或者说: 一个 Controller 有多个方法,也就有多个 url, 也可以认为是一种映射.
 *                          -->
 *                      <!--处理器映射器:HandlerMapping-->
 *                      <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"/>
 *                      <!--适配器:HandlerAdapter-->
 *                      <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter"/>
 *              2.加载 classpath 下的 springMVC.xml 配置文件
 *              3.这个前端控制器 拦截除了 jsp/html 外的所有资源(在 web.xml 中 配置 url-pattern =="/"))
 *
 *      处理器映射器 HandlerMapping: 有多种: 可以是 注解映射器, 接口映射器, 配置文件映射器
 *      处理器适配器: HandlerAdapter : 自动识别 需要使用的 处理器映射器 类型, 然后转接给指定的 处理器映射器(注解/接口/配置文件)
 *
 *  二: 浏览器 --访问 --> http://localhost:8080/${pageContext.request.contextPath}/方法上 @RequestMapping 指定的 地址
 *      -> 前段控制器(DispatcherServlet) -> springMVC.xml --适配器--> 处理器 -> 根据映射的关系找指定的方法执行
 *
 *  封装 处理器和映射器的配置 : <mvc:annotation-driven/> -- 替换上面 两个 <bean> 标签的配置
 *          <annotaion-driven/>标签：包含以下两个实例：
 *                  RequestMappingHandlerMapping   处理@RequestMapping注解的
 *                  RequestMappingHandlerAdapter   处理@Controller注解的控制器类
 *
 *  术语:
 *      1. DispatcherServlet 前端控制器(front controller) (springmvc 封装的 servlet)
 *          用户(浏览器)请求到达 前端控制器, 就相当于 mvc 模式中的 c (controller)
 *          dispatcherServlet 是整个流程控制的中心, 由它调用其他组件处理用户的请求,
 *          dispatcherServlet 的存在 降低了 组件之间的耦合性
 *      2. HandlerMapping 处理器映射器
 *          handlerMapping 负责根据用户请求找到 handler (处理器), springMVC 提供了
 *          不同的 映射器(handlerMapping) 实现不同的 映射方式, 如 配置文件方式,实现接口方式, 注解方式等
 *      3. Handler: 处理器
 *          它就是开发中要编写的具体业务控制器. 由 dispactherServlet 把 用户请求转发到 Handler.
 *          由 Handler 对具体的用户请求进行处理.
 *      4. HandlerAdapter 处理器适配器
 *          通过 HandlerAdapter 对 处理器 进行执行,这是 适配器模式 的应用,通过扩展 适配器 可以对更多类型的 处理器 进行执行.
 *      5. View Resolver: 视图解析器
 *          view resolver 负责将处理结果生成 view 试图, view resolver 首先根据逻辑视图名解析成物理视图名(即 具体的页面地址)
 *          再生成 view 试图对象, 最后对 view 进行渲染,将处理结果通过页面展示给 用户.
 *      6. view 视图
 *          springMVC 框架 提供了很多 view 视图类型的支持, 包括: jstlView, freemarkerView, pdfView 等. 我们最常用的视图就是 jsp
 *          一般情况下,需要通过页面标签或页面模板技术将模型数据通过页面展示给用户,需要由程序员根据业务需求开发具体的页面
 *
 * 总结: springMVC 基本使用
 *    1. 在 web.xml 中配置 前端控制器: DispatcherServlet -- spring 提供的 servlet (浏览器服务器 交互的 接口)
 *     <!--配置 springMVC 提供的前端控制器: servlet-->
 *   <servlet>
 *     <servlet-name>dispatcherServlet</servlet-name>
 *     <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
 *     <init-param>  //指定 初始化 参数: servlet 的 init 方法, servlet 对象创建时 执行的方法
 *       <!--springMVC.xml 的配置文件地址: DispatcherServlet 对象创建时, 会加载 springMVC.xml(ioc/aop 配置文件)-->
 *       <param-name>contextConfigLocation</param-name> //参数名是 固定的: 由 springMVC 指定 - 底层就是通过 参数名 获取 指定的 参数值
 *       <param-value>classpath:springMVC.xml</param-value> //指定 参数值: ioc/aop 配置文件地址 classpath:xxx.xml
 *     </init-param>
 *
 *     <!--配置将第一次访问创建 servlet 变成 服务器已启动就创建
 *       服务器启动:
 *          1. DispatcherServlet 对象被创建出来
 *          2. 加载 classpath 下的配置文件: springMVC.xml
 *     -->
 *     <load-on-startup>1</load-on-startup>
 *   </servlet>
 *
 *   <servlet-mapping>
 *     <servlet-name>dispatcherServlet</servlet-name>
 *     <!--
 *       /* : 拦截所有
 *       / : 拦截所有,不包含 jsp/html
 *     -->
 *     <url-pattern>/</url-pattern>
 *   </servlet-mapping>
 *
 * 在 spring.xml 中
 * 2.配置 注解方式的 处理器映射器(HandlerMapping), 处理器适配器(HandlerAdapter)
 *      <mvc:annotation-driven/>
 * 3.开启 注解扫描器:
 *      <context:component-scan base-package="org.anonymous"/>
 *     把 相应的 类 交给 ioc 管理: 配置 @Controller()
 * 4.配置 视图解析器
 *       <!--视图解析器
 *         配置了 视图解析器之后: 指定 前/后缀,
 *         以后 所有 @RequestMapping 注解标注的方法的 返回值(跳转地址) 都可以简写
 *         eg: "/jsp/demo1.jsp" -> 简化为 "demo1"
 *     -->
 *     <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
 *         <!--前缀-->
 *         <property name="prefix" value="/jsp/"/>
 *         <!--后缀-->
 *         <property name="suffix" value=".jsp"/>
 *     </bean>
 * 5.定义类,在类中定义方法:
 *    方法注解: @RequestMapping('url')
 *    方法返回值: 跳转地址 - "/jsp/demo1.jsp"  或 在 配置了试图解析器之后 简写为: "demo1"
 *
 *
 * @RequestMapping 注解详解:
 *   配置请求路径的相关参数
 *      1.可以配置到 类上(用于区分模块), 也可以配置到 方法上
 *         如果 只配置在 方法上, 则通过浏览器访问时: 直接访问方法上定义的 路径
 *         如果 类和方法都配置了,则通过浏览器访问时: 必须以 类上的路径为 父路径, 方法上的路径为子路径, 才能访问到相应的方法
 *      2. value 属性 == path 属性: 配置请求路径,两个属性,等价
 *      3. method 属性: 接受请求的方式(post/get): 默认接受所有请求
 *      4. params 属性: 定义页面请求要携带的参数(如: params={"username", "password"}), 如果定义了,则页面必须携带请求参数(可以没有值), 否则会报错
 *
 * springMVC 中 参数封装(参数绑定)
 *   参数封装: 将页面请求的参数封装到对象中
 *   1.封装简单数据类型
 *   2.封装对象
 *   3.封装 list，map
 *   4.获取 servlet 相关 api： request，response，session。。。
 *
 * 另：
 *  1.post 中文乱码
 *   <!--配置 springMVC 提供的解决乱码的 filter-->
 *   <filter>
 *     <filter-name>characterEncodingFilter</filter-name>
 *     <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
 *     <init-param>
 *       <param-name>encoding</param-name>
 *       <param-value>utf8</param-value>
 *     </init-param>
 *     <!--开启 过滤器-->
 *     <init-param>
 *       <param-name>forceEncode</param-name>
 *       <param-value>true</param-value>
 *     </init-param>
 *   </filter>
 *
 *   <filter-mapping>
 *     <filter-name>characterEncodingFilter</filter-name>
 *     <url-pattern>/*</url-pattern>
 *   </filter-mapping>
 *
 * 2. servlet
 *      <!--配置 springMVC 提供的 servlet-->
 *   <servlet>
 *     <servlet-name>dispatcherServlet</servlet-name>
 *     <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
 *     <init-param> <!--初始化参数: 加载配置文件-->
 *       <param-name>contextConfigLocation</param-name>
 *       <param-value>classpath:springMVC.xml</param-value>
 *     </init-param>
 *     <!--提前加载-->
 *     <load-on-startup>1</load-on-startup>
 *   </servlet>
 *
 *   <servlet-mapping>
 *     <servlet-name>dispatcherServlet</servlet-name>
 *     <url-pattern>/</url-pattern>
 *   </servlet-mapping>
 *
 * 3. 类型转换器： 将页面的数据转换成指定的格式（如 String->Date）
 *    1) 自定义 类型转换器： 实现 springMVC 的 Converter<S,T> 接口，(实现方法)在其中定义 转换逻辑
 *    2）在 xml 中配置 类型转换器: 加入 自定义的 类型转换器
 *        <bean id="conversionService2" class="org.springframework.context.support.ConversionServiceFactoryBean">
 *          <!--在类型转换器中配置自定义的转换器: 把自定义的 类型转换器 加入到 springMVC 的 转换器集合中
 *            源码:
 *              public void setConverters(Set<?> converters) {
 * 		           this.converters = converters;
 * 	            }
 *         -->
 *          <property name="converters">
 *             <array> //注入 自定义的 类型转换器
 *                 <bean class="org.anonymous.utils.StringToDate"/>
 *             </array>
 *          </property>
 *        </bean>
 *    3) 在 映射器/适配器 标签中,加入 类型转换器属性
 *          <mvc:annotation-driven conversion-service="conversionService2(类型转换器标签的 id)"/>
 *
 * 4. 视图解析器: 指定路径的 前后缀(以后用到这些前后缀的地方都可以简写)
 *    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
 *         <property name="prefix" value="/jsp/"/>
 *         <property name="suffix" value=".jsp"/>
 *     </bean>
 * 5. 获取 请求头(键值对) 信息: @RequestHeader()
 *   属性:
 *      value: 请求头的 key 值
 *      required: 是否必须由此消息头(默认true: 没有该指定的请求头参数就会报错)
 *    注: 一般不用
 * 6. 获取 cookie 信息: @CookieValue()
 *    属性: 同 @RequestHeader()
 *      可以获取: JSESSIONID
 * 7. 在 springMVC.xml 中配置: 静态资源不拦截
 *   <!--设置静态资源不过滤: 不拦截
 *       location: 父目录
 *       mapping: 具体资源
 *       **: 目录下的所有资源
 *     -->
 *     <mvc:resources mapping="/js/**" location="/js/"/>
 *     <mvc:resources mapping="/img/**" location="/img/"/>
 *     <mvc:resources mapping="/css/**" location="/css/"/>
 *
 *
 * day02: 响应数据, 页面跳转方式, 文件上传
 *     1. spring 的 2 个注解: @ModelAttribute @SessionAttribute
 *          @ModelAttribute : 在访问 资源之前执行的 方法 - 类似于 junit 的 @Before -- 没有实现成功
 *          被 @ModelAttribute 注释的方法会在Controller每个方法执行之前都执行，因此对于一个Controller中包含多个URL的时候，要谨慎使用。
 *          可以使用在 方法上, 也可以使用在 方法参数上
 *              应用场景:
 *                  修改用户名/密码： update user set username = ?, password = ? where id = ?
 *                  1. 无法确定页面传过来的修改数据是 需要修改用户名，还是密码
 *                      因此，需要先 拿着 id 去数据库 查询该 id 对应的用户的信息(用户名/密码)- 封装的对象信息
 *                      再把页面的修改信息(用户名/密码)设置到 查询到的对象(user.setXxx(xxx))
 *                      然后,通过 该初始化方法的返回值(return user;),传递到 下一个执行方法(页面访问的实际地址)
 *
 *          @SessionAttribute
 *              配置到控制器类上,表明此类中的所有控制器方法涉及到了执行属性,就会将此属性保存到 session 域中
 *
 *
 *     2. RestFul 编程风格： @PathVariable("id")
 *         1) 必须使用的是地址参数
 *         2) 在同一个地址中,根据不同的提交方式做不同的功能实现
 *            如: get (查询), delete(删除), put(修改), post(曾)
 *        地址参数与请求参数:
 *           请求参数: https://item.jd.com/26603112292.html
 *           地址参数: https://item.jd.com?id=26603112292
 *       RestFul 特性
 *         资源:resources 网络上的一个实体,或者说网络上的一个具体信息(文字,图片,mp3,视频)
 *           要获取这个资源,必须访问它对应的 URI : 因此 uri 为每一个资源独一无二的识别符
 *         表现层: Representation 把资源具体呈现出来的形式,叫做表现层
 *           比如: 文本可以用 txt 格式表现,也可以用 HTML 格式, XML 格式, JSON 格式, 或 二进制格式
 *         状态转化 state transfer: 每发出一个请求,就代表了客户端和服务器的一次交互过程.
 *            HTTP 协议,是一个无状态协议,即所有的状态都保存在服务器端. 因此,如果客户端想要操作服务器,
 *            必须通过某种手段,让服务器端发生"状态转化"(state transfer). 而这种转化是建立在表现层之上的,
 *            所以就是 "表现层转化状态". 具体说,就是 HTTP 协议里面,四个表示操作方式的动词: get/post/put/delete
 *            它们分别对应四种基本操作: get 用来获取资源(查), post 用来新建资源(曾), put 用来更新资源(改), delete 用来删除资源(删)
 *
 *     3. ajax 数据封装和数据转换
 *          @RequestBody: 配置到方法形参上,表示将 页面的 ajax 提交的 Jason 数据转换成对象
 *          @ResponseBody: 配置到 方法返回值 上, 表示将 返回值(对象类型) 转换成 jason 字符串
 *
 *     4. 数据响应和响应方式: 祥见:D:\Develop\IDEA_Project\maven-project\springMVC_day02_a\src\main\java\org\anonymous\controller\StringVoidModelAndViewReturn.java
 *        控制器方法中,将数据 绑定到 request 域中, 就叫 数据响应
 *        页面跳转:
 *           请求转发: 发送一次请求
 *           重定向: 发送两次请求(第一次是用户主动利用浏览器的执行请求,第二次是浏览器自动访问了服务器的重定向地址)
 *
 *       1) String 类型放回置
 *         数据响应: request 对象
 *         页面跳转:
 *             默认: 以请求转发的方式跳转
 *       2) void 返回值
 *
 *       3) ModelAndView 数据类型返回值
 *
 *
 *    5. springMVC 实现文件上传
 *       上传: 使用 form 表单
 *          三个条件
 *            1) 表单类型必须是 file, 必须得有 name 属性
 *            2) 提交方式必须是 post 方式
 *            3) 表单的 enctype 属性值必须是: multipart/form-data
 *                如果表单中不写这个属性,默认值是: enctype=application/x-www-form-urlencoded
 *                     -- 这个默认值表示: 告诉服务器,浏览器提交的都是 简单字符串数据
 *
 * day03: 统一异常处理, 拦截器, ssm 整合
 *    1.文件上传： 跨服务器
 *        1) 导包
 *        2) 确定要传递的服务器地址: 另一个 tomcat 服务器地址(本机就改端口)
 *        3) 自己创建一个客户端
 *        4) 使用提供好的对象 io 上传
 *
 *    2.统一异常处理：
 *        自定义异常处理器: 实现 HandlerExceptionResolver 接口
 *        在 ioc 中配置 该自定义异常处理器
 *        以后服务器任何错误都会跳转到 该异常处理器
 *
 *    3.拦截器
 *        机制: 同过滤器
 *        过滤器: 是 servlet 规范的一种,任何 Java web 工程都可以使用
 *            在 url-pattern 中 配置了 /* ,就会对所有资源拦截
 *        拦截器: 是 springMVC 框架的, 只有使用了 springMVC 框架的工程才能用
 *            它只会拦截访问的 控制器方法 ,如果访问的是 jsp,html,css,image 或 js 是不会拦截的
 *
 *      自定义拦截器: 实现 HandlerInterceptor(其中是默认方法: 需要主动实现)
 *        配置 ioc
 *
 *    4.ssm 整合
 *       springMVC: servlet
 *       spring: ioc， aop事务
 *       mybatis: dao
 *     整合三层的基本思路
 *      1) spring 单独跑通(只测试 service 层: 接口+实现类)
 *           坐标： springJunit, ioc, aop
 *           配置文件: bean.xml
 *           测试: SpringJunit
 *      2) mybatis 单独跑通(只测试 dao 层)
 *           坐标: mybatis + dao 接口 (动态代理)
 *           配置文件: SqlMapConfig.xml, xxxDao.xml
 *           测试: MybatisTest: SqlSessionFactoryBuilder ...
 *      3) 整合 spring+mybatis
 *          整合坐标: mybatis-spring
 *          bean.xml: 注入 SqlSessionFactory -- 替代 SqlMapConfig.xml (别名,数据库连接信息,sql 映射配置文件)
 *             <!--将 sqlSessionFactory 交给 ioc: 替代 mybatis 核心配置文件-->
 *              <!--整合包提供了一个类,可以帮助创建 sqlSessionFactory 的实现类-->
 *          <bean class="org.mybatis.spring.SqlSessionFactoryBean">
 *              <!--别名-->
 *               <property name="typeAliasesPackage" value="org.anonymous.domain"/>
 *              <!--连接池-->
 *               <property name="dataSource" ref="druid"/> //数据库连接池: c3p0,druid,spring
 *              <!--sql映射配置文件-->
 *          </bean>
 *
 *              <!--专门用来加载 sql 映射配置文件: 来自整合包
 *                  1. 加载 sql 映射配置文件
 *                  2. 创建接口的 代理对象,交给 ioc
 *                  -->
 *          <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
 *               <property name="basePackage" value="org.anonymous.dao"/>
 *          </bean>
 *
 *       aop 与 事务:
 *          <!-- aop事务配置: 事务切面类交给 ioc-->
 *          <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
 *              <property name="dataSource" ref="druid"/> //注入连接池
 *          </bean>
 *          <!--为方法配置事务特性-->
 *          <tx:advice id="txAdvice">
 *                      <!--给指定方法设置事务特性-->
 *                  <tx:attributes>
 *                              <!--查询方法: 设置只读,不需要事务, 效率更高
 *                                  其他方法: 非只读, 需要事务
 *                                  -->
 *                          <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
 *                          <tx:method name="*" propagation="REQUIRED" read-only="false"/>
 *                  </tx:attributes>
 *          </tx:advice>
 *          <!--aop 在指定切入点上增强事务-->
 *          <aop:config>
 *                      //切入点
 *                  <aop:pointcut id="pt" expression="execution(* org.anonymous.service..*.*(..))"/>
 *                  //关联切入点和通知 (关联 aop 和 事务)
 *                  <aop:advisor advice-ref="txAdvice" pointcut-ref="pt"/>
 *          </aop:config>
 *
 *     4) springMVC 单独跑通
 *         坐标:
 *         控制器方法: @RequestMapping(value)
 *         tomcat: 访问控制器方法
 *
 *   整合三层:
 *      一: web.xml
 *         1. 处理中文乱码
 *            <filter>
 *                  <filter-name>characterEncodingFilter</filter-name>
 *                  <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
 *                  <init-param>
 *                      <param-name>encoding</param-name>
 *                      <param-value>utf8</param-value>
 *                  </init-param>
 *            </filter>
 *            <filter-mapping>
 *                  <filter-name>characterEncodingFilter</filter-name>
 *                      <!--过滤所有-->
 *                  <url-pattern>/*</url-pattern>
 *            </filter-mapping>
 *         2. servlet: 服务器启动,加载 springMVC.xml 和 bean.xml
 *             <servlet>
 *                  <servlet-name>dispatcherServlet</servlet-name>
 *                  <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
 *                  <init-param>
 *                        <param-name>contextConfigLocation</param-name>
 *                          <!--关键: 服务器启动: 加载 springMVC.xml 和 bean.xml-->
 *                   <!--<param-value>classpath:springMVC.xml, classpath:bean.xml</param-value>-->
 *                          <!--方式二: 使用 监听器加载 bean.xml-->
 *                          <param-value>classpath:springMVC.xml</param-value>
 *                  </init-param>
 *                  <load-on-startup>1</load-on-startup>
 *             </servlet>
 *             <servlet-mapping>
 *                  <servlet-name>dispatcherServlet</servlet-name>
 *                      <!--拦截所有:不包含 jsp/html-->
 *                  <url-pattern>/</url-pattern>
 *             </servlet-mapping>
 *         3. 服务器启动 同时 加载 bean.xml 方式二: 监听器方式
 *              <listener>
 *                  <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
 *              </listener>
 *              <context-param>
 *                   <param-name>contextConfigLocation</param-name>
 *                   <param-value>classpath:bean.xml</param-value>
 *              </context-param>
 *
 *    二: springMVC.xml
 *        <!--包扫描: web: 这里扫描了 web层的注解, bean.xml 不会再创建 web 层对象 -->
 *     <context:component-scan base-package="org.anonymous.web"/>
 *     <!--映射器/适配器-->
 *     <mvc:annotation-driven/>
 *     <!--视图解析器-->
 *     <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
 *         <property name="prefix" value="/WEB-INF/jsp/"/>
 *         <property name="suffix" value=".jsp"/>
 *     </bean>
 *
 *   三: bean.xml
 *       1. 引入 jdbc 连接信息(.properties 文件) : 可选
 *       2. 注解扫描
 *       3. SqlSessionFactoryBean: 属性 typeAliasesPackage, dataSource
 *       4. sql 映射配置文件加载器: MapperScannerConfigurer
 *       5. 数据库连接池: druid,spring(DriverManagerDataSource),c3p0
 *       6. aop 事务配置: 事务切面类(DataSourceTransactionManager: 注入 连接池), 通知, 通知+切入点
 *                  <!--aop 事务配置-->
 *                  <!--事务切面类:其中有管理事务的通知-->
 *              <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
 *                  <property name="dataSource" ref="druid"/>
 *              </bean>
 *                  <!--为方法配置事务特性-->
 *              <tx:advice id="txAdvice">
 *                  <!--给指定方法设置事务特性-->
 *                  <tx:attributes>
 *                  <!--查询方法: 设置只读,不需要事务, 效率更高
 *                          其他方法: 非只读, 需要事务
 *                      -->
 *                      <tx:method name="find*" propagation="SUPPORTS" read-only="true" isolation="DEFAULT" timeout="-1"/>
 *                      <tx:method name="*" propagation="REQUIRED" read-only="false"/>
 *                  </tx:attributes>
 *               </tx:advice>
 *                      <!--aop 在指定切入点上增强事务-->
 *              <aop:config>
 *                  <aop:pointcut id="pt" expression="execution(* org.anonymous.service..*.*(..))"/>
 *                  <!--连接 通知与 切入点-->
 *                  <aop:advisor advice-ref="txAdvice" pointcut-ref="pt"/>
 *              </aop:config>
 *      7. 静态资源不过滤
 *                  <!--设置静态资源不过滤: 不拦截
 *                 location: 父目录
 *                 mapping: 具体资源
 *                 **: 目录下的所有资源
 *                  -->
 *              <mvc:resources mapping="/js/**" location="/js/"/>
 *              <mvc:resources mapping="/img/**" location="/img/"/>
 *              <mvc:resources mapping="/css/**" location="/css/"/>
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
public class SSM_SpringMVC {
}
