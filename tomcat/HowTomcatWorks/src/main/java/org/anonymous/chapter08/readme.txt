一个 servlet 容器需要一个定制的容器，而不是简单的使用系统的加
载器。如果像前面章节中那样使用系统的加载器来加载 servlet 和其他需要的类，
这样 servlet 就可以进入 Java 虚拟机 CLASSPATH 环境下面的任何类和类库，这
会带来安全隐患。Servlet 只允许访问 WEB-INF/目录及其子目录下面的类以及部
署在WEB-INF/lib目录下的类库。所以一个servlet容器需要一个自己的加载器，
该加载器遵守一些特定的规则来加载类。在 Catalina 中，加载器使用
org.apache.catalina.Loader 接口表示。
Tomcat 需要一个自己的加载器的另一个原因是它需要支持在 WEB-INF/classes
或者是 WEB-INF/lib 目录被改变的时候会重新加载。Tomcat 的加载器实现中使
用一个单独的线程来检查 servlet 和支持类文件的时间戳。要支持类的自动加载
功能，一个加载器类必须实现 org.apache.catalina.loader.Reloader 接口。
---
库（repository）和源（resources）。库表示加载
器查找的地方，源表示加载器中的DirContext对象，它的文档基（document base）
指向了上下文的文档基。
---
Java 类加载器

在每次创建一个 Java 类的实例时候，必须先将该类加载到内存中。Java 虚拟机
（JVM）使用类加载器来加载类。Java 加载器在 Java 核心类库和 CLASSPATH 环
境下面的所有类中查找类。如果需要的类找不到，会抛出
java.lang.ClassNotFoundException 异常。
从 J2SE1.2 开始，JVM 使用了三种类加载器：bootstrap 类加载器、extension
类加载器和 system 类加载器。这三个加载器是父子关系，其中 bootstrap 类加
载器在顶端，而 system 加载器在结构的最底层。
其中 bootstrap 类加载器用于引导 JVM，一旦调用 java.exe 程序，bootstrap
类加载器就开始工作。因此，它必须使用本地代码实现，然后加载 JVM 需要的类
到函数中。另外，它还负责加载所有的 Java 核心类，例如 java.lang 和 java.io
包。另外 bootstrap 类加载器还会查找核心类库如 rt.jar、i18n.jar 等，这些
类库根据 JVM 和操作系统来查找。
extension 类加载器负责加载标准扩展目录下面的类。这样就可以使得编写程序
变得简单，只需把 JAR 文件拷贝到扩展目录下面即可，类加载器会自动的在下面
查找。不同的供应商提供的扩展类库是不同的，Sun 公司的 JVM 的标准扩展目录
是/jdk/jre/lib/ext。
system 加载器是默认的加载器，它在环境变量 CLASSPATH 目录下面查找相应的
类。

这样，JVM 使用哪个类加载器？答案在于委派模型(delegation model)，这是出
于安全原因。每次一类需要加载，system 类加载器首先调用。但是，它不会马
上加载类。相反，它委派该任务给它的父类-extension 类加载器。extension
类加载器也把任务委派给它的父类 bootstrap 类加载器。因此，bootstrap 类加
载器总是首先加载类。如果 bootstrap 类加载器不能找到所需要的类的
extension 类加载器会尝试加载类。如果扩展类加载器也失败，system 类加载器
将 执 行 任 务 。 如 果 系 统 类 加 载 器 找 不 到 类 ， 一 个
java.lang.ClassNotFoundException 异常。为什么需要这样的往返模式？
委派模型对于安全性是非常重要的。如你所知，可以使用安全管理器来限制访问
某个目录。现在，恶意的意图有人能写出一类叫做 java.lang.Object，可用于
访问任何在硬盘上的目录。因为 JVM 的信任 java.lang.Object 类，它不会关注
这方面的活动。因此，如果自定义 java.lang.Object 被允许加载的安全管理器
将很容易瘫痪。幸运的是，这将不会发生，因为委派模型会阻止这种情况的发生。
下面是它的工作原理。
当自定义 java.lang.Object 类在程序中被调用的时候，system 类加载器将该请
求委派给 extension 类加载器，然后委派给 bootstrap 类加载器。这样 bootstrap
类加载器先搜索的核心库，找到标准 java.lang.Object 并实例化它。这样，自
定义 java.lang.Object 类永远不会被加载.

关于在 Java 类加载机制的优势在于可以通过扩展
java.lang.ClassLoader 抽象类来扩展自己的类加载器。Tomcat 的需求
自定义自己的类加载器原因包括以下内容
· 要制定类加载器的某些特定规则
· 缓存以前加载的类
· 事先加载类以预备使用


