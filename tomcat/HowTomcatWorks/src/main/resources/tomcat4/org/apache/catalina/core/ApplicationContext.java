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

import tomcat4.org.apache.catalina.*;
import tomcat4.org.apache.catalina.deploy.FilterDef;
import tomcat4.org.apache.catalina.util.RequestUtil;
import tomcat4.org.apache.catalina.util.ServerInfo;
import tomcat4.org.apache.catalina.util.StringManager;

import javax.naming.NamingException;
import javax.servlet.*;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Standard implementation of <code>ServletContext</code> that represents
 * a web application's execution environment.  An instance of this class is
 * associated with each instance of <code>StandardContext</code>.
 *
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 */
public class ApplicationContext implements ServletContext {

    protected static final boolean STRICT_SERVLET_COMPLIANCE;

    protected static final boolean GET_RESOURCE_REQUIRE_SLASH;


    static {
        STRICT_SERVLET_COMPLIANCE = Globals.STRICT_SERVLET_COMPLIANCE;

        String requireSlash = System.getProperty(
                "org.apache.catalina.core.ApplicationContext.GET_RESOURCE_REQUIRE_SLASH");
        if (requireSlash == null) {
            GET_RESOURCE_REQUIRE_SLASH = STRICT_SERVLET_COMPLIANCE;
        } else {
            GET_RESOURCE_REQUIRE_SLASH = Boolean.parseBoolean(requireSlash);
        }
    }

    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new instance of this class, associated with the specified
     * Context instance.
     *
     * @param context The associated Context instance
     */
    public ApplicationContext(StandardContext context) {
        super();
        this.context = context;
        this.service = ((Engine) context.getParent().getParent()).getService();
        this.sessionCookieConfig = new ApplicationSessionCookieConfig(context);

        // Populate session tracking modes
        populateSessionTrackingModes();
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The context attributes for this context.
     */
    protected Map<String, Object> attributes = new ConcurrentHashMap<>();


    /**
     * List of read only attributes for this context.
     */
    private final Map<String, String> readOnlyAttributes = new ConcurrentHashMap<>();


    /**
     * The Context instance with which we are associated.
     */
    private final StandardContext context;


    /**
     * The Service instance with which we are associated.
     */
    private final Service service;


    /**
     * Empty String collection to serve as the basis for empty enumerations.
     */
    private static final List<String> emptyString = Collections.emptyList();


    /**
     * Empty Servlet collection to serve as the basis for empty enumerations.
     */
    private static final List<Servlet> emptyServlet = Collections.emptyList();


    /**
     * The facade around this object.
     */
    private final ServletContext facade = new ApplicationContextFacade(this);


    /**
     * The merged context initialization parameters for this Context.
     */
    private final Map<String, String> parameters = new ConcurrentHashMap<>();


    /**
     * The string manager for this package.
     */
    private static final StringManager sm = StringManager.getManager(Constants.Package);


    /**
     * Thread local data used during request dispatch.
     */
    private final ThreadLocal<DispatchData> dispatchData = new ThreadLocal<>();


    /**
     * Session Cookie config
     */
    private SessionCookieConfig sessionCookieConfig;

    /**
     * Session tracking modes
     */
    private Set<SessionTrackingMode> sessionTrackingModes = null;
    private Set<SessionTrackingMode> defaultSessionTrackingModes = null;
    private Set<SessionTrackingMode> supportedSessionTrackingModes = null;

    /**
     * Flag that indicates if a new {@link ServletContextListener} may be added
     * to the application. Once the first {@link ServletContextListener} is
     * called, no more may be added.
     */
    private boolean newServletContextListenerAllowed = true;


    // ------------------------------------------------- ServletContext Methods

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }


    @Override
    public Enumeration<String> getAttributeNames() {
        Set<String> names = new HashSet<>(attributes.keySet());
        return Collections.enumeration(names);
    }


    @Override
    public ServletContext getContext(String uri) {

        // Validate the format of the specified argument
        if (uri == null || !uri.startsWith("/")) {
            return null;
        }

        Context child = null;
        //try {
        //    // Look for an exact match
        //    Container host = context.getParent();
        //    child = (Context) host.findChild(uri);
        //
        //    // Non-running contexts should be ignored.
        //    if (child != null && !child.getState().isAvailable()) {
        //        child = null;
        //    }
        //
        //    // Remove any version information and use the mapper
        //    if (child == null) {
        //        int i = uri.indexOf("##");
        //        if (i > -1) {
        //            uri = uri.substring(0, i);
        //        }
        //        // Note: This could be more efficient with a dedicated Mapper
        //        //       method but such an implementation would require some
        //        //       refactoring of the Mapper to avoid copy/paste of
        //        //       existing code.
        //        MessageBytes hostMB = MessageBytes.newInstance();
        //        hostMB.setString(host.getName());
        //
        //        MessageBytes pathMB = MessageBytes.newInstance();
        //        pathMB.setString(uri);
        //
        //        MappingData mappingData = new MappingData();
        //        service.getMapper().map(hostMB, pathMB, null, mappingData);
        //        child = mappingData.context;
        //    }
        //} catch (Throwable t) {
        //    ExceptionUtils.handleThrowable(t);
        //    return null;
        //}

        if (child == null) {
            return null;
        }

        if (context.getCrossContext()) {
            // If crossContext is enabled, can always return the context
            return child.getServletContext();
        } else if (child == context) {
            // Can still return the current context
            return context.getServletContext();
        } else {
            // Nothing to return
            return null;
        }
    }


    @Override
    public String getContextPath() {
        return context.getPath();
    }


    @Override
    public String getInitParameter(final String name) {
        // Special handling for XML settings as the context setting must
        // always override anything that might have been set by an application.
        if (Globals.JASPER_XML_VALIDATION_TLD_INIT_PARAM.equals(name) &&
                context.getTldValidation()) {
            return "true";
        }
        if (Globals.JASPER_XML_BLOCK_EXTERNAL_INIT_PARAM.equals(name)) {
            if (!context.getXmlBlockExternal()) {
                // System admin has explicitly changed the default
                return "false";
            }
        }
        return parameters.get(name);
    }


    @Override
    public Enumeration<String> getInitParameterNames() {
        Set<String> names = new HashSet<>(parameters.keySet());
        // Special handling for XML settings as these attributes will always be
        // available if they have been set on the context
        if (context.getTldValidation()) {
            names.add(Globals.JASPER_XML_VALIDATION_TLD_INIT_PARAM);
        }
        if (!context.getXmlBlockExternal()) {
            names.add(Globals.JASPER_XML_BLOCK_EXTERNAL_INIT_PARAM);
        }
        return Collections.enumeration(names);
    }


    @Override
    public int getMajorVersion() {
        return org.apache.catalina.core.Constants.MAJOR_VERSION;
    }


    @Override
    public int getMinorVersion() {
        return org.apache.catalina.core.Constants.MINOR_VERSION;
    }


    /**
     * Return the MIME type of the specified file, or <code>null</code> if
     * the MIME type cannot be determined.
     *
     * @param file Filename for which to identify a MIME type
     */
    @Override
    public String getMimeType(String file) {

        if (file == null)
            return null;
        int period = file.lastIndexOf('.');
        if (period < 0)
            return null;
        String extension = file.substring(period + 1);
        if (extension.length() < 1)
            return null;
        return context.findMimeMapping(extension);

    }


    /**
     * Return a <code>RequestDispatcher</code> object that acts as a
     * wrapper for the named servlet.
     *
     * @param name Name of the servlet for which a dispatcher is requested
     */
    @Override
    public RequestDispatcher getNamedDispatcher(String name) {

        // Validate the name argument
        if (name == null)
            return null;

        // Create and return a corresponding request dispatcher
        Wrapper wrapper = (Wrapper) context.findChild(name);
        if (wrapper == null)
            return null;

        return new org.apache.catalina.core.ApplicationDispatcher(wrapper, null, null, null, null, null, name);

    }


    @Override
    public String getRealPath(String path) {
        String validatedPath = validateResourcePath(path, true);
        return context.getRealPath(validatedPath);
    }


    @Override
    public RequestDispatcher getRequestDispatcher(final String path) {

        // Validate the path argument
        if (path == null) {
            return null;
        }
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException(
                    sm.getString("applicationContext.requestDispatcher.iae", path));
        }

        // Same processing order as InputBuffer / CoyoteAdapter
        // First remove query string
        String uri;
        String queryString;
        int pos = path.indexOf('?');
        if (pos >= 0) {
            uri = path.substring(0, pos);
            queryString = path.substring(pos + 1);
        } else {
            uri = path;
            queryString = null;
        }

        // Remove path parameters
        String uriNoParams = stripPathParams(uri);

        // Then normalize
        String normalizedUri = RequestUtil.normalize(uriNoParams);
        if (normalizedUri == null) {
            return null;
        }

        // Mapping is against the normalized uri

