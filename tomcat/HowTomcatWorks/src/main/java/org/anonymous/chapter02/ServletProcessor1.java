package org.anonymous.chapter02;

import org.anonymous.Constants;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class ServletProcessor1 {

    public void process(Request request, Response response) {

        String uri = request.getUri();
        String servletName = uri.substring(uri.lastIndexOf("/") + 1);
        URLClassLoader loader = null;

        try {
            // create a URLClassLoader
            URL[] urls = new URL[1];
            File classPath = new File(Constants.WEB_ROOT);
            String canonicalPath = classPath.getCanonicalPath();
            System.out.println("canonicalPath = " + canonicalPath);
            // the forming of repository is taken from the createClassLoader method in
            // org.apache.catalina.startup.ClassLoaderFactory
            // repository 表示类加载器查找 servlet 类的目录: 实际是 .class 文件的名字(编译后文件)
            String repository = (new URL("file", null, canonicalPath + File.separator)).toString();
            // the code for forming the URL is taken from the addRepository method in
            // org.apache.catalina.loader.StandardClassLoader class.
            urls[0] = new URL(null, repository, (URLStreamHandler) null);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            System.out.println(e);
        }
        Class myClass = null;
        try {
            myClass = loader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        System.out.println("myClass = " + myClass);

        Servlet servlet;

        try {
            servlet = (Servlet) myClass.newInstance();
            System.out.println("servlet = " + servlet);
            servlet.service(request, response);
        } catch (Throwable e) {
            System.out.println(e);
        }

    }

}