主机（host）和引擎（Engine）
如果需要在一个 Tomcat 部署中部署多个上下文，需要使用一个主机。理论上，当只有一个上下文容器的
时候不需要主机，正如 org.apache.catalina.Context 接口中描述的那样。

对于每一个请求，都会调用主机的 invoke 方法。由于 StanardHost 类并没有实
现invoke方法，所以会调用它的父类ContainerBase类的invoke方法。该invoke
方法会转而调用 StandardHost 的基本阀门 StandardHostValue 的 invoke 方法。
StandardHostValue 阀门的 invoke 方法将在 StandardHostValue 小节讨论。
StandardHostValue 的 invoke 方法调用 StandardHost 类的 map 方法获得一个合
适的上下文容器来处理请求。
StandardHostValue 的 invoke 方法调用 ContainerBase 的 map
方法，它由转而调用 StandardHost 的 map 方法。

StandardHost 类的 start 方法在它的最后调用 super.start()，这样保证
了创建一个默认的映射器。


为什么 Host 是必须的
一个 Tomcat 部署必须有一个主机如果该上下文使用 ContextConfig 来配置。原
因如下：
ContextConfig 需要应用文件 web.xml 的位置，它在它的 applicationConfig 方
法中尝试打开该文件，下面是该方法的片段：
synchronized (webDigester) {
try {
URL url =
servletContext.getResource(Constants.ApplicationWebXml);
InputSource is = new InputSource(url.toExternalForm());
is.setByteStream(stream);
...
webDigester.parse(is);
...
Constants.ApplicationWebXml 定义的是 /WEB-INF/web.xml，servletContext
是一个 org.apache.catalina.core.ApplicationContext 类型的对象。
下面是 ApplicationContext 类的 getResource 方法
public URL getResource(String path)
throws MalformedURLException {
DirContext resources = context.getResources();
if (resources != null) {
String fullPath = context.getName() + path;
// this is the problem. Host must not be null
String hostName = context.getParent().getName();
最后一行清楚的现实上下文的父容器（主机）是必须的，如果使用 ContextConfig
来配置的话。在第 15 章中将会介绍如何解析 web.xml 文件。简单的说，除非你
自己写 ContextConfig 类，否则你必须有一个主机。

Engine 接口
org.apache.catalina.Engine 接口用来表示一个引擎。引擎表示整个 Catalina
的 Servlet 引擎。当你想要支持多个虚拟主机的时候，需要一个引擎，实际上，
Tomcat 部署正是使用了引擎。

可以给引擎设置默认主机或者默认上下文。注意引擎也可以跟服务相关联（service）