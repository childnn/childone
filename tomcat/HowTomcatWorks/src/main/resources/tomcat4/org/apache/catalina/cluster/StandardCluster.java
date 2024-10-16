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

import tomcat4.org.apache.catalina.*;
import tomcat4.org.apache.catalina.util.LifecycleSupport;
import tomcat4.org.apache.catalina.util.StringManager;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Vector;

/**
 * A <b>Cluster</b> implementation. Responsible for setting up
 * a cluster and provides callers with a valid multicast receiver/sender.
 *
 * @author Bip Thelin
 * @version $Revision: 1.7 $, $Date: 2004/08/26 21:28:18 $
 */

public final class StandardCluster
    implements Cluster, Lifecycle, Runnable {

    // ----------------------------------------------------- Instance Variables

    /**
     * Descriptive information about this component implementation.
     */
    private static final String info = "StandardCluster/1.0";

    /**
     * Name to register for the background thread.
     */
    private String threadName = "StandardCluster";

    /**
     * Name for logging purpose
     */
    private String clusterImpName = "StandardCluster";

    /**
     * The string manager for this package.
     */
    private StringManager sm = StringManager.getManager(Constants.Package);

    /**
     * Our Cluster info for this JVM
     */
    private ClusterMemberInfo localClusterMember = null;

    /**
     * The stack that keeps incoming cluster members
     */
    private Vector clusterMembers = new Vector();

    /**
     * The background thread.
     */
    private Thread thread = null;

    /**
     * The background thread completion semaphore.
     */
    private boolean threadDone = false;

    /**
     * The cluster name to join
     */
    private String clusterName = null;

    /**
     * The Container associated with this Cluster.
     */
    private Container container = null;

    /**
     * Our ClusterSender, used when replicating
     */
    private ClusterSender clusterSender = null;

    /**
     * Our ClusterReceiver
     */
    private ClusterReceiver clusterReceiver = null;

    /**
     * The MulticastPort to use with this cluster
     */
    private int multicastPort;

    /**
     * The MulticastAdress to use with this cluster
     */
    private InetAddress multicastAddress = null;

    /**
     * Our MulticastSocket
     */
    private MulticastSocket multicastSocket = null;

    /**
     * The lifecycle event support for this component.
     */
    private LifecycleSupport lifecycle = new LifecycleSupport(this);

    /**
     * Has this component been started?
     */
    private boolean started = false;

    /**
     * The property change support for this component.
     */
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * The debug level for this Container
     */
    private int debug = 0;

    /**
     * The interval for the background thread to sleep
     */
    private int checkInterval = 60;

    // ------------------------------------------------------------- Properties

    /**
     * Return descriptive information about this Cluster implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    public String getInfo() {
        return(this.info);
    }

    /**
     * Return a <code>String</code> containing the name of this
     * Cluster implementation, used for logging
     *
     * @return The Cluster implementation
     */
    protected String getName() {
        return(this.clusterImpName);
    }

    /**
     * Set the debug level for this component
     *
     * @param debug The debug level
     */
    public void setDebug(int debug) {
        this.debug = debug;
    }

    /**
     * Get the debug level for this component
     *
     * @return The debug level
     */
    public int getDebug() {
        return(this.debug);
    }

    /**
     * Set the name of the cluster to join, if no cluster with
     * this name is present create one.
     *
     * @param clusterName The clustername to join
     */
    public void setClusterName(String clusterName) {
        String oldClusterName = this.clusterName;
        this.clusterName = clusterName;
        support.firePropertyChange("clusterName",
                                   oldClusterName,
                                   this.clusterName);
    }

    /**
     * Return the name of the cluster that this Server is currently
     * configured to operate within.
     *
     * @return The name of the cluster associated with this server
     */
    public String getClusterName() {
        return(this.clusterName);
    }

    /**
     * Set the Container associated with our Cluster
     *
     * @param container The Container to use
     */
    public void setContainer(Container container) {
        Container oldContainer = this.container;
        this.container = container;
        support.firePropertyChange("container",
                                   oldContainer,
                                   this.container);
    }

    /**
     * Get the Container associated with our Cluster
     *
     * @return The Container associated with our Cluster
     */
    public Container getContainer() {
        return(this.container);
    }

    /**
     * Set the Port associated with our Cluster
     *
     * @param multicastPort The Port to use
     */
    public void setMulticastPort(int multicastPort) {
        int oldMulticastPort = this.multicastPort;
        this.multicastPort = multicastPort;
        support.firePropertyChange("multicastPort",
                                   oldMulticastPort,
                                   this.multicastPort);
    }

    /**
     * Get the Port associated with our Cluster
     *
     * @return The Port associated with our Cluster
     */
    public int getMulticastPort() {
        return(this.multicastPort);
    }

    /**
     * Set the Groupaddress associated with our Cluster
     *
     * @param multicastAddress The Groupaddress to use
     */
    public void setMulticastAddress(String multicastAddress) {
        try {
            InetAddress oldMulticastAddress = this.multicastAddress;
            this.multicastAddress = InetAddress.getByName(multicastAddress);
            support.firePropertyChange("multicastAddress",
                                       oldMulticastAddress,
                                       this.multicastAddress);
        } catch (UnknownHostException e) {
            log(sm.getString("standardCluster.invalidAddress",
                             multicastAddress));
        }
    }

    /**
     * Get the Groupaddress associated with our Cluster
     *
     * @return The Groupaddress associated with our Cluster
     */
    public InetAddress getMulticastAddress() {
        return(this.multicastAddress);
    }

    /**
     * Set the time in seconds for this component to
     * Sleep before it checks for new received data in the Cluster
     *
     * @param checkInterval The time to sleep
     */
    public void setCheckInterval(int checkInterval) {
        int oldCheckInterval = this.checkInterval;
        this.checkInterval = checkInterval;
        support.firePropertyChange("checkInterval",
                                   oldCheckInterval,
                                   this.checkInterval);
    }

    /**
     * Get the time in seconds this Cluster sleeps
     *
     * @return The time in seconds this Cluster sleeps
     */
    public int getCheckInterval() {
        return(this.checkInterval);
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Returns a collection containing <code>ClusterMemberInfo</code>
     * on the remote members of this Cluster. This method does
     * not include the local host, to retrieve
     * <code>ClusterMemberInfo</code> on the local host
     * use <code>getLocalClusterInfo()</code> instead.
     *
     * @return Collection with all members in the Cluster
     */
    public ClusterMemberInfo[] getRemoteClusterMembers() {
        return((ClusterMemberInfo[])this.clusterMembers.toArray());
    }

    /**
     * Return cluster information about the local host
     *
     * @return Cluster information
     */
    public ClusterMemberInfo getLocalClusterMember() {
        return(this.localClusterMember);
    }

    /**
     * Returns a <code>ClusterSender</code> which is the interface
     * to use when sending information in the Cluster. senderId is
     * used as a identifier so that information sent through this
     * instance can only be used with the respectice
     * <code>ClusterReceiver</code>
     *
     * @return The ClusterSender
     */
    public ClusterSender getClusterSender(String senderId) {
        Logger logger = null;
        MulticastSender send = new MulticastSender(senderId,
                                                   multicastSocket,
                                                   multicastAddress,
                                                   multicastPort);
        if (container != null)
            logger = container.getLogger();

        send.setLogger(logger);
        send.setDebug(debug);

        if(debug > 1)
            log(sm.getString("standardCluster.createSender", senderId));

        return(send);
    }

    /**
     * Returns a <code>ClusterReceiver</code> which is the interface
     * to use when receiving information in the Cluster. senderId is
     * used as a indentifier, only information send through the
     * <code>ClusterSender</code> with the same senderId can be received.
     *
     * @return The ClusterReceiver
     */
    public ClusterReceiver getClusterReceiver(String senderId) {
        Logger logger = null;
        MulticastReceiver recv = new MulticastReceiver(senderId,
                                                       multicastSocket,
                                                       multicastAddress,
                                                       multicastPort);

        if (container != null)
            logger = container.getLogger();

        recv.setDebug(debug);
        recv.setLogger(logger);
        recv.setCheckInterval(checkInterval);
        recv.start();

        if(debug > 1)
            log(sm.getString("standardCluster.createReceiver", senderId));

        return(recv);
    }

    /**
     * Log a message on the Logger associated with our Container (if any).
     *
     * @param message Message to be logged
     */
    protected void log(String message) {
        Logger logger = null;

        if (container != null)
            logger = container.getLogger();

        if (logger != null) {
            logger.log(getName() + "[" + container.getName() + "]: "
                       + message);
        } else {
            String containerName = null;
            if (container != null)
                containerName = container.getName();

            System.out.println(getName() + "[" + containerName
                               + "]: " + message);
        }
    }

    // --------------------------------------------------------- Private Methods

    private void processReceive() {
        Object[] objs = clusterReceiver.getObjects();

        for(int i=0; i < objs.length;i++) {
            clusterMembers.add((ClusterMemberInfo)objs[i]);
        }
    }

    // ------------------------------------------------------ Lifecycle Methods


    /**
     * Add a lifecycle event listener to this component.
     *
     * @param listener The listener to add
     */
    public void addLifecycleListener(LifecycleListener listener) {
        lifecycle.addLifecycleListener(listener);
    }


    /**
     * Get the lifecycle listeners associated with this lifecycle. If this 
     * Lifecycle has no listeners registered, a zero-length array is returned.
     */
    public LifecycleListener[] findLifecycleListeners() {

        return lifecycle.findLifecycleListeners();

    }


    /**
     * Remove a lifecycle event listener from this component.
     *
     * @param listener The listener to remove
     */
    public void removeLifecycleListener(LifecycleListener listener) {
        lifecycle.removeLifecycleListener(listener);
    }

    @Override
    public void init() throws LifecycleException {

    }

    /**
     * Prepare for the beginning of active use of the public methods of this
     * component.  This method should be called after <code>configure()</code>,
     * and before any of the public methods of the component are utilized.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    public void start() throws LifecycleException {
        // Validate and update our current component state
        if (started)
            throw new LifecycleException(sm.getString("standardCluster.alreadyStarted"));

        try {
            multicastSocket = new MulticastSocket(multicastPort);

            if(multicastSocket != null && multicastAddress != null) {
                multicastSocket.joinGroup(multicastAddress);

                clusterSender = getClusterSender(getName());
                clusterReceiver = getClusterReceiver(getName());

                localClusterMember = new ClusterMemberInfo();
                localClusterMember.setClusterName(getClusterName());
                localClusterMember.setHostName(null);
                localClusterMember.setClusterInfo(getInfo());

                clusterSender.send(localClusterMember);

                if (debug > 1)
                    log(sm.getString("standardCluster.joinGroup",
                                     multicastAddress));
            } else {
                log(sm.getString("standardCluster.socketOrAddressNull"));
            }
        } catch (IOException e) {
            log(sm.getString("standardCluster.joinException", e.toString()));
        }

        lifecycle.fireLifecycleEvent(START_EVENT, null);
        started = true;

        // Start the background reaper thread
        threadStart();
    }

    /**
     * Gracefully terminate the active use of the public methods of this
     * component.  This method should be the last one called on a given
     * instance of this component.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that needs to be reported
     */
    public void stop() throws LifecycleException {
        // Validate and update our current component state
        if (!started)
            log(sm.getString("standardCluster.notStarted"));

        try {
            multicastSocket.leaveGroup(multicastAddress);
            multicastSocket = null;
        } catch (IOException e) {
            log(sm.getString("standardCluster.leaveException",
                             multicastAddress));
        }

        if (debug > 1)
            log(sm.getString("standardCluster.leaveGroup",
                             multicastAddress));

        lifecycle.fireLifecycleEvent(STOP_EVENT, null);
        started = false;

        // Stop the background reaper thread
        threadStop();
    }

    @Override
    public void destroy() throws LifecycleException {

    }

    @Override
    public LifecycleState getState() {
        return null;
    }

    @Override
    public String getStateName() {
        return null;
    }

    // ------------------------------------------------------ Background Thread

    /**
     * The background thread.
     */
    public void run() {
        // Loop until the termination semaphore is set
        while (!threadDone) {
            processReceive();
            threadSleep();
        }
    }

    /**
     * Sleep for the duration specified by the <code>checkInterval</code>
     * property.
     */
    private void threadSleep() {
        try {
            Thread.sleep(checkInterval * 1000L);
        } catch (InterruptedException e) {
            ;
        }
    }

    /**
     * Start the background thread.
     */
    private void threadStart() {
        if (thread != null)
            return;

        threadDone = false;
        threadName = "StandardCluster[" + getClusterName() + "]";
        thread = new Thread(this, threadName);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Stop the background thread.
     */
    private void threadStop() {
        if (thread == null)
            return;

        threadDone = true;
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            ;
        }

        thread = null;
    }
}