        if (getContext().getDispatchersUseEncodedPaths()) {
            // Decode
            String decodedUri = UDecoder.URLDecode(normalizedUri, StandardCharsets.UTF_8);

            // Security check to catch attempts to encode /../ sequences
            normalizedUri = RequestUtil.normalize(decodedUri);
            if (!decodedUri.equals(normalizedUri)) {
                getContext().getLogger().warn(
                        sm.getString("applicationContext.illegalDispatchPath", path),
                        new IllegalArgumentException());
                return null;
            }

            // URI needs to include the context path
            uri = URLEncoder.DEFAULT.encode(getContextPath(), StandardCharsets.UTF_8) + uri;
        } else {
            // uri is passed to the constructor for ApplicationDispatcher and is
            // ultimately used as the value for getRequestURI() which returns
            // encoded values. Therefore, since the value passed in for path
            // was decoded, encode uri here.
            uri = URLEncoder.DEFAULT.encode(getContextPath() + uri, StandardCharsets.UTF_8);
        }

        // Use the thread local URI and mapping data
        DispatchData dd = dispatchData.get();
        if (dd == null) {
            dd = new DispatchData();
            dispatchData.set(dd);
        }

        // Use the thread local mapping data
        MessageBytes uriMB = dd.uriMB;
        MappingData mappingData = dd.mappingData;

