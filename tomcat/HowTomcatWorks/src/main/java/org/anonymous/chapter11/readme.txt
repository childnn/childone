StandardContext
一个上下文容器（Context）代表一个 web 应用，每
一个上下文包括多个包装器（Wrapper），每个包装器代表一个 Servlet。但是，上下文还需要其它的一些组件如加载器和管理器。
StandardContext 配置
创建一个 StandardContext 实例之后，必须调用它的 start 方法，这样它就能为
受到的 HTTP 请求服务了。一个 StandardContext 对象可能启动失败，这时候属
性 available 被设置为 false，属性 available 表示了 StandardContext 对象的
可用性。
如果 start 方法启动成功，StandardContext 对象需要配置它的属性。在一个
Tomcat 部署中，StandardContext 的配置过程做了以下事情：准备读取和解
析%CATALINA_HOME%/conf 目录下面的 web.xml，部署所有应用程序，确保
StandardContext 实例可以处理应用级别的 web.xml。另外，配置需要添加一个
验证器阀门和证书阀门（authenticator valve and a certificate valve）
---
启动 StandardContext
Start 方法初始化 StandardContext 对象并让生命周期监听器配置该
StandardContext 实例。如果配置成功，生命周期监听器会将 configured 属性
设置为 true。最后 start 方法，将 available 属性设置为 true 或者 false。如
果是 true 的话表示该 StandardContext 属性配置完毕并且所有相关子容器和组
件已经成功启动，这样就能对 HTTP 请求进行服务了，如果是 false 则表示出现
了错误。
StandardContext 类将 configured 的值初始化为 false，如果生命周期监听器的
配置过程成功，则将该值设置为 true。在 start 方法的最后，它检查
StandardContext 对象的 configured 属性，如果该值为 true，则启动该
StandardContext 成，否则调用 stop 方法停止所有已经启动的组件
下面是该方法做的事情：start
    · 触发 BEFORE_START 事件
    · 设置 availability 属性为 false
    · 设置 configured 属性为 false
    · 设置源（resources）
    · 设置加载器
    · 设置管理器
    · 初始化属性 map
    · 启动跟该上下文相关的组件
    · 启动子容器（包装器）
    · 启动流水线
    · 启动管理器
    · 触发 START 事件。监听器（ContextConfig）会进行一系列配
    置操作，配置成功后，将 StandardContext 实例的 configured 属性设置
    为 true。
    · 检查 configured 属性的值，如果为 true：调用
    postWelcomPages方法，加载子包装器，并将available属性设置为true。
    如果 configured 属性为 false 调用 stop 方法
    · 触发 AFTER_START 事件
---
Invoke 方法
在 Tomcat4 中，StandardContext's 方法由相关联的连接器调用，如果该上下文
是一个主机（host）的子容器，有该主机的 invoke 方法调用。StandardContext
的 invoke 方法首先检查是否正在重加载该应用程序，是的话，等待知道加载完
毕。然后调用它的父类 ContainerBase 的 invoke 方法。