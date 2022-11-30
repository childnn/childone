将 tomcat 注册为 windows 服务：
bin 目录: service.bat install/remove [service_name]
启/停: net start/stop <service_name>


catalina 是一个非常复杂的, 并设计优雅的软件, 同时也是模块化的.
catalina 可以看成是由两个主要模块所组成的: connector 和 container
connector 用来连接容器里的请求, 它的工作是为接收到每一个 HTTP 请求构造一个 request 和 response 对象
然后它把流程传递给 container, container 从 connector 接收到 request/response 对象之后调用 servlet#serive
方法用于响应. 谨记, 这个描述仅仅是冰山一角. 这里容器做了很多事情, 比如, 在它调用 servlet 的 service 方法之前,
它必须加载这个 servlet, 验证用户、更新用户会话等。


tomcat 组件:
1. Server
   表示整个 Catalina Servlet 容器. 在 Server 容器中, 可以包含一个或多个 Service 组件.
2. Service
    Service 是存活在 Server 内部的中间组件, 它将一个或多个连接器 Connector 组件绑定到一个单独的引擎 Engine 上.
    在 Server 中, 可以包含一个或多个 Service 组件.
3. Connector
    连接器 Connector 处理与客户端的通信, 它负责接收客户请求, 以及向客户返回响应结果.
4. Engine
    在 Tomcat 中, 每个 Service 只能包含一个 Servlet 引擎 Engine. 引擎表示一个特定的 Service 的请求处理流水线.
    作为一个 Service 可以有多个连接器, 引擎从连接器接收和处理所有的请求, 将响应返回给合适的连接器, 通过连接器传输给用户.
5. Host
    Host 表示一个虚拟主机, 一个 Engine 可以包含多个 Host.
6. Context
    一个 Context 表示一个 Web 应用程序, 运行在特定的 Host 中.
    "一个 Web 应用程序是由一组 Servlet, HTML 页面, 类, 以及其他的资源组成的运行在 Web 服务器上的完整的应用程序. 它可以在
    多个供应商提供的实现了 Servlet 规范的 Web 容器中运行".
    一个 Host 可以包含多个 Context, 每个 Context 都有一个唯一的路径.



从 servlet 容器的角度审视 servlet 程序的开发. 对一个 Servlet 的每个 HTTP 请求, 一个功能齐全的 servlet 容器有以下几件事情要做:
 1. 当第一次调用某个 servlet 时, 要载入该 servlet 类, 并调用其 init() 方法(仅此一次);
 2. 针对每个 request 请求, 创建一个 javax.servlet.ServletRequest 实例和一个 javax.servlet.ServletResponse 实例;
 3. 调用该 servlet 的 service() 方法, 将 servletRequest 对象和 servletResponse 对象作为参数传入;
 4. 当关闭该 servlet 类时, 调用其 destroy() 方法, 并卸载该 servlet 类.

---
HTTP 1.1 新特性
 1. 持久连接
    在 HTTP 1.1 之前, 无论浏览器何时连接到 Web 服务器, 当服务器将请求的资源返回后, 就会断开与浏览器的链接.
    但是, 网页上会包含一些其他资源, 如图片文件,applet等. 因此, 当请求一个页面时, 浏览器还需要下载这些被页面引用的资源.
    如果页面和它引用的所有资源文件都使用不同的连接进行下载的话, 处理过程会很慢. 这就是为什么 HTTP 1.1 引入持久连接.
    使用持久连接后, 当下载了页面后, 服务器并不会立即关闭连接. 相反, 它会等待 Web 客户端请求被该页面所引用的所有资源.
    这样一来, 页面和被页面引用的资源都会使用同一个连接来下载. 考虑到建立/关闭 HTTP 连接是一个系统开销很大的操作, 使用同一个
    连接来下载所有的资源回为 Web 服务器、客户端和网络节省很多时间和工作量.
    在 HTTP 1.1 中, 会默认使用持久连接. 当然, 也可以显示地使用, 方法是浏览器发送如下地请求头信息:
     connection: keep-alive
 2. 块编码
    建立持久连接后,服务器可以从多个资源发送字节流,而客户端也可以使用该连接发送多个请求. 这样的结果就是发送方必须在每个请求
    或响应中添加 "content-length" 头信息, 这样, 接收方才能知道如何解释这些字节信息.
    但通常情况下,发送方并不知道发送多少字节. 例如, servlet 容器可能要在接收到一些字节之后, 就开始发送响应信息,而不必等到
    接收完所有的信息. 这就是说, 必须有一种方法来告诉接收方在不知道发送内容长度的情况下, 如何解析已经接收到的内容.
    其实, 即使没有发出多个请求或发送多个响应, 服务器或客户端也不需要知道有多少字节要发送. 在 HTTP1.0 中, 服务器可以不写
    "content-length" 头信息, 尽管往连接中写响应内容就行了. 当发送完响应信息后, 它就直接关闭连接. 在这种情况下,
    客户端会一直读取内容, 知道读方法返回 -1,这表明已经读到了文件末尾.
    HTTP1.1 使用一个名为 "transfer-encoding" 的特殊请求头,来指明字节流将会分块发送. 对每一个块, 块的长度(十六进制表示)
    后面会有一个回车/换行符(CR/LF, Carriage Return/Linefeed),然后是具体的数据. 一个事务以长度为 0 的块标记.
    假设要用两个发送下面 38 个字节的内容, 其中第一个块为 29 个字节, 第二个块为 9 个字节:
    I'm as helpless as a kitten up a tree.
    那么, 实际上应该发送如下内容:
    1D\r\n
    I'm as helpless as a kitten u
    9\r\n
    p a tree
    0\r\n
    "1D" 的十进制表示是 29,表明第一个块的长度是 29 个字节, "0\r\n" 表明事务已经完成.
 3. 状态码 100 的使用
    使用 HTTP1.1 的客户端可以在向服务器发送请求体之前发送如下的请求头, 并等待服务器确认:
     Expect: 100-continue
    当客户端准备发送一个较长的请求体,而不确定服务端是否会接收时, 就可能会发送上面的头信息. 若是客户端发送了
    较长的请求体, 却发现服务器拒绝接收时, 会是较大的浪费.
    接收到 "Expect: 100-continue" 请求头后, 若服务器可以接收并处理该请求时, 可以发送如下的响应头:
     HTTP/1.1 100 Continue
    注意,返回内容后面要加上 CRLF 字符.
    然后,服务器继续读取输入流的内容.