Loader 接口
在 Web 应用程序中加载 servlet 和其他类需要遵循一些规则。例如，在一个应用
程序中 Servlet 可以使用部署到 WEB-INF/classes 目录和任何子目录下面的类。
然而，没有 servlet 的不能访问其他类，即使这些类是在运行 Tomcat 所在的 JVM
的 CLASSPATH 中。此外，一个 servlet 只能访问 WEB-INF/lib 目录下的类库，而
不能访问其他目录下面的。
一个 Tomcat 类加载器表示一个 Web 应用程序加载器，而不是一个类加载器。一
个加载器必须实现 org.apache.catalina.Loader 接口。加载器的实现使用定制
的类加载器 org.apache.catalina.loader.WebappClassLoader。可以使用
Loader 接口的 getClassLoader 方法获取一个网络加载器 ClassLoader。
值得一提的是 Loader 接口定义了一系列方法跟库协作。Web 应用程序的
WEB-INF/classes 和 WEB-INF/lib 目录作为库添加上。Loader 接口的
addReposity 方法用于添加一个库，findRepositories 方法用于返回一个所有库
的队列。
一个 Tomcat 的加载器通常跟一个上下文相关联，Loader 接口的和 getContainer
及 setContainer 方法是建立此关联。一个加载器还可以支持重新加载，如果在
上下文中的一个或多个类已被修改。这样，一个 servlet 程序员可以重新编译
servlet 或辅助类，新类将被重新加载而不需要不重新启动 Tomcat 加载。为了
达到重新加载的目的，Loader 接口有修改方法。在加载器的实现中，如果在其
库中一个或多个类别已被修改，modeify 方法必须返回 true，因此需要重新加载。
一个加载器自己进行重新加载，而是调用上下文接口的重载方法。另外两种方法，
setReloadable 和 getReloadable，用于确定加载器中是否可以使用重加载。默
认 情 况 下 ， 在 标 准 的 上 下 文 实 现 中
（org.apache.catalina.core.StandardContext 类将在第 12 章讨论）重载机制
并未启用。因此，要使得上下文启动重载机制，需要在 server.xml 文件添加一
些元素如下：
<Context path="/myApp" docBase="myApp" debug="0" reloadable="true"/>
另外，一个加载器的实现可以确定是否委派给父加载器类。为了实现这一点，
Loader 接口提供了 getDelegate 和 setDelegate 方法。

Reloader 接口里最重要的方法是 modified 方法，如果在 web 应用程序中的
servlet 任何支持类被修改的时候该方法返回 true。addRepository 方法用于添
加一个库而findRepositories方法用于返回实现了Reloader接口的加载器的所
有的库。

WebappLoader 类
org.apache.catalina.loader.WebappLoader 类是 Loader 接口的实现，它表示
一个 web 应用程序的加载器，负责给 web 应用程序加载类。WebappLoader 创建
一个 org.apache.catalina.loader.WebappClassLoader 类的实例作为它的类加
载器。像其他的 Catalina 组件一样，WebappLoader 实现了
org.apache.catalina.Lifecycle 接口，可有由关联容器启动和停止。
WebappLoader 类还实现了 java.lang.Runnable 接口，所以可以通过一个线程来
重复的调用 modified 方法，如果 modified 方法返回 true，WebappLoader 实例
同志它的关联容器。类通过上下文重新加载自己，而不是 WebappLoader。上下
文的怎么实现该功能会在第 12 章，标准上下文中介绍。
WebappLoader 类的 start 方法被调用的时候，将会完成下面几项重要任务：
· Creating a class loader
· Setting repositories
· Setting the class path
· Setting permissions
· Starting a new thread for auto-reload.
· 创建一个类加载器
· 设置库
· 设置类路径
· 设置访问权限
· 开启一个新线程用来进行自动重载
--
创建类加载器
WebappLoader 使用一个内部类加载器来加载类。可以回头看 Loader 接口，该接
口提供了 getClassLoader 方法但是并没有 setClassLoader 方法。因此，不能通
过传递一个 WebappLoader 来初始化它。这样没有默认类加载器是否意味着
WebappLoader 不够灵活的？
答案当然是否定的，WebappLoader 类提供了 getLoaderClass 和
setLoaderClass 方法来获得或者改变它的私有变量 loaderClass 的值。该变量
是一个的表示加载器类名 String 类型表示形式。默认的 loaderClass 值是
org.apahce.catalina.loader.WebappClassLoader，如果你愿意，可以创建继承
WebappClassLoader 类的自己的加载器，然后使用 setLoaderClass 方法来强制
WebappLoader 使用你创建的加载器。否则，当它 WebappLoader 启动的时候，它
会使用它的私有方法 createClassLoader 创建 WebappClassLoader 的实例
--
设置库
WebappLoader 的 start 方法会调用 setRepositories 方法来给类加载器添加一
个库。WEB-INF/classes 目录传递给加载器 addRepository 方法，而 WEB-INF/lib
传递给加载器的 setJarPath 方法。这样，类加载器能能从 WEB-INF/classes 目
录下面和 WEB-INF/lib 目录下面部署的类库里加载类。
--
设置类路径
该任务由 start 方法调用 setClassPath 方法完成，setClassPath 方法会给
servlet 上下文分配一个 String 类型属性保存 Jasper JSP 编译的类路径，该内
容先不予讨论。
--
设置访问权限
如果 Tomcat 使用了安全管理器，setPermissions 给类加载器给必要的目录添加
访问权限，例如 WEB-INF/classes 和 WEB-INF/lib。如果不使用管理器，该方法
马上返回。
--
开启自动重载线程
WebappLoader 支持自动重载，如果 WEB-INF/classes 或者 WEB-INF/lib 目录被
重新编译过，在不重启 Tomcat 的情况下必须自动重新载入这些类。为了实现这
个目的，WebappLoader 有一个单独的线程每个 x 秒会检查源的时间戳。x 的值由
checkInterval 变量定义，它的默认值是 15，也就是每隔 15 秒会进行一次检查
是否需要自动重载。该类还提供了两个方法 getCheckInterval 和
setCheckInterval 方法来访问或者设置 checkInterval 的值