        try {
            // Map the URI
            CharChunk uriCC = uriMB.getCharChunk();
            try {
                uriCC.append(context.getPath());
                uriCC.append(normalizedUri);
                service.getMapper().map(context, uriMB, mappingData);
                if (mappingData.wrapper == null) {
                    return null;
                }
            } catch (Exception e) {
                // Should never happen
                log(sm.getString("applicationContext.mapping.error"), e);
                return null;
            }

            Wrapper wrapper = mappingData.wrapper;
            String wrapperPath = mappingData.wrapperPath.toString();
            String pathInfo = mappingData.pathInfo.toString();
            HttpServletMapping mapping = new ApplicationMapping(mappingData).getHttpServletMapping();

            // Construct a RequestDispatcher to process this request
            return new org.apache.catalina.core.ApplicationDispatcher(wrapper, uri, wrapperPath, pathInfo,
                    queryString, mapping, null);
        } finally {
            // Recycle thread local data at the end of the request so references
            // are not held to a completed request as there is potential for
            // that to trigger a memory leak if a context is unloaded. Not
            // strictly necessary here for uriMB but it needs to be recycled at
            // some point so do it here for consistency with mappingData which
            // must be recycled here.
            uriMB.recycle();
            mappingData.recycle();
        }
    }


    // Package private to facilitate testing
    static String stripPathParams(String input) {
        // Shortcut
        if (input.indexOf(';') < 0) {
            return input;
        }

        StringBuilder sb = new StringBuilder(input.length());
        int pos = 0;
        int limit = input.length();
        while (pos < limit) {
            int nextSemiColon = input.indexOf(';', pos);
            if (nextSemiColon < 0) {
                nextSemiColon = limit;
            }
            sb.append(input.substring(pos, nextSemiColon));
            int followingSlash = input.indexOf('/', nextSemiColon);
            if (followingSlash < 0) {
                pos = limit;
            } else {
                pos = followingSlash;
            }
        }

        return sb.toString();
    }


    @Override
    public URL getResource(String path) throws MalformedURLException {

        String validatedPath = validateResourcePath(path, false);

        if (validatedPath == null) {
            throw new MalformedURLException(
                    sm.getString("applicationContext.requestDispatcher.iae", path));
        }

        WebResourceRoot resources = context.getResources();
        if (resources != null) {
            return resources.getResource(validatedPath).getURL();
        }

        return null;
    }


    @Override
    public InputStream getResourceAsStream(String path) {

        String validatedPath = validateResourcePath(path, false);

        if (validatedPath == null) {
            return null;
        }

        WebResourceRoot resources = context.getResources();
        if (resources != null) {
            return resources.getResource(validatedPath).getInputStream();
        }

        return null;
    }


    /*
     * Returns null if the input path is not valid or a path that will be
     * acceptable to resources.getResource().
     */
    private String validateResourcePath(String path, boolean allowEmptyPath) {
        if (path == null) {
            return null;
        }

        if (path.length() == 0 && allowEmptyPath) {
            return path;
        }

        if (!path.startsWith("/")) {
            if (GET_RESOURCE_REQUIRE_SLASH) {
                return null;
            } else {
                return "/" + path;
            }
        }

        return path;
    }


    @Override
    public Set<String> getResourcePaths(String path) {

        // Validate the path argument
        if (path == null) {
            return null;
        }
        if (!path.startsWith("/")) {
            throw new IllegalArgumentException(sm.getString("applicationContext.resourcePaths.iae", path));
        }

        WebResourceRoot resources = context.getResources();
        if (resources != null) {
            return resources.listWebAppPaths(path);
        }

        return null;
    }


    @Override
    public String getServerInfo() {
        return ServerInfo.getServerInfo();
    }


    @Override
    @Deprecated
    public Servlet getServlet(String name) {
        return null;
    }


    @Override
    public String getServletContextName() {
        return context.getDisplayName();
    }


    @Override
    @Deprecated
    public Enumeration<String> getServletNames() {
        return Collections.enumeration(emptyString);
    }


    @Override
    @Deprecated
    public Enumeration<Servlet> getServlets() {
        return Collections.enumeration(emptyServlet);
    }


    @Override
    public void log(String message) {
        context.getLogger().info(message);
    }


    @Override
    @Deprecated
    public void log(Exception exception, String message) {
        context.getLogger().error(message, exception);
    }


    @Override
    public void log(String message, Throwable throwable) {
        context.getLogger().error(message, throwable);
    }


    @Override
    public void removeAttribute(String name) {

        Object value = null;

        // Remove the specified attribute
        // Check for read only attribute
        if (readOnlyAttributes.containsKey(name)) {
            return;
        }
        value = attributes.remove(name);
        if (value == null) {
            return;
        }

        // Notify interested application event listeners
        Object listeners[] = context.getApplicationEventListeners();
        if ((listeners == null) || (listeners.length == 0)) {
            return;
        }
        ServletContextAttributeEvent event = new ServletContextAttributeEvent(
                context.getServletContext(), name, value);
        for (Object obj : listeners) {
            if (!(obj instanceof ServletContextAttributeListener)) {
                continue;
            }
            ServletContextAttributeListener listener = (ServletContextAttributeListener) obj;
            try {
                context.fireContainerEvent("beforeContextAttributeRemoved", listener);
                listener.attributeRemoved(event);
                context.fireContainerEvent("afterContextAttributeRemoved", listener);
            } catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                context.fireContainerEvent("afterContextAttributeRemoved", listener);
                // FIXME - should we do anything besides log these?
                log(sm.getString("applicationContext.attributeEvent"), t);
            }
        }
    }


    @Override
    public void setAttribute(String name, Object value) {
        // Name cannot be null
        if (name == null) {
            throw new NullPointerException(sm.getString("applicationContext.setAttribute.namenull"));
        }

        // Null value is the same as removeAttribute()
        if (value == null) {
            removeAttribute(name);
            return;
        }

        // Add or replace the specified attribute
        // Check for read only attribute
        if (readOnlyAttributes.containsKey(name)) {
            return;
        }

        Object oldValue = attributes.put(name, value);
        boolean replaced = oldValue != null;

        // Notify interested application event listeners
        Object listeners[] = context.getApplicationEventListeners();
        if ((listeners == null) || (listeners.length == 0)) {
            return;
        }
        ServletContextAttributeEvent event = null;
        if (replaced) {
            event = new ServletContextAttributeEvent(context.getServletContext(), name, oldValue);
        } else {
            event = new ServletContextAttributeEvent(context.getServletContext(), name, value);
        }

        for (Object obj : listeners) {
            if (!(obj instanceof ServletContextAttributeListener)) {
                continue;
            }
            ServletContextAttributeListener listener = (ServletContextAttributeListener) obj;
            try {
                if (replaced) {
                    context.fireContainerEvent("beforeContextAttributeReplaced", listener);
                    listener.attributeReplaced(event);
                    context.fireContainerEvent("afterContextAttributeReplaced", listener);
                } else {
                    context.fireContainerEvent("beforeContextAttributeAdded", listener);
                    listener.attributeAdded(event);
                    context.fireContainerEvent("afterContextAttributeAdded", listener);
                }
            } catch (Throwable t) {
                ExceptionUtils.handleThrowable(t);
                if (replaced) {
                    context.fireContainerEvent("afterContextAttributeReplaced", listener);
                } else {
                    context.fireContainerEvent("afterContextAttributeAdded", listener);
                }
                // FIXME - should we do anything besides log these?
                log(sm.getString("applicationContext.attributeEvent"), t);
            }
        }
    }


    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, String className) {
        return addFilter(filterName, className, null);
    }


    @Override
    public FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {
        return addFilter(filterName, null, filter);
    }


    @Override
    public FilterRegistration.Dynamic addFilter(String filterName,
                                                Class<? extends Filter> filterClass) {
        return addFilter(filterName, filterClass.getName(), null);
    }


    private FilterRegistration.Dynamic addFilter(String filterName,
                                                 String filterClass, Filter filter) throws IllegalStateException {

        if (filterName == null || filterName.equals("")) {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.invalidFilterName", filterName));
        }

        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            //TODO Spec breaking enhancement to ignore this restriction
            throw new IllegalStateException(
                    sm.getString("applicationContext.addFilter.ise",
                            getContextPath()));
        }

        FilterDef filterDef = context.findFilterDef(filterName);

        // Assume a 'complete' FilterRegistration is one that has a class and
        // a name
        if (filterDef == null) {
            filterDef = new FilterDef();
            filterDef.setFilterName(filterName);
            context.addFilterDef(filterDef);
        } else {
            if (filterDef.getFilterName() != null &&
                    filterDef.getFilterClass() != null) {
                return null;
            }
        }

        if (filter == null) {
            filterDef.setFilterClass(filterClass);
        } else {
            filterDef.setFilterClass(filter.getClass().getName());
            filterDef.setFilter(filter);
        }

        return new ApplicationFilterRegistration(filterDef, context);
    }


    @Override
    public <T extends Filter> T createFilter(Class<T> c) throws ServletException {
        try {
            @SuppressWarnings("unchecked")
            T filter = (T) context.getInstanceManager().newInstance(c.getName());
            return filter;
        } catch (InvocationTargetException e) {
            ExceptionUtils.handleThrowable(e.getCause());
            throw new ServletException(e);
        } catch (ReflectiveOperationException | NamingException e) {
            throw new ServletException(e);
        }
    }


    @Override
    public FilterRegistration getFilterRegistration(String filterName) {
        FilterDef filterDef = context.findFilterDef(filterName);
        if (filterDef == null) {
            return null;
        }
        return new ApplicationFilterRegistration(filterDef, context);
    }


    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, String className) {
        return addServlet(servletName, className, null, null);
    }


    @Override
    public ServletRegistration.Dynamic addServlet(String servletName, Servlet servlet) {
        return addServlet(servletName, null, servlet, null);
    }


    @Override
    public ServletRegistration.Dynamic addServlet(String servletName,
                                                  Class<? extends Servlet> servletClass) {
        return addServlet(servletName, servletClass.getName(), null, null);
    }


    @Override
    public ServletRegistration.Dynamic addJspFile(String jspName, String jspFile) {

        // jspName is validated in addServlet()
        if (jspFile == null || !jspFile.startsWith("/")) {
            throw new IllegalArgumentException(
                    sm.getString("applicationContext.addJspFile.iae", jspFile));
        }

        String jspServletClassName = null;
        Map<String, String> jspFileInitParams = new HashMap<>();

        Wrapper jspServlet = (Wrapper) context.findChild("jsp");

        if (jspServlet == null) {
            // No JSP servlet currently defined.
            // Use default JSP Servlet class name
            jspServletClassName = Constants.JSP_SERVLET_CLASS;
        } else {
            // JSP Servlet defined.
            // Use same JSP Servlet class name
            jspServletClassName = jspServlet.getServletClass();
            // Use same init parameters
            String[] params = jspServlet.findInitParameters();
            for (String param : params) {
                jspFileInitParams.put(param, jspServlet.findInitParameter(param));
            }
        }

        // Add init parameter to specify JSP file
        jspFileInitParams.put("jspFile", jspFile);

        return addServlet(jspName, jspServletClassName, null, jspFileInitParams);
    }


    private ServletRegistration.Dynamic addServlet(String servletName, String servletClass,
                                                   Servlet servlet, Map<String, String> initParams) throws IllegalStateException {

        if (servletName == null || servletName.equals("")) {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.invalidServletName", servletName));
        }

        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            //TODO Spec breaking enhancement to ignore this restriction
            throw new IllegalStateException(
                    sm.getString("applicationContext.addServlet.ise",
                            getContextPath()));
        }

        Wrapper wrapper = (Wrapper) context.findChild(servletName);

        // Assume a 'complete' ServletRegistration is one that has a class and
        // a name
        if (wrapper == null) {
            wrapper = context.createWrapper();
            wrapper.setName(servletName);
            context.addChild(wrapper);
        } else {
            if (wrapper.getName() != null &&
                    wrapper.getServletClass() != null) {
                if (wrapper.isOverridable()) {
                    wrapper.setOverridable(false);
                } else {
                    return null;
                }
            }
        }

        ServletSecurity annotation = null;
        if (servlet == null) {
            wrapper.setServletClass(servletClass);
            Class<?> clazz = Introspection.loadClass(context, servletClass);
            if (clazz != null) {
                annotation = clazz.getAnnotation(ServletSecurity.class);
            }
        } else {
            wrapper.setServletClass(servlet.getClass().getName());
            wrapper.setServlet(servlet);
            if (context.wasCreatedDynamicServlet(servlet)) {
                annotation = servlet.getClass().getAnnotation(ServletSecurity.class);
            }
        }

        if (initParams != null) {
            for (Map.Entry<String, String> initParam : initParams.entrySet()) {
                wrapper.addInitParameter(initParam.getKey(), initParam.getValue());
            }
        }

        ServletRegistration.Dynamic registration =
                new ApplicationServletRegistration(wrapper, context);
        if (annotation != null) {
            registration.setServletSecurity(new ServletSecurityElement(annotation));
        }
        return registration;
    }


    @Override
    public <T extends Servlet> T createServlet(Class<T> c) throws ServletException {
        try {
            @SuppressWarnings("unchecked")
            T servlet = (T) context.getInstanceManager().newInstance(c.getName());
            context.dynamicServletCreated(servlet);
            return servlet;
        } catch (InvocationTargetException e) {
            ExceptionUtils.handleThrowable(e.getCause());
            throw new ServletException(e);
        } catch (ReflectiveOperationException | NamingException e) {
            throw new ServletException(e);
        }
    }


    @Override
    public ServletRegistration getServletRegistration(String servletName) {
        Wrapper wrapper = (Wrapper) context.findChild(servletName);
        if (wrapper == null) {
            return null;
        }

        return new ApplicationServletRegistration(wrapper, context);
    }


    @Override
    public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
        return defaultSessionTrackingModes;
    }


    private void populateSessionTrackingModes() {
        // URL re-writing is always enabled by default
        defaultSessionTrackingModes = EnumSet.of(SessionTrackingMode.URL);
        supportedSessionTrackingModes = EnumSet.of(SessionTrackingMode.URL);

        if (context.getCookies()) {
            defaultSessionTrackingModes.add(SessionTrackingMode.COOKIE);
            supportedSessionTrackingModes.add(SessionTrackingMode.COOKIE);
        }

        // SSL not enabled by default as it can only used on its own
        // Context > Host > Engine > Service
        org.apache.catalina.connector.Connector[] connectors = service.findConnectors();
        // Need at least one SSL enabled connector to use the SSL session ID.
        for (Connector connector : connectors) {
            if (Boolean.TRUE.equals(connector.getProperty("SSLEnabled"))) {
                supportedSessionTrackingModes.add(SessionTrackingMode.SSL);
                break;
            }
        }
    }


    @Override
    public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
        if (sessionTrackingModes != null) {
            return sessionTrackingModes;
        }
        return defaultSessionTrackingModes;
    }


    @Override
    public SessionCookieConfig getSessionCookieConfig() {
        return sessionCookieConfig;
    }


    @Override
    public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {

        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(
                    sm.getString("applicationContext.setSessionTracking.ise",
                            getContextPath()));
        }

        // Check that only supported tracking modes have been requested
        for (SessionTrackingMode sessionTrackingMode : sessionTrackingModes) {
            if (!supportedSessionTrackingModes.contains(sessionTrackingMode)) {
                throw new IllegalArgumentException(sm.getString(
                        "applicationContext.setSessionTracking.iae.invalid",
                        sessionTrackingMode.toString(), getContextPath()));
            }
        }

        // Check SSL has not be configured with anything else
        if (sessionTrackingModes.contains(SessionTrackingMode.SSL)) {
            if (sessionTrackingModes.size() > 1) {
                throw new IllegalArgumentException(sm.getString(
                        "applicationContext.setSessionTracking.iae.ssl",
                        getContextPath()));
            }
        }

        this.sessionTrackingModes = sessionTrackingModes;
    }


    @Override
    public boolean setInitParameter(String name, String value) {
        // Name cannot be null
        if (name == null) {
            throw new NullPointerException(sm.getString("applicationContext.setAttribute.namenull"));
        }
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(
                    sm.getString("applicationContext.setInitParam.ise",
                            getContextPath()));
        }

        return parameters.putIfAbsent(name, value) == null;
    }


    @Override
    public void addListener(Class<? extends EventListener> listenerClass) {
        EventListener listener;
        try {
            listener = createListener(listenerClass);
        } catch (ServletException e) {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.init",
                    listenerClass.getName()), e);
        }
        addListener(listener);
    }


    @Override
    public void addListener(String className) {

        try {
            if (context.getInstanceManager() != null) {
                Object obj = context.getInstanceManager().newInstance(className);

                if (!(obj instanceof EventListener)) {
                    throw new IllegalArgumentException(sm.getString(
                            "applicationContext.addListener.iae.wrongType",
                            className));
                }

                EventListener listener = (EventListener) obj;
                addListener(listener);
            }
        } catch (InvocationTargetException e) {
            ExceptionUtils.handleThrowable(e.getCause());
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.cnfe", className),
                    e);
        } catch (ReflectiveOperationException | NamingException e) {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.cnfe", className),
                    e);
        }

    }


    @Override
    public <T extends EventListener> void addListener(T t) {
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(
                    sm.getString("applicationContext.addListener.ise",
                            getContextPath()));
        }

        boolean match = false;
        if (t instanceof ServletContextAttributeListener ||
                t instanceof ServletRequestListener ||
                t instanceof ServletRequestAttributeListener ||
                t instanceof HttpSessionIdListener ||
                t instanceof HttpSessionAttributeListener) {
            context.addApplicationEventListener(t);
            match = true;
        }

        if (t instanceof HttpSessionListener ||
                (t instanceof ServletContextListener && newServletContextListenerAllowed)) {
            // Add listener directly to the list of instances rather than to
            // the list of class names.
            context.addApplicationLifecycleListener(t);
            match = true;
        }

        if (match) return;

        if (t instanceof ServletContextListener) {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.sclNotAllowed",
                    t.getClass().getName()));
        } else {
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.wrongType",
                    t.getClass().getName()));
        }
    }


    @Override
    public <T extends EventListener> T createListener(Class<T> c)
            throws ServletException {
        try {
            @SuppressWarnings("unchecked")
            T listener = (T) context.getInstanceManager().newInstance(c);
            if (listener instanceof ServletContextListener ||
                    listener instanceof ServletContextAttributeListener ||
                    listener instanceof ServletRequestListener ||
                    listener instanceof ServletRequestAttributeListener ||
                    listener instanceof HttpSessionListener ||
                    listener instanceof HttpSessionIdListener ||
                    listener instanceof HttpSessionAttributeListener) {
                return listener;
            }
            throw new IllegalArgumentException(sm.getString(
                    "applicationContext.addListener.iae.wrongType",
                    listener.getClass().getName()));
        } catch (InvocationTargetException e) {
            ExceptionUtils.handleThrowable(e.getCause());
            throw new ServletException(e);
        } catch (ReflectiveOperationException | NamingException e) {
            throw new ServletException(e);
        }
    }


    @Override
    public void declareRoles(String... roleNames) {

        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            //TODO Spec breaking enhancement to ignore this restriction
            throw new IllegalStateException(
                    sm.getString("applicationContext.addRole.ise",
                            getContextPath()));
        }

        if (roleNames == null) {
            throw new IllegalArgumentException(
                    sm.getString("applicationContext.roles.iae",
                            getContextPath()));
        }

        for (String role : roleNames) {
            if (role == null || "".equals(role)) {
                throw new IllegalArgumentException(
                        sm.getString("applicationContext.role.iae",
                                getContextPath()));
            }
            context.addSecurityRole(role);
        }
    }


    @Override
    public ClassLoader getClassLoader() {
        ClassLoader result = context.getLoader().getClassLoader();
        if (Globals.IS_SECURITY_ENABLED) {
            ClassLoader tccl = Thread.currentThread().getContextClassLoader();
            ClassLoader parent = result;
            while (parent != null) {
                if (parent == tccl) {
                    break;
                }
                parent = parent.getParent();
            }
            if (parent == null) {
                System.getSecurityManager().checkPermission(
                        new RuntimePermission("getClassLoader"));
            }
        }

        return result;
    }


    @Override
    public int getEffectiveMajorVersion() {
        return context.getEffectiveMajorVersion();
    }


    @Override
    public int getEffectiveMinorVersion() {
        return context.getEffectiveMinorVersion();
    }


    @Override
    public Map<String, ? extends FilterRegistration> getFilterRegistrations() {
        Map<String, ApplicationFilterRegistration> result = new HashMap<>();

        FilterDef[] filterDefs = context.findFilterDefs();
        for (FilterDef filterDef : filterDefs) {
            result.put(filterDef.getFilterName(),
                    new ApplicationFilterRegistration(filterDef, context));
        }

        return result;
    }


    @Override
    public JspConfigDescriptor getJspConfigDescriptor() {
        return context.getJspConfigDescriptor();
    }


    @Override
    public Map<String, ? extends ServletRegistration> getServletRegistrations() {
        Map<String, ApplicationServletRegistration> result = new HashMap<>();

        Container[] wrappers = context.findChildren();
        for (Container wrapper : wrappers) {
            result.put(wrapper.getName(),
                    new ApplicationServletRegistration(
                            (Wrapper) wrapper, context));
        }

        return result;
    }


    @Override
    public String getVirtualServerName() {
        // Constructor will fail if context or its parent is null
        Container host = context.getParent();
        Container engine = host.getParent();
        return engine.getName() + "/" + host.getName();
    }


    @Override
    public int getSessionTimeout() {
        return context.getSessionTimeout();
    }


    @Override
    public void setSessionTimeout(int sessionTimeout) {
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(
                    sm.getString("applicationContext.setSessionTimeout.ise",
                            getContextPath()));
        }

        context.setSessionTimeout(sessionTimeout);
    }


    @Override
    public String getRequestCharacterEncoding() {
        return context.getRequestCharacterEncoding();
    }


    @Override
    public void setRequestCharacterEncoding(String encoding) {
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(
                    sm.getString("applicationContext.setRequestEncoding.ise",
                            getContextPath()));
        }

        context.setRequestCharacterEncoding(encoding);
    }


    @Override
    public String getResponseCharacterEncoding() {
        return context.getResponseCharacterEncoding();
    }


    @Override
    public void setResponseCharacterEncoding(String encoding) {
        if (!context.getState().equals(LifecycleState.STARTING_PREP)) {
            throw new IllegalStateException(
                    sm.getString("applicationContext.setResponseEncoding.ise",
                            getContextPath()));
        }

        context.setResponseCharacterEncoding(encoding);
    }


    // -------------------------------------------------------- Package Methods
    protected StandardContext getContext() {
        return this.context;
    }

    /**
     * Clear all application-created attributes.
     */
    protected void clearAttributes() {

        // Create list of attributes to be removed
        List<String> list = new ArrayList<>(attributes.keySet());

        // Remove application originated attributes
        // (read only attributes will be left in place)
        for (String key : list) {
            removeAttribute(key);
        }

    }


    /**
     * @return the facade associated with this ApplicationContext.
     */
    protected ServletContext getFacade() {
        return this.facade;
    }


    /**
     * Set an attribute as read only.
     */
    void setAttributeReadOnly(String name) {

        if (attributes.containsKey(name))
            readOnlyAttributes.put(name, name);

    }


    protected void setNewServletContextListenerAllowed(boolean allowed) {
        this.newServletContextListenerAllowed = allowed;
    }

    /**
     * Internal class used as thread-local storage when doing path
     * mapping during dispatch.
     */
    private static final class DispatchData {

        public MessageBytes uriMB;
        public MappingData mappingData;

        public DispatchData() {
            uriMB = MessageBytes.newInstance();
            CharChunk uriCC = uriMB.getCharChunk();
            uriCC.setLimit(-1);
            mappingData = new MappingData();
        }
    }
}

