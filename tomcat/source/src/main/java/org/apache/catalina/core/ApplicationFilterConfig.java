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


import org.apache.catalina.Context;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.util.Enumerator;
import org.apache.tomcat.util.log.SystemLogHandler;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

/**
 * Implementation of a <code>javax.servlet.FilterConfig</code> useful in
 * managing the filter instances instantiated when a web application
 * is first started.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.9 $ $Date: 2004/08/26 21:31:21 $
 */

final class ApplicationFilterConfig implements FilterConfig {


    // ----------------------------------------------------------- Constructors


    /**
     * The Context with which we are associated.
     */
    private Context context = null;


    // ----------------------------------------------------- Instance Variables
    /**
     * The application Filter we are configured for.
     */
    private Filter filter = null;
    /**
     * The <code>FilterDef</code> that defines our associated Filter.
     */
    private FilterDef filterDef = null;


    /**
     * Construct a new ApplicationFilterConfig for the specified filter
     * definition.
     *
     * @param context   The context with which we are associated
     * @param filterDef Filter definition for which a FilterConfig is to be
     *                  constructed
     * @throws ClassCastException     if the specified class does not implement
     *                                the <code>javax.servlet.Filter</code> interface
     * @throws ClassNotFoundException if the filter class cannot be found
     * @throws IllegalAccessException if the filter class cannot be
     *                                publicly instantiated
     * @throws InstantiationException if an exception occurs while
     *                                instantiating the filter object
     * @throws ServletException       if thrown by the filter's init() method
     */
    public ApplicationFilterConfig(Context context, FilterDef filterDef)
            throws ClassCastException, ClassNotFoundException,
            IllegalAccessException, InstantiationException,
            ServletException {

        super();
        this.context = context;
        setFilterDef(filterDef);

    }


    // --------------------------------------------------- FilterConfig Methods

    /**
     * Return the name of the filter we are configuring.
     */
    public String getFilterName() {

        return (filterDef.getFilterName());

    }


    /**
     * Return a <code>String</code> containing the value of the named
     * initialization parameter, or <code>null</code> if the parameter
     * does not exist.
     *
     * @param name Name of the requested initialization parameter
     */
    public String getInitParameter(String name) {

        Map map = filterDef.getParameterMap();
        if (map == null)
            return (null);
        else
            return ((String) map.get(name));

    }


    /**
     * Return an <code>Enumeration</code> of the names of the initialization
     * parameters for this Filter.
     */
    public Enumeration getInitParameterNames() {

        Map map = filterDef.getParameterMap();
        if (map == null)
            return (new Enumerator(new ArrayList()));
        else
            return (new Enumerator(map.keySet()));

    }


    /**
     * Return the ServletContext of our associated web application.
     */
    public ServletContext getServletContext() {

        return (this.context.getServletContext());

    }


    /**
     * Return a String representation of this object.
     */
    public String toString() {

        StringBuffer sb = new StringBuffer("ApplicationFilterConfig[");
        sb.append("name=");
        sb.append(filterDef.getFilterName());
        sb.append(", filterClass=");
        sb.append(filterDef.getFilterClass());
        sb.append("]");
        return (sb.toString());

    }


    // -------------------------------------------------------- Package Methods


    /**
     * Return the application Filter we are configured for.
     *
     * @throws ClassCastException     if the specified class does not implement
     *                                the <code>javax.servlet.Filter</code> interface
     * @throws ClassNotFoundException if the filter class cannot be found
     * @throws IllegalAccessException if the filter class cannot be
     *                                publicly instantiated
     * @throws InstantiationException if an exception occurs while
     *                                instantiating the filter object
     * @throws ServletException       if thrown by the filter's init() method
     */
    Filter getFilter() throws ClassCastException, ClassNotFoundException,
            IllegalAccessException, InstantiationException, ServletException {

        // Return the existing filter instance, if any
        if (this.filter != null)
            return (this.filter);

        // Identify the class loader we will be using
        String filterClass = filterDef.getFilterClass();
        ClassLoader classLoader = null;
        if (filterClass.startsWith("org.apache.catalina."))
            classLoader = this.getClass().getClassLoader();
        else
            classLoader = context.getLoader().getClassLoader();

        ClassLoader oldCtxClassLoader =
                Thread.currentThread().getContextClassLoader();

        // Instantiate a new instance of this filter and return it
        Class clazz = classLoader.loadClass(filterClass);
        this.filter = (Filter) clazz.newInstance();
        if (context instanceof StandardContext &&
                ((StandardContext) context).getSwallowOutput()) {
            try {
                SystemLogHandler.startCapture();
                filter.init(this);
            } finally {
                String log = SystemLogHandler.stopCapture();
                if (log != null && log.length() > 0) {
                    getServletContext().log(log);
                }
            }
        } else {
            filter.init(this);
        }
        return (this.filter);

    }


    /**
     * Return the filter definition we are configured for.
     */
    FilterDef getFilterDef() {

        return (this.filterDef);

    }

    /**
     * Set the filter definition we are configured for.  This has the side
     * effect of instantiating an instance of the corresponding filter class.
     *
     * @param filterDef The new filter definition
     * @throws ClassCastException     if the specified class does not implement
     *                                the <code>javax.servlet.Filter</code> interface
     * @throws ClassNotFoundException if the filter class cannot be found
     * @throws IllegalAccessException if the filter class cannot be
     *                                publicly instantiated
     * @throws InstantiationException if an exception occurs while
     *                                instantiating the filter object
     * @throws ServletException       if thrown by the filter's init() method
     */
    void setFilterDef(FilterDef filterDef)
            throws ClassCastException, ClassNotFoundException,
            IllegalAccessException, InstantiationException,
            ServletException {

        this.filterDef = filterDef;
        if (filterDef == null) {

            // Release any previously allocated filter instance
            if (this.filter != null)
                this.filter.destroy();
            this.filter = null;

        } else {

            // Allocate a new filter instance
            Filter filter = getFilter();

        }

    }

    /**
     * Release the Filter instance associated with this FilterConfig,
     * if there is one.
     */
    void release() {

        if (this.filter != null)
            filter.destroy();
        this.filter = null;

    }


    // -------------------------------------------------------- Private Methods


}
