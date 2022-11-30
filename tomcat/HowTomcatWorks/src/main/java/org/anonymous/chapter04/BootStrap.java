package org.anonymous.chapter04;

import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardPipeline;

import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/11/4 21:34
 * ---
 * Tomcat 4 种容器
 * @see org.apache.catalina.Engine 整个 Catalina servelt 引擎.
 * @see org.apache.catalina.Host 含有一个或多个 Context 容器的虚拟主机.
 * @see org.apache.catalina.Context 一个 Web 应用程序. 一个 Context 可以有多个 Wrapper.
 * @see org.apache.catalina.Wrapper 一个独立的 servlet.
 * 以上四个容器的标准实现 StandardXxx.
 * @see org.apache.catalina.Container#addChild(Container) 添加子容器
 * @see Container#removeChild(Container) 移除某容器中的一个子容器
 * @see Container#findChild(String)
 * @see Container#findChildren()
 * 部署应用时, Tomcat 管理员可以通过编辑配置文件 server.xml 来决定使用哪种容器.
 * 这是通过引入容器中的管道 pipeline 和阀 value 的集合实现的.
 * Container 在高版本中有很多方法删改
 * ---
 * 管道任务
 * @see org.apache.catalina.Pipeline
 * @see org.apache.catalina.Valve
 * @see org.apache.catalina.ValveContext
 * @see org.apache.catalina.Contained
 * 管道包含该 servlet 容器将要调用的任务. 一个阀表示一个具体的执行任务.
 * 在 servlet 容器的管道中, 有一个基础阀, 但是可以添加任意数量的阀. 阀的数量指的是额外添加的阀数量,
 * 即, 不包括基础阀. 可以通过 Tomcat 的 server.xml 配置文件动态添加阀.
 * 管道就像过滤器链, 阀就像过滤器. 可以处理传递给它的 request/response 对象. 当一个阀执行完成后, 会调用下一个阀继续执行.
 * 基础阀总是最后一个执行的.
 * 一个 servlet 容器可以有一条管道. 当调用了容器的 invoke() 方法后, 容器会将处理工作交由管道完成, 而管道会调用其中的第一个阀开始处理.
 * 当第一个阀处理完后, 它会调用后续的阀继续执行任务, 直到管道中所有的阀处理完成.
 * 下面是在管道的 invoke() 方法中执行的伪代码:
 * // invoke each value added to the pipeline
 * for(int n = 0; n < values.length; n++) {
 *      value[n].invoke(...);
 * }
 * // then, invoke the basic value
 * basicValue.invoke(...);
 * 但是, Tomcat 的设计者选择了另一种实现方法, 通过引入接口 {@linkplain org.apache.catalina.ValveContext}
 * 来实现阀的遍历执行.
 * 当连接器调用 invoke() 方法后, 容器中要执行的任务并没有硬编码写在 invoke() 方法中. 相反, 容器会调用其管道的
 * invoke() 方法. {@linkplain org.apache.catalina.Pipeline} 接口的 invoke() 方法的签名与
 * {@linkplain Container} 的 invoke() 方法完全相同.
 * @see org.apache.catalina.core.ContainerBase#invoke 中调用 {@linkplain org.apache.catalina.core.StandardPipeline#invoke}
 * 管道必须保证添加到其中的所有阀及其基础阀都被调用一次, 这是通过 ValueContext 接口实例来实现的. ValueContext 是作为管道的一个内部实现的,
 * 因此, ValueContext 接口可以访问管道的所有成员. {@link org.apache.catalina.ValveContext#invokeNext(Request, Response)}
 * 在创建 ValueContext 实例后, 管道会调用 {@link org.apache.catalina.ValveContext#invokeNext(Request, Response)} 方法.
 * ValueContext 实例会首先调用管道中的第一个阀, 第一个阀执行完后, 会调用后面的阀继续执行. ValueContext 实例会将自身传给每个阀,
 * 因此, 每个阀都可以调用 ValueContext 实例的 invokeNext() 方法. {@link org.apache.catalina.Valve#invoke(Request, Response, ValveContext)}
 * Value 接口的 invoke() 方法的实现类似如下代码:
 * public void invoke(Req, Resp, ValueContext)
 * // pass the request and response on to the next value in our pipeline
 * valueContext.invokeNext(req, resp);
 * // now perform what this value is supposed to do.
 * ...
 * @see org.apache.catalina.core.StandardPipeline 是所有 servlet 容器中的 Pipeline 接口的实现.
 * 在 Tomcat-4 中, 该类有一个实现了 ValueContext 接口的内部类, 名为 {@link StandardPipeline.StandardPipelineValveContext}
 * org.apache.catalina.core.StandardPipeline.StandardPipelineValveContext#invokeNext(org.apache.catalina.Request, org.apache.catalina.Response)
 * 方法使用遍历 subscript 和 stage 标明当前正在调用的阀. 当第一次调用管道的 invoke() 方法时, subscript 值为 0, stage 值为 1, 因此, 第一个阀(数组索引为 0)
 * 会被调用. 管道中的第一个阀接收 ValueContext 实例, 并调用其 invokeNext() 方法. 这时, subsript 的值变为 1, 这样就会调用第二个阀, 依次类推.
 * 当从最后一个阀调用 invokeNext() 方法时, subsript 值等于阀的数量, 于是, 基础阀被调用.
 * Tomcat-5- org.apache.catalina.core.StandardValveContext 替代 org.apache.catalina.core.StandardPipeline.StandardPipelineValveContext
 * ---
 * @see Pipeline#invoke(Request, Response)
 * @see Pipeline#addValve(Valve)
 * @see Pipeline#removeValve(Valve)
 * @see Pipeline#setBasic(Valve)
 * ---
 * @see Valve#invoke(Request, Response, ValveContext)
 * @see Valve#getInfo()
 * ---
 * @see ValveContext#invokeNext(Request, Response)
 * @see ValveContext#getInfo()
 * ---
 * @see Contained#getContainer()
 * @see Contained#setContainer(Container)
 * ---
 * @see Wrapper 表示一个独立的 servlet 定义. 继承自 Container 接口, 又添加了一些额外的方法.
 * Wrapper 接口的实现类要负责管理其基础 servlet 类的 servlet 生命周期, 即, 调用 servlet 的 init/service/destory 等方法.
 * 由于 Wrapper 已经是最低等级的 servlet 容器了, 因此不能再向其中添加子容器. {@link Wrapper#addChild(Container)} 抛异常.
 * @see Wrapper#load() 载入并初始化 servlet 类.
 * @see Wrapper#allocate() 分配一个已经初始化的 servlet 实例, 而且, allocate() 方法还要考虑该 servlet 是否实现 {@code SingleThreadModel}
 * ---
 * @see Context 接口的实例表示一个 Web 应用程序, 一个 Context 实例可以有一个或多个 Wrapper 实例作为其子容器.
 * @see Context#setWrapperClass(String)
 * @see Context#createWrapper()
 */
public final class BootStrap {

    /**
     * {@code org.apache.catalina.connector.http.HttpProcessor#process(java.net.Socket)} 中调用
     * 调用 {@linkplain org.apache.catalina.Container#invoke}
     *
     */
    public static void main(String[] args) throws LifecycleException, IOException {
        // 方 法 构 造 了 一 个
        // org.apache.catalina.connector.http.HttpConnector 实例和一个 SimpleContainer 实例。它
        // 接下去调用 connector 的 setContainer 方法传递 container，让 connector 和 container 关联
        // 起来。下一步，它调用 connector 的 initialize 和 start 方法。这将会使得 connector 为处理
        // 8080 端口上的任何请求做好了准备。
        HttpConnector connector = new HttpConnector();
        SimpleContainer container = new SimpleContainer();
        connector.setContainer(container);
        // init serverSocket
        connector.initialize();
        // start connector thread: HttpConnector["8080"]
        // new newProcessors
        connector.start();

        // make the application wait until we press any key.
        System.in.read();

    }

}
