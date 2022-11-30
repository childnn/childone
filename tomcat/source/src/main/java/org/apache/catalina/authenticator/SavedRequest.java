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


package org.apache.catalina.authenticator;


import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;


/**
 * Object that saves the critical information from a request so that
 * form-based authentication can reproduce it once the user has been
 * authenticated.
 * <p>
 * <b>IMPLEMENTATION NOTE</b> - It is assumed that this object is accessed
 * only from the context of a single thread, so no synchronization around
 * internal collection classes is performed.
 * <p>
 * <b>FIXME</b> - Currently, this object has no mechanism to save or
 * restore the data content of the request, although it does save
 * request parameters so that a POST transaction can be faithfully
 * duplicated.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.5 $ $Date: 2004/08/26 21:27:39 $
 */

public final class SavedRequest {


    /**
     * The set of Cookies associated with this Request.
     */
    private ArrayList cookies = new ArrayList();
    /**
     * The set of Headers associated with this Request.  Each key is a header
     * name, while the value is a ArrayList containing one or more actual
     * values for this header.  The values are returned as an Iterator when
     * you ask for them.
     */
    private HashMap headers = new HashMap();
    /**
     * The set of Locales associated with this Request.
     */
    private ArrayList locales = new ArrayList();
    /**
     * The request method used on this Request.
     */
    private String method = null;
    /**
     * The set of request parameters associated with this Request.  Each
     * entry is keyed by the parameter name, pointing at a String array of
     * the corresponding values.
     */
    private HashMap parameters = new HashMap();
    /**
     * The query string associated with this Request.
     */
    private String queryString = null;
    /**
     * The request URI associated with this Request.
     */
    private String requestURI = null;

    public void addCookie(Cookie cookie) {
        cookies.add(cookie);
    }

    public Iterator getCookies() {
        return (cookies.iterator());
    }

    public void addHeader(String name, String value) {
        ArrayList values = (ArrayList) headers.get(name);
        if (values == null) {
            values = new ArrayList();
            headers.put(name, values);
        }
        values.add(value);
    }

    public Iterator getHeaderNames() {
        return (headers.keySet().iterator());
    }

    public Iterator getHeaderValues(String name) {
        ArrayList values = (ArrayList) headers.get(name);
        if (values == null)
            return ((new ArrayList()).iterator());
        else
            return (values.iterator());
    }

    public void addLocale(Locale locale) {
        locales.add(locale);
    }

    public Iterator getLocales() {
        return (locales.iterator());
    }

    public String getMethod() {
        return (this.method);
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void addParameter(String name, String values[]) {
        parameters.put(name, values);
    }

    public Iterator getParameterNames() {
        return (parameters.keySet().iterator());
    }

    public String[] getParameterValues(String name) {
        return ((String[]) parameters.get(name));
    }

    public String getQueryString() {
        return (this.queryString);
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getRequestURI() {
        return (this.requestURI);
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }


}