/**
 * Standard implementation of <code>ServletContext</code> that represents
 * a web application's execution environment.  An instance of this class is
 * associated with each instance of <code>StandardContext</code>.
 *
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 * @version $Revision: 1.45 $ $Date: 2004/08/26 21:31:21 $
 * <p>
 * Construct a new instance of this class, associated with the specified
 * Context instance.
 * @param context The associated Context instance
 * <p>
 * The context attributes for this context.
 * <p>
 * List of read only attributes for this context.
 * <p>
 * The Context instance with which we are associated.
 * <p>
 * Empty collection to serve as the basis for empty enumerations.
 * <strong>DO NOT ADD ANY ELEMENTS TO THIS COLLECTION!</strong>
 * <p>
 * The facade around this object.
 * <p>
 * The merged context initialization parameters for this Context.
 * <p>
 * The string manager for this package.
 * <p>
 * Base path.
 * <p>
 * Clear all application-created attributes.
 * <p>
 * Return the resources object that is mapped to a specified path.
 * The path must begin with a "/" and is interpreted as relative to the
 * current context root.
 * <p>
 * Set an attribute as read only.
 * <p>
 * Return the value of the specified context attribute, if any;
 * otherwise return <code>null</code>.
 * @param name Name of the context attribute to return
 * <p>
 * Return an enumeration of the names of the context attributes
 * associated with this context.
 * <p>
 * Return a <code>ServletContext</code> object that corresponds to a
 * specified URI on the server.  This method allows servlets to gain
 * access to the context for various parts of the server, and as needed
 * obtain <code>RequestDispatcher</code> objects or resources from the
 * context.  The given path must be absolute (beginning with a "/"),
 * and is interpreted based on our virtual host's document root.
 * @param uri Absolute URI of a resource on the server
 * <p>
 * Return the value of the specified initialization parameter, or
 * <code>null</code> if this parameter does not exist.
 * @param name Name of the initialization parameter to retrieve
 * <p>
 * Return the names of the context's initialization parameters, or an
 * empty enumeration if the context has no initialization parameters.
 * <p>
 * Return the major version of the Java Servlet API that we implement.
 * <p>
 * Return the minor version of the Java Servlet API that we implement.
 * <p>
 * Return the MIME type of the specified file, or <code>null</code> if
 * the MIME type cannot be determined.
 * @param file Filename for which to identify a MIME type
 * <p>
 * Return a <code>RequestDispatcher</code> object that acts as a
 * wrapper for the named servlet.
 * @param name Name of the servlet for which a dispatcher is requested
 * <p>
 * Return the real path for a given virtual path, if possible; otherwise
 * return <code>null</code>.
 * @param path The path to the desired resource
 * <p>
 * Return a <code>RequestDispatcher</code> instance that acts as a
 * wrapper for the resource at the given path.  The path must begin
 * with a "/" and is interpreted as relative to the current context root.
 * @param path The path to the desired resource.
 * <p>
 * Return the URL to the resource that is mapped to a specified path.
 * The path must begin with a "/" and is interpreted as relative to the
 * current context root.
 * @param path The path to the desired resource
 * @exception MalformedURLException if the path is not given
 * in the correct form
 * <p>
 * Return the requested resource as an <code>InputStream</code>.  The
 * path must be specified according to the rules described under
 * <code>getResource</code>.  If no such resource can be identified,
 * return <code>null</code>.
 * @param path The path to the desired resource.
 * <p>
 * <p>
 * Return a Set containing the resource paths of resources member of the
 * specified collection. Each path will be a String starting with
 * a "/" character. The returned set is immutable.
 * @param path Collection path
 * <p>
 * Internal implementation of getResourcesPath() logic.
 * @param resources Directory context to search
 * @param path Collection path
 * <p>
 * Return the name and version of the servlet container.
 * @param message Message to be written
 * <p>
 * Writes the specified exception and message to a servlet log file.
 * @param exception Exception to be reported
 * @param message Message to be written
 * @param message Message to be written
 * @param throwable Exception to be reported
 * <p>
 * Remove the context attribute with the specified name, if any.
 * @param name Name of the context attribute to be removed
 * <p>
 * Bind the specified value with the specified context attribute name,
 * replacing any existing value for that name.
 * @param name Attribute name to be bound
 * @param value New attribute value to be bound
 * <p>
 * Return the facade associated with this ApplicationContext.
 * <p>
 * Return a context-relative path, beginning with a "/", that represents
 * the canonical version of the specified path after ".." and "." elements
 * are resolved out.  If the specified path attempts to go outside the
 * boundaries of the current context (i.e. too many ".." path elements
 * are present), return <code>null</code> instead.
 * @param path Path to be normalized
 * <p>
 * Merge the context initialization parameters specified in the application
 * deployment descriptor with the application parameters described in the
 * server configuration, respecting the <code>override</code> property of
 * the application parameters appropriately.
 * <p>
 * List resource paths (recursively), and store all of them in the given
 * Set.
 * <p>
 * List resource paths (recursively), and store all of them in the given
 * Set.
 * <p>
 * Get full path, based on the host name and the context path.
 * @param context The associated Context instance
 * <p>
 * The context attributes for this context.
 * <p>
 * List of read only attributes for this context.
 * <p>
 * The Context instance with which we are associated.
 * <p>
 * Empty collection to serve as the basis for empty enumerations.
 * <strong>DO NOT ADD ANY ELEMENTS TO THIS COLLECTION!</strong>
 * <p>
 * The facade around this object.
 * <p>
 * The merged context initialization parameters for this Context.
 * <p>
 * The string manager for this package.
 * <p>
 * Base path.
 * <p>
 * Clear all application-created attributes.
 * <p>
 * Return the resources object that is mapped to a specified path.
 * The path must begin with a "/" and is interpreted as relative to the
 * current context root.
 * <p>
 * Set an attribute as read only.
 * <p>
 * Return the value of the specified context attribute, if any;
 * otherwise return <code>null</code>.
 * @param name Name of the context attribute to return
 * <p>
 * Return an enumeration of the names of the context attributes
 * associated with this context.
 * <p>
 * Return a <code>ServletContext</code> object that corresponds to a
 * specified URI on the server.  This method allows servlets to gain
 * access to the context for various parts of the server, and as needed
 * obtain <code>RequestDispatcher</code> objects or resources from the
 * context.  The given path must be absolute (beginning with a "/"),
 * and is interpreted based on our virtual host's document root.
 * @param uri Absolute URI of a resource on the server
 * <p>
 * Return the value of the specified initialization parameter, or
 * <code>null</code> if this parameter does not exist.
 * @param name Name of the initialization parameter to retrieve
 * <p>
 * Return the names of the context's initialization parameters, or an
 * empty enumeration if the context has no initialization parameters.
 * <p>
 * Return the major version of the Java Servlet API that we implement.
 * <p>
 * Return the minor version of the Java Servlet API that we implement.
 * <p>
 * Return the MIME type of the specified file, or <code>null</code> if
 * the MIME type cannot be determined.
 * @param file Filename for which to identify a MIME type
 * <p>
 * Return a <code>RequestDispatcher</code> object that acts as a
 * wrapper for the named servlet.
 * @param name Name of the servlet for which a dispatcher is requested
 * <p>
 * Return the real path for a given virtual path, if possible; otherwise
 * return <code>null</code>.
 * @param path The path to the desired resource
 * <p>
 * Return a <code>RequestDispatcher</code> instance that acts as a
 * wrapper for the resource at the given path.  The path must begin
 * with a "/" and is interpreted as relative to the current context root.
 * @param path The path to the desired resource.
 * <p>
 * Return the URL to the resource that is mapped to a specified path.
 * The path must begin with a "/" and is interpreted as relative to the
 * current context root.
 * @param path The path to the desired resource
 * @exception MalformedURLException if the path is not given
 * in the correct form
 * <p>
 * Return the requested resource as an <code>InputStream</code>.  The
 * path must be specified according to the rules described under
 * <code>getResource</code>.  If no such resource can be identified,
 * return <code>null</code>.
 * @param path The path to the desired resource.
 * <p>
 * <p>
 * Return a Set containing the resource paths of resources member of the
 * specified collection. Each path will be a String starting with
 * a "/" character. The returned set is immutable.
 * @param path Collection path
 * <p>
 * Internal implementation of getResourcesPath() logic.
 * @param resources Directory context to search
 * @param path Collection path
 * <p>
 * Return the name and version of the servlet container.
 * @param message Message to be written
 * <p>
 * Writes the specified exception and message to a servlet log file.
 * @param exception Exception to be reported
 * @param message Message to be written
 * @param message Message to be written
 * @param throwable Exception to be reported
 * <p>
 * Remove the context attribute with the specified name, if any.
 * @param name Name of the context attribute to be removed
 * <p>
 * Bind the specified value with the specified context attribute name,
 * replacing any existing value for that name.
 * @param name Attribute name to be bound
 * @param value New attribute value to be bound
 * <p>
 * Return the facade associated with this ApplicationContext.
 * <p>
 * Return a context-relative path, beginning with a "/", that represents
 * the canonical version of the specified path after ".." and "." elements
 * are resolved out.  If the specified path attempts to go outside the
 * boundaries of the current context (i.e. too many ".." path elements
 * are present), return <code>null</code> instead.
 * @param path Path to be normalized
 * <p>
 * Merge the context initialization parameters specified in the application
 * deployment descriptor with the application parameters described in the
 * server configuration, respecting the <code>override</code> property of
 * the application parameters appropriately.
 * <p>
 * List resource paths (recursively), and store all of them in the given
 * Set.
 * <p>
 * List resource paths (recursively), and store all of them in the given
 * Set.
 * <p>
 * Get full path, based on the host name and the context path.
 * @param context The associated Context instance
 * <p>
 * The context attributes for this context.
 * <p>
 * List of read only attributes for this context.
 * <p>
 * The Context instance with which we are associated.
 * <p>
 * Empty collection to serve as the basis for empty enumerations.
 * <strong>DO NOT ADD ANY ELEMENTS TO THIS COLLECTION!</strong>
 * <p>
 * The facade around this object.
 * <p>
 * The merged context initialization parameters for this Context.
 * <p>
 * The string manager for this package.
 * <p>
 * Base path.
 * <p>
 * Clear all application-created attributes.
 * <p>
 * Return the resources object that is mapped to a specified path.
 * The path must begin with a "/" and is interpreted as relative to the
 * current context root.
 * <p>
 * Set an attribute as read only.
 * <p>
 * Return the value of the specified context attribute, if any;
 * otherwise return <code>null</code>.
 * @param name Name of the context attribute to return
 * <p>
 * Return an enumeration of the names of the context attributes
 * associated with this context.
 * <p>
 * Return a <code>ServletContext</code> object that corresponds to a
 * specified URI on the server.  This method allows servlets to gain
 * access to the context for various parts of the server, and as needed
 * obtain <code>RequestDispatcher</code> objects or resources from the
 * context.  The given path must be absolute (beginning with a "/"),
 * and is interpreted based on our virtual host's document root.
 * @param uri Absolute URI of a resource on the server
 * <p>
 * Return the value of the specified initialization parameter, or
 * <code>null</code> if this parameter does not exist.
 * @param name Name of the initialization parameter to retrieve
 * <p>
 * Return the names of the context's initialization parameters, or an
 * empty enumeration if the context has no initialization parameters.
 * <p>
 * Return the major version of the Java Servlet API that we implement.
 * <p>
 * Return the minor version of the Java Servlet API that we implement.
 * <p>
 * Return the MIME type of the specified file, or <code>null</code> if
 * the MIME type cannot be determined.
 * @param file Filename for which to identify a MIME type
 * <p>
 * Return a <code>RequestDispatcher</code> object that acts as a
 * wrapper for the named servlet.
 * @param name Name of the servlet for which a dispatcher is requested
 * <p>
 * Return the real path for a given virtual path, if possible; otherwise
 * return <code>null</code>.
 * @param path The path to the desired resource
 * <p>
 * Return a <code>RequestDispatcher</code> instance that acts as a
 * wrapper for the resource at the given path.  The path must begin
 * with a "/" and is interpreted as relative to the current context root.
 * @param path The path to the desired resource.
 * <p>
 * Return the URL to the resource that is mapped to a specified path.
 * The path must begin with a "/" and is interpreted as relative to the
 * current context root.
 * @param path The path to the desired resource
 * @exception MalformedURLException if the path is not given
 * in the correct form
 * <p>
 * Return the requested resource as an <code>InputStream</code>.  The
 * path must be specified according to the rules described under
 * <code>getResource</code>.  If no such resource can be identified,
 * return <code>null</code>.
 * @param path The path to the desired resource.
 * <p>
 * <p>
 * Return a Set containing the resource paths of resources member of the
 * specified collection. Each path will be a String starting with
 * a "/" character. The returned set is immutable.
 * @param path Collection path
 * <p>
 * Internal implementation of getResourcesPath() logic.
 * @param resources Directory context to search
 * @param path Collection path
 * <p>
 * Return the name and version of the servlet container.
 * @param message Message to be written
 * <p>
 * Writes the specified exception and message to a servlet log file.
 * @param exception Exception to be reported
 * @param message Message to be written
 * @param message Message to be written
 * @param throwable Exception to be reported
 * <p>
 * Remove the context attribute with the specified name, if any.
 * @param name Name of the context attribute to be removed
 * <p>
 * Bind the specified value with the specified context attribute name,
 * replacing any existing value for that name.
 * @param name Attribute name to be bound
 * @param value New attribute value to be bound
 * <p>
 * Return the facade associated with this ApplicationContext.
 * <p>
 * Return a context-relative path, beginning with a "/", that represents
 * the canonical version of the specified path after ".." and "." elements
 * are resolved out.  If the specified path attempts to go outside the
 * boundaries of the current context (i.e. too many ".." path elements
 * are present), return <code>null</code> instead.
 * @param path Path to be normalized
 * <p>
 * Merge the context initialization parameters specified in the application
 * deployment descriptor with the application parameters described in the
 * server configuration, respecting the <code>override</code> property of
 * the application parameters appropriately.
 * <p>
 * List resource paths (recursively), and store all of them in the given
 * Set.
 * <p>
 * List resource paths (recursively), and store all of them in the given
 * Set.
 * <p>
 * Get full path, based on the host name and the context path.
 * @deprecated As of Java Servlet API 2.1, use
 * <code>log(String, Throwable)</code> instead
 * <p>
 * Writes the specified message and exception to a servlet log file.
 *//*


public class ApplicationContext implements ServletContext {

    protected class PrivilegedGetInitParameter implements PrivilegedAction {

        private String name;
        
        PrivilegedGetInitParameter(String name){
            this.name = name;
        }
                
        public Object run(){
            return ((String) parameters.get(name));
        }
    }


    protected class PrivilegedGetInitParameterNames
        implements PrivilegedAction {

            PrivilegedGetInitParameterNames(){
        }
   
        public Object run() {
            return (new Enumerator(parameters.keySet()));
        }
    }        


    protected class PrivilegedGetNamedDispatcher
        implements PrivilegedAction {

            private Wrapper wrapper;
            private String name;

            PrivilegedGetNamedDispatcher(Wrapper wrapper, String name) {
            this.wrapper = wrapper;
            this.name = name;
        }
        public Object run() {
            return new ApplicationDispatcher(wrapper, null, null, null, name);
        }
    }


    protected class PrivilegedGetRequestDispatcher
        implements PrivilegedAction {

        private String contextPath;
        private String relativeURI;
        private String queryString;

        PrivilegedGetRequestDispatcher(String contextPath, String relativeURI,
                                       String queryString) {
            this.contextPath = contextPath;
            this.relativeURI = relativeURI;
            this.queryString = queryString;
        }

        public Object run() {
            HttpRequest request = new MappingRequest
                (context.getPath(), contextPath + relativeURI, queryString);
            */
