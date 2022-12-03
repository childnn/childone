package org.anonymous.HTTP;

/**
 * @author child
 * 2019/3/23 8:44
 * B/S
 *     浏览器向 服务器发送请求
 *     服务器给 浏览器 响应
 * HTTP： hyper text transfer protocal
 *    传输协议： 定义了 客户端/浏览器 与 服务端通信 (request/response) 时 发送数据的格式/内容
 *    特点：
 *      1. 基于 TCP/IP 的高级协议
 *      2. 默认端口号： 80
 *      3. 基于 请求/响应 模型：先有请求,后有响应. 一次请求，对应一次响应  -- request/response
 *      4. 无状态： 每次请求之间相互独立，不能交互数据
 *    历史版本：
 *          1.0: 每一次请求响应都会建立新的连接
 *          1.1：复用连接 -- 第一次建立连接时，会判断后续是否需要新链接， 有需要则复用连接
 *   浏览器请求服务器的方式: request
 *      1.在地址栏中直接输入
 *      2.表单的 action
 *      3.超链接 <a></a> 的href
 *      4.js的 location.href
 *   明确学习要点:
 *      1. 看一看浏览器都带了那些格式的数据给服务器
 *      2. 看一看服务器都带了那些格式的数据给浏览器
 *      3. 想一想他们之间带的这些数据在开发中可以帮助我们做什么事情
 *   请求消息数据格式
 *      1.请求行
 *          请求方式(get/post) 请求url 请求协议/版本
 *          eg: GET /index.html HTTP/1.1
 *          请求方式: HTTP 有 7 种请求方式,常用有 2 种
 *              GET:
 *                  1) 请求参数在请求行中, 紧接着 url 后面 (地址栏中显示提交信息)
 *                  2) 请求的 url 长度有限制
 *                  3) 不安全 --(显示)
 *              POST:
 *                  1) 请求参数在请求体中, (不在地址栏中显示具体信息)
 *                  2) 请求的 url 长度没有限制
 *                  3) 安全
 *      2.请求头 (键值对)
 *           请求头名称: 请求头值1,值2..
 *           referer: 当前页面的来源地址 -- 防盗链
 *           user-agent: 用户的浏览器版本信息 -- 下载
 *      3.请求空行
 *          空行
 *      4.请求体:
 *               get 方式没有请求体 -- 提交的内容在请求行(地址栏)中
 *               post 提交有请求体 -- 所有数据在请求体中
 *          请求参数数据: 表单提交的键值对内容
 *  api: 想要获取到浏览器 带过来的请求行 请求头 请求体./......
 *  浏览器发送请求, 把 请求行/请求头/请求体 数据 发送给服务器
 *      服务器 接受请求,就会创建 请求对象(Request) --封装浏览器带过来的 请求行/请求头/请求体数据
 *              然后将 request 当成参数传递给 servlet
 *            还会创建一个 响应对象(Response),用来以后 封装数据给 浏览器做响应
 *     注意:  请求一次,request/response对象创建一次,响应回来,就销毁一次 --- 于servlet 对象的区别!!!
 *  Request 对象:
 *      代表浏览器的请求, 当浏览器端通过http协议访问服务器时,http请求中所有信息(请求行,请求头,请求体数据)封装在这个对象中.
 *      开发人员通过这个对象的api,可以获得客户通过浏览器传递过来的数据.
 *  Interface ServletRequest --子接口--> Interface HttpServletRequest --> 服务器(tomcat)的 实现类
 *          -->  RequestFacade 类 (由 tomcat 提供)
 *  request 功能: （重点）----------------------请求行 4 个 + 请求头 2 个 + 请求体 3 个方法 + 请求转发（链式） + 资源共享 3 个方法
 *      1.获取请求消息数据
 *          1) 获取 请求行 数据
 *              Get URL HTTP/1.1
 *              方法: (4个方法)
 *                  1. 获取请求方式: GET/POST ----------- (tomcat 7 以下使用较多,重点)
 *                        String getMethod() : 解决 tomcat 7 post 传递汉字乱码
 *                        tomcat 7， get/post 传递中文都会乱码
 *                        tomcat 8+, post 传递中文会乱码(get不会乱码) -- 解决： request.setCharacterEncoding("utf-8"); 设置 tomcat 的解码方式
 *                        浏览器 把 数据以 utf-8 编码 后 发送给服务器， 服务器以 iso8859-1 解码
 *                 (重点) 2. 获取虚拟目录: 一般虚拟目录就是 项目名 ---------- (重点)
 *                        String getContextPath()
 *                  3. 获取Sevlet路径: sevlet 别名
 *                        String getSevletPath()
 *                  4. 获取get 方式请求参数: 表单提交的数据
 *                        String getQueryString()
 *                  5. 获取请求uri(统一资源标识符)/url(统一资源定位符): 项目名/servlet别名 ------------ 重点
 *                         String getRequestURI():(不精准) 不包括 IP 地址,
 *                         StringBuffer getRequestURL():(精准定位) 包括 ip 地址: http://192.168.11.11/项目虚拟路径(项目名)/servlet路径(web.xml中的servlet别名: url-pattern)
 *                  6. 获取协议版本: HTTP/1.1
 *                          String getProtocal()
 *                  7. 获取客户机的IP地址 ---------------- (重点) -- 投票系统(一个 ip 只能投一次,把 ip 放入 map 集合,类似于计数字符个数,ip存在就不再投票,不存在就把ip放入集合并投票)
 *                        String getRemoteAddr()
 *          2) 获取 请求头 数据
 *              方法: (2个方法)
 *                  Enumeration<String> getHeaderNames(): 获取所有的 请求头 键值对
 *                  String getHeader(String name): 根据指定请求头 获取相应的 value
 *                              Referer(发送请求页面的地址来源 -- 防盗链) 和 user-agent(浏览器版本(编码有关) -- 下载) 请求头的 值(value)
 *          3) 获取 请求体 数据: 只有 post 请求方式有请求体, 在请求体中封装了 post 请求参数
 *              方法:
 *                  BufferedReader getReader() Retrieves the body of the request as character data using a BufferedReader.
 *                  ServletInputStream getInputStream() Retrieves the body of the request as binary data using a ServletInputStream.
 *                          在文件上传知识中讲解
 *                 重点:（3个）
 *                  String getParameter("name键"): 获取一键 单值的数据
 *                  String[] getParameterValues("复选框name键"): 获取一键 多值 的数据
 *                  数据过大,使用下面的方法:
 *                      Map<String, String[]> getParameterMap(): map 的 key 是 标签中的 name 属性值, value 是 页面提交的数据,以数组形式返回
 *     请求转发 （forward）（重点）----------------------------2个方法-----------------------------------------------------
 *          一种在服务器内部的资源跳转方式
 *          1.通过 request 对象获取请求转发器对象： RequestDispatcher getRequestDispatcher("转发路径"); -- 转发的 url
 *          2.RequestDispatcher 类的方法： void forward(ServletRequest request, ServletResponse response) Forwards a request from a servlet to another resource (servlet, JSP file, or HTML file) on the server.
 *          链式编程： request.getRequestDispatcher("转发路径内部资源").forword(request, response);
 *          特点：（面试题） ---------------------------------   注意于 重定向 的区别----------------
 *              1.浏览器地址栏路径不变 -- 第一个接受请求的 servlet
 *              2.只能转发到 当前服务器 当前项目的内部路径（内部资源）
 *              3.转发中，多个资源使用的是同一个请求对象， 只有一次请求 -- 可以用来在转发中数据共享
 *     共享数据：（重点） -----------3个方法------------
 *          域对象： 一个有作用范围的对象，可以在范围内共享数据
 *          request 域： 代表 一次请求 的范围，一般用于请求转发的多个资源中共享数据
 *              request 对象可以用来存数据，底层有个 map 集合（键值对）， 这个对象称为： 域对象
 *              重点： request 对象存完数据后在哪些范围中可以使用：【一次请求的多次转发中可以使用】
 *              request.setAttribute(String name,Object obj); 存储数据 -- key = value
 *              request.getAttribute(String name); 通过键获取值
 *              request.removeAttribute(String name); 通过键移除键值对
 *     获取 ServletContext 对象 : 全局管理者 -- 域对象 ---  重点 ---
 *          部署到服务器的每一个项目有且只有一个 ServletContext 对象,用来管理整个项目的应用
 *          创建: 当服务器启动,会为部署在它上面的项目创建一个 ServletContext 对象,有且只有一个,用来管理项目
 *          销毁: 当服务器关闭/项目撤销部署,会为当前的项目销毁 ServletContext 对象
 *      获取 ServletContext 对象:
 *          1) Servlet 接口的 方法 getServletConfig()
 *                  --> ServletConfig 接口的方法 : ServletContext getServletContext();
 *          2) HttpServlet 是 ServletConfig 的子接口
 *                  直接用: this.getServletContext();
 *          3) request.getServletContext();
 *          概念: 代表整个 web 应用, 可以和程序的容器(服务器,tomcat)来通信
 *          功能:
 *             1.域对象: 共享数据 -- 核心功能!!!
 *                  底层也是 map 集合
 *                  setAttribute(String key, Object value)
 *                  getAttribute(key)
 *                  removeAttribute(key): 删除键值对
 *                作用范围: 整个项目共享数据 -- 只要本项目 不撤销部署, ServletContext 对象中存的资源可以共享
 *             2.获取文件类型:  MIME 类型 -- tomcat\conf\web.xml 文件中有各种 mime 类型
 *                  mime 类型: 在互联网通信过程中定义的一种文件数据类型
 *                  格式: 大类型/小类型(具体类型)
 *                  .html/.htm: text/html
 *                  .jpg/.jpeg: image/jpeg
 *                获取:
 *                    String getMimeType(String file): Returns the MIME type of the specified file, or null if the MIME type is not known.
 *             3.获取文件的真实路径: 本服务器本项目的实际路径(硬盘地址) -- idea 对应 out 文件夹(.class 文件的存放位置)
 *                   ServletContext 类的方法 getRealPath(String 文件路径/文件名)
 *                     web 目录下: /文件名
 *                     WEB-INF 目录下: /WEB-INF/文件名
 *                     src 目录下: /WEB-INF/classes/文件名 -- 也可以用 classLoader()
 *
 * 解决乱码:
 *           request.setCharacterEncoding("utf8"); //解决请求的 post 提交中文乱码
 *           response.setContentType("text/html;charset=utf8"); //解决响应的 中文乱码
 *
 * 2019年3月25日 09点27分 ---------------response 对象-----------------------
 *  响应： 服务器响应浏览器 --------- （重点）--------------
 *      响应行： http/1.1 200/404/500/405 ----> 协议版本 状态码
 *              状态码：3 位数字 -- （面试题）
 *                  1xx：服务器正在处理请求/响应中，还未完成
 *                  2xx：成功，请求响应完成。 代表码：200
 *                  3xx：重定向
 *                      302：重定向 -- 浏览器接受到 【响应行】的 302 和 【响应头】 的 新的 url，自动跳转 新的 url （新的 servlet路径）
 *                      304：访问缓存 -- 浏览历史记录（浏览器自己的缓存）
 *                  4xx：客户端/浏览器错误
 *                      404 -- 没有浏览器输入的对应资源（路径不对）
 *                      405 -- 请求方式没有对应的 doXxx() 方法
 *                  5xx： 服务器错误。 代表码 ： 500  -- 服务器代码写错了
 *      响应头：头名称：值 （键值对）
 *          content-type: 服务器告诉客户端本次响应数据格式及编码格式
 *          content-disposition: 服务器告诉客户端以什么格式打开响应体数据
 *              in-line: 默认值，当前页面内打开展示
 *              attachment;filename=xxx: 以附件(弹出框)的形式打开响应内容。 -- 文件下载
 *              filename 对应的文件名: 可以是数字,字符,字母,不能是中文.
 *                  如果是中文,要传递给浏览器解析: 不同的浏览器要求传递中文的格式不一致
 *                  火狐: 需要把中文编码成 Base64位 的编码格式进行传递
 *                  其他浏览器 需要把中文编码成 utf-8
 *                  URLEncoder: 工具类的方法
 *                      static String encode​(String s, String enc) 使用特定的编码方案将字符串转换为 application/x-www-form-urlencoded格式。
 *      响应体：服务器给浏览器用来在 页面 展示的数据
 *
 *   response 响应对象： 响应一次创建一次对象(实际上就是请求时,就可以认为有了响应对象)，响应结束，对象销毁
 *      javax.servlet.http Interface HttpServletResponse
 *      重定向：（redirect）
 *         1.操作响应行：
 *              设置状态码： void setStatus(int sc) Sets the status code for this response.
 *                  eg: response.setStatus(302) -- 设置重定向
 *         2.操作响应头：
 *              1) 解决响应数据的中文乱码
 *              2) 指明文件类型
 *              void setHeader(String name, String value) Sets a response header with the given name and value.
 *                  eg: response.setHeader("content-type", "text/html;charset=utf-8");
 *                  简化: response.setContentType("text/html;charset=utf-8");
 *      （简化）同时设置 响应行 和 响应头   -- 直接传入 url
 *              void sendRedirect(String location) Sends a temporary redirect response to the client using the specified redirect location URL.
 *        面试题： forward 和 redirect 区别 -------- 重点 -----------------
 *          重定向 （redirect） 的特点：
 *              1.浏览器地址栏发生变化 -- 自动跳转
 *              2.重定向可以访问其他服务器的资源
 *              3.重定向是多次请求/多次响应： 至少两次 -- 不能使用 request 对象来共享数据 -- 一次请求/响应 就会有一个新的 request/response 对象
 *                                  不同 request 对象是不同的域
 *              4.重定向: 需要加 虚拟目录 -- 服务器告诉浏览器 去找另一个资源 -- 可能是内部/可能是外部
 *            请求转发的特点：
 *              1.浏览器地址栏不变 -- 只有一个 请求
 *              2.只能转发到 当前服务器 当前项目的 内部路径
 *              3.在一次请求转发过程中，请求的响应（可能有多次转发）完成之前，所有的转发使用同一的 request 对象，整个过程 只有一次 请求
 *              4.转发: 不需要加 虚拟目录 -- 服务器内部转发
 *         3.操作响应体：
 *              使用步骤：
 *                  1.获取输出流
 *                      字节： 下载/验证码 --  ServletOutputStream getOutputStream() Returns a ServletOutputStream suitable for writing binary data in the response.
 *                      字符：直接将 文本内容 响应到页面-- PrintWriter getWriter() Returns a PrintWriter object that can send character text to the client.
 *                  2.使用输出流
 *                      .print("");
 *        课外知识: (了解,类似于正则)
 *             验证码: 本质是图片  --  防止恶意表单注册
 *                      -- 不是用一张张实际的图片,而是由程序控制 -- 内存中的图片
 *                          -- servlet 控制: 浏览器端点击一下图片,表示向服务器发送一次[请求request],服务器[响应response]一次,生成一个随机数
 *
 *   路径写法：
 *         1.路径分类
 *              1）相对路径： 通过相对路径不可以确定唯一资源
 *                      不以 / 开头，以 . 开头
 *                      ./ 当前路径
 *                      ../ 父目录
 *                      eg： ./index.html
 *              2）绝对路径: 通过绝对路径可以确定唯一资源
 *                      Http：//localhost:8080/day22/index.html
 *                      简写： /day22/index.html -- 必须以 / 开始!!!
 *                          /项目名/资源
 *                      注意: 动态获取虚拟目录: request.getContextPath() -- 返回 项目的虚拟目录 -- 防止 项目的虚拟目录 改变
 *                    1) 客户端(浏览器)的绝对路径: /项目名(虚拟路径名)/资源    ----  重定向
 *                           <a href="..."></a>, <from action="..."></from>
 *                           response.sendRedirect(String location) -- 参数 需要加项目名
 *                          (重点!!!) 标准写法:   /request.getContxtPath()/资源
 *                    2) 服务端的绝对路径:  /资源                           ----  请求转发
 *                          request.getRequestDispatcher("转发路径内部资源").forword(request, response);
 *
 *    下载: 用户从服务器上将资源下载到本地
 *          1.页面显示超链接
 *          2.点击超链接弹出提示框
 *          3.保存文件
 *    上传: 用户将本地的资源上传到服务器(后期学)
 *
 */
public class Http$ {
}
