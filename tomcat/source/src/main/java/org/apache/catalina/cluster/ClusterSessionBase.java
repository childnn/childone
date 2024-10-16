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

package org.apache.catalina.cluster;

import org.apache.catalina.Logger;
import org.apache.catalina.util.StringManager;

/**
 * This is an abstract implementation of <code>ClusterSender</code>
 * and <code>ClusterReceiver</code> which provide basic functionallity
 * shared by the two components.
 *
 * @author Bip Thelin
 * @version $Revision: 1.4 $, $Date: 2004/08/26 21:28:18 $
 */

public abstract class ClusterSessionBase {

    // ----------------------------------------------------- Instance Variables

    /**
     * The string manager for this package.
     */
    protected StringManager sm = StringManager.getManager(Constants.Package);
    /**
     * The senderId associated with this component
     */
    private String senderId = null;
    /**
     * The debug level for this component
     */
    private int debug = 0;
    /**
     * The Logger associated with this component.
     */
    private Logger logger = null;

    // --------------------------------------------------------- Public Methods

    /**
     * get the senderId used to identify messages being
     * send or received in a Cluster.
     *
     * @return The senderId for this component
     */
    public String getSenderId() {
        return (this.senderId);
    }

    /**
     * The senderId is a identifier used to identify different
     * packagesin a Cluster. Each package received or send through
     * the concrete implementation of this interface will have
     * the senderId set at runtime. Usually the senderId is the
     * name of the component that is using this component.
     *
     * @param senderId The senderId to use
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    /**
     * Get the debug level for this component
     *
     * @return The debug level
     */
    public int getDebug() {
        return (this.debug);
    }

    /**
     * Set the debug detail level for this component.
     *
     * @param debug The debug level
     */
    public void setDebug(int debug) {
        this.debug = debug;
    }

    /**
     * Get the Logger for this component
     *
     * @return The Logger associated with this component.
     */
    public Logger getLogger() {
        return (this.logger);
    }

    /**
     * Set the Logger for this component.
     *
     * @param debug The Logger to use with this component.
     */
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public abstract String getName();

    /**
     * The log method to use in the implementation
     *
     * @param message The message to be logged.
     */
    public void log(String message) {
        Logger logger = getLogger();

        if (logger != null)
            logger.log("[Cluster/" + getName() + "]: " + message);
        else
            System.out.println("[Cluster/" + getName() + "]: " + message);
    }
}
