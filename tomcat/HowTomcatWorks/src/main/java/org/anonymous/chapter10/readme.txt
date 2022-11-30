安全

综述
有些 web 应用程序的内容是有限制的，只允许有权限的用户在提供正确的用户名
和密码的情况下才允许访问。Servlet 通过配置部署文件 web.xml 来对安全性提
供技术支持。本章的主要内容是容器对于安全性限制的支持。
一个 servlet 通过一个叫 authenticator 的阀门（valve）来支持安全性限制。
当容器启动的时候，authenticator 被添加到容器的流水线上。如果你忘了流水
线是如何工作的，需要重新复习下第六章的内容。
authenticator 阀门会在包装器阀门之前被调用。authenticator 用于对用户进
行验证，如果用户熟人了正确的用户名和密码，authenticator 阀门调用下一个
用于处理请求 servlet 的阀门。如果验证失败，authenticator 不唤醒下一个阀
门直接返回。由于验证失败，用户并不能看到请求的 servlet。
在用户验证的时候 authenticator 阀门调用的是上下文域（realm）内的
authenticate 方法，将用户名和密码传递给它。该容器域可以访问合法的用户
名密码。
---
（域） Realm
域是用于进行用户验证的一个组件，它可以告诉你一个用户名密码对是否是合法
的。一个域跟一个上下文容器相联系，一个容器可以只有一个域。可以使用容器
的 setRealm 方法来建立它们之间的联系。
一个域是如何验证一个用户的合法性的？一个域拥有所有的合法用户的密码或
者是可以访问它们。至于它们存放在哪里则取决于域的实现。在 Tomcat 的默认
实现里，合法用户被存储在 tomcat-users.xml 文件里。但是可以使用域的其它
实现来访问其它的源，如关系数据库。
在 Catalina 中，一个域用接口 org.apache.catalina.Realm 表示。该接口最重
要的方法是四个 authenticate 方法
---
GenericPrincipal
一个 principal 使用 java.security.Principal 接口来表示，Tomcat 中该接口
的实现为 org.apache.catalina.realm.GenericPrincipal 接口。
GenericPrincipal 必须拥有一个用户名和一个密码，此外还可选择性的传递一
列角色。可以使用 hasRole 方法来检查一个 principal 是否有一个特定的角色，
传递的参数为角色的字符串表示形式
---
LoginConfig 类
一个 login configuration 包括一个域名，用
org.apache.catalina.deploy.LoginConfig 类表示。LoginConfig 的实例封装了
域名和验证要用的方法。可以使用 LoginConfig 实例的 getRealmName 方法来获
得域名，可以使用 getAuthName 方法来验证用户。一个验证（authentication）
的名字必须是下面的之一：BASIC, DIGEST, FORM, o 或者 CLIENT-CERT。如果用
到的是基于表单（form）的验证，该 LoginConfig 对象还包括登录或者错误页面
像对应的 URL。
Tomcat 一个部署启动的时候，先读取 web.xml。如果 web.xml 包括一个
login-confgi 元素，Tomcat 创建一 LoginConfig 对象并相应的设置它的属性。
验证阀门调用 LoginConfig 的 getRealmName 方法并将域名发送给浏览器显示登
录表单。如果 getRealmName 名字返回值为 null，则发送给浏览器服务器的名字
和端口名。
---
Authenticator 类
org.apache.catalina.Authenticator 接口用来表示一个验证器。该方接口并没
有方法，只是一个组件的标志器，这样就能使用 instanceof 来检查一个组件是
否为验证器。
Catalina 提供了 Authenticator 接口的基本实现：
org.apache.catalina.authenticator.AuthenticatorBase 类。除了实现
Authenticator 接口外，AuthenticatorBase 还继承了
org.apache.catalina.valves.ValveBase 类。这就是说 AuthenticatorBase 也
是一个阀门。可以在 org.apache.catalina.authenticator 包中找到该接口的几
个类：BasicAuthenticator 用于基本验证, FormAuthenticator 用于基于表单的
验证, DigestAuthentication 用于摘要（digest）验证, SSLAuthenticator 用
于 SSL 验证。NonLoginAuthenticator 用于 Tomcat 没有指定验证元素的时候。
NonLoginAuthenticator 类表示只是检查安全限制的验证器，但是不进行用户验
证。
一个验证器的主要工作是验证用户。因此，AuthenticatorBase 类的 invoke 方
法调用了抽象方法 authenticate，该方法的具体实现由子类完成。在
BasicAuthenticator 中，它 authenticate 使用基本验证器来验证用户。
安装 Authenticator 阀门
在部署文件中，只能出现一个 login-config 元素，login-config 元素包括了
auth-method 元素用于定义验证方法。这也就是说一个上下文容器只能有一个
LoginConfig 对象来使用一个 authentication 的实现类。
AuthenticatorBase 的子类在上下文中被用作验证阀门，这依赖于部署文件中
auth-method 元素的值。

Value of the auth-method element Authenticator class
BASIC       BasicAuthenticator
FORM        FormAuthenticator
DIGEST      DigestAuthenticator
CLIENT-CERT SSLAuthenticator

---
wrapper
一个包装器表示一个独立 Servlet 的容器。这样，包装器就不能再有子容器，因
此不可以调用它的 addChild 方法，如果调用了会得到一个 java.langIllegalStateException。
一个包装器的父容器只能是一个上下文容器。如果传递的参数不是一个上下文容
器，它的 setParent 方法会抛出 java.lang.IllegalArgumentException。

StandardWrapperValve
StandardWrapperValve 是 StandardWrapper 实例上的基本阀门，该阀门做两件
事情：
· 提交 Servlet 的所有相关过滤器
· 调用发送者的 service 方法
要实现这些内容，下面是 StandardWrapperValve 在他的 invoke 方法要实现的：
· 调用StandardWrapper的allocate的方法来获得一个servlet
实例
· 调用它的 private createFilterChain 方法获得过滤链
· 调用过滤器链的 doFilter 方法。包括调用 servlet 的 service
方法
· 释放过滤器链
· 调用包装器的 deallocate 方法
· 如果 Servlet 无法使用了，调用包装器的 unload 方法

