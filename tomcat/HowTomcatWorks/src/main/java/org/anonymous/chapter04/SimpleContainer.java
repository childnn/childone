package org.anonymous.chapter04;

import org.apache.catalina.*;

import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import static org.anonymous.Constants.WEB_ROOT;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/11/4 19:58
 * @see org.apache.catalina.connector.http.HttpConnector#initialize()
 * @see org.apache.catalina.connector.http.HttpConnector#start()
 * 这两个方法在组件的生命周期里必须只调用一次.
 * HttpConnector 创建 socket, 维护一个 http-processor 对象池
 * 一个 processor 处理一个 http 请求
 * 处理 http 请求在 #run 方法中, while 循环, 一直运行到 connetor 关闭
 * 对每个 http 请求, 会调用 #createProcessor 创建 http-processor 实例
 * 大多时候 #createProcessor 并不创建一个新的 http-processor 对象, 而是从
 * Stack 池子中获取一个.
 */
public class SimpleContainer implements Container {

    public static void main(String[] args) throws IOException {
        String property = System.getProperty("user.dir");
        System.out.println("property = " + property);
        String canonicalPath = new File(property).getCanonicalPath();
        System.out.println("canonicalPath = " + canonicalPath);
    }

    /**
     * {@code HttpProcessor#process(java.net.Socket)} 调用 invoke 方法
     *
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        String servletName = ((HttpServletRequest) request).getRequestURI();
        servletName = servletName.substring(servletName.lastIndexOf("/") + 1);
        URLClassLoader loader = null;
        try {
            URL[] urls = new URL[1];
            URLStreamHandler streamHandler = null;
            File classPath = new File(WEB_ROOT);
            String repository = new URL("file", null, classPath.getCanonicalPath() + File.separator).toString();
            urls[0] = new URL(null, repository, streamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            System.out.println(e);
        }

        Class myClass = null;
        try {
            myClass = loader.loadClass(servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Servlet servlet;
        try {
            servlet = (Servlet) myClass.newInstance();
            servlet.service((ServletRequest) request, (ServletResponse) response);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addChild(Container container) {

    }

    @Override
    public void addContainerListener(ContainerListener containerListener) {

    }

    @Override
    public void addMapper(Mapper mapper) {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }

    @Override
    public Container findChild(String s) {
        return null;
    }

    @Override
    public Container[] findChildren() {
        return new Container[0];
    }

    @Override
    public ContainerListener[] findContainerListeners() {
        return new ContainerListener[0];
    }

    @Override
    public Mapper findMapper(String s) {
        return null;
    }

    @Override
    public Mapper[] findMappers() {
        return new Mapper[0];
    }

    @Override
    public Cluster getCluster() {
        return null;
    }

    @Override
    public void setCluster(Cluster cluster) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Loader getLoader() {
        return null;
    }

    @Override
    public void setLoader(Loader loader) {

    }

    @Override
    public Logger getLogger() {
        return null;
    }

    @Override
    public void setLogger(Logger logger) {

    }

    @Override
    public Manager getManager() {
        return null;
    }

    @Override
    public void setManager(Manager manager) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String s) {

    }

    @Override
    public Container getParent() {
        return null;
    }

    @Override
    public void setParent(Container container) {

    }

    @Override
    public ClassLoader getParentClassLoader() {
        return null;
    }

    @Override
    public void setParentClassLoader(ClassLoader classLoader) {

    }

    @Override
    public Realm getRealm() {
        return null;
    }

    @Override
    public void setRealm(Realm realm) {

    }

    @Override
    public DirContext getResources() {
        return null;
    }

    @Override
    public void setResources(DirContext dirContext) {

    }

    @Override
    public Container map(Request request, boolean b) {
        return null;
    }

    @Override
    public void removeChild(Container container) {

    }

    @Override
    public void removeContainerListener(ContainerListener containerListener) {

    }

    @Override
    public void removeMapper(Mapper mapper) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }
}