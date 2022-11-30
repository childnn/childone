package org.anonymous.chapter05;

import org.anonymous.chapter05.core.SimpleLoader;
import org.anonymous.chapter05.core.SimpleWrapper;
import org.anonymous.chapter05.values.ClientIPLoggerValue;
import org.anonymous.chapter05.values.HeaderLoggerValue;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Loader;
import org.apache.catalina.Valve;
import org.apache.catalina.connector.http.HttpConnector;

import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/11/6 21:51
 * @see org.apache.catalina.Mapper
 * 一个容器也
 * 可以有多个 mapper 来支持多协议。例如容器可以用一个 mapper 来支持 HTTP 协
 * 议，而使用另一个 mapper 来支持 HTTPS 协议。
 */
public class BootStrap {

    // BootStrap 该程序仅仅包括一个 servlet，也许会有一些应用仅仅需要一个 servlet
    // 名单是大多数的网络应用需要多个 servlet。在这些应用中，你需要一个跟包装
    // 器不同的容器：上下文。 context

    /**
     * @see org.anonymous.chapter05.Bootstrap2
     */
    public static void main(String[] args) throws IOException, LifecycleException {
        // 创建 HttpConnector 和 SimpleWrapper 类的实例以后，主方法里分配
        // ModernServlet 给 SimpleWrapper 的 setServletClass 方法，告诉包装器要加载
        // 的类的名字以便于加载。
        HttpConnector connector = new HttpConnector();
        SimpleWrapper wrapper = new SimpleWrapper();
        wrapper.setServletClass("ModernServlet");

        // 然后它创建了加载器和两个阀门然后将把加载器给包装器：
        Loader loader = new SimpleLoader();
        Valve va1 = new HeaderLoggerValue();
        Valve va2 = new ClientIPLoggerValue();

        wrapper.setLoader(loader);
        // 然后把两个阀门添加到包装器流水线中。
        wrapper.addValve(va1);
        wrapper.addValve(va2);

        // 最后，把包装器当做容器添加到连接器中，然后初始化并启动连接器。
        connector.setContainer(wrapper);

        connector.initialize();
        connector.start();

        // make the app wait until we press a key.
        System.in.read();
    }

}
