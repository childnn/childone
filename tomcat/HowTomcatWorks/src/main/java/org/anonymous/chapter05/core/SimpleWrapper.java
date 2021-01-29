package org.anonymous.chapter05.core;

import org.apache.catalina.Cluster;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.InstanceListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Logger;
import org.apache.catalina.Manager;
import org.apache.catalina.Mapper;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Realm;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;

import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/11/6 21:58
 */
public class SimpleWrapper implements Wrapper, Pipeline {

    private final Pipeline pipeline = new SimplePipeline(this);
    protected Container parent;
    private Loader loader;

    public SimpleWrapper() {
        pipeline.setBasic(new SimpleWrapperValue());
    }

    @Override
    public long getAvailable() {
        return 0;
    }

    @Override
    public void setAvailable(long l) {

    }

    @Override
    public String getJspFile() {
        return null;
    }

    @Override
    public void setJspFile(String s) {

    }

    @Override
    public int getLoadOnStartup() {
        return 0;
    }

    @Override
    public void setLoadOnStartup(int i) {

    }

    @Override
    public String getRunAs() {
        return null;
    }

    @Override
    public void setRunAs(String s) {

    }

    @Override
    public String getServletClass() {
        return null;
    }

    @Override
    public void setServletClass(String s) {

    }

    @Override
    public boolean isUnavailable() {
        return false;
    }

    @Override
    public void addInitParameter(String s, String s1) {

    }

    @Override
    public void addInstanceListener(InstanceListener instanceListener) {

    }

    @Override
    public void addSecurityReference(String s, String s1) {

    }

    @Override
    public Servlet allocate() {
        return null;
    }

    @Override
    public void deallocate(Servlet servlet) throws ServletException {

    }

    @Override
    public String findInitParameter(String s) {
        return null;
    }

    @Override
    public String[] findInitParameters() {
        return new String[0];
    }

    @Override
    public String findSecurityReference(String s) {
        return null;
    }

    @Override
    public String[] findSecurityReferences() {
        return new String[0];
    }

    @Override
    public void load() throws ServletException {

    }

    @Override
    public void removeInitParameter(String s) {

    }

    @Override
    public void removeInstanceListener(InstanceListener instanceListener) {

    }

    @Override
    public void removeSecurityReference(String s) {

    }

    @Override
    public void unavailable(UnavailableException e) {

    }

    @Override
    public void unload() throws ServletException {

    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public Loader getLoader() {
        if (loader != null) {
            return loader;
        }
        if (parent != null) {
            return parent.getLoader();
        }
        return null;
    }

    @Override
    public void setLoader(Loader loader) {
        this.loader = loader;
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
    public Cluster getCluster() {
        return null;
    }

    @Override
    public void setCluster(Cluster cluster) {

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
    public Valve getBasic() {
        return null;
    }

    @Override
    public void setBasic(Valve valve) {

    }

    @Override
    public void addValve(Valve valve) {
    }

    @Override
    public Valve[] getValves() {
        return new Valve[0];
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {

    }

    @Override
    public void removeValve(Valve valve) {

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
