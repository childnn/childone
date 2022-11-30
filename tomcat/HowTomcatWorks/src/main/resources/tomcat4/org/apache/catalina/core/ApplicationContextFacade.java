/*
 * Copyright 1999,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package tomcat4.org.apache.catalina.core;


import javax.servlet.*;
import javax.servlet.descriptor.JspConfigDescriptor;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;


/**
 * Facade object which masks the internal <code>ApplicationContext</code>
 * object from the web application.
 *
 * @author Remy Maucherat
 * @version $Revision: 1.4 $ $Date: 2004/08/26 21:31:21 $
 */

public final class ApplicationContextFacade
    implements ServletContext {


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new instance of this class, associated with the specified
     * Context instance.
     *
     * @param context The associated Context instance
     */
    public ApplicationContextFacade(ApplicationContext context) {
        super();
        this.context = context;
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * Wrapped application context.
     */
    private ApplicationContext context = null;


    // ------------------------------------------------- ServletContext Methods


    @Override
    public String getContextPath() {
        return null;
    }

    public ServletContext getContext(String uripath) {
        ServletContext theContext = context.getContext(uripath);
        if ((theContext != null) &&
            (theContext instanceof ApplicationContext))
            theContext = ((ApplicationContext) theContext).getFacade();
        return (theContext);
    }


    public int getMajorVersion() {
        return context.getMajorVersion();
    }


    public int getMinorVersion() {
        return context.getMinorVersion();
    }

    @Override
    public int getEffectiveMajorVersion() {
        return 0;
    }

    @Override
    public int getEffectiveMinorVersion() {
        return 0;
    }


    public String getMimeType(String file) {
        return context.getMimeType(file);
    }


    public Set getResourcePaths(String path) {
        return context.getResourcePaths(path);
    }


    public URL getResource(String path)
        throws MalformedURLException {
        return context.getResource(path);
    }


    public InputStream getResourceAsStream(String path) {
        return context.getResourceAsStream(path);
    }


    public RequestDispatcher getRequestDispatcher(String path) {
        return context.getRequestDispatcher(path);
    }


    public RequestDispatcher getNamedDispatcher(String name) {
        return context.getNamedDispatcher(name);
    }


    public Servlet getServlet(String name)
        throws ServletException {
        return context.getServlet(name);
    }


    public Enumeration getServlets() {
        return context.getServlets();
    }


    public Enumeration getServletNames() {
        return context.getServletNames();
    }


    public void log(String msg) {
        context.log(msg);
    }


    public void log(Exception exception, String msg) {
        context.log(exception, msg);
    }


    public void log(String message, Throwable throwable) {
        context.log(message, throwable);
    }


    public String getRealPath(String path) {
        return context.getRealPath(path);
    }


    public String getServerInfo() {
        return context.getServerInfo();
    }


    public String getInitParameter(String name) {
        return context.getInitParameter(name);
    }


    public Enumeration getInitParameterNames() {
        return context.getInitParameterNames();
    }

    @Override
    public boolean setInitParameter(String name, String value) {
        return false;
    }


    public Object getAttribute(String name) {
        return context.getAttribute(name);
    }


    public Enumeration getAttributeNames() {
        return context.getAttributeNames();
    }


    public void setAttribute(String name, Object object) {
        context.setAttribute(name, object);
    }


    public void removeAttribute(String name) {
        context.removeAttribute(name);
    }


    public String getServletContextName() {
        return context.getServletContextName();
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, String className) {
        return null;
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet) {
        return null;
    }

    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {
        return null;
    }

    @Override
    public ServletRegistration.Dynamic addJspFile(String servletName, String jspFile) {
        return null;
    }

    @Override
    public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {
        return null;
    }

    @Override
    public ServletRegistration getServletRegistration(String servletName) {
        return null;
    }

    @Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        return null;
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, String className) {
        return null;
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
        return null;
    }

    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass) {
        return null;
    }

    @Override
    public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {
        return null;
    }

    @Override
    public FilterRegistration getFilterRegistration(String filterName) {
        return null;
    }

    @Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        return null;
    }

    @Override
    public SessionCookieConfig getSessionCookieConfig() {
        return null;
    }

    @Override
    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {

    }

    @Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        return null;
    }

    @Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        return null;
    }

    @Override
    public void addListener(String className) {

    }

    @Override
    public <T extends EventListener> void addListener(T t) {

    }

    @Override
    public void addListener(Class<? extends EventListener> listenerClass) {

    }

    @Override
    public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {
        return null;
    }

    @Override
    public JspConfigDescriptor getJspConfigDescriptor() {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public void declareRoles(String... roleNames) {

    }

    @Override
    public String getVirtualServerName() {
        return null;
    }

    @Override
    public int getSessionTimeout() {
        return 0;
    }

    @Override
    public void setSessionTimeout(int sessionTimeout) {

    }

    @Override
    public String getRequestCharacterEncoding() {
        return null;
    }

    @Override
    public void setRequestCharacterEncoding(String encoding) {

    }

    @Override
    public String getResponseCharacterEncoding() {
        return null;
    }

    @Override
    public void setResponseCharacterEncoding(String encoding) {

    }


}
