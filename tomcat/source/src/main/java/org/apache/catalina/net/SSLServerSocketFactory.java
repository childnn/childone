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
package org.apache.catalina.net;

import com.sun.net.ssl.KeyManagerFactory;
import com.sun.net.ssl.SSLContext;

import javax.net.ssl.SSLServerSocket;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.security.*;
import java.security.cert.CertificateException;


/**
 * Socket factory for SSL sockets, using the Java Server Sockets Extension
 * (JSSE) reference implementation support classes.  Besides the usual
 * configuration mechanism based on setting JavaBeans properties, this
 * component may also be configured by passing a series of attributes set
 * with calls to <code>setAttribute()</code>.  The following attribute
 * names are recognized, with default values in square brackets:
 * <ul>
 * <li><strong>algorithm</strong> - Certificate encoding algorithm
 *     to use. [SunX509]</li>
 * <li><strong>clientAuth</strong> - Require client authentication if
 *     set to <code>true</code>. [false]</li>
 * <li><strong>keystoreFile</strong> - Pathname to the Key Store file to be
 *     loaded.  This must be an absolute path, or a relative path that
 *     is resolved against the "catalina.base" system property.
 *     ["./keystore" in the user home directory]</li>
 * <li><strong>keystorePass</strong> - Password for the Key Store file to be
 *     loaded. ["changeit"]</li>
 * <li><strong>keystoreType</strong> - Type of the Key Store file to be
 *     loaded. ["JKS"]</li>
 * <li><strong>protocol</strong> - SSL protocol to use. [TLS]</li>
 * </ul>
 *
 * @author Harish Prabandham
 * @author Costin Manolache
 * @author Craig McClanahan
 */

