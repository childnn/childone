package org.anonymous.tomcat_servlet;

/**
 * 银行使用的 数据库 和 web服务器： db2 + WebSphere(IBM)
 * 09点55分 2019/3/22
 *  web资源： 可以通过浏览器访问的资源（数据）
 *      静态资源： 在页面写死的数据， 永久不变（HTML/css/js 图片）
 *      动态资源： 数据不是写死在页面， 数据是从中介（数据库）获取 （servlet/jsp）
 *  常见 web 服务器:
 *      tomcat_servlet : 小型免费web服务器软件 --Apache
 *      WebSphere: 大型收费web服务器软件 -- IBM
 *      WebLogic: 大型收费web服务器软件 -- Oracle
 *
 *  只要在电脑上安装了 tomcat_servlet 软件,这个电脑就可以称为 tomcat_servlet 服务器(web服务器)
 *  只要是部署在 web 服务器中的项目,就称为 web 项目, 可以被所有人通过浏览器访问
 *      tomcat_servlet: 安装教程
 *              1 下载一个tomcat服务器软件. 官网地址 http://tomcat.apache.org
 *              2 解压下载好的tomcat. 注意：将解压后的文件copy到一个没有中文和空格的路径下
 *              3 启动tomcat服务：进入bin路径 双击startup.bat 服务器就可以启动：
 *              4 测试 在页面输入地址访问http://localhost:8080
 *              5 关闭 tomcat服务：直接关闭 或者去 bin路径 双击shutdown.bat
 *      tomcat_servlet 解压后的文件夹目录结构 ---- 重点------------------------------------------
 *        1. bin: 二进制目录 都是 tomcat_servlet 的大量命令 关注: 启动/关闭
 *            开启: bin\startup.bat --windows  (Linux -- bin\startup.sh)
 *            关闭: 叉掉 / bin\shutdown.bat
 *        2. conf: tomcat_servlet 核心配置文件 关注: server.xml(tomcat_servlet 端口号配置)  web.xml(规定了大量的文件类型-后期下载文件时使用)
 *          端口: 8080
 *              查询: cm--> netstat -ano
 *              改端口: conf\server.xml -- 69行 改端口
 *           注: tomcat_servlet 只要被访问, 就会自动在 conf 目录下创建 目录: Catalina\localhost -- 用来存放 用户自定义的 .xml 配置文件(外部引入的web项目)
 *        3. lib: jar 包
 *        4. logs: 日志文件目录  关注: catalina.yyyy-MM-dd.log (每天的日志)
 *              日志: logs
 *        5. temp: 启动过程中用来自动存放的临时文件
 *        6. webapps: 存放开发好的项目 -- 把 web 项目交给 tomcat_servlet 来管理, 等待被访问
 *            在浏览器输入: localhost:8080 打开的就是 webapps 目录下的项目
 *                  默认打开 tomcat_servlet 自带项目, 想打开自己的项目, 表示为 localhost:8080\项目目录名
 *        7. work: 和后期的 jsp 有关
 *  web项目(静态资源和动态资源)的目录结构: 重点中的重点!!!------------------------------------------------
 *    MyWeb: 整个 web 项目的根目录
 *        根目录: 存放 静态资源(html/css/js 图片,视频,多层目录)
 *        WEB-INF 目录 -- 该目录下的资源不能通过浏览器直接访问,必须通过 Java 代码(程序)访问 -- 存放保密的静态资源
 *              classes 文件夹 -- 存放 .class 文件
 *              lib 文件夹 -- 整个项目需要的 jar 包 -- 手动创建
 *              web.xml 文件 -- 整个 web 项目的核心配置文件 作用域整个项目
 *                      Servlet2.5 版本有 web.xml 文件, Servlet3.0版本没有 web.xml 文件,被注解 annotation 替代了
 *        index.html/index.jsp --- 默认首页
 *  Servlet
 *      概述：
 *          官方： servlet 是运行在 web 服务器中的小型 java 程序
 *          Servlet 本质上就是一个 Java 接口， 只有实现了 Servlet 接口（规范），这个 Java 类才能在 web 服务器上运行
 *          这个实现了 Servlet 接口的 实现类 就可以称为 Servlet
 *      作用：
 *            1. servlet 接受浏览器发送过来的 【请求数据】 （HTML 中的 表单form 的 action 属性）
 *            2. 给浏览器做 【响应数据】 -- 如果不给响应信息,默认为 空响应
 *   tomcat_servlet 启动的第一件事，就是加载 web.xml 文件 -- 在 web.xml 中配置 具体的 Servlet
 *
 *   servlet 的生命周期: (重点) --- 接口的三个方法
 *      init: 初始化
 *          执行者: tomcat_servlet 服务器
 *          执行时机: 默认第一次访问执行// 也可以在 web.xml 文件中配置,在 tomcat_servlet 服务器启动时,初始化创建对象
 *          执行次数: 1 次, servlet 只有一只对象
 *      service : 处理请求/响应数据
 *          执行者: tomcat_servlet 服务器
 *          执行时机: 浏览器的 请求数据 发送到服务器时
 *          执行次数: 访问一次,执行一次
 *      destroy: 销毁方法
 *          执行者: tomcat_servlet 服务器
 *          执行时机: 关闭 本 web 项目与 tomcat_servlet 服务器的连接时 -- 结束项目部署
 *          执行次数: 1 次
 *     配置 servlet 在 tomcat_servlet 服务器 初始化的时候加载(创建对象)
 *         在 <servlet></servlet> 标签下,添加 子标签
 *          <load-on-startup>1</load-on-startup>
 *
 *  Servlet 3.0
 *      支持注解配置, 可以不需要 web.xml 文件
 *
 *   三层架构:
 *          web 服务器：（tomcat）
 *              1.web 层： （Servlet） -- spring
 *                  作用： 获取浏览器的请求信息。 给浏览器做响应信息。
 *                  步骤：
 *                      1）.解决页面传递的 post 中文乱码
 *                      2）.需要接受页面传递的信息（用户名/密码）
 *                      3）.调用service层传递参数
 *                    接收 service 层的返回值，根据返回值给 页面做 响应
 *              2.service 层： （业务处理层） -- WebSphere(IBM)
 *                   1）编写 事务（增删改需要事务）
 *                   2）处理业务逻辑
 *                   3）调用 dao 层传递参数
 *                  将 dao 层的返回值返回给 web 层
 *              3.dao 层：（数据持久层 -- 有各种类） -- db2
 *                    作用：只用来和数据库交互
 *                   将返回值 返回给 service 层
 *  mvc 设计模式思想:(面试题)
 *   java 中的 mvc 体现: model2
 *      jsp + javabean + servlet : model2 (目前方式)
 *      jsp: 收集 servlet 传递的数据, 展示数据
 *      servlet: 接受页面请求,找到合适 javabean 去封装并处理
 *      JavaBean: 封装业务,封装对数据的操作
 *
 *      核心思想: 将业务逻辑,数据 和 展示 相分离的一种思想,让每一块都专注于做自己的事情
 *      M: model 封装数据/数据的操作 -- Javabean
 *      V: view  展示数据(java使用jsp) -- jsp
 *      C: ctrl 接受请求并响应 --- servlet
 *
 */
public class Tomcat_Servlet_Demo {
}
