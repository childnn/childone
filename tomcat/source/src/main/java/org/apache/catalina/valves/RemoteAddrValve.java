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


package org.apache.catalina.valves;


import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.ValveContext;

import javax.servlet.ServletException;
import java.io.IOException;


/**
 * Concrete implementation of <code>RequestFilterValve</code> that filters
 * based on the string representation of the remote client's IP address.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.4 $ $Date: 2004/08/26 21:43:51 $
 */

public final class RemoteAddrValve
        extends RequestFilterValve {


    // ----------------------------------------------------- Instance Variables


    /**
     * The descriptive information related to this implementation.
     */
    private static final String info =
            "org.apache.catalina.valves.RemoteAddrValve/1.0";


    // ------------------------------------------------------------- Properties


    /**
     * Return descriptive information about this Valve implementation.
     */
    public String getInfo() {

        return (info);

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Extract the desired request property, and pass it (along with the
     * specified request and response objects) to the protected
     * <code>process()</code> method to perform the actual filtering.
     * This method must be implemented by a concrete subclass.
     *
     * @param request  The servlet request to be processed
     * @param response The servlet response to be created
     * @param context  The valve context used to invoke the next valve
     *                 in the current processing pipeline
     * @throws IOException      if an input/output error occurs
     * @throws ServletException if a servlet error occurs
     */
    public void invoke(Request request, Response response,
                       ValveContext context)
            throws IOException, ServletException {

        process(request.getRequest().getRemoteAddr(),
                request, response, context);

    }


}
