package org.anonymous.cookie_session;

/**
 * @author child
 * 2019/3/26 9:16
 * 是什么,为什么要用,怎么用
 * 会话技术:
 *      当打开一个浏览器的时候,意味着会话开始了,当关闭浏览器的时候,意味着会话结束了
 *      开始和结束之间,浏览器和服务器可以做n多次交互
 *    作用: 用来存储浏览器和服务器交互过程中产生的数据(cookie和session)
 *    保证每个人的私有数据可以在同一个服务器(同项目)的所有 servlet 对象中共享
 *    每个人有自己独有的 cookie/session
 *   注:
 *      域对象: 用来共享数据. 只针对服务器端 (所以 Cookie 对象不被称为 域对象,虽然类似域对象)
 *      request 域对象 用来请求转发 (每一次请求就有一个对象)
 *      ServletContext 域对象 用来重定向 (多次请求只有一个对象--一个项目只有一个对象)
 *      session 域对象 用来 在 servlet 之间的共享 私有数据
 *
 * Cookie: 客户端会话技术.将存储的数据保存在 cookie 中
 *     由服务器创建
 *     往 cookie 中存数据
 *     将存储完数据的 cookie 交给浏览器保存 -- 响应头
 *     注: 服务器 可以创建多个 cookie, 可以给 浏览器 传递多个 cookie, 但是存在 key 值覆盖
 *         同一个浏览器访问同一服务器,都会携带 存储的 cookie 到 请求头 给服务器 (键值对).服务器的 servlet 可以拿到 cookie, 执行业务
 *    1.将 会话级别 变成持久化级别:  浏览器默认 记录 cookie 为 会话级别(浏览器关闭就清空 cookie)
 *       coookie.setMaxAge(秒时间): 在设置的有效时间内,浏览器都会保存 cookie(不论浏览器是否关闭)
 *       设置时间一到,浏览器自动销毁 cookie
 *       不同浏览器不能共享 cookie
 *    2.默认情况下,浏览器访问不同的项目,会携带不同的 cookie(cookie中存的资源只在本项目中有效)
 *       但是,在服务器向浏览器传 cookie 时, 可以设置 本 cookie 在哪些路径/项目中有效
 *       --- 设置 cookie 的有效路径
 *       cookie.setPath("/虚拟项目名/servlet虚拟名"); // 告诉浏览器, 本 cookie 只有在访问 指定路径 资源 时才会携带
 *       cookie.setPath("/项目名"); //告诉浏览器 访问指定项目下的所有 资源 都会 携带 本 cookie
 *       cookie.setPath("/"); //告诉浏览器 访问 本 服务器 下的所有项目 都会携带 -- 同一服务器的多个项目 之间 资源对接
 *       默认不设置路径时: 表示 当前 servlet(传递 cookie 的servlet) 虚拟路径 的 上一级目录(父目录)
 *      注意：
 *          cookie 中不能出现特殊符号： 如：空格，冒号，分号 -- 编码解码  URLEncoder/URLDecoder
 *          存入数据大小限制：4kb
 *
 * Session: 服务器端会话技术. 将存储的数据保存在 session 中 -- 域对象
 *     request.getSession(); -- 第一次调用此方法时,创建 session 对象 -- 后面运行中再次调用该方法,会先判断 是否有 session 对象,如果有直接用
 *     session.setAttribute(String key, Object value)
 *     session.getAttribute(key)
 *     removeAttribute(key)
 *    作用范围:(域对象)
 *       同一次会话(浏览器不关闭): 只有 一个 session // 关闭浏览器后重新打开就时不同的 session 对象
 *    服务器如何确保在一次会话中,多次请求获取的是同一个 session
 *      session 是依赖于 cookie 的
 *      session 对象的创建:
 *       第一次请求, 获取 session -- getSession() 时, 会在内存中创建一个新的 session 对象 -- 分配 JSESSIONID (内存地址)
 *            同时 getSession() 方法底层会 创建 cookie 对象, 把 JSESSIONID:session地址值 以键值对的形式 存在 cookie 中,
 *       第一次响应时,会将 session 以 cookie 的 键值对形式 发送到 响应头
 *       当前会话中(浏览器不关闭),再次访问项目中的任意servlet时, 会得到 请求页面的 cookie(请求头键值对),其中有 session 的 id (对象地址值)
 *
 *       在会话级别中,浏览器关闭, cookie 被销毁, session 也就 找不到了(session 在服务端,并没有被销毁) -- 不考虑持久化级别的 cookie
 *          会话级别中,浏览器关闭只是 cookie 被销毁, session 并没有被销毁(session 域的数据保存再 服务端, 浏览器端需要利用 JSESSIONID 获取 服务端的 session)
 *      如果希望 session 也得到持久化,必须通过 cookie 的持久化,设置
 *       Cookie cookie = new Cookie("JSESSIONID", getSession().getId())
 *       cookie.setMaxAge(秒时间); --设置 cookie 的生命周期
 *       response.addCookie(cookie); -- 设置响应头, 让浏览器 记录 cookie
 *       下一次浏览器访问服务器时, 携带 cookie 的请求头(键值对), 通过 JSESSIONID 获取 session 的地址值,得到上一次的 session 对象
 *
 *       浏览器不关闭,服务器关闭后,如何保证 浏览器获取到同一 session
 *          服务器正常关闭,session 不会销毁,而是序列化到本地硬盘
 *              session 的钝化: 将 session 的数据保存到 浏览器端 本地硬盘
 *              session 的活化: 再次启动服务器,浏览器访问时, tomcat 会自动加载 session 的数据到内存
 *      session 的销毁:
 *          1.服务器非正常关闭: session 来不及序列化  -- 正常关闭服务器(撤销项目部署) session 不会销毁 -- 可以使用 HttpSessionListener 监听 session 的生命周期
 *              -- 可以尝试 重启服务器,浏览器不关闭,等待浏览器重启完成,再次访问 服务器, session 并没有销毁, 还会得到之前 session 域中 的数据
 *          2.session对象调用 invalidate() 方法
 *          3.session 默认失效时间 30 分钟
 *              可以配置 tomcat 的文件: conf/web.xml  文件中的 session-config 标签设置时间
 *      session 的特点:
 *          存储一次会话的多次请求(多个 request 对象)的数据, 存在服务器端
 *          可以存储 任意类型(Object) -- cookie 只能通过 new 对象时, 给定 两个 String
 *         域对象储存数据时: key 都是 String, value 都是 Object
 *
 *    session 和 cookie 区别:
 *          session 存在服务器端, cookie 存在客户端
 *          session 无大小数据限制,cookie 有
 *          session 数据安全, cookie 相对不安全
 *
 * 域对象:
 *    只要涉及到多个 servlet 或者是 jsp 的资源传递就需要用到域对象
 *    都可以用来存储数据,
 *    都可以用来在多个 servlet 或 jsp 之间进行数据传递
 *    都有 xxxAttribute() : set/get/remove 增删取 数据
 *
 *    request
 *      创建: 一次请求创建一个 request 对象
 *      销毁: 响应即销毁当前的 request 对象
 *      数据的作用范围: 在浏览器的一次请求中, 只要涉及到多个 servlet/jsp 的多次转发
 *              request 对象的数据 可以共享
 *    servletContext
 *      创建: 服务器启动,项目部署,就创建该项目的 servletContext 对象, 一个项目有且只有一个 此对象
 *      销毁: 服务器关闭/项目撤销部署,即销毁
 *      数据作用范围: 只要在当前项目下,项目下的所有 servlet/jsp 都可以共享 (数据公开)
 *    session
 *      创建: 第一次调用 request.getSession() 时
 *          注: 每一次 request 调用 getSession() 方法时, 底层会先判断 请求头 中 有无 cookie 带过来的 JSESSIONID
 *      销毁:
 *          主动: session.invalidate()
 *          被动: tomcat 默认 30 分钟不操作改 session 销毁
 *          非正常: 服务器非正常关闭
 *      数据作用范围: 只要在同一次 会话(浏览器不关闭), 多次请求,得到的是同一个 session 对象,一次会话中
 *
 */
public class CookieAndSession {
}
