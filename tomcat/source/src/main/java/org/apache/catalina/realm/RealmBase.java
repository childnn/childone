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


package org.apache.catalina.realm;


import org.apache.catalina.*;
import org.apache.catalina.util.HexUtils;
import org.apache.catalina.util.LifecycleSupport;
import org.apache.catalina.util.MD5Encoder;
import org.apache.catalina.util.StringManager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.cert.X509Certificate;


/**
 * Simple implementation of <b>Realm</b> that reads an XML file to configure
 * the valid users, passwords, and roles.  The file format (and default file
 * location) are identical to those currently supported by Tomcat 3.X.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.14 $ $Date: 2004/08/26 21:37:21 $
 */

public abstract class RealmBase
        implements Lifecycle, Realm {


    // ----------------------------------------------------- Instance Variables


    /**
     * Descriptive information about this Realm implementation.
     */
    protected static final String info =
            "org.apache.catalina.realm.RealmBase/1.0";
    /**
     * The MD5 helper object for this class.
     */
    protected static final MD5Encoder md5Encoder = new MD5Encoder();
    /**
     * MD5 message digest provider.
     */
    protected static MessageDigest md5Helper;
    /**
     * The string manager for this package.
     */
    protected static StringManager sm =
            StringManager.getManager(Constants.Package);
    /**
     * The Container with which this Realm is associated.
     */
    protected Container container = null;
    /**
     * The debugging detail level for this component.
     */
    protected int debug = 0;
    /**
     * Digest algorithm used in storing passwords in a non-plaintext format.
     * Valid values are those accepted for the algorithm name by the
     * MessageDigest class, or <code>null</code> if no digesting should
     * be performed.
     */
    protected String digest = null;
    /**
     * The lifecycle event support for this component.
     */
    protected LifecycleSupport lifecycle = new LifecycleSupport(this);
    /**
     * The MessageDigest object for digesting user credentials (passwords).
     */
    protected MessageDigest md = null;
    /**
     * Has this component been started?
     */
    protected boolean started = false;


    /**
     * The property change support for this component.
     */
    protected PropertyChangeSupport support = new PropertyChangeSupport(this);


    /**
     * Should we validate client certificate chains when they are presented?
     */
    protected boolean validate = true;


    // ------------------------------------------------------------- Properties

    /**
     * Digest password using the algorithm especificied and
     * convert the result to a corresponding hex string.
     * If exception, the plain credentials string is returned
     *
     * @param credentials Password or other credentials to use in
     *                    authenticating this username
     * @param algorithm   Algorithm used to do th digest
     */
    public final static String Digest(String credentials, String algorithm) {

        try {
            // Obtain a new message digest with "digest" encryption
            MessageDigest md =
                    (MessageDigest) MessageDigest.getInstance(algorithm).clone();
            // encode the credentials
            md.update(credentials.getBytes());
            // Digest the credentials and return as hexadecimal
            return (HexUtils.convert(md.digest()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return credentials;
        }

    }

    /**
     * Digest password using the algorithm especificied and
     * convert the result to a corresponding hex string.
     * If exception, the plain credentials string is returned
     */
    public static void main(String args[]) {

        if (args.length > 2 && args[0].equalsIgnoreCase("-a")) {
            for (int i = 2; i < args.length; i++) {
                System.out.print(args[i] + ":");
                System.out.println(Digest(args[i], args[1]));
            }
        } else {
            System.out.println
                    ("Usage: RealmBase -a <algorithm> <credentials>");
        }

    }

    /**
     * Return the Container with which this Realm has been associated.
     */
    public Container getContainer() {

        return (container);

    }

    /**
     * Set the Container with which this Realm has been associated.
     *
     * @param container The associated Container
     */
    public void setContainer(Container container) {

        Container oldContainer = this.container;
        this.container = container;
        support.firePropertyChange("container", oldContainer, this.container);

    }

    /**
     * Return the debugging detail level for this component.
     */
    public int getDebug() {

        return (this.debug);

    }

    /**
     * Set the debugging detail level for this component.
     *
     * @param debug The new debugging detail level
     */
    public void setDebug(int debug) {

        this.debug = debug;

    }

    /**
     * Return the digest algorithm  used for storing credentials.
     */
    public String getDigest() {

        return digest;

    }

    /**
     * Set the digest algorithm used for storing credentials.
     *
     * @param digest The new digest algorithm
     */
    public void setDigest(String digest) {

        this.digest = digest;

    }

    /**
     * Return descriptive information about this Realm implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    public String getInfo() {

        return info;

    }


    // --------------------------------------------------------- Public Methods

    /**
     * Return the "validate certificate chains" flag.
     */
    public boolean getValidate() {

        return (this.validate);

    }

    /**
     * Set the "validate certificate chains" flag.
     *
     * @param validate The new validate certificate chains flag
     */
    public void setValidate(boolean validate) {

        this.validate = validate;

    }

    /**
     * Add a property change listener to this component.
     *
     * @param listener The listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {

        support.addPropertyChangeListener(listener);

    }

    /**
     * Return the Principal associated with the specified username and
     * credentials, if there is one; otherwise return <code>null</code>.
     *
     * @param username    Username of the Principal to look up
     * @param credentials Password or other credentials to use in
     *                    authenticating this username
     */
    public Principal authenticate(String username, String credentials) {

        String serverCredentials = getPassword(username);

        if ((serverCredentials == null)
                || (!serverCredentials.equals(credentials)))
            return null;

        return getPrincipal(username);

    }

    /**
     * Return the Principal associated with the specified username and
     * credentials, if there is one; otherwise return <code>null</code>.
     *
     * @param username    Username of the Principal to look up
     * @param credentials Password or other credentials to use in
     *                    authenticating this username
     */
    public Principal authenticate(String username, byte[] credentials) {

        return (authenticate(username, credentials.toString()));

    }

    /**
     * Return the Principal associated with the specified username, which
     * matches the digest calculated using the given parameters using the
     * method described in RFC 2617; otherwise return <code>null</code>.
     *
     * @param username     Username of the Principal to look up
     * @param clientDigest Digest which has been submitted by the client
     * @param nOnce        Unique (or supposedly unique) token which has been used
     *                     for this request
     * @param realm        Realm name
     * @param md5a2        Second MD5 digest used to calculate the digest :
     *                     MD5(Method + ":" + uri)
     */
    public Principal authenticate(String username, String clientDigest,
                                  String nOnce, String nc, String cnonce,
                                  String qop, String realm,
                                  String md5a2) {

        /*
          System.out.println("Digest : " + clientDigest);

          System.out.println("************ Digest info");
          System.out.println("Username:" + username);
          System.out.println("ClientSigest:" + clientDigest);
          System.out.println("nOnce:" + nOnce);
          System.out.println("nc:" + nc);
          System.out.println("cnonce:" + cnonce);
          System.out.println("qop:" + qop);
          System.out.println("realm:" + realm);
          System.out.println("md5a2:" + md5a2);
        */


        String md5a1 = getDigest(username, realm);
        if (md5a1 == null)
            return null;
        String serverDigestValue;
        if (!"auth".equals(qop))
            serverDigestValue = md5a1 + ":" + nOnce + ":" + md5a2;
        else
            serverDigestValue = md5a1 + ":" + nOnce + ":" + nc + ":"
                    + cnonce + ":" + qop + ":" + md5a2;
        String serverDigest =
                md5Encoder.encode(md5Helper.digest(serverDigestValue.getBytes()));
        //System.out.println("Server digest : " + serverDigest);

        if (serverDigest.equals(clientDigest))
            return getPrincipal(username);
        else
            return null;
    }

    /**
     * Return the Principal associated with the specified chain of X509
     * client certificates.  If there is none, return <code>null</code>.
     *
     * @param certs Array of client certificates, with the first one in
     *              the array being the certificate of the client itself.
     */
    public Principal authenticate(X509Certificate certs[]) {

        if ((certs == null) || (certs.length < 1))
            return (null);

        // Check the validity of each certificate in the chain
        if (debug >= 1)
            log("Authenticating client certificate chain");
        if (validate) {
            for (int i = 0; i < certs.length; i++) {
                if (debug >= 2)
                    log(" Checking validity for '" +
                            certs[i].getSubjectDN().getName() + "'");
                try {
                    certs[i].checkValidity();
                } catch (Exception e) {
                    if (debug >= 2)
                        log("  Validity exception", e);
                    return (null);
                }
            }
        }

        // Check the existence of the client Principal in our database
        return (getPrincipal(certs[0].getSubjectDN().getName()));

    }


    // ------------------------------------------------------ Lifecycle Methods

    /**
     * Return <code>true</code> if the specified Principal has the specified
     * security role, within the context of this Realm; otherwise return
     * <code>false</code>.  This method can be overridden by Realm
     * implementations, but the default is adequate when an instance of
     * <code>GenericPrincipal</code> is used to represent authenticated
     * Principals from this Realm.
     *
     * @param principal Principal for whom the role is to be checked
     * @param role      Security role to be checked
     */
    public boolean hasRole(Principal principal, String role) {

        if ((principal == null) || (role == null) ||
                !(principal instanceof GenericPrincipal))
            return (false);
        GenericPrincipal gp = (GenericPrincipal) principal;
        if (!(gp.getRealm() == this))
            return (false);
        boolean result = gp.hasRole(role);
        if (debug >= 2) {
            String name = principal.getName();
            if (result)
                log(sm.getString("realmBase.hasRoleSuccess", name, role));
            else
                log(sm.getString("realmBase.hasRoleFailure", name, role));
        }
        return (result);

    }

    /**
     * Remove a property change listener from this component.
     *
     * @param listener The listener to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {

        support.removePropertyChangeListener(listener);

    }

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


    // ------------------------------------------------------ Protected Methods

    /**
     * Prepare for the beginning of active use of the public methods of this
     * component.  This method should be called before any of the public
     * methods of this component are utilized.  It should also send a
     * LifecycleEvent of type START_EVENT to any registered listeners.
     *
     * @throws LifecycleException if this component detects a fatal error
     *                            that prevents this component from being used
     */
    public void start() throws LifecycleException {

        // Validate and update our current component state
        if (started)
            throw new LifecycleException
                    (sm.getString("realmBase.alreadyStarted"));
        lifecycle.fireLifecycleEvent(START_EVENT, null);
        started = true;

        // Create a MessageDigest instance for credentials, if desired
        if (digest != null) {
            try {
                md = MessageDigest.getInstance(digest);
            } catch (NoSuchAlgorithmException e) {
                throw new LifecycleException
                        (sm.getString("realmBase.algorithm", digest), e);
            }
        }

    }

    /**
     * Gracefully terminate the active use of the public methods of this
     * component.  This method should be the last one called on a given
     * instance of this component.  It should also send a LifecycleEvent
     * of type STOP_EVENT to any registered listeners.
     *
     * @throws LifecycleException if this component detects a fatal error
     *                            that needs to be reported
     */
    public void stop()
            throws LifecycleException {

        // Validate and update our current component state
        if (!started)
            throw new LifecycleException
                    (sm.getString("realmBase.notStarted"));
        lifecycle.fireLifecycleEvent(STOP_EVENT, null);
        started = false;

        // Clean up allocated resources
        md = null;

    }

    /**
     * Digest the password using the specified algorithm and
     * convert the result to a corresponding hexadecimal string.
     * If exception, the plain credentials string is returned.
     *
     * <strong>IMPLEMENTATION NOTE</strong> - This implementation is
     * synchronized because it reuses the MessageDigest instance.
     * This should be faster than cloning the instance on every request.
     *
     * @param credentials Password or other credentials to use in
     *                    authenticating this username
     */
    protected String digest(String credentials) {

        // If no MessageDigest instance is specified, return unchanged
        if (hasMessageDigest() == false)
            return (credentials);

        // Digest the user credentials and return as hexadecimal
        synchronized (this) {
            try {
                md.reset();
                md.update(credentials.getBytes());
                return (HexUtils.convert(md.digest()));
            } catch (Exception e) {
                log(sm.getString("realmBase.digest"), e);
                return (credentials);
            }
        }

    }

    protected boolean hasMessageDigest() {
        return !(md == null);
    }

    /**
     * Return the digest associated with given principal's user name.
     */
    protected String getDigest(String username, String realmName) {
        if (md5Helper == null) {
            try {
                md5Helper = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                throw new IllegalStateException();
            }
        }
        String digestValue = username + ":" + realmName + ":"
                + getPassword(username);
        byte[] digest =
                md5Helper.digest(digestValue.getBytes());
        return md5Encoder.encode(digest);
    }

    /**
     * Return a short name for this Realm implementation, for use in
     * log messages.
     */
    protected abstract String getName();

    /**
     * Return the password associated with the given principal's user name.
     */
    protected abstract String getPassword(String username);

    /**
     * Return the Principal associated with the given user name.
     */
    protected abstract Principal getPrincipal(String username);


    // --------------------------------------------------------- Static Methods

    /**
     * Log a message on the Logger associated with our Container (if any)
     *
     * @param message Message to be logged
     */
    protected void log(String message) {

        Logger logger = null;
        String name = null;
        if (container != null) {
            logger = container.getLogger();
            name = container.getName();
        }

        if (logger != null) {
            logger.log(getName() + "[" + name + "]: " + message);
        } else {
            System.out.println(getName() + "[" + name + "]: " + message);
        }

    }

    /**
     * Log a message on the Logger associated with our Container (if any)
     *
     * @param message   Message to be logged
     * @param throwable Associated exception
     */
    protected void log(String message, Throwable throwable) {

        Logger logger = null;
        String name = null;
        if (container != null) {
            logger = container.getLogger();
            name = container.getName();
        }

        if (logger != null) {
            logger.log(getName() + "[" + name + "]: " + message, throwable);
        } else {
            System.out.println(getName() + "[" + name + "]: " + message);
            throwable.printStackTrace(System.out);
        }
    }


}
