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

/**
 * This class is responsible for sending outgoing packets to a Cluster.
 * Different Implementations may use different protocol to
 * communicate within the Cluster.
 *
 * @author Bip Thelin
 * @version $Revision: 1.3 $, $Date: 2004/08/26 21:28:18 $
 */

public interface ClusterSender {

    // --------------------------------------------------------- Public Methods

    /**
     * get the senderId used to identify messages being sent in a Cluster.
     *
     * @return The senderId for this ClusterSender
     */
    public String getSenderId();

    /**
     * The senderId is a identifier used to identify different
     * packages being sent in a Cluster. Each package sent through
     * the concrete implementation of this interface will have
     * the senderId set at runtime. Usually the senderId is the
     * name of the component that is using this <code>ClusterSender</code>
     *
     * @param senderId The senderId to use
     */
    public void setSenderId(String senderId);

    /**
     * Get the debug level for this component
     *
     * @return The debug level
     */
    public int getDebug();

    /**
     * Set the debug detail level for this component.
     *
     * @param debug The debug level
     */
    public void setDebug(int debug);

    /**
     * Get the Logger for this component
     *
     * @return The Logger associated with this component.
     */
    public Logger getLogger();

    /**
     * Set the Logger for this component.
     *
     * @param debug The Logger to use with this component.
     */
    public void setLogger(Logger logger);

    /**
     * The log method to use in the implementation
     *
     * @param message The message to be logged.
     */
    public void log(String message);

    /**
     * Send an array of bytes, the implementation of this
     * <code>ClusterSender</code> is responsible for modifying
     * the bytearray to something that it can use. Before anything
     * is sent it is transformed into a ReplicationWrapper object
     * and the right senderId is set.
     *
     * @param b the bytearray to send
     */
    public void send(byte[] b);

    /**
     * Send an object, the implementation of this
     * <code>ClusterSender</code> is responsible for modifying
     * the Object to something that it can use. Before anything
     * is sent it is transformed into a ReplicationWrapper object
     * and the right senderId is set.
     *
     * @param o The object to send
     */
    public void send(Object o);
}
