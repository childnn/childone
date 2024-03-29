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


package tomcat4.org.apache.catalina;


/**
 * <p><strong>ServerFactory</strong> allows the registration of the
 * (singleton) <code>Server</code> instance for this JVM, so that it
 * can be accessed independently of any existing reference to the
 * component hierarchy.  This is important for administration tools
 * that are built around the internal component implementation classes.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.2 $ $Date: 2004/08/26 22:05:54 $
 */

public class ServerFactory {


    // ------------------------------------------------------- Static Variables


    /**
     * The singleton <code>Server</code> instance for this JVM.
     */
    private static Server server = null;


    // --------------------------------------------------------- Public Methods


    /**
     * Return the singleton <code>Server</code> instance for this JVM.
     */
    public static Server getServer() {

        return (server);

    }


    /**
     * Set the singleton <code>Server</code> instance for this JVM.  This
     * method must <strong>only</strong> be called from a constructor of
     * the (singleton) <code>Server</code> instance that is created for
     * this execution of Catalina.
     *
     * @param theServer The new singleton instance
     */
    public static void setServer(Server theServer) {

        if (server == null)
            server = theServer;

    }


}
