/*
 * Copyright 1999-2001,2004 The Apache Software Foundation.
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


package tomcat4.org.apache.catalina.valves;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.Container;
import org.apache.catalina.HttpRequest;
import org.apache.catalina.HttpResponse;
import org.apache.catalina.Logger;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;
import org.apache.catalina.connector.HttpResponseWrapper;
import org.apache.catalina.util.StringManager;


/**
 * <p>Implementation of a Valve that logs interesting contents from the
 * specified Request (before processing) and the corresponding Response
 * (after processing).  It is especially useful in debugging problems
 * related to headers and cookies.</p>
 *
 * <p>This Valve may be attached to any Container, depending on the granularity
 * of the logging you wish to perform.</p>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.6 $ $Date: 2004/08/26 21:43:51 $
 */

public class RequestDumperValve
    extends ValveBase {


    // ----------------------------------------------------- Instance Variables


    /**
     * The descriptive information related to this implementation.
     */
    private static final String info =
        "org.apache.catalina.valves.RequestDumperValve/1.0";


    /**
     * The StringManager for this package.
     */
    protected static StringManager sm =
        StringManager.getManager(Constants.Package);


    // ------------------------------------------------------------- Properties


    /**
     * Return descriptive information about this Valve implementation.
     */
    public String getInfo() {

        return (info);

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Log the interesting request parameters, invoke the next Valve in the
     * sequence, and log the interesting response parameters.
     *
     * @param request The servlet request to be processed
     * @param response The servlet response to be created
     * @param context The valve context used to invoke the next valve
     *  in the current processing pipeline
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void invoke(Request request, Response response,
                       ValveContext context)
        throws IOException, ServletException {

        // Skip logging for non-HTTP requests and responses
        if (!(request instanceof HttpRequest) ||
            !(response instanceof HttpResponse)) {
            context.invokeNext(request, response);
            return;
        }
        HttpRequest hrequest = (HttpRequest) request;
        HttpResponse hresponse = (HttpResponse) response;
        HttpServletRequest hreq =
            (HttpServletRequest) hrequest.getRequest();
        HttpServletResponse hres =
            (HttpServletResponse) hresponse.getResponse();

        // Log pre-service information
        log("REQUEST URI       =" + hreq.getRequestURI());
        log("          authType=" + hreq.getAuthType());
        log(" characterEncoding=" + hreq.getCharacterEncoding());
        log("     contentLength=" + hreq.getContentLength());
        log("       contentType=" + hreq.getContentType());
        log("       contextPath=" + hreq.getContextPath());
        Cookie cookies[] = hreq.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++)
                log("            cookie=" + cookies[i].getName() + "=" +
                    cookies[i].getValue());
        }
        Enumeration hnames = hreq.getHeaderNames();
        while (hnames.hasMoreElements()) {
            String hname = (String) hnames.nextElement();
            Enumeration hvalues = hreq.getHeaders(hname);
            while (hvalues.hasMoreElements()) {
                String hvalue = (String) hvalues.nextElement();
                log("            header=" + hname + "=" + hvalue);
            }
        }
        log("            locale=" + hreq.getLocale());
        log("            method=" + hreq.getMethod());
        Enumeration pnames = hreq.getParameterNames();
        while (pnames.hasMoreElements()) {
            String pname = (String) pnames.nextElement();
            String pvalues[] = hreq.getParameterValues(pname);
            StringBuffer result = new StringBuffer(pname);
            result.append('=');
            for (int i = 0; i < pvalues.length; i++) {
                if (i > 0)
                    result.append(", ");
                result.append(pvalues[i]);
            }
            log("         parameter=" + result.toString());
        }
        log("          pathInfo=" + hreq.getPathInfo());
        log("          protocol=" + hreq.getProtocol());
        log("       queryString=" + hreq.getQueryString());
        log("        remoteAddr=" + hreq.getRemoteAddr());
        log("        remoteHost=" + hreq.getRemoteHost());
        log("        remoteUser=" + hreq.getRemoteUser());
        log("requestedSessionId=" + hreq.getRequestedSessionId());
        log("            scheme=" + hreq.getScheme());
        log("        serverName=" + hreq.getServerName());
        log("        serverPort=" + hreq.getServerPort());
        log("       servletPath=" + hreq.getServletPath());
        log("          isSecure=" + hreq.isSecure());
        log("---------------------------------------------------------------");

        // Perform the request
        context.invokeNext(request, response);

        // Log post-service information
        log("---------------------------------------------------------------");
        log("          authType=" + hreq.getAuthType());
        log("     contentLength=" + hresponse.getContentLength());
        log("       contentType=" + hresponse.getContentType());
        Cookie rcookies[] = hresponse.getCookies();
        for (int i = 0; i < rcookies.length; i++) {
            log("            cookie=" + rcookies[i].getName() + "=" +
                rcookies[i].getValue() + "; domain=" +
                rcookies[i].getDomain() + "; path=" + rcookies[i].getPath());
        }
        String rhnames[] = hresponse.getHeaderNames();
        for (int i = 0; i < rhnames.length; i++) {
            String rhvalues[] = hresponse.getHeaderValues(rhnames[i]);
            for (int j = 0; j < rhvalues.length; j++)
                log("            header=" + rhnames[i] + "=" + rhvalues[j]);
        }
        log("           message=" + hresponse.getMessage());
        log("        remoteUser=" + hreq.getRemoteUser());
        log("            status=" + hresponse.getStatus());
        log("===============================================================");

    }


    /**
     * Return a String rendering of this object.
     */
    public String toString() {

        StringBuffer sb = new StringBuffer("RequestDumperValve[");
        if (container != null)
            sb.append(container.getName());
        sb.append("]");
        return (sb.toString());

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Log a message on the Logger associated with our Container (if any).
     *
     * @param message Message to be logged
     */
    protected void log(String message) {

        Logger logger = container.getLogger();
        if (logger != null)
            logger.log(this.toString() + ": " + message);
        else
            System.out.println(this.toString() + ": " + message);

    }


    /**
     * Log a message on the Logger associated with our Container (if any).
     *
     * @param message Message to be logged
     * @param throwable Associated exception
     */
    protected void log(String message, Throwable throwable) {

        Logger logger = container.getLogger();
        if (logger != null)
            logger.log(this.toString() + ": " + message, throwable);
        else {
            System.out.println(this.toString() + ": " + message);
            throwable.printStackTrace(System.out);
        }

    }


}
