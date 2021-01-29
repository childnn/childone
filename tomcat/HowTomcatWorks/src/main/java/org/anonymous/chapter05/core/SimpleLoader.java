package org.anonymous.chapter05.core;

import org.apache.catalina.Container;
import org.apache.catalina.DefaultContext;
import org.apache.catalina.Loader;

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
 * @since 2020/11/6 22:06
 */
public class SimpleLoader implements Loader {

    private final URLClassLoader classLoader;
    private Container container;

    public SimpleLoader() throws IOException {
        URL[] urls = new URL[1];
        URLStreamHandler streamHandler = null;
        File classpath = new File(WEB_ROOT);
        String repository = new URL("file", null, classpath.getCanonicalPath() + File.separator).toString();
        urls[0] = new URL(null, repository, streamHandler);
        classLoader = new URLClassLoader(urls);
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public DefaultContext getDefaultContext() {
        return null;
    }

    @Override
    public void setDefaultContext(DefaultContext defaultContext) {

    }

    @Override
    public boolean getDelegate() {
        return false;
    }

    @Override
    public void setDelegate(boolean b) {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public boolean getReloadable() {
        return false;
    }

    @Override
    public void setReloadable(boolean b) {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }

    @Override
    public void addRepository(String s) {

    }

    @Override
    public String[] findRepositories() {
        return new String[0];
    }

    @Override
    public boolean modified() {
        return false;
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }
}