public class SSLServerSocketFactory
        implements org.apache.catalina.net.ServerSocketFactory {


    // ----------------------------------------------------- Instance Variables


    /**
     * The name of our protocol handler package for the "https:" protocol.
     */
    private static final String PROTOCOL_HANDLER =
            "com.sun.net.ssl.internal.www.protocol";


    /**
     * The name of the system property containing a "|" delimited list of
     * protocol handler packages.
     */
    private static final String PROTOCOL_PACKAGES =
            "java.protocol.handler.pkgs";

    /**
     * The configured socket factory.
     */
    private javax.net.ssl.SSLServerSocketFactory sslProxy = null;


    /**
     * The trust manager factory used with JSSE 1.0.1.
     */
    //    TrustManagerFactory trustManagerFactory = null;


    // ------------------------------------------------------------- Properties


    /**
     * Certificate encoding algorithm to be used.
     */
    private String algorithm = "SunX509";
    /**
     * Should we require client authentication?
     */
    private boolean clientAuth = false;
    /**
     * The internal represenation of the key store file that contains
     * our server certificate.
     */
    private KeyStore keyStore = null;
    /**
     * Pathname to the key store file to be used.
     */
    private String keystoreFile =
            System.getProperty("user.home") + File.separator + ".keystore";
    /**
     * Password for accessing the key store file.
     */
    private String keystorePass = "changeit";
    /**
     * Storeage type of the key store file to be used.
     */
    private String keystoreType = "JKS";
    /**
     * SSL protocol variant to use.
     */
    private String protocol = "TLS";

    public String getAlgorithm() {
        return (this.algorithm);
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public boolean getClientAuth() {
        return (this.clientAuth);
    }

    public void setClientAuth(boolean clientAuth) {
        this.clientAuth = clientAuth;
    }

    public KeyStore getKeyStore()
            throws IOException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException,
            KeyManagementException {
        if (sslProxy == null)
            initialize();
        return (this.keyStore);
    }

    public String getKeystoreFile() {
        return (this.keystoreFile);
    }

    public void setKeystoreFile(String keystoreFile) {
        File file = new File(keystoreFile);
        if (!file.isAbsolute())
            file = new File(System.getProperty("catalina.base"),
                    keystoreFile);
        this.keystoreFile = file.getAbsolutePath();
    }

    public void setKeystorePass(String keystorePass) {
        this.keystorePass = keystorePass;
    }

    public String getKeystoreType() {
        return (this.keystoreType);
    }

    public void setKeystoreType(String keystoreType) {
        this.keystoreType = keystoreType;
    }

    public String getProtocol() {
        return (this.protocol);
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Return a server socket that uses all network interfaces on the host,
     * and is bound to a specified port.  The socket is configured with the
     * socket options (such as accept timeout) given to this factory.
     *
     * @param port Port to listen to
     * @throws IOException               input/output or network error
     * @throws KeyStoreException         error instantiating the
     *                                   KeyStore from file
     * @throws NoSuchAlgorithmException  KeyStore algorithm unsupported
     *                                   by current provider
     * @throws CertificateException      general certificate error
     * @throws UnrecoverableKeyException internal KeyStore problem with
     *                                   the certificate
     * @throws KeyManagementException    problem in the key management
     *                                   layer
     */
    public ServerSocket createSocket(int port)
            throws IOException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException,
            KeyManagementException {

        if (sslProxy == null)
            initialize();
        ServerSocket socket =
                sslProxy.createServerSocket(port);
        initServerSocket(socket);
        return (socket);

    }


    /**
     * Return a server socket that uses all network interfaces on the host,
     * and is bound to a specified port, and uses the specified
     * connection backlog.  The socket is configured with the
     * socket options (such as accept timeout) given to this factory.
     *
     * @param port    Port to listen to
     * @param backlog Maximum number of connections to be queued
     * @throws IOException               input/output or network error
     * @throws KeyStoreException         error instantiating the
     *                                   KeyStore from file
     * @throws NoSuchAlgorithmException  KeyStore algorithm unsupported
     *                                   by current provider
     * @throws CertificateException      general certificate error
     * @throws UnrecoverableKeyException internal KeyStore problem with
     *                                   the certificate
     * @throws KeyManagementException    problem in the key management
     *                                   layer
     */
    public ServerSocket createSocket(int port, int backlog)
            throws IOException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException,
            KeyManagementException {

        if (sslProxy == null)
            initialize();
        ServerSocket socket =
                sslProxy.createServerSocket(port, backlog);
        initServerSocket(socket);
        return (socket);

    }


    /**
     * Return a server socket that uses the specified interface on the host,
     * and is bound to a specified port, and uses the specified
     * connection backlog.  The socket is configured with the
     * socket options (such as accept timeout) given to this factory.
     *
     * @param port      Port to listen to
     * @param backlog   Maximum number of connections to be queued
     * @param ifAddress Address of the interface to be used
     * @throws IOException               input/output or network error
     * @throws KeyStoreException         error instantiating the
     *                                   KeyStore from file
     * @throws NoSuchAlgorithmException  KeyStore algorithm unsupported
     *                                   by current provider
     * @throws CertificateException      general certificate error
     * @throws UnrecoverableKeyException internal KeyStore problem with
     *                                   the certificate
     * @throws KeyManagementException    problem in the key management
     *                                   layer
     */
    public ServerSocket createSocket(int port, int backlog,
                                     InetAddress ifAddress)
            throws IOException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException,
            KeyManagementException {

        if (sslProxy == null)
            initialize();
        ServerSocket socket =
                sslProxy.createServerSocket(port, backlog, ifAddress);
        initServerSocket(socket);
        return (socket);

    }


    // -------------------------------------------------------- Private Methods


    /**
     * Initialize objects that will be required to create sockets.
     *
     * @throws IOException               input/output or network error
     * @throws KeyStoreException         error instantiating the
     *                                   KeyStore from file
     * @throws NoSuchAlgorithmException  KeyStore algorithm unsupported
     *                                   by current provider
     * @throws CertificateException      general certificate error
     * @throws UnrecoverableKeyException internal KeyStore problem with
     *                                   the certificate
     * @throws KeyManagementException    problem in the key management
     *                                   layer
     */
    private synchronized void initialize()
            throws IOException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException, UnrecoverableKeyException,
            KeyManagementException {

        initHandler();
        initKeyStore();
        initProxy();

    }


    /**
     * Register our URLStreamHandler for the "https:" protocol.
     */
    private void initHandler() {

        String packages = System.getProperty(PROTOCOL_PACKAGES);
        if (packages == null)
            packages = PROTOCOL_HANDLER;
        else if (packages.indexOf(PROTOCOL_HANDLER) < 0)
            packages += "|" + PROTOCOL_HANDLER;
        System.setProperty(PROTOCOL_PACKAGES, packages);

    }


    /**
     * Initialize the internal representation of the key store file.
     *
     * @throws IOException              input/output or network error
     * @throws KeyStoreException        error instantiating the
     *                                  KeyStore from file
     * @throws NoSuchAlgorithmException KeyStore algorithm unsupported
     *                                  by current provider
     * @throws CertificateException     general certificate error
     */
    private void initKeyStore()
            throws IOException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException {

        FileInputStream istream = null;

        try {
            keyStore = KeyStore.getInstance(keystoreType);
            istream = new FileInputStream(keystoreFile);
            keyStore.load(istream, keystorePass.toCharArray());
        } catch (IOException ioe) {
            throw ioe;
        } catch (KeyStoreException kse) {
            throw kse;
        } catch (NoSuchAlgorithmException nsae) {
            throw nsae;
        } catch (CertificateException ce) {
            throw ce;
        } finally {
            if (istream != null)
                istream.close();
        }

    }


    /**
     * Initialize the SSL socket factory.
     *
     * @throws KeyStoreException         error instantiating the
     *                                   KeyStore from file
     * @throws NoSuchAlgorithmException  KeyStore algorithm unsupported
     *                                   by current provider
     * @throws UnrecoverableKeyException internal KeyStore problem with
     *                                   the certificate
     * @throws KeyManagementException    problem in the key management
     *                                   layer
     */
    private void initProxy()
            throws KeyStoreException, NoSuchAlgorithmException,
            UnrecoverableKeyException, KeyManagementException {

        // Register the JSSE security Provider (if it is not already there)
        try {
            Security.addProvider((java.security.Provider)
                    Class.forName("com.sun.net.ssl.internal.ssl.Provider").newInstance());
        } catch (Throwable t) {
            ;
        }

        // Create an SSL context used to create an SSL socket factory
        SSLContext context = SSLContext.getInstance(protocol);

        // Create the key manager factory used to extract the server key
        KeyManagerFactory keyManagerFactory =
                KeyManagerFactory.getInstance(algorithm);
        keyManagerFactory.init(keyStore, keystorePass.toCharArray());

        // Create the trust manager factory used for checking certificates
        /*
          trustManagerFactory = TrustManagerFactory.getInstance(algorithm);
          trustManagerFactory.init(keyStore);
        */

        // Initialize the context with the key managers
        context.init(keyManagerFactory.getKeyManagers(), null,
                new java.security.SecureRandom());

        // Create the proxy and return
        sslProxy = context.getServerSocketFactory();

    }


    /**
     * Set the requested properties for this server socket.
     *
     * @param ssocket The server socket to be configured
     */
    private void initServerSocket(ServerSocket ssocket) {

        SSLServerSocket socket = (SSLServerSocket) ssocket;

        // Enable all available cipher suites when the socket is connected
        String cipherSuites[] = socket.getSupportedCipherSuites();
        socket.setEnabledCipherSuites(cipherSuites);

        // Set client authentication if necessary
        socket.setNeedClientAuth(clientAuth);

    }


}
