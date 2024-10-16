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


package org.apache.catalina.core;


import org.apache.catalina.Globals;
import org.apache.catalina.util.Enumerator;
import org.apache.catalina.util.RequestUtil;
import org.apache.catalina.util.StringManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;


/**
 * Wrapper around a <code>javax.servlet.http.HttpServletRequest</code>
 * that transforms an application request object (which might be the original
 * one passed to a servlet, or might be based on the 2.3
 * <code>javax.servlet.http.HttpServletRequestWrapper</code> class)
 * back into an internal <code>org.apache.catalina.HttpRequest</code>.
 * <p>
 * <strong>WARNING</strong>:  Due to Java's lack of support for multiple
 * inheritance, all of the logic in <code>ApplicationRequest</code> is
 * duplicated in <code>ApplicationHttpRequest</code>.  Make sure that you
 * keep these two classes in synchronization when making changes!
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.13 $ $Date: 2004/08/26 21:31:21 $
 */

class ApplicationHttpRequest extends HttpServletRequestWrapper {


    // ------------------------------------------------------- Static Variables


    /**
     * The set of attribute names that are special for request dispatchers.
     */
    protected static final String specials[] =
            {Globals.REQUEST_URI_ATTR, Globals.CONTEXT_PATH_ATTR,
                    Globals.SERVLET_PATH_ATTR, Globals.PATH_INFO_ATTR,
                    Globals.QUERY_STRING_ATTR};


    // ----------------------------------------------------------- Constructors
    /**
     * Descriptive information about this implementation.
     */
    protected static final String info =
            "org.apache.catalina.core.ApplicationHttpRequest/1.0";


    // ----------------------------------------------------- Instance Variables
    /**
     * The string manager for this package.
     */
    protected static StringManager sm =
            StringManager.getManager(Constants.Package);
    /**
     * The request attributes for this request.  This is initialized from the
     * wrapped request, but updates are allowed.
     */
    protected HashMap attributes = new HashMap();
    /**
     * The context path for this request.
     */
    protected String contextPath = null;
    /**
     * The request parameters for this request.  This is initialized from the
     * wrapped request, but updates are allowed.
     */
    protected Map parameters = new HashMap();


    /**
     * The path information for this request.
     */
    protected String pathInfo = null;


    /**
     * The query string for this request.
     */
    protected String queryString = null;


    /**
     * The request URI for this request.
     */
    protected String requestURI = null;


    /**
     * The servlet path for this request.
     */
    protected String servletPath = null;


    /**
     * Construct a new wrapped request around the specified servlet request.
     *
     * @param request The servlet request being wrapped
     */
    public ApplicationHttpRequest(HttpServletRequest request) {

        super(request);
        setRequest(request);

    }


    // ------------------------------------------------- ServletRequest Methods

    /**
     * Override the <code>getAttribute()</code> method of the wrapped request.
     *
     * @param name Name of the attribute to retrieve
     */
    public Object getAttribute(String name) {

        synchronized (attributes) {
            return (attributes.get(name));
        }

    }


    /**
     * Override the <code>getAttributeNames()</code> method of the wrapped
     * request.
     */
    public Enumeration getAttributeNames() {

        synchronized (attributes) {
            return (new Enumerator(attributes.keySet()));
        }

    }


    /**
     * Override the <code>removeAttribute()</code> method of the
     * wrapped request.
     *
     * @param name Name of the attribute to remove
     */
    public void removeAttribute(String name) {

        synchronized (attributes) {
            attributes.remove(name);
            if (!isSpecial(name))
                getRequest().removeAttribute(name);
        }

    }


    /**
     * Override the <code>setAttribute()</code> method of the
     * wrapped request.
     *
     * @param name  Name of the attribute to set
     * @param value Value of the attribute to set
     */
    public void setAttribute(String name, Object value) {

        synchronized (attributes) {
            attributes.put(name, value);
            if (!isSpecial(name))
                getRequest().setAttribute(name, value);
        }

    }


    // --------------------------------------------- HttpServletRequest Methods


    /**
     * Override the <code>getContextPath()</code> method of the wrapped
     * request.
     */
    public String getContextPath() {

        return (this.contextPath);

    }

    /**
     * Set the context path for this request.
     *
     * @param contextPath The new context path
     */
    void setContextPath(String contextPath) {

        this.contextPath = contextPath;

    }

    /**
     * Override the <code>getParameter()</code> method of the wrapped request.
     *
     * @param name Name of the requested parameter
     */
    public String getParameter(String name) {

        synchronized (parameters) {
            Object value = parameters.get(name);
            if (value == null)
                return (null);
            else if (value instanceof String[])
                return (((String[]) value)[0]);
            else if (value instanceof String)
                return ((String) value);
            else
                return (value.toString());
        }

    }

    /**
     * Override the <code>getParameterMap()</code> method of the
     * wrapped request.
     */
    public Map getParameterMap() {

        return (parameters);

    }

    /**
     * Override the <code>getParameterNames()</code> method of the
     * wrapped request.
     */
    public Enumeration getParameterNames() {

        synchronized (parameters) {
            return (new Enumerator(parameters.keySet()));
        }

    }