/*
            HttpRequestBase request = new HttpRequestBase();
            request.setContext(context);
            request.setContextPath(context.getPath());
            request.setRequestURI(contextPath + relativeURI);
            request.setQueryString(queryString);
            *//*

            Wrapper wrapper = (Wrapper) context.map(request, true);
            if (wrapper == null)
                return (null);

            // Construct a RequestDispatcher to process this request
            HttpServletRequest hrequest =
                (HttpServletRequest) request.getRequest();
            return (RequestDispatcher) new ApplicationDispatcher
                (wrapper,
                 hrequest.getServletPath(),
                 hrequest.getPathInfo(),
                 hrequest.getQueryString(),
                 null);
        }

    }



    protected class PrivilegedGetResource
        implements PrivilegedExceptionAction {

        private String path;
        private String host;
        private DirContext resources;

        PrivilegedGetResource(String host, String path, DirContext resources) {
            this.host = host;
            this.path = path;
            this.resources = resources;
        }

        public Object run() throws Exception {
            return new URL("jndi", null, 0, getJNDIUri(host, path),
                           new DirContextURLStreamHandler(resources));
        }
    }


    protected class PrivilegedGetResourcePaths
        implements PrivilegedAction {

        private String path;
        private DirContext resources;

        PrivilegedGetResourcePaths(DirContext resources, String path) {
            this.resources = resources;
            this.path = path;
        }

        public Object run() {
            return (getResourcePathsInternal(resources, path));
        }

    }


    protected class PrivilegedLogMessage
        implements PrivilegedAction {

        private String message;

        PrivilegedLogMessage(String message) {
            this.message = message;
        }

        public Object run() {
            internalLog(message);
            return null;
        }

    }

    protected class PrivilegedLogException
        implements PrivilegedAction {

        private String message;
        private Exception exception;

        PrivilegedLogException(Exception exception,String message) {
            this.message = message;
            this.exception = exception;
        }

        public Object run() {
            internalLog(exception,message);
            return null;
        }

    }

    protected class PrivilegedLogThrowable
        implements PrivilegedAction {


        private String message;
        private Throwable throwable;

        PrivilegedLogThrowable(String message,Throwable throwable) {
            this.message = message;
            this.throwable = throwable;
        }

        public Object run() {
            internalLog(message,throwable);
            return null;
        }

    }


    // ----------------------------------------------------------- Constructors


    */
