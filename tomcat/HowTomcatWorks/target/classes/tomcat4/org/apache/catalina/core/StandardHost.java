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


package tomcat4.org.apache.catalina.core;


import tomcat4.org.apache.catalina.*;
import tomcat4.org.apache.catalina.valves.ErrorDispatcherValve;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * Standard implementation of the <b>Host</b> interface.  Each
 * child container must be a Context implementation to process the
 * requests directed to a particular web application.
 *
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 * @version $Revision: 1.32 $ $Date: 2004/08/26 21:32:20 $
 */

public class StandardHost
        extends ContainerBase
        implements Deployer, Host {


    // ----------------------------------------------------------- Constructors


    /**
     * Create a new StandardHost component with the default basic Valve.
     */
    public StandardHost() {

        super();
        pipeline.setBasic(new StandardHostValve());

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The set of aliases for this Host.
     */
    private String[] aliases = new String[0];


    /**
     * The application root for this Host.
     */
    private String appBase = ".";


    /**
     * The auto deploy flag for this Host.
     */
    private boolean autoDeploy = true;


    /**
     * The Java class name of the default context configuration class
     * for deployed web applications.
     */
    private String configClass =
            "org.apache.catalina.startup.ContextConfig";


    /**
     * The Java class name of the default Context implementation class for
     * deployed web applications.
     */
    private String contextClass =
            "org.apache.catalina.core.StandardContext";


    /**
     * The <code>Deployer</code> to whom we delegate application
     * deployment requests.
     */
    //private Deployer deployer = new StandardHostDeployer(this);


    /**
     * deploy Context XML config files property.
     */
    private boolean deployXML = true;


    /**
     * The Java class name of the default error reporter implementation class
     * for deployed web applications.
     */
    private String errorReportValveClass =
            "org.apache.catalina.valves.ErrorReportValve";


    /**
     * The descriptive information string for this implementation.
     */
    private static final String info =
            "org.apache.catalina.core.StandardHost/1.0";


    /**
     * The live deploy flag for this Host.
     */
    private boolean liveDeploy = true;


    /**
     * The Java class name of the default Mapper class for this Container.
     */
    private String mapperClass =
            "org.apache.catalina.core.StandardHostMapper";


    /**
     * Unpack WARs property.
     */
    private boolean unpackWARs = true;


    /**
     * Work Directory base for applications.
     */
    private String workDir = null;


    /**
     * DefaultContext config
     */
    private DefaultContext defaultContext;


    // ------------------------------------------------------------- Properties


    /**
     * Return the application root for this Host.  This can be an absolute
     * pathname, a relative pathname, or a URL.
     */
    public String getAppBase() {

        return (this.appBase);

    }


    /**
     * Set the application root for this Host.  This can be an absolute
     * pathname, a relative pathname, or a URL.
     *
     * @param appBase The new application root
     */
    public void setAppBase(String appBase) {

        String oldAppBase = this.appBase;
        this.appBase = appBase;
        support.firePropertyChange("appBase", oldAppBase, this.appBase);

    }


    /**
     * Return the value of the auto deploy flag.  If true, it indicates that
     * this host's child webapps should be discovred and automatically
     * deployed at startup time.
     */
    public boolean getAutoDeploy() {

        return (this.autoDeploy);

    }


    /**
     * Set the auto deploy flag value for this host.
     *
     * @param autoDeploy The new auto deploy flag
     */
    public void setAutoDeploy(boolean autoDeploy) {

        boolean oldAutoDeploy = this.autoDeploy;
        this.autoDeploy = autoDeploy;
        support.firePropertyChange("autoDeploy", oldAutoDeploy,
                this.autoDeploy);

    }


    /**
     * Return the Java class name of the context configuration class
     * for new web applications.
     */
    public String getConfigClass() {

        return (this.configClass);

    }


    /**
     * Set the Java class name of the context configuration class
     * for new web applications.
     *
     * @param configClass The new context configuration class
     */
    public void setConfigClass(String configClass) {

        String oldConfigClass = this.configClass;
        this.configClass = configClass;
        support.firePropertyChange("configClass",
                oldConfigClass, this.configClass);

    }


    /**
     * Set the DefaultContext
     * for new web applications.
     *
     * @param defaultContext The new DefaultContext
     */
    public void addDefaultContext(DefaultContext defaultContext) {

        DefaultContext oldDefaultContext = this.defaultContext;
        this.defaultContext = defaultContext;
        support.firePropertyChange("defaultContext",
                oldDefaultContext, this.defaultContext);

    }


    /**
     * Retrieve the DefaultContext for new web applications.
     */
    public DefaultContext getDefaultContext() {
        return (this.defaultContext);
    }


    /**
     * Return the Java class name of the Context implementation class
     * for new web applications.
     */
    public String getContextClass() {

        return (this.contextClass);

    }


    /**
     * Set the Java class name of the Context implementation class
     * for new web applications.
     *
     * @param contextClass The new context implementation class
     */
    public void setContextClass(String contextClass) {

        String oldContextClass = this.contextClass;
        this.contextClass = contextClass;
        support.firePropertyChange("contextClass",
                oldContextClass, this.contextClass);

    }


    /**
     * Deploy XML Context config files flag accessor.
     */
    public boolean isDeployXML() {

        return (deployXML);

    }


    /**
     * Deploy XML Context config files flag mutator.
     */
    public void setDeployXML(boolean deployXML) {

        this.deployXML = deployXML;

    }


    /**
     * Return the value of the live deploy flag.  If true, it indicates that
     * a background thread should be started that looks for web application
     * context files, WAR files, or unpacked directories being dropped in to
     * the <code>appBase</code> directory, and deploys new ones as they are
     * encountered.
     */
    public boolean getLiveDeploy() {

        return (this.liveDeploy);

    }


    /**
     * Set the live deploy flag value for this host.
     *
     * @param liveDeploy The new live deploy flag
     */
    public void setLiveDeploy(boolean liveDeploy) {

        boolean oldLiveDeploy = this.liveDeploy;
        this.liveDeploy = liveDeploy;
        support.firePropertyChange("liveDeploy", oldLiveDeploy,
                this.liveDeploy);

    }


    /**
     * Return the default Mapper class name.
     */
    public String getMapperClass() {

        return (this.mapperClass);

    }


    /**
     * Set the default Mapper class name.
     *
     * @param mapperClass The new default Mapper class name
     */
    public void setMapperClass(String mapperClass) {

        String oldMapperClass = this.mapperClass;
        this.mapperClass = mapperClass;
        support.firePropertyChange("mapperClass",
                oldMapperClass, this.mapperClass);

    }


    /**
     * Return the Java class name of the error report valve class
     * for new web applications.
     */
    public String getErrorReportValveClass() {

        return (this.errorReportValveClass);

    }


    /**
     * Set the Java class name of the error report valve class
     * for new web applications.
     *
     * @param errorReportValveClass The new error report valve class
     */
    public void setErrorReportValveClass(String errorReportValveClass) {

        String oldErrorReportValveClassClass = this.errorReportValveClass;
        this.errorReportValveClass = errorReportValveClass;
        support.firePropertyChange("errorReportValveClass",
                oldErrorReportValveClassClass,
                this.errorReportValveClass);

    }


    /**
     * Return the canonical, fully qualified, name of the virtual host
     * this Container represents.
     */
    public String getName() {

        return (name);

    }


    /**
     * Set the canonical, fully qualified, name of the virtual host
     * this Container represents.
     *
     * @param name Virtual host name
     * @throws IllegalArgumentException if name is null
     */
    public void setName(String name) {

        if (name == null)
            throw new IllegalArgumentException
                    (sm.getString("standardHost.nullName"));

        name = name.toLowerCase();      // Internally all names are lower case

        String oldName = this.name;
        this.name = name;
        support.firePropertyChange("name", oldName, this.name);

    }


    /**
     * Unpack WARs flag accessor.
     */
    public boolean isUnpackWARs() {

        return (unpackWARs);

    }


    /**
     * Unpack WARs flag mutator.
     */
    public void setUnpackWARs(boolean unpackWARs) {

        this.unpackWARs = unpackWARs;

    }


    /**
     * Host work directory base.
     */
    public String getWorkDir() {

        return (workDir);
    }


    /**
     * Host work directory base.
     */
    public void setWorkDir(String workDir) {

        this.workDir = workDir;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Install the StandardContext portion of the DefaultContext
     * configuration into current Context.
     *
     * @param context current web application context
     */
    public void installDefaultContext(Context context) {

        if (defaultContext != null &&
                defaultContext instanceof StandardDefaultContext) {

            ((StandardDefaultContext) defaultContext).installDefaultContext(context);
        }

    }

    /**
     * Import the DefaultContext config into a web application context.
     *
     * @param context web application context to import default context
     */
    public void importDefaultContext(Context context) {

        if (this.defaultContext != null)
            this.defaultContext.importDefaultContext(context);

    }

    /**
     * Add an alias name that should be mapped to this same Host.
     *
     * @param alias The alias to be added
     */
    public void addAlias(String alias) {

        alias = alias.toLowerCase();

        // Skip duplicate aliases
        for (int i = 0; i < aliases.length; i++) {
            if (aliases[i].equals(alias))
                return;
        }

        // Add this alias to the list
        String newAliases[] = new String[aliases.length + 1];
        for (int i = 0; i < aliases.length; i++)
            newAliases[i] = aliases[i];
        newAliases[aliases.length] = alias;

        aliases = newAliases;

        // Inform interested listeners
        fireContainerEvent(ADD_ALIAS_EVENT, alias);

    }


    /**
     * Add a child Container, only if the proposed child is an implementation
     * of Context.
     *
     * @param child Child container to be added
     */
    public void addChild(Container child) {

        if (!(child instanceof Context))
            throw new IllegalArgumentException
                    (sm.getString("standardHost.notContext"));
        super.addChild(child);

    }


    /**
     * Return the set of alias names for this Host.  If none are defined,
     * a zero length array is returned.
     */
    public String[] findAliases() {

        return (this.aliases);

    }


    /**
     * Return descriptive information about this Container implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    public String getInfo() {

        return (info);

    }


    /**
     * Return the Context that would be used to process the specified
     * host-relative request URI, if any; otherwise return <code>null</code>.
     *
     * @param uri Request URI to be mapped
     */
    public Context map(String uri) {

        if (debug > 0)
            log("Mapping request URI '" + uri + "'");
        if (uri == null)
            return (null);

        // Match on the longest possible context path prefix
        if (debug > 1)
            log("  Trying the longest context path prefix");
        Context context = null;
        String mapuri = uri;
        while (true) {
            context = (Context) findChild(mapuri);
            if (context != null)
                break;
            int slash = mapuri.lastIndexOf('/');
            if (slash < 0)
                break;
            mapuri = mapuri.substring(0, slash);
        }

        // If no Context matches, select the default Context
        if (context == null) {
            if (debug > 1)
                log("  Trying the default context");
            context = (Context) findChild("");
        }

        // Complain if no Context has been selected
        if (context == null) {
            log(sm.getString("standardHost.mappingError", uri));
            return (null);
        }

        // Return the mapped Context (if any)
        if (debug > 0)
            log(" Mapped to context '" + context.getPath() + "'");
        return (context);

    }


    /**
     * Remove the specified alias name from the aliases for this Host.
     *
     * @param alias Alias name to be removed
     */
    public void removeAlias(String alias) {

        alias = alias.toLowerCase();

        synchronized (aliases) {

            // Make sure this alias is currently present
            int n = -1;
            for (int i = 0; i < aliases.length; i++) {
                if (aliases[i].equals(alias)) {
                    n = i;
                    break;
                }
            }
            if (n < 0)
                return;

            // Remove the specified alias
            int j = 0;
            String results[] = new String[aliases.length - 1];
            for (int i = 0; i < aliases.length; i++) {
                if (i != n)
                    results[j++] = aliases[i];
            }
            aliases = results;

        }

        // Inform interested listeners
        fireContainerEvent(REMOVE_ALIAS_EVENT, alias);

    }


    /**
     * Return a String representation of this component.
     */
    public String toString() {

        StringBuffer sb = new StringBuffer();
        if (getParent() != null) {
            sb.append(getParent().toString());
            sb.append(".");
        }
        sb.append("StandardHost[");
        sb.append(getName());
        sb.append("]");
        return (sb.toString());

    }


    /**
     * Start this host.
     *
     * @throws LifecycleException if this component detects a fatal error
     *                            that prevents it from being started
     */
    public synchronized void start() throws LifecycleException {

        // Set error report valve
        if ((errorReportValveClass != null)
                && (!errorReportValveClass.equals(""))) {
            try {
                Valve valve = (Valve) Class.forName(errorReportValveClass)
                        .newInstance();
                addValve(valve);
            } catch (Throwable t) {
                log(sm.getString
                        ("standardHost.invalidErrorReportValveClass",
                                errorReportValveClass));
            }
        }

        // Set dispatcher valve
        addValve(new ErrorDispatcherValve());

        super.start();

    }


    // ------------------------------------------------------- Deployer Methods


    /**
     * Install a new web application, whose web application archive is at the
     * specified URL, into this container with the specified context path.
     * A context path of "" (the empty string) should be used for the root
     * application for this container.  Otherwise, the context path must
     * start with a slash.
     * <p>
     * If this application is successfully installed, a ContainerEvent of type
     * <code>INSTALL_EVENT</code> will be sent to all registered listeners,
     * with the newly created <code>Context</code> as an argument.
     *
     * @param contextPath The context path to which this application should
     *                    be installed (must be unique)
     * @param war         A URL of type "jar:" that points to a WAR file, or type
     *                    "file:" that points to an unpacked directory structure containing
     *                    the web application to be installed
     * @throws IllegalArgumentException if the specified context path
     *                                  is malformed (it must be "" or start with a slash)
     * @throws IllegalStateException    if the specified context path
     *                                  is already attached to an existing web application
     * @throws IOException              if an input/output error was encountered
     *                                  during install
     */
    public void install(String contextPath, URL war) throws IOException {

        if (contextPath == null) {
            throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.pathRequired"));
        } else if (!contextPath.equals("") && !contextPath.startsWith("/")) {
            throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.pathFormat", contextPath));
        } else if (this.findDeployedApp(contextPath) != null) {
            throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.pathUsed", contextPath));
        } else if (war == null) {
            throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.warRequired"));
        } else {
            String url = war.toString();
            String docBase = null;
            this.log(ContainerBase.sm.getString("standardHost.installing", contextPath, url));
            if (this.isUnpackWARs()) {
                if (url.startsWith("jar:")) {
                    docBase = this.expand(war);
                } else if (url.startsWith("file://")) {
                    docBase = url.substring(7);
                } else {
                    if (!url.startsWith("file:")) {
                        throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.warURL", url));
                    }

                    docBase = url.substring(5);
                }

                File docBaseDir = new File(docBase);
                if (!docBaseDir.exists() || !docBaseDir.isDirectory() || !docBaseDir.canRead()) {
                    throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.accessBase", docBase));
                }
            } else {
                if (url.startsWith("jar:")) {
                    url = url.substring(4, url.length() - 2);
                }

                if (url.startsWith("file://")) {
                    docBase = url.substring(7);
                } else {
                    if (!url.startsWith("file:")) {
                        throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.warURL", url));
                    }

                    docBase = url.substring(5);
                }
            }

            try {
                Class clazz = Class.forName(this.contextClass);
                Context context = (Context) clazz.newInstance();
                context.setPath(contextPath);
                context.setDocBase(docBase);
                if (context instanceof Lifecycle) {
                    clazz = Class.forName(this.configClass);
                    LifecycleListener listener = (LifecycleListener) clazz.newInstance();
                    ((Lifecycle) context).addLifecycleListener(listener);
                }

                this.addChild(context);
                this.fireContainerEvent("install", context);
            } catch (Exception var8) {
                this.log(ContainerBase.sm.getString("standardHost.installError", contextPath), var8);
                throw new IOException(var8.toString());
            }
        }

    }
    protected String expand(URL war) throws IOException {
        if (super.debug >= 1) {
            this.log("expand(" + war.toString() + ")");
        }

        String pathname = war.toString().replace('\\', '/');
        if (pathname.endsWith("!/")) {
            pathname = pathname.substring(0, pathname.length() - 2);
        }

        int period = pathname.lastIndexOf(46);
        if (period >= pathname.length() - 4) {
            pathname = pathname.substring(0, period);
        }

        int slash = pathname.lastIndexOf(47);
        if (slash >= 0) {
            pathname = pathname.substring(slash + 1);
        }

        if (super.debug >= 1) {
            this.log("  Proposed directory name: " + pathname);
        }

        File appBase = new File(this.getAppBase());
        if (!appBase.isAbsolute()) {
            appBase = new File(System.getProperty("catalina.base"), this.getAppBase());
        }

        if (appBase.exists() && appBase.isDirectory()) {
            File docBase = new File(appBase, pathname);
            if (docBase.exists()) {
                return docBase.getAbsolutePath();
            } else {
                docBase.mkdir();
                if (super.debug >= 2) {
                    this.log("  Have created expansion directory " + docBase.getAbsolutePath());
                }

                JarURLConnection juc = (JarURLConnection)war.openConnection();
                juc.setUseCaches(false);
                JarFile jarFile = juc.getJarFile();
                if (super.debug >= 2) {
                    this.log("  Have opened JAR file successfully");
                }

                Enumeration jarEntries = jarFile.entries();
                if (super.debug >= 2) {
                    this.log("  Have retrieved entries enumeration");
                }

                while(jarEntries.hasMoreElements()) {
                    JarEntry jarEntry = (JarEntry)jarEntries.nextElement();
                    String name = jarEntry.getName();
                    if (super.debug >= 2) {
                        this.log("  Am processing entry " + name);
                    }

                    int last = name.lastIndexOf(47);
                    if (last >= 0) {
                        File parent = new File(docBase, name.substring(0, last));
                        if (super.debug >= 2) {
                            this.log("  Creating parent directory " + parent);
                        }

                        parent.mkdirs();
                    }

                    if (!name.endsWith("/")) {
                        if (super.debug >= 2) {
                            this.log("  Creating expanded file " + name);
                        }

                        InputStream input = jarFile.getInputStream(jarEntry);
                        this.expand(input, docBase, name);
                        input.close();
                    }
                }

                jarFile.close();
                return docBase.getAbsolutePath();
            }
        } else {
            throw new IOException(ContainerBase.sm.getString("standardHost.appBase", appBase.getAbsolutePath()));
        }
    }

    protected void expand(InputStream input, File docBase, String name) throws IOException {
        File file = new File(docBase, name);
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
        byte[] buffer = new byte[2048];

        while(true) {
            int n = input.read(buffer);
            if (n <= 0) {
                output.close();
                return;
            }

            output.write(buffer, 0, n);
        }
    }


    /**
     * <p>Install a new web application, whose context configuration file
     * (consisting of a <code>&lt;Context&gt;</code> element) and web
     * application archive are at the specified URLs.</p>
     *
     * <p>If this application is successfully installed, a ContainerEvent
     * of type <code>INSTALL_EVENT</code> will be sent to all registered
     * listeners, with the newly created <code>Context</code> as an argument.
     * </p>
     *
     * @param config A URL that points to the context configuration file to
     *  be used for configuring the new Context
     * @param war A URL of type "jar:" that points to a WAR file, or type
     *  "file:" that points to an unpacked directory structure containing
     *  the web application to be installed
     *
     * @exception IllegalArgumentException if one of the specified URLs is
     *  null
     * @exception IllegalStateException if the context path specified in the
     *  context configuration file is already attached to an existing web
     *  application
     * @exception IOException if an input/output error was encountered
     *  during installation
     */
    //public synchronized void install(URL config, URL war) throws IOException {
    //
    //    deployer.install(config, war);
    //
    //}


    /**
     * Return the Context for the deployed application that is associated
     * with the specified context path (if any); otherwise return
     * <code>null</code>.
     *
     * @param contextPath The context path of the requested web application
     */
    public Context findDeployedApp(String contextPath) {
        if (contextPath == null) {
            return null;
        } else {
            HashMap var3 = super.children;
            synchronized (var3) {
            }

            Context var2;
            try {
                var2 = (Context) super.children.get(contextPath);
            } catch (Throwable var6) {
                throw var6;
            }

            return var2;
        }

    }


    /**
     * Return the context paths of all deployed web applications in this
     * Container.  If there are no deployed applications, a zero-length
     * array is returned.
     */
    public String[] findDeployedApps() {
        HashMap var2 = super.children;
        synchronized (var2) {
        }

        String[] var1;
        try {
            String[] results = new String[super.children.size()];
            var1 = (String[]) super.children.keySet().toArray(results);
        } catch (Throwable var6) {
            throw var6;
        }

        return var1;

    }


    /**
     * Remove an existing web application, attached to the specified context
     * path.  If this application is successfully removed, a
     * ContainerEvent of type <code>REMOVE_EVENT</code> will be sent to all
     * registered listeners, with the removed <code>Context</code> as
     * an argument.
     *
     * @param contextPath The context path of the application to be removed
     * @throws IllegalArgumentException if the specified context path
     *                                  is malformed (it must be "" or start with a slash)
     * @throws IllegalArgumentException if the specified context path does
     *                                  not identify a currently installed web application
     * @throws IOException              if an input/output error occurs during
     *                                  removal
     */
    public void remove(String contextPath) throws IOException {

        //deployer.remove(contextPath);
        if (contextPath == null) {
            throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.pathRequired"));
        } else if (!contextPath.equals("") && !contextPath.startsWith("/")) {
            throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.pathFormat", contextPath));
        } else {
            Context context = this.findDeployedApp(contextPath);
            if (context == null) {
                throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.pathMissing", contextPath));
            } else {
                this.log(ContainerBase.sm.getString("standardHost.removing", contextPath));

                try {
                    this.removeChild(context);
                } catch (Exception var4) {
                    this.log(ContainerBase.sm.getString("standardHost.removeError", contextPath), var4);
                    throw new IOException(var4.toString());
                }
            }
        }

    }


    /**
     * Remove an existing web application, attached to the specified context
     * path.  If this application is successfully removed, a
     * ContainerEvent of type <code>REMOVE_EVENT</code> will be sent to all
     * registered listeners, with the removed <code>Context</code> as
     * an argument. Deletes the web application war file and/or directory
     * if they exist in the Host's appBase.
     *
     * @param contextPath The context path of the application to be removed
     * @param undeploy boolean flag to remove web application from server
     *
     * @exception IllegalArgumentException if the specified context path
     *  is malformed (it must be "" or start with a slash)
     * @exception IllegalArgumentException if the specified context path does
     *  not identify a currently installed web application
     * @exception IOException if an input/output error occurs during
     *  removal
     */
    //public void remove(String contextPath, boolean undeploy) throws IOException {
    //
    //    deployer.remove(contextPath,undeploy);
    //
    //}


    /**
     * Start an existing web application, attached to the specified context
     * path.  Only starts a web application if it is not running.
     *
     * @param contextPath The context path of the application to be started
     * @throws IllegalArgumentException if the specified context path
     *                                  is malformed (it must be "" or start with a slash)
     * @throws IllegalArgumentException if the specified context path does
     *                                  not identify a currently installed web application
     * @throws IOException              if an input/output error occurs during
     *                                  startup
     */
    public void start(String contextPath) throws IOException {

        //deployer.start(contextPath);
        if (contextPath == null) {
            throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.pathRequired"));
        } else if (!contextPath.equals("") && !contextPath.startsWith("/")) {
            throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.pathFormat", contextPath));
        } else {
            Context context = this.findDeployedApp(contextPath);
            if (context == null) {
                throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.pathMissing", contextPath));
            } else {
                this.log("standardHost.start " + contextPath);

                try {
                    ((Lifecycle) context).start();
                } catch (LifecycleException var4) {
                    this.log("standardHost.start " + contextPath + ": ", var4);
                    throw new IllegalStateException("standardHost.start " + contextPath + ": " + var4);
                }
            }
        }

    }


    /**
     * Stop an existing web application, attached to the specified context
     * path.  Only stops a web application if it is running.
     *
     * @param contextPath The context path of the application to be stopped
     * @throws IllegalArgumentException if the specified context path
     *                                  is malformed (it must be "" or start with a slash)
     * @throws IllegalArgumentException if the specified context path does
     *                                  not identify a currently installed web application
     * @throws IOException              if an input/output error occurs while stopping
     *                                  the web application
     */
    public void stop(String contextPath) throws IOException {

        //deployer.stop(contextPath);
        if (contextPath == null) {
            throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.pathRequired"));
        } else if (!contextPath.equals("") && !contextPath.startsWith("/")) {
            throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.pathFormat", contextPath));
        } else {
            Context context = this.findDeployedApp(contextPath);
            if (context == null) {
                throw new IllegalArgumentException(ContainerBase.sm.getString("standardHost.pathMissing", contextPath));
            } else {
                this.log("standardHost.stop " + contextPath);

                try {
                    ((Lifecycle) context).stop();
                } catch (LifecycleException var4) {
                    this.log("standardHost.stop " + contextPath + ": ", var4);
                    throw new IllegalStateException("standardHost.stop " + contextPath + ": " + var4);
                }
            }
        }
    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Add a default Mapper implementation if none have been configured
     * explicitly.
     *
     * @param mapperClass Java class name of the default Mapper
     */
    protected void addDefaultMapper(String mapperClass) {

        super.addDefaultMapper(this.mapperClass);

    }


}