    /**
     * Override the <code>getParameterValues()</code> method of the
     * wrapped request.
     *
     * @param name Name of the requested parameter
     */
    public String[] getParameterValues(String name) {

        synchronized (parameters) {
            Object value = parameters.get(name);
            if (value == null)
                return ((String[]) null);
            else if (value instanceof String[])
                return ((String[]) value);
            else if (value instanceof String) {
                String values[] = new String[1];
                values[0] = (String) value;
                return (values);
            } else {
                String values[] = new String[1];
                values[0] = value.toString();
                return (values);
            }
        }

    }

    /**
     * Override the <code>getPathInfo()</code> method of the wrapped request.
     */
    public String getPathInfo() {

        return (this.pathInfo);

    }

    /**
     * Set the path information for this request.
     *
     * @param pathInfo The new path info
     */
    void setPathInfo(String pathInfo) {

        this.pathInfo = pathInfo;

    }

    /**
     * Override the <code>getQueryString()</code> method of the wrapped
     * request.
     */
    public String getQueryString() {

        return (this.queryString);

    }


    // -------------------------------------------------------- Package Methods

    /**
     * Set the query string for this request.
     *
     * @param queryString The new query string
     */
    void setQueryString(String queryString) {

        this.queryString = queryString;

    }

    /**
     * Override the <code>getRequestURI()</code> method of the wrapped
     * request.
     */
    public String getRequestURI() {

        return (this.requestURI);

    }

    /**
     * Set the request URI for this request.
     *
     * @param requestURI The new request URI
     */
    void setRequestURI(String requestURI) {

        this.requestURI = requestURI;

    }

    /**
     * Override the <code>getServletPath()</code> method of the wrapped
     * request.
     */
    public String getServletPath() {

        return (this.servletPath);

    }

    /**
     * Set the servlet path for this request.
     *
     * @param servletPath The new servlet path
     */
    void setServletPath(String servletPath) {

        this.servletPath = servletPath;

    }

    /**
     * Return descriptive information about this implementation.
     */
    public String getInfo() {

        return (this.info);

    }

    /**
     * Perform a shallow copy of the specified Map, and return the result.
     *
     * @param orig Origin Map to be copied
     */
    Map copyMap(Map orig) {

        if (orig == null)
            return (new HashMap());
        HashMap dest = new HashMap();
        synchronized (orig) {
            Iterator keys = orig.keySet().iterator();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                dest.put(key, orig.get(key));
            }
        }
        return (dest);

    }

    /**
     * Merge the parameters from the specified query string (if any), and
     * the parameters already present on this request (if any), such that
     * the parameter values from the query string show up first if there are
     * duplicate parameter names.
     *
     * @param queryString The query string containing parameters to be merged
     */
    void mergeParameters(String queryString) {

        if ((queryString == null) || (queryString.length() < 1))
            return;

        HashMap queryParameters = new HashMap();
        String encoding = getCharacterEncoding();
        if (encoding == null)
            encoding = "ISO-8859-1";
        try {
            RequestUtil.parseParameters
                    (queryParameters, queryString, encoding);
        } catch (Exception e) {
            ;
        }
        synchronized (parameters) {
            Iterator keys = parameters.keySet().iterator();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                Object value = queryParameters.get(key);
                if (value == null) {
                    queryParameters.put(key, parameters.get(key));
                    continue;
                }
                queryParameters.put
                        (key, mergeValues(value, parameters.get(key)));
            }
            parameters = queryParameters;
        }

    }

    /**
     * Set the request that we are wrapping.
     *
     * @param request The new wrapped request
     */
    void setRequest(HttpServletRequest request) {

        super.setRequest(request);

        // Initialize the attributes for this request
        synchronized (attributes) {
            attributes.clear();
            Enumeration names = request.getAttributeNames();
            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                if (!(Globals.REQUEST_URI_ATTR.equals(name) ||
                        Globals.SERVLET_PATH_ATTR.equals(name))) {
                    Object value = request.getAttribute(name);
                    attributes.put(name, value);
                }
            }
        }

        // Initialize the parameters for this request
        synchronized (parameters) {
            parameters = copyMap(request.getParameterMap());
        }

        // Initialize the path elements for this request
        contextPath = request.getContextPath();
        pathInfo = request.getPathInfo();
        queryString = request.getQueryString();
        requestURI = request.getRequestURI();
        servletPath = request.getServletPath();

    }


    // ------------------------------------------------------ Protected Methods

    /**
     * Is this attribute name one of the special ones that is added only for
     * included servlets?
     *
     * @param name Attribute name to be tested
     */
    protected boolean isSpecial(String name) {

        for (int i = 0; i < specials.length; i++) {
            if (specials[i].equals(name))
                return (true);
        }
        return (false);

    }


    /**
     * Merge the two sets of parameter values into a single String array.
     *
     * @param values1 First set of values
     * @param values2 Second set of values
     */
    protected String[] mergeValues(Object values1, Object values2) {

        ArrayList results = new ArrayList();

        if (values1 == null)
            ;
        else if (values1 instanceof String)
            results.add(values1);
        else if (values1 instanceof String[]) {
            String values[] = (String[]) values1;
            for (int i = 0; i < values.length; i++)
                results.add(values[i]);
        } else
            results.add(values1.toString());

        if (values2 == null)
            ;
        else if (values2 instanceof String)
            results.add(values2);
        else if (values2 instanceof String[]) {
            String values[] = (String[]) values2;
            for (int i = 0; i < values.length; i++)
                results.add(values[i]);
        } else
            results.add(values2.toString());

        String values[] = new String[results.size()];
        return ((String[]) results.toArray(values));

    }


}