/**
 * Construct a new instance of this class, associated with the specified
 * Context instance.
 *
 * @param context The associated Context instance
 *//*

    public ApplicationContext(String basePath, StandardContext context) {
        super();
        this.context = context;
        this.basePath = basePath;
    }


    // ----------------------------------------------------- Instance Variables


    */
/**
 * The context attributes for this context.
 *//*

    private HashMap attributes = new HashMap();


    */
/**
 * List of read only attributes for this context.
 *//*

    private HashMap readOnlyAttributes = new HashMap();


    */
/**
 * The Context instance with which we are associated.
 *//*

    private StandardContext context = null;


    */
/**
 * Empty collection to serve as the basis for empty enumerations.
 * <strong>DO NOT ADD ANY ELEMENTS TO THIS COLLECTION!</strong>
 *//*

    private static final ArrayList empty = new ArrayList();


    */
/**
 * The facade around this object.
 *//*

    private ServletContext facade = new ApplicationContextFacade(this);


    */
/**
 * The merged context initialization parameters for this Context.
 *//*

    private HashMap parameters = null;


    */
/**
 * The string manager for this package.
 *//*

    private static final StringManager sm =
      StringManager.getManager(Constants.Package);


    */
/**
 * Base path.
 *//*

    private String basePath = null;


    // --------------------------------------------------------- Public Methods


    */
/**
 * Clear all application-created attributes.
 *//*

    public void clearAttributes() {

        // Create list of attributes to be removed
        ArrayList list = new ArrayList();
        synchronized (attributes) {
            Iterator iter = attributes.keySet().iterator();
            while (iter.hasNext()) {
                list.add(iter.next());
            }
        }

        // Remove application originated attributes
        // (read only attributes will be left in place)
        Iterator keys = list.iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            removeAttribute(key);
        }


    }


    */
/**
 * Return the resources object that is mapped to a specified path.
 * The path must begin with a "/" and is interpreted as relative to the
 * current context root.
 *//*

    public DirContext getResources() {

        return context.getResources();

    }


    */
/**
 * Set an attribute as read only.
 *//*

    public void setAttributeReadOnly(String name) {

        synchronized (attributes) {
            if (attributes.containsKey(name))
                readOnlyAttributes.put(name, name);
        }

    }


    // ------------------------------------------------- ServletContext Methods


    */
/**
 * Return the value of the specified context attribute, if any;
 * otherwise return <code>null</code>.
 *
 * @param name Name of the context attribute to return
 *//*

    public Object getAttribute(String name) {

        synchronized (attributes) {
            return (attributes.get(name));
        }

    }


    */
/**
 * Return an enumeration of the names of the context attributes
 * associated with this context.
 *//*

    public Enumeration getAttributeNames() {

        synchronized (attributes) {
            return (new Enumerator(attributes.keySet()));
        }

    }


    */
/**
 * Return a <code>ServletContext</code> object that corresponds to a
 * specified URI on the server.  This method allows servlets to gain
 * access to the context for various parts of the server, and as needed
 * obtain <code>RequestDispatcher</code> objects or resources from the
 * context.  The given path must be absolute (beginning with a "/"),
 * and is interpreted based on our virtual host's document root.
 *
 * @param uri Absolute URI of a resource on the server
 *//*

    public ServletContext getContext(String uri) {

        // Validate the format of the specified argument
        if ((uri == null) || (!uri.startsWith("/")))
            return (null);

        // Return the current context if requested
        String contextPath = context.getPath();
        if (!contextPath.endsWith("/"))
            contextPath = contextPath + "/";

        if (((contextPath.length() > 1) && (uri.startsWith(contextPath))) ||
            ((contextPath.equals("/")) && (uri.equals("/")))) {
            return (this);
        }

        // Return other contexts only if allowed
        if (!context.getCrossContext())
            return (null);
        try {
            Host host = (Host) context.getParent();
            Context child = host.map(uri);
            if (child != null)
                return (child.getServletContext());
            else
                return (null);
        } catch (Throwable t) {
            return (null);
        }

    }


    */
/**
 * Return the value of the specified initialization parameter, or
 * <code>null</code> if this parameter does not exist.
 *
 * @param name Name of the initialization parameter to retrieve
 *//*

    public String getInitParameter(final String name) {
        mergeParameters();
        synchronized (parameters) {
            if (System.getSecurityManager() != null){
                PrivilegedGetInitParameter ip =
                    new PrivilegedGetInitParameter(name);
                return (String)AccessController.doPrivileged(ip);
            } else {
                return ((String) parameters.get(name));
            }                   
        }
    }


    */
/**
 * Return the names of the context's initialization parameters, or an
 * empty enumeration if the context has no initialization parameters.
 *//*

    public Enumeration getInitParameterNames() {
        mergeParameters();
        synchronized (parameters) {
            if (System.getSecurityManager() != null){
                PrivilegedGetInitParameterNames pn =
                    new PrivilegedGetInitParameterNames();
                return (Enumeration)AccessController.doPrivileged(pn);
            } else {
                return (new Enumerator(parameters.keySet()));
            }
        }
    }


    */
/**
 * Return the major version of the Java Servlet API that we implement.
 *//*

    public int getMajorVersion() {

        return (Constants.MAJOR_VERSION);

    }


    */
/**
 * Return the minor version of the Java Servlet API that we implement.
 *//*

    public int getMinorVersion() {

        return (Constants.MINOR_VERSION);

    }


    */
/**
 * Return the MIME type of the specified file, or <code>null</code> if
 * the MIME type cannot be determined.
 *
 * @param file Filename for which to identify a MIME type
 *//*

    public String getMimeType(String file) {

        if (file == null)
            return (null);
        int period = file.lastIndexOf(".");
        if (period < 0)
            return (null);
        String extension = file.substring(period + 1);
        if (extension.length() < 1)
            return (null);
        return (context.findMimeMapping(extension));

    }


    */
/**
 * Return a <code>RequestDispatcher</code> object that acts as a
 * wrapper for the named servlet.
 *
 * @param name Name of the servlet for which a dispatcher is requested
 *//*

    public RequestDispatcher getNamedDispatcher(String name) {

        // Validate the name argument
        if (name == null)
            return (null);

        // Create and return a corresponding request dispatcher
        Wrapper wrapper = (Wrapper) context.findChild(name);
        if (wrapper == null)
            return (null);

        ApplicationDispatcher dispatcher;
        if (System.getSecurityManager() != null){
            PrivilegedGetNamedDispatcher nd = 
                new PrivilegedGetNamedDispatcher(wrapper, name);
            dispatcher = (ApplicationDispatcher)AccessController.doPrivileged(nd);
        } else {
            dispatcher =
                new ApplicationDispatcher(wrapper, null, null, null, name);
        }

        return ((RequestDispatcher) dispatcher);
    }


    */
/**
 * Return the real path for a given virtual path, if possible; otherwise
 * return <code>null</code>.
 *
 * @param path The path to the desired resource
 *//*

    public String getRealPath(String path) {

        if (!context.isFilesystemBased())
            return null;

        File file = new File(basePath, path);
        return (file.getAbsolutePath());

    }


    */
/**
 * Return a <code>RequestDispatcher</code> instance that acts as a
 * wrapper for the resource at the given path.  The path must begin
 * with a "/" and is interpreted as relative to the current context root.
 *
 * @param path The path to the desired resource.
 *//*

    public RequestDispatcher getRequestDispatcher(String path) {

        // Validate the path argument
        if (path == null)
            return (null);
        if (!path.startsWith("/"))
            throw new IllegalArgumentException
              (sm.getString("applicationContext.requestDispatcher.iae", path));
        path = normalize(path);
        if (path == null)
            return (null);

        // Construct a "fake" request to be mapped by our Context
        String contextPath = context.getPath();
        if (contextPath == null)
            contextPath = "";
        String relativeURI = path;
        String queryString = null;
        int question = path.indexOf('?');
        if (question >= 0) {
            relativeURI = path.substring(0, question);
            queryString = path.substring(question + 1);
        }
        if( System.getSecurityManager() != null ) {
            PrivilegedGetRequestDispatcher dp =
                new PrivilegedGetRequestDispatcher(contextPath,
                        relativeURI,queryString);
            return (RequestDispatcher)AccessController.doPrivileged(dp);
        }

        // The remaining code is duplicated in PrivilegedGetRequestDispatcher,
        // we need to make sure they stay in sync
        HttpRequest request = new MappingRequest
            (context.getPath(), contextPath + relativeURI, queryString);
        */
/*
        request.setContext(context);
        request.setContextPath(context.getPath());
        request.setRequestURI(contextPath + relativeURI);
        request.setQueryString(queryString);
        *//*

        Wrapper wrapper = (Wrapper) context.map(request, true);
        if (wrapper == null)
            return (null);

        // Construct a RequestDispatcher to process this request
        HttpServletRequest hrequest =
            (HttpServletRequest) request.getRequest();
        return (RequestDispatcher) new ApplicationDispatcher(wrapper,
                        hrequest.getServletPath(),
                        hrequest.getPathInfo(),
                        hrequest.getQueryString(),
                        null);

    }



    */
/**
 * Return the URL to the resource that is mapped to a specified path.
 * The path must begin with a "/" and is interpreted as relative to the
 * current context root.
 *
 * @param path The path to the desired resource
 *
 * @exception MalformedURLException if the path is not given
 *  in the correct form
 *//*

    public URL getResource(String path)
        throws MalformedURLException {

        path = normalize(path);
        if (path == null)
            return (null);

        DirContext resources = context.getResources();
        if (resources != null) {
            String fullPath = context.getName() + path;
            String hostName = context.getParent().getName();
            try {
                resources.lookup(path);
                if( System.getSecurityManager() != null ) {
                    try {
                        PrivilegedGetResource dp =
                            new PrivilegedGetResource
                                (hostName, fullPath, resources);
                        return (URL)AccessController.doPrivileged(dp);
                    } catch( PrivilegedActionException pe) {
                        throw pe.getException();
                    }
                } else {
                    return new URL
                        ("jndi", null, 0, getJNDIUri(hostName, fullPath),
                         new DirContextURLStreamHandler(resources));
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return (null);

    }


    */
/**
 * Return the requested resource as an <code>InputStream</code>.  The
 * path must be specified according to the rules described under
 * <code>getResource</code>.  If no such resource can be identified,
 * return <code>null</code>.
 *
 * @param path The path to the desired resource.

 *//*

    public InputStream getResourceAsStream(String path) {

        path = normalize(path);
        if (path == null)
            return (null);

        DirContext resources = context.getResources();
        if (resources != null) {
            try {
                Object resource = resources.lookup(path);
                if (resource instanceof Resource)
                    return (((Resource) resource).streamContent());
            } catch (Exception e) {
            }
        }
        return (null);

    }


    */
/**
 * Return a Set containing the resource paths of resources member of the
 * specified collection. Each path will be a String starting with
 * a "/" character. The returned set is immutable.
 *
 * @param path Collection path
 *//*

    public Set getResourcePaths(String path) {

        DirContext resources = context.getResources();
        if (resources != null) {
            if (System.getSecurityManager() != null) {
                PrivilegedAction dp =
                    new PrivilegedGetResourcePaths(resources, path);
                return ((Set) AccessController.doPrivileged(dp));
            } else {
                return (getResourcePathsInternal(resources, path));
            }
        }
        return (null);

    }


    */
/**
 * Internal implementation of getResourcesPath() logic.
 *
 * @param resources Directory context to search
 * @param path Collection path
 *//*

    private Set getResourcePathsInternal(DirContext resources, String path) {

        ResourceSet set = new ResourceSet();
        try {
            listCollectionPaths(set, resources, path);
        } catch (NamingException e) {
            return (null);
        }
        set.setLocked(true);
        return (set);

    }


    */
/**
 * Return the name and version of the servlet container.
 *//*

    public String getServerInfo() {

        return (ServerInfo.getServerInfo());

    }


    */
/**
 * @deprecated As of Java Servlet API 2.1, with no direct replacement.
 *//*

    public Servlet getServlet(String name) {

        return (null);

    }


    */
/**
 * Return the display name of this web application.
 *//*

    public String getServletContextName() {

        return (context.getDisplayName());

    }


    */
/**
 * @deprecated As of Java Servlet API 2.1, with no direct replacement.
 *//*

    public Enumeration getServletNames() {
        if (System.getSecurityManager() != null){
            return (Enumeration)AccessController.doPrivileged(
                new PrivilegedAction(){

                    public Object run(){
                        return (new Enumerator(empty)); 
                    }
                }
            );
        } else {
            return (new Enumerator(empty));
        }
    }


    */