---
WebappClassLoader 类
类 org.apache.catalina.loader.WebappClassLoader 表示在一个 web 应用程序
中使用的加载器。WebappClassLoader 类继承了 java.net.URLClassLoader 类，
该类在前面章节中用于加载 Java 类。
WebappClassLoader 被可以的进行了优化和安全方面的考虑。例如它缓存了以前
加载的类以改进性能，下一次收到第一次没有找到的类的请求的时候，可以直接
抛出 ClassNotFound 异常。WebappClassLoader 在源列表以及特定的 JAR 文件中
查找类。
处于安全性的考虑，WebappClassLoader 类不允许一些特定的类被加载。这些类
被存储在一个 String 类型的数组中，现在仅仅有一个成员。
private static final String[] triggers = {
    "javax.servlet.Servlet" // Servlet API
};
另外在委派给系统加载器的时候，你也不允许加载属于该包的其它类或者它的子包：
private static final String[] packageTriggers = {
    "javax", // Java extensions
    "org.xml.sax", // SAX 1 & 2
    "org.w3c.dom", // DOM 1 & 2
    "org.apache.xerces", // Xerces 1 & 2
    "org.apache.xalan" // Xalan
};

---
缓存
为了提高性能，当一个类被加载的时候会被放到缓存中，这样下次需要加载该类
的时候直接从缓存中调用即可。缓存由 WebappClassLoader 类实例自己管理。另
外，java.lang.ClassLoader 维护了一个 Vector，可以避免前面加载过的类被当
做垃圾回收掉。在这里，缓存被该超类管理。
每一个可以被加载的类(放在 WEB-INF/classes 目录下的类文件或者 JAR 文件)
都被当做一个源。一个源被 org.apache.catalina.loader.ResourceEntry 类表
示。一个 ResourceEntry 实例保存一个 byte 类型的数组表示该类、最后修改的
数据或者副本等等。

---
加载类
当加载一个类的时候，WebappClassLoader 类遵循以下规则：
    · 所有加载过的类都要进行缓存，所以首先需要检查本地缓存。
    · 如果无法再本地缓存找到类，使用 java.langClassLoader 类
    的 findLoaderClass 方法在缓存查找类、
    · 如果在两个缓存中都无法找到该类，使用系统的类加载器避免
    从 J2EE 类中覆盖来的 web 应用程序。
    · 如果使用了安全管理器，检查该类是否允许加载，如果该类不
    允许加载，则抛出 ClassNotFoundException 异常。
    · 如果要加载的类使用了委派标志或者该类属于 trigger 包中，
    使用父加载器来加载类，如果父加载器为 null，使用系统加载器加载。
    · 从当前的源中加载类
    · 如果在当前的源中找不到该类并且没有使用委派标志，使用父
    类加载器。如果父类加载器为 null，使用系统加载器
    · 如果该类仍然找不到，抛出 ClassNotFoundException 异常