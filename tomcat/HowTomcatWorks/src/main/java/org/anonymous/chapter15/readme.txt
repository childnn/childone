Digester

在前面章节中已经看到，使用 Bootstrap 类来初始化连接器、上下文、包装器以
及其它组件。一旦你获得了它们的对象就可以使用 set 方法来关联它们。例如可
以如下初始化连接器和上下文
Connector connector = new HttpConnector();
Context context = new StandardContext();
将连接器和上下文关联起来可以如下实现：
connector.setContainer(context);
可以使用相应的 set 方法来配置这些对象的属性。例如可以使用 setPath 和
setDocBase 方法来设置 path 和 docBase 属性。
context.setPath("/myApp") ;
context.setDocBase("myApp");
另外，可以初始化各种组件，然后使用相应的 add 方法将其添加到上下文容器中。
例如，下面是如何在上下文对象中添加生命周期监听器和加载器：
LifecycleListener listener = new SimpleContextConfig();
((Lifecycle) context).addLifecycleListener(listener);
Loader loader = new WebappLoader();
context.setLoader(loader);
一旦必要的关联和添加设置完毕，就可以调用连接器的 initialize 和 start 方
法和上下文的 start 方法了。
connector.initialize();
((Lifecycle) connector).start ();
((Lifecycle) context).start();
这种方式来配置应用程序有一个很明显的缺点，所有的东西都是硬编码的。要更
改一个组件或者一个属性的值都需要重新编译整个 Bootstrap 类。幸运的是，
Tomcat 的设计者选择了一种更优雅的方式来进行配置，使用名为 server.xml 的
XML 文档。Server.xml 中的每一个元素都被转换为一个 Java 对象，元素的属性
用来设置属性。这样，就可以通过编辑 server.xml 来改变 Tomcat 的配置。例如，
上下文容器元素就可以这样在 server.xml 中表示
<context/>
To set the path and docBase properties you use attributes in the XML
element:
<context docBase="myApp" path="/myApp"/>
Tomcat 使用开源工具 Digester 来讲 XML 元素转换为 Java 对象。

---
org.apache.commons.digester.Digester 类是 Digester 库里的主类。使用它来
解析 XML 文档。对于该文档中的每一个元素，Digester 都检查它是否需要做点
事情，程序员只需决定 Digester 实例在调用 parser 方法之前需要做什么即可。
你怎样告诉一个 Digester 对象遇到一个 XML 元素的时候怎么做？很简单，你定
义模式并且将模式跟一条或多条规则相关联即可。XML 的根元素有一个跟他的元
素名相同的模式，例如：Listing15.1 所示的 XML 文档。
Listing 15.1: The example.xml file
<?xml version="1.0" encoding="ISO-8859-1"?>
<employee firstName="Brian" lastName="May">
<office>
<address streeName="Wellington Street" streetNumber="110"/>
</office>
</employee>
该文档的根元素师 employee，该元素的模式 emploee。它的 office 元素是
<employee>的子元素。一个子元素的模式是它的名字加上它属于的元素作为前缀。
所以 office 元素的模式是 employee/office，而 address 元素的模式是：
the parent element's pattern + "/" + the name of the element
Address 元素的父元素师<office>，<office>元素的模式 employee/office。因
此，<address>的模式 employee/office/address。
现在你已经明白了如何从 XML 文档中获得模式，接下来讨论下规则（rules）。
一个规则定义了 Digester 遇到特别的模式的时候必须做的动作。一个规则用
org.apache.commons.digester.Rule类。Digester类包括零个或多个Rule对象。
在一个 Digester 实例中，规则及其模式被存储在接口
org.apache.commons.digester.Rules 定义的类型中。每一次给 Digester 实例
添加规则，都将 Rule 对象添加到 Rules 对象。
在 Rule 中有两个方法：begin 方法和 end 方法。当解析一个 XML 文档的时候，
遇到开始元素，Digester 实例调用 Rule 对象的 begin 方法，而遇到结束元素的
时候调用 stop 方法。
当解析如 Listing15.1 所示的 example.xml 所示的文档的时候，下面是 Digester
对象所作的。
· 第一次遇到 employee 开始元素，检查是否已经有存在的规则
模式 employee。如果有，Digester 调用 Rule 对象的 begin 方法，从 begin
方法开始添加第一个模式。
· 然后检查 office 元素，所以 Digester 对象检查是否存在规则
模式为 employee/office。如果有，，调用 Rule 对象的 begin 方法、
· 接下来检查模式 employee/office/address， ，如果找到了规则，
则调用 begin 方法
· 接下来 Digester 遇到了 address 结束符，调用对应规则的 end
方法。
· 解析来遇到了 office 结束符，调用相应规则的 end 方法。
· 最后遇到了 employee 结束符，调用相应规则的 end 方法。
可以使用什么规则？Digester 预先定义了一些规则，甚至在不了解 Rule 类的时
候都可以使用这些规则。但是，如果这些规则不足够，你需要建立自己的规则。
实现定义的规则包括创建对象，设置属性值等。
---
建立对象间的联系
Digester 实例有一个栈用来临时存储对象。当调用 addObjectCreate 创建对象
后，将对象压入堆栈中。可以把堆栈想象成一口井，可以将对象放入到井中，而
pop 方法相当于取出井中最上边的元素。
当通过 addObjectCreate 方法创建两个对象的时候，第一个对象被放入井中，然
后是第二个。addSetNext 用于建立第一个对象和第二个对象之间的关系，它把
第二个对象作为参数传递给第一个对象。下面是 addSetNext 方法的签名：
public void addSetNext(java.lang.String pattern,
java.lang.String methodName)
参数 argument 定义了触发该规则的模式，methodName 参数是是第一个对象要被
调用的方法名。该模式的形式如 firstObject/secondObject
---
验证 XML 文档
可以使用 Digester 来对 XML 文档的结构进行验证，一个 XML 文档是否合法取决
于由 Digester 定义的 validating 属性，该属性的默认值为 false。
方法 setValidating 用来用来设置是否要验证 XML 文档，该方法的签名如下：
public void setValidating(boolean validating)
如果想要验证 XML 文档，可以传递一个 true 值该该方法：