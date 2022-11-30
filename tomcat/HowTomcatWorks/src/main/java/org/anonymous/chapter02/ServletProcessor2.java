package org.anonymous.chapter02;

import org.anonymous.Constants;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ServletProcessor2 {

    public void process(Request request, Response response) {

        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader loader = null;

        try {
            // create a URLClassLoader
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(Constants.WEB_ROOT);
            String canonicalPath = classPath.getCanonicalPath();
            System.out.println("canonicalPath = " + canonicalPath);
            // the forming of repository is taken from the createClassLoader method in
            // org.apache.catalina.startup.ClassLoaderFactory
            String repository = new URL("file", null, canonicalPath + File.separator).toString();
            // the code for forming the URL is taken from the addRepository method in
            // org.apache.catalina.loader.StandardClassLoader class.
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        Class myClass = null;
        try {
            myClass = loader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }

        Servlet servlet;

        // 在 ServletProcessor1 中, 直接传递 request/response
        // 这种方式存在一定的风险, 知道这个 servlet 容器的内部运作的 servlet 程序员可以
        // 分别把 request/response 强转为 相应的实现 Request/Response
        // 这样可以调用内部的 public 方法. 比如 #parseUri, #sendStaticResource
        // 这些方法在 servlet 内部应该是不可见的.
        // 如果使用 默认访问修饰符, 在包外不可访问.
        // 因此使用 facade 类.
        RequestFacade requestFacade = new RequestFacade(request);
        ResponseFacade responseFacade = new ResponseFacade(response);
        try {
            servlet = (Servlet) myClass.newInstance();
            servlet.service(requestFacade, responseFacade);
        } catch (Throwable e) {
            System.out.println(e.toString());
        }

    }
}