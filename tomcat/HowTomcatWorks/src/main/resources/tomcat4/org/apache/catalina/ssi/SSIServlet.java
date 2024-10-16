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

package tomcat4.org.apache.catalina.ssi;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.TimeZone;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.Globals;

/**
 * Servlet to process SSI requests within a webpage.
 * Mapped to a path from within web.xml.
 *
 * @author Bip Thelin
 * @author Amy Roh
 * @author Dan Sandberg
 * @version $Revision: 1.3 $, $Date: 2004/08/26 21:40:03 $
 */
public class SSIServlet extends HttpServlet {

    /** Debug level for this servlet. */
    protected int debug = 0;

    /** Should the output be buffered. */
    protected boolean buffered = false;

    /** Expiration time in seconds for the doc. */
    protected Long expires = null;

    /** virtual path can be webapp-relative */
    protected boolean isVirtualWebappRelative = false;

    //----------------- Public methods.

    /**
     * Initialize this servlet.
     * @exception ServletException if an error occurs
     */
    public void init() throws ServletException {
        String value = null;
        try {
            value = getServletConfig().getInitParameter("debug");
            debug = Integer.parseInt(value);
        } catch (Throwable t) {
            ;
        }

        try {
            value = getServletConfig().getInitParameter("isVirtualWebappRelative");
            isVirtualWebappRelative = Integer.parseInt(value) > 0 ? true : false;
        } catch (Throwable t) {
            ;
        }

        try {
            value = getServletConfig().getInitParameter("expires");
            expires = Long.valueOf(value);
        } catch (NumberFormatException e) {
            expires = null;
            log("Invalid format for expires initParam; expected integer (seconds)");
        } catch (Throwable t) {
            ;
        }
        try {
            value = getServletConfig().getInitParameter("buffered");
            buffered = Integer.parseInt(value) > 0 ? true : false;
        } catch (Throwable t) {
            ;
        }
        if (debug > 0)
            log("SSIServlet.init() SSI invoker started with 'debug'="
                + debug);
    }

    /**
     * Process and forward the GET request
     * to our <code>requestHandler()</code>     *
     * @param req a value of type 'HttpServletRequest'
     * @param res a value of type 'HttpServletResponse'
     * @exception IOException if an error occurs
     * @exception ServletException if an error occurs
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException {

        if (debug > 0)
            log("SSIServlet.doGet()");
        requestHandler(req, res);
    }

    /**
     * Process and forward the POST request
     * to our <code>requestHandler()</code>.
     *
     * @param req a value of type 'HttpServletRequest'
     * @param res a value of type 'HttpServletResponse'
     * @exception IOException if an error occurs
     * @exception ServletException if an error occurs
     */
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException {

        if (debug > 0)
            log("SSIServlet.doPost()");
        requestHandler(req, res);
    }

    /**
     * Process our request and locate right SSI command.
     * @param req a value of type 'HttpServletRequest'
     * @param res a value of type 'HttpServletResponse'
     */
    protected void requestHandler(HttpServletRequest req,
                                HttpServletResponse res)
        throws IOException, ServletException {

        ServletContext servletContext = getServletContext();
        String path = SSIServletRequestUtil.getRelativePath( req );

        if (debug > 0)
            log("SSIServlet.requestHandler()\n" +
                "Serving " + (buffered ? "buffered " : "unbuffered ") +
                "resource '" + path + "'");

        // Exclude any resource in the /WEB-INF and /META-INF subdirectories
        // (the "toUpperCase()" avoids problems on Windows systems)
        if ( path == null ||
             path.toUpperCase().startsWith("/WEB-INF") ||
             path.toUpperCase().startsWith("/META-INF") ) {

            res.sendError(res.SC_NOT_FOUND, path);
        log( "Can't serve file: " + path );
            return;
        }
    
        URL resource = servletContext.getResource(path);
        if (resource==null) {
            res.sendError(res.SC_NOT_FOUND, path);
        log( "Can't find file: " + path );
            return;
        }

        res.setContentType("text/html;charset=UTF-8");

        if (expires != null) {
            res.setDateHeader("Expires", (
                new java.util.Date()).getTime() + expires.longValue() * 1000);
        }
        
        req.setAttribute(Globals.SSI_FLAG_ATTR,"true");
        processSSI( req, res, resource );
    }

    protected void processSSI( HttpServletRequest req,
                   HttpServletResponse res,
                   URL resource ) throws IOException {
                   
        SSIExternalResolver ssiExternalResolver = 
                            new SSIServletExternalResolver( this, req, res,
                            isVirtualWebappRelative,
                            debug );
        SSIProcessor ssiProcessor = 
                            new SSIProcessor( ssiExternalResolver, debug );

        PrintWriter printWriter = null;
        StringWriter stringWriter = null;
        if (buffered) {
            stringWriter = new StringWriter();
            printWriter = new PrintWriter( stringWriter );
        } else {
            printWriter = res.getWriter();
        }

        URLConnection resourceInfo = resource.openConnection();
        InputStream resourceInputStream = resourceInfo.getInputStream();
        BufferedReader bufferedReader = 
            new BufferedReader(new InputStreamReader(resourceInputStream));
        Date lastModifiedDate = new Date(resourceInfo.getLastModified());
        ssiProcessor.process(bufferedReader, lastModifiedDate, printWriter);

        if (buffered) {
            printWriter.flush();
            String text = stringWriter.toString();
            res.getWriter().write(text);
        }
    }
}
