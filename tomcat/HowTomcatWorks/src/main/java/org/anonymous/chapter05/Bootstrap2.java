package org.anonymous.chapter05;

import org.anonymous.chapter05.core.SimpleContext;
import org.anonymous.chapter05.core.SimpleContextMapper;
import org.anonymous.chapter05.core.SimpleLoader;
import org.anonymous.chapter05.core.SimpleWrapper;
import org.anonymous.chapter05.values.ClientIPLoggerValue;
import org.anonymous.chapter05.values.HeaderLoggerValue;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;

import java.io.IOException;

public final class Bootstrap2 {

    public static void main(String[] args) throws IOException {
        // BootStrap 该程序仅仅包括一个 servlet，也许会有一些应用仅仅需要一个 servlet
        // 名单是大多数的网络应用需要多个 servlet。在这些应用中，你需要一个跟包装
        // 器不同的容器：上下文。

        // 一个包含两个包装器的上下文来包装两个
        // servlet 类。当有多于一个得包装器的时候，需要一个 map 来处理这些子容器，
        // 对于特殊的请求可以使用特殊的子容器来处理。

        /*
            1. 一个容器 container 有一个流水线 pipeline，容器的 invoke 方法会调用流水线的 invoke 方法。
            2. 流水线的 invoke 方法会调用添加到容器中的阀门 value 的 invoke 方法，
                然后调用基本阀门(basic)的 invoke 方法。
            3. 在一个包装器中，基本阀门负责加载相关的 servlet 类并对请求作出相应。
            4. 在一个有子容器的上下文中，基本法门使用 mapper 来查找负责处
                理请求的子容器。如果一个子容器被找到，子容器的 invoke 方法会被调
                用，然后返回步骤 1。
         */

        HttpConnector connector = new HttpConnector();
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        Context context = new SimpleContext();
        context.addChild(wrapper1);
        context.addChild(wrapper2);

        Valve valve1 = new HeaderLoggerValue();
        Valve valve2 = new ClientIPLoggerValue();

        ((Pipeline) context).addValve(valve1);
        ((Pipeline) context).addValve(valve2);

        Mapper mapper = new SimpleContextMapper();
        mapper.setProtocol("http");
        context.addMapper(mapper);
        Loader loader = new SimpleLoader();
        context.setLoader(loader);
        // context.addServletMapping(pattern, name);
        context.addServletMapping("/Primitive", "Primitive");
        context.addServletMapping("/Modern", "Modern");
        connector.setContainer(context);
        try {
            connector.initialize();
            connector.start();

            // make the application wait until we press a key.
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}