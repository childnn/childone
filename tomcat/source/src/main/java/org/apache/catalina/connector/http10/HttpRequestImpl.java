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


package org.apache.catalina.connector.http10;


import org.apache.catalina.connector.HttpRequestBase;

import java.net.InetAddress;


/**
 * Implementation of <b>HttpRequest</b> specific to the HTTP connector.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.6 $ $Date: 2004/08/26 21:29:22 $
 * @deprecated
 */

final class HttpRequestImpl
        extends HttpRequestBase {


    // ----------------------------------------------------- Instance Variables


    /**
     * Descriptive information about this Request implementation.
     */
    protected static final String info =
            "org.apache.catalina.connector.http10.HttpRequestImpl/1.0";
    /**
     * The InetAddress of the remote client of ths request.
     */
    protected InetAddress inet = null;


    // ------------------------------------------------------------- Properties

    /**
     * [Package Private] Return the InetAddress of the remote client of
     * this request.
     */
    InetAddress getInet() {

        return (inet);

    }


    /**
     * [Package Private] Set the InetAddress of the remote client of
     * this request.
     *
     * @param inet The new InetAddress
     */
    void setInet(InetAddress inet) {

        this.inet = inet;

    }


    /**
     * Return descriptive information about this Request implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    public String getInfo() {

        return (info);

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Release all object references, and initialize instance variables, in
     * preparation for reuse of this object.
     */
    public void recycle() {

        super.recycle();
        inet = null;

    }


    // ------------------------------------------------- ServletRequest Methods


    /**
     * Return the Internet Protocol (IP) address of the client that sent
     * this request.
     */
    public String getRemoteAddr() {

        return (inet.getHostAddress());

    }


    /**
     * Return the fully qualified name of the client that sent this request,
     * or the IP address of the client if the name cannot be determined.
     */
    public String getRemoteHost() {

        if (connector.getEnableLookups())
            return (inet.getHostName());
        else
            return (getRemoteAddr());

    }


    // --------------------------------------------- HttpServletRequest Methods


}
