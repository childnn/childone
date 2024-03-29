package org.anonymous.chapter08.startup;

import org.anonymous.chapter08.core.SimpleContextConfig;
import org.anonymous.chapter08.core.SimpleWrapper;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappClassLoader;
import org.apache.catalina.loader.WebappLoader;
// import org.apache.naming.resources.ProxyDirContext;

public final class Bootstrap {

    public static void main(String[] args) {

        //invoke: http://localhost:8080/Modern or  http://localhost:8080/Primitive

        System.setProperty("catalina.base", System.getProperty("user.dir"));
        Connector connector = new HttpConnector();

        // 初始化两个 servlet 的两个包装器
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");
        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        // 创建了一个 StandardContext 得实例并设置该上下文的文档基（document base）.
        // 这样做跟设置如下设置 server.xml 效果相同：
        // <Context path="/myApp" docBase="myApp"/>
        Context context = new StandardContext();
        // StandardContext's start method adds a default mapper
        context.setPath("/myApp");
        context.setDocBase("myApp");

        // 两个包装器被添加到上下文中，并且添加映射关系，这样上下文就可以找
        // 到包装器。
        context.addChild(wrapper1);
        context.addChild(wrapper2);

        // context.addServletMapping(pattern, name);
        context.addServletMapping("/Primitive", "Primitive");
        context.addServletMapping("/Modern", "Modern");

        // 初始化一个监听器并且在上下文中注册
        // add ContextConfig. This listener is important because it configures
        // StandardContext (sets configured to true), otherwise StandardContext
        // won't start
        LifecycleListener listener = new SimpleContextConfig();
        ((Lifecycle) context).addLifecycleListener(listener);

        // 初始化该上下文相关联的 WebappLoader
        // here is our loader
        Loader loader = new WebappLoader();
        // associate the loader with the Context
        context.setLoader(loader);

        connector.setContainer(context);

        try {
            connector.initialize();
            ((Lifecycle) connector).start();
            ((Lifecycle) context).start();

            // 打印出源的 docBase 已经改加载器的所有源
            // now we want to know some details about WebappLoader
            WebappClassLoader classLoader = (WebappClassLoader) loader.getClassLoader();
            System.out.println("Resources' docBase: " + classLoader.getResources()/*((ProxyDirContext)classLoader.getResources()).getDocBase()*/);
            String[] repositories = classLoader.findRepositories();
            for (int i = 0; i < repositories.length; i++) {
                System.out.println("  repository: " + repositories[i]);
            }

            // make the application wait until we press a key.
            System.in.read();
            ((Lifecycle) context).stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}