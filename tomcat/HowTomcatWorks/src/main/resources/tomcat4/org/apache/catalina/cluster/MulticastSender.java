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

package tomcat4.org.apache.catalina.cluster;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;


/**
 * This class is responsible for sending outgoing multicast
 * packets to a Cluster.
 *
 * @author Bip Thelin
 * @version $Revision: 1.5 $, $Date: 2004/08/26 21:28:18 $
 */

public class MulticastSender
    extends ClusterSessionBase implements ClusterSender {

    // ----------------------------------------------------- Instance Variables

    /**
     * The unique message ID
     */
    private static String senderId = null;

    /**
     * The name of our component, used for logging.
     */
    private String senderName = "MulticastSender";

    /**
     * The MulticastSocket to use
     */
    private MulticastSocket multicastSocket = null;

    /**
     * The multicastAdress this socket is bound to
     */
    private InetAddress multicastAddress = null;

    /**
     * The multicastPort this socket is bound to
     */
    private int multicastPort;


    // --------------------------------------------------------- Public Methods


    /**
     * Create a new MulticastSender, only receivers with our
     * senderId will receive our data.
     *
     * @param senderId The senderId
     * @param multicastSocket the socket to use
     * @param multicastAddress the address to use
     * @param multicastPort the port to use
     */
    MulticastSender(String senderId, MulticastSocket multicastSocket,
                    InetAddress multicastAddress, int multicastPort) {
        this.multicastAddress = multicastAddress;
        this.multicastPort = multicastPort;
        this.multicastSocket = multicastSocket;
        this.senderId = senderId;
    }

    /**
     * Return a <code>String</code> containing the name of this
     * implementation, used for logging
     *
     * @return The name of the implementation
     */
    public String getName() {
        return(this.senderName);
    }

    /**
     * Send an object using a multicastSocket
     *
     * @param o The object to be sent.
     */
    public void send(Object o) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream bos = null;

        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(new BufferedOutputStream(bos));

            oos.writeObject(o);
            oos.flush();

            byte[] obs = bos.toByteArray();

            send(obs);
        } catch (IOException e) {
            log(sm.getString("multicastSender.sendException", e.toString()));
        }
    }

    /**
     * Send multicast data
     *
     * @param b data to be sent
     */
    public void send(byte[] b) {
        ReplicationWrapper out = new ReplicationWrapper(b, senderId);
        ObjectOutputStream oos = null;
        ByteArrayOutputStream bos = null;

        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(new BufferedOutputStream(bos));

            oos.writeObject(out);
            oos.flush();

            byte[] obs = bos.toByteArray();
            int size = obs.length;
            DatagramPacket p = new DatagramPacket(obs, size,
                                                  multicastAddress, multicastPort);
            send(p);
        } catch (IOException e) {
            log(sm.getString("multicastSender.sendException", e.toString()));
        }
    }

    /**
     * Send multicast data
     *
     * @param p data to be sent
     */
    private synchronized void send(DatagramPacket p) {
        try {
            multicastSocket.send(p);
        } catch (IOException e) {
            log(sm.getString("multicastSender.sendException", e.toString()));
        }
    }
}