/**
 * @deprecated As of Java Servlet API 2.1, with no direct replacement.
 *//*

    public Enumeration getServlets() {
        if (System.getSecurityManager() != null){
            return (Enumeration)AccessController.doPrivileged(
                new PrivilegedAction(){

                    public Object run(){
                        return (new Enumerator(empty)); 
                    }
                }
            );
        } else {
            return (new Enumerator(empty));
        }        
    }


    */
/**
 * Writes the specified message to a servlet log file.
 *
 * @param message Message to be written
 *//*

    public void log(String message) {
        if( System.getSecurityManager() != null ) {
            PrivilegedLogMessage dp =
                new PrivilegedLogMessage(message);
            AccessController.doPrivileged(dp);
        } else {
            internalLog(message);
        }
    }

    private void internalLog(String message) {

        Logger logger = context.getLogger();
        if (logger != null)
            logger.log(message);

    }


    */
/**
 * Writes the specified exception and message to a servlet log file.
 *
 * @param exception Exception to be reported
 * @param message Message to be written
 *
 * @deprecated As of Java Servlet API 2.1, use
 *  <code>log(String, Throwable)</code> instead
 *//*

    public void log(Exception exception, String message) {
        if( System.getSecurityManager() != null ) {
            PrivilegedLogException dp =
                new PrivilegedLogException(exception,message);
            AccessController.doPrivileged(dp);
        } else {
            internalLog(exception,message);
        }
    }

    private void internalLog(Exception exception, String message) {
        Logger logger = context.getLogger();
        if (logger != null)
            logger.log(exception, message);

    }


    */
/**
 * Writes the specified message and exception to a servlet log file.
 *
 * @param message Message to be written
 * @param throwable Exception to be reported
 *//*

    public void log(String message, Throwable throwable) {
        if( System.getSecurityManager() != null ) {
            PrivilegedLogThrowable dp =
                new PrivilegedLogThrowable(message,throwable);
            AccessController.doPrivileged(dp);
        } else {
            internalLog(message,throwable);
        }
    }

    private void internalLog(String message, Throwable throwable) {

        Logger logger = context.getLogger();
        if (logger != null)
            logger.log(message, throwable);

    }


    */
/**
 * Remove the context attribute with the specified name, if any.
 *
 * @param name Name of the context attribute to be removed
 *//*

    public void removeAttribute(String name) {

        Object value = null;
        boolean found = false;

        // Remove the specified attribute
        synchronized (attributes) {
            // Check for read only attribute
           if (readOnlyAttributes.containsKey(name))
                return;
            found = attributes.containsKey(name);
            if (found) {
                value = attributes.get(name);
                attributes.remove(name);
            } else {
                return;
            }
        }

        // Notify interested application event listeners
        Object listeners[] = context.getApplicationListeners();
        if ((listeners == null) || (listeners.length == 0))
            return;
        ServletContextAttributeEvent event =
          new ServletContextAttributeEvent(context.getServletContext(),
                                            name, value);
        for (int i = 0; i < listeners.length; i++) {
            if (!(listeners[i] instanceof ServletContextAttributeListener))
                continue;
            ServletContextAttributeListener listener =
                (ServletContextAttributeListener) listeners[i];
            try {
                context.fireContainerEvent("beforeContextAttributeRemoved",
                                           listener);
                listener.attributeRemoved(event);
                context.fireContainerEvent("afterContextAttributeRemoved",
                                           listener);
            } catch (Throwable t) {
                context.fireContainerEvent("afterContextAttributeRemoved",
                                           listener);
                // FIXME - should we do anything besides log these?
                log(sm.getString("applicationContext.attributeEvent"), t);
            }
        }

    }


    */
/**
 * Bind the specified value with the specified context attribute name,
 * replacing any existing value for that name.
 *
 * @param name Attribute name to be bound
 * @param value New attribute value to be bound
 *//*

    public void setAttribute(String name, Object value) {

        // Name cannot be null
        if (name == null)
            throw new IllegalArgumentException
                (sm.getString("applicationContext.setAttribute.namenull"));

        // Null value is the same as removeAttribute()
        if (value == null) {
            removeAttribute(name);
            return;
        }

        Object oldValue = null;
        boolean replaced = false;

        // Add or replace the specified attribute
        synchronized (attributes) {
            // Check for read only attribute
            if (readOnlyAttributes.containsKey(name))
                return;
            oldValue = attributes.get(name);
            if (oldValue != null)
                replaced = true;
            attributes.put(name, value);
        }

        // Notify interested application event listeners
        Object listeners[] = context.getApplicationListeners();
        if ((listeners == null) || (listeners.length == 0))
            return;
        ServletContextAttributeEvent event = null;
        if (replaced)
            event =
                new ServletContextAttributeEvent(context.getServletContext(),
                                                 name, oldValue);
        else
            event =
                new ServletContextAttributeEvent(context.getServletContext(),
                                                 name, value);

        for (int i = 0; i < listeners.length; i++) {
            if (!(listeners[i] instanceof ServletContextAttributeListener))
                continue;
            ServletContextAttributeListener listener =
                (ServletContextAttributeListener) listeners[i];
            try {
                if (replaced) {
                    context.fireContainerEvent
                        ("beforeContextAttributeReplaced", listener);
                    listener.attributeReplaced(event);
                    context.fireContainerEvent("afterContextAttributeReplaced",
                                               listener);
                } else {
                    context.fireContainerEvent("beforeContextAttributeAdded",
                                               listener);
                    listener.attributeAdded(event);
                    context.fireContainerEvent("afterContextAttributeAdded",
                                               listener);
                }
            } catch (Throwable t) {
                if (replaced)
                    context.fireContainerEvent("afterContextAttributeReplaced",
                                               listener);
                else
                    context.fireContainerEvent("afterContextAttributeAdded",
                                               listener);
                // FIXME - should we do anything besides log these?
                log(sm.getString("applicationContext.attributeEvent"), t);
            }
        }

    }


    // -------------------------------------------------------- Package Methods


    */
/**
 * Return the facade associated with this ApplicationContext.
 *//*

    ServletContext getFacade() {

        return (this.facade);

    }


    // -------------------------------------------------------- Private Methods


    */
/**
 * Return a context-relative path, beginning with a "/", that represents
 * the canonical version of the specified path after ".." and "." elements
 * are resolved out.  If the specified path attempts to go outside the
 * boundaries of the current context (i.e. too many ".." path elements
 * are present), return <code>null</code> instead.
 *
 * @param path Path to be normalized
 *//*

    private String normalize(String path) {

	String normalized = path;

	// Normalize the slashes and add leading slash if necessary
	if (normalized.indexOf('\\') >= 0)
	    normalized = normalized.replace('\\', '/');

	// Resolve occurrences of "/../" in the normalized path
	while (true) {
	    int index = normalized.indexOf("/../");
	    if (index < 0)
		break;
	    if (index == 0)
		return (null);	// Trying to go outside our context
	    int index2 = normalized.lastIndexOf('/', index - 1);
	    normalized = normalized.substring(0, index2) +
		normalized.substring(index + 3);
	}

	// Return the normalized path that we have completed
	return (normalized);

    }


    */
/**
 * Merge the context initialization parameters specified in the application
 * deployment descriptor with the application parameters described in the
 * server configuration, respecting the <code>override</code> property of
 * the application parameters appropriately.
 *//*

    private void mergeParameters() {

        if (parameters != null)
            return;
        HashMap results = new HashMap();
        String names[] = context.findParameters();
        for (int i = 0; i < names.length; i++)
            results.put(names[i], context.findParameter(names[i]));
        ApplicationParameter params[] =
            context.findApplicationParameters();
        for (int i = 0; i < params.length; i++) {
            if (params[i].getOverride()) {
                if (results.get(params[i].getName()) == null)
                    results.put(params[i].getName(), params[i].getValue());
            } else {
                results.put(params[i].getName(), params[i].getValue());
            }
        }
        parameters = results;

    }


    */
/**
 * List resource paths (recursively), and store all of them in the given
 * Set.
 *//*

    private static void listPaths(Set set, DirContext resources, String path)
        throws NamingException {

        Enumeration childPaths = resources.listBindings(path);
        while (childPaths.hasMoreElements()) {
            Binding binding = (Binding) childPaths.nextElement();
            String name = binding.getName();
            String childPath = path + "/" + name;
            set.add(childPath);
            Object object = binding.getObject();
            if (object instanceof DirContext) {
                listPaths(set, resources, childPath);
            }
        }

    }


    */
/**
 * List resource paths (recursively), and store all of them in the given
 * Set.
 *//*

    private static void listCollectionPaths
        (Set set, DirContext resources, String path)
        throws NamingException {

        Enumeration childPaths = resources.listBindings(path);
        while (childPaths.hasMoreElements()) {
            Binding binding = (Binding) childPaths.nextElement();
            String name = binding.getName();
            StringBuffer childPath = new StringBuffer(path);
            if (!"/".equals(path) && !path.endsWith("/"))
                childPath.append("/");
            childPath.append(name);
            Object object = binding.getObject();
            if (object instanceof DirContext) {
                childPath.append("/");
            }
            set.add(childPath.toString());
        }

    }


    */
/**
 * Get full path, based on the host name and the context path.
 *//*

    public static String getJNDIUri(String hostName, String path) {
        if (!path.startsWith("/"))
            return "/" + hostName + "/" + path;
        else
            return "/" + hostName + path;
    }


}

*/
