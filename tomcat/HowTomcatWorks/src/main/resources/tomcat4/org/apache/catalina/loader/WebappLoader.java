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

package tomcat4.org.apache.catalina.loader;

import tomcat4.org.apache.catalina.*;
import tomcat4.org.apache.catalina.util.LifecycleSupport;
import tomcat4.org.apache.catalina.util.StringManager;
import tomcat4.org.apache.naming.resources.DirContextURLStreamHandler;
import tomcat4.org.apache.naming.resources.DirContextURLStreamHandlerFactory;
import tomcat4.org.apache.naming.resources.Resource;

import javax.naming.Binding;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.servlet.ServletContext;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.util.jar.JarFile;


/**
 * Classloader implementation which is specialized for handling web
 * applications in the most efficient way, while being Catalina aware (all
 * accesses to resources are made through the DirContext interface).
 * This class loader supports detection of modified
 * Java classes, which can be used to implement auto-reload support.
 * <p>
 * This class loader is configured by adding the pathnames of directories,
 * JAR files, and ZIP files with the <code>addRepository()</code> method,
 * prior to calling <code>start()</code>.  When a new class is required,
 * these repositories will be consulted first to locate the class.  If it
 * is not present, the system class loader will be used instead.
 *
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 * @version $Revision: 1.32 $ $Date: 2004/08/26 21:34:02 $
 */

public class WebappLoader
    implements Lifecycle, Loader, PropertyChangeListener, Runnable {


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new WebappLoader with no defined parent class loader
     * (so that the actual parent will be the system class loader).
     */
    public WebappLoader() {

        this(null);

    }


    /**
     * Construct a new WebappLoader with the specified class loader
     * to be defined as the parent of the ClassLoader we ultimately create.
     *
     * @param parent The parent class loader
     */
    public WebappLoader(ClassLoader parent) {


        super();
        this.parentClassLoader = parent;

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The number of seconds between checks for modified classes, if
     * automatic reloading is enabled.
     */
    private int checkInterval = 15;


    /**
     * The class loader being managed by this Loader component.
     */
    private WebappClassLoader classLoader = null;


    /**
     * The Container with which this Loader has been associated.
     */
    private Container container = null;


    /**
     * The debugging detail level for this component.
     */
    private int debug = 0;


    /**
     * The DefaultContext with which this Loader is associated.
     */
    protected DefaultContext defaultContext = null;
    

    /**
     * The "follow standard delegation model" flag that will be used to
     * configure our ClassLoader.
     */
    private boolean delegate = false;


    /**
     * The descriptive information about this Loader implementation.
     */
    private static final String info =
        "org.apache.catalina.loader.WebappLoader/1.0";


    /**
     * The lifecycle event support for this component.
     */
    protected LifecycleSupport lifecycle = new LifecycleSupport(this);


    /**
     * The Java class name of the ClassLoader implementation to be used.
     * This class should extend WebappClassLoader, otherwise, a different 
     * loader implementation must be used.
     */
    private String loaderClass =
        "org.apache.catalina.loader.WebappClassLoader";


    /**
     * The parent class loader of the class loader we will create.
     */
    private ClassLoader parentClassLoader = null;


    /**
     * The reloadable flag for this Loader.
     */
    private boolean reloadable = false;


    /**
     * The set of repositories associated with this class loader.
     */
    private String repositories[] = new String[0];


    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);


    /**
     * Has this component been started?
     */
    private boolean started = false;


    /**
     * The property change support for this component.
     */
    protected PropertyChangeSupport support = new PropertyChangeSupport(this);


    /**
     * The background thread.
     */
    private Thread thread = null;


    /**
     * The background thread completion semaphore.
     */
    private boolean threadDone = false;


    /**
     * Name to register for the background thread.
     */
    private String threadName = "WebappLoader";


    // ------------------------------------------------------------- Properties


    /**
     * Return the check interval for this Loader.
     */
    public int getCheckInterval() {

        return (this.checkInterval);

    }


    /**
     * Set the check interval for this Loader.
     *
     * @param checkInterval The new check interval
     */
    public void setCheckInterval(int checkInterval) {

        int oldCheckInterval = this.checkInterval;
        this.checkInterval = checkInterval;
        support.firePropertyChange("checkInterval",
                                   new Integer(oldCheckInterval),
                                   new Integer(this.checkInterval));

    }


    /**
     * Return the Java class loader to be used by this Container.
     */
    public ClassLoader getClassLoader() {

        return ((ClassLoader) classLoader);

    }


    /**
     * Return the Container with which this Logger has been associated.
     */
    public Container getContainer() {

        return (container);

    }


    /**
     * Set the Container with which this Logger has been associated.
     *
     * @param container The associated Container
     */
    public void setContainer(Container container) {

        // Deregister from the old Container (if any)
        if ((this.container != null) && (this.container instanceof Context))
            ((Context) this.container).removePropertyChangeListener(this);

        // Process this property change
        Container oldContainer = this.container;
        this.container = container;
        support.firePropertyChange("container", oldContainer, this.container);

        // Register with the new Container (if any)
        if ((this.container != null) && (this.container instanceof Context)) {
            setReloadable( ((Context) this.container).getReloadable() );
            ((Context) this.container).addPropertyChangeListener(this);
        }

    }


    /**
     * Return the DefaultContext with which this Loader is associated.
     */
    public DefaultContext getDefaultContext() {

        return (this.defaultContext);

    }


    /**
     * Set the DefaultContext with which this Loader is associated.
     *
     * @param defaultContext The newly associated DefaultContext
     */
    public void setDefaultContext(DefaultContext defaultContext) {

        DefaultContext oldDefaultContext = this.defaultContext;
        this.defaultContext = defaultContext;
        support.firePropertyChange("defaultContext", oldDefaultContext, this.defaultContext);

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

        int oldDebug = this.debug;
        this.debug = debug;
        support.firePropertyChange("debug", new Integer(oldDebug),
                                   new Integer(this.debug));

    }


    /**
     * Return the "follow standard delegation model" flag used to configure
     * our ClassLoader.
     */
    public boolean getDelegate() {

        return (this.delegate);

    }


    /**
     * Set the "follow standard delegation model" flag used to configure
     * our ClassLoader.
     *
     * @param delegate The new flag
     */
    public void setDelegate(boolean delegate) {

        boolean oldDelegate = this.delegate;
        this.delegate = delegate;
        support.firePropertyChange("delegate", new Boolean(oldDelegate),
                                   new Boolean(this.delegate));

    }


    /**
     * Return descriptive information about this Loader implementation and
     * the corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    public String getInfo() {

        return (info);

    }


    /**
     * Return the ClassLoader class name.
     */
    public String getLoaderClass() {

        return (this.loaderClass);

    }


    /**
     * Set the ClassLoader class name.
     *
     * @param loaderClass The new ClassLoader class name
     */
    public void setLoaderClass(String loaderClass) {

        this.loaderClass = loaderClass;

    }


    /**
     * Return the reloadable flag for this Loader.
     */
    public boolean getReloadable() {

        return (this.reloadable);

    }


    /**
     * Set the reloadable flag for this Loader.
     *
     * @param reloadable The new reloadable flag
     */
    public void setReloadable(boolean reloadable) {

        // Process this property change
        boolean oldReloadable = this.reloadable;
        this.reloadable = reloadable;
        support.firePropertyChange("reloadable",
                                   new Boolean(oldReloadable),
                                   new Boolean(this.reloadable));

        // Start or stop our background thread if required
        if (!started)
            return;
        if (!oldReloadable && this.reloadable)
            threadStart();
        else if (oldReloadable && !this.reloadable)
            threadStop();

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Add a property change listener to this component.
     *
     * @param listener The listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {

        support.addPropertyChangeListener(listener);

    }


    /**
     * Add a new repository to the set of repositories for this class loader.
     *
     * @param repository Repository to be added
     */
    public void addRepository(String repository) {

        if (debug >= 1)
            log(sm.getString("webappLoader.addRepository", repository));

        for (int i = 0; i < repositories.length; i++) {
            if (repository.equals(repositories[i]))
                return;
        }
        String results[] = new String[repositories.length + 1];
        for (int i = 0; i < repositories.length; i++)
            results[i] = repositories[i];
        results[repositories.length] = repository;
        repositories = results;

        if (started && (classLoader != null)) {
            classLoader.addRepository(repository);
            setClassPath();
        }

    }


    /**
     * Return the set of repositories defined for this class loader.
     * If none are defined, a zero-length array is returned.
     */
    public String[] findRepositories() {

        return (repositories);

    }


    /**
     * Has the internal repository associated with this Loader been modified,
     * such that the loaded classes should be reloaded?
     */
    public boolean modified() {

        return (classLoader.modified());

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
     * Return a String representation of this component.
     */
    public String toString() {

        StringBuffer sb = new StringBuffer("WebappLoader[");
        if (container != null)
            sb.append(container.getName());
        sb.append("]");
        return (sb.toString());

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
     * Start this component, initializing our associated class loader.
     *
     * @exception LifecycleException if a lifecycle error occurs
     */
    public void start() throws LifecycleException {

        // Validate and update our current component state
        if (started)
            throw new LifecycleException
                (sm.getString("webappLoader.alreadyStarted"));
        if (debug >= 1)
            log(sm.getString("webappLoader.starting"));
        lifecycle.fireLifecycleEvent(START_EVENT, null);
        started = true;

        if (container.getResources() == null)
            return;

        // Register a stream handler factory for the JNDI protocol
        URLStreamHandlerFactory streamHandlerFactory =
            new DirContextURLStreamHandlerFactory();
        try {
            URL.setURLStreamHandlerFactory(streamHandlerFactory);
        } catch (Throwable t) {
            // Ignore the error here.
        }

        // Construct a class loader based on our current repositories list
        try {

            classLoader = createClassLoader();
            classLoader.setResources(container.getResources());
            classLoader.setDebug(this.debug);
            classLoader.setDelegate(this.delegate);

            for (int i = 0; i < repositories.length; i++) {
                classLoader.addRepository(repositories[i]);
            }

            // Configure our repositories
            setRepositories();
            setClassPath();

            setPermissions();

            if (classLoader instanceof Lifecycle)
                ((Lifecycle) classLoader).start();

            // Binding the Webapp class loader to the directory context
            DirContextURLStreamHandler.bind
                ((ClassLoader) classLoader, this.container.getResources());

        } catch (Throwable t) {
            throw new LifecycleException("start: ", t);
        }

        // Validate that all required packages are actually available
        validatePackages();

        // Start our background thread if we are reloadable
        if (reloadable) {
            log(sm.getString("webappLoader.reloading"));
            try {
                threadStart();
            } catch (IllegalStateException e) {
                throw new LifecycleException(e);
            }
        }

    }


    /**
     * Stop this component, finalizing our associated class loader.
     *
     * @exception LifecycleException if a lifecycle error occurs
     */
    public void stop() throws LifecycleException {

        // Validate and update our current component state
        if (!started)
            throw new LifecycleException
                (sm.getString("webappLoader.notStarted"));
        if (debug >= 1)
            log(sm.getString("webappLoader.stopping"));
        lifecycle.fireLifecycleEvent(STOP_EVENT, null);
        started = false;

        // Stop our background thread if we are reloadable
        if (reloadable)
            threadStop();

        // Remove context attributes as appropriate
        if (container instanceof Context) {
            ServletContext servletContext =
                ((Context) container).getServletContext();
            servletContext.removeAttribute(Globals.CLASS_PATH_ATTR);
        }

        // Throw away our current class loader
        if (classLoader instanceof Lifecycle)
            ((Lifecycle) classLoader).stop();
        DirContextURLStreamHandler.unbind((ClassLoader) classLoader);
        classLoader = null;

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


    // ----------------------------------------- PropertyChangeListener Methods


    /**
     * Process property change events from our associated Context.
     *
     * @param event The property change event that has occurred
     */
    public void propertyChange(PropertyChangeEvent event) {

        // Validate the source of this event
        if (!(event.getSource() instanceof Context))
            return;
        Context context = (Context) event.getSource();

        // Process a relevant property change
        if (event.getPropertyName().equals("reloadable")) {
            try {
                setReloadable
                    ( ((Boolean) event.getNewValue()).booleanValue() );
            } catch (NumberFormatException e) {
                log(sm.getString("webappLoader.reloadable",
                                 event.getNewValue().toString()));
            }
        }

    }


    // ------------------------------------------------------- Private Methods


    /**
     * Create associated classLoader.
     */
    private WebappClassLoader createClassLoader()
        throws Exception {

        Class clazz = Class.forName(loaderClass);
        WebappClassLoader classLoader = null;

        if (parentClassLoader == null) {
            // Will cause a ClassCast is the class does not extend WCL, but
            // this is on purpose (the exception will be caught and rethrown)
            classLoader = (WebappClassLoader) clazz.newInstance();
        } else {
            Class[] argTypes = { ClassLoader.class };
            Object[] args = { parentClassLoader };
            Constructor constr = clazz.getConstructor(argTypes);
            classLoader = (WebappClassLoader) constr.newInstance(args);
        }

        return classLoader;

    }


    /**
     * Log a message on the Logger associated with our Container (if any)
     *
     * @param message Message to be logged
     */
    private void log(String message) {

        Logger logger = null;
        if (container != null)
            logger = container.getLogger();
        if (logger != null)
            logger.log("WebappLoader[" + container.getName() + "]: "
                       + message);
        else {
            String containerName = null;
            if (container != null)
                containerName = container.getName();
            System.out.println("WebappLoader[" + containerName
                               + "]: " + message);
        }

    }


    /**
     * Log a message on the Logger associated with our Container (if any)
     *
     * @param message Message to be logged
     * @param throwable Associated exception
     */
    private void log(String message, Throwable throwable) {

        Logger logger = null;
        if (container != null)
            logger = container.getLogger();
        if (logger != null) {
            logger.log("WebappLoader[" + container.getName() + "] "
                       + message, throwable);
        } else {
            String containerName = null;
            if (container != null)
                containerName = container.getName();
            System.out.println("WebappLoader[" + containerName
                               + "]: " + message);
            System.out.println("" + throwable);
            throwable.printStackTrace(System.out);
        }

    }


    /**
     * Notify our Context that a reload is appropriate.
     */
    private void notifyContext() {

        WebappContextNotifier notifier = new WebappContextNotifier();
        (new Thread(notifier)).start();

    }


    /**
     * Configure associated class loader permissions.
     */
    private void setPermissions() {

        if (System.getSecurityManager() == null)
            return;
        if (!(container instanceof Context))
            return;

        // Tell the class loader the root of the context
        ServletContext servletContext =
            ((Context) container).getServletContext();

        // Assigning permissions for the work directory
        File workDir =
            (File) servletContext.getAttribute(Globals.WORK_DIR_ATTR);
        if (workDir != null) {
            try {
                String workDirPath = workDir.getCanonicalPath();
                classLoader.addPermission
                    (new FilePermission(workDirPath, "read,write"));
                classLoader.addPermission
                    (new FilePermission(workDirPath + File.separator + "-", 
                                        "read,write,delete"));
            } catch (IOException e) {
                // Ignore
            }
        }

        try {

            URL rootURL = servletContext.getResource("/");
            classLoader.addPermission(rootURL);

            String contextRoot = servletContext.getRealPath("/");
            if (contextRoot != null) {
                try {
                    contextRoot = (new File(contextRoot)).getCanonicalPath();
                    classLoader.addPermission(contextRoot);
                } catch (IOException e) {
                    // Ignore
                }
            }

            URL classesURL = servletContext.getResource("/WEB-INF/classes/");
            classLoader.addPermission(classesURL);
            URL libURL = servletContext.getResource("/WEB-INF/lib/");
            classLoader.addPermission(libURL);

            if (contextRoot != null) {

                if (libURL != null) {
                    File rootDir = new File(contextRoot);
                    File libDir = new File(rootDir, "WEB-INF/lib/");
                    try {
                        String path = libDir.getCanonicalPath();
                        classLoader.addPermission(path);
                    } catch (IOException e) {
                    }
                }

            } else {

                if (workDir != null) {
                    if (libURL != null) {
                        File libDir = new File(workDir, "WEB-INF/lib/");
                        try {
                            String path = libDir.getCanonicalPath();
                            classLoader.addPermission(path);
                        } catch (IOException e) {
                        }
                    }
                    if (classesURL != null) {
                        File classesDir = new File(workDir, "WEB-INF/classes/");
                        try {
                            String path = classesDir.getCanonicalPath();
                            classLoader.addPermission(path);
                        } catch (IOException e) {
                        }
                    }
                }

            }

        } catch (MalformedURLException e) {
        }

    }


    /**
     * Configure the repositories for our class loader, based on the
     * associated Context.
     */
    private void setRepositories() {

        if (!(container instanceof Context))
            return;
        ServletContext servletContext =
            ((Context) container).getServletContext();
        if (servletContext == null)
            return;

        // Loading the work directory
        File workDir =
            (File) servletContext.getAttribute(Globals.WORK_DIR_ATTR);
        if (workDir == null)
            return;

        log(sm.getString("webappLoader.deploy", workDir.getAbsolutePath()));

        DirContext resources = container.getResources();

        // Setting up the class repository (/WEB-INF/classes), if it exists

        String classesPath = "/WEB-INF/classes";
        DirContext classes = null;

        try {
            Object object = resources.lookup(classesPath);
            if (object instanceof DirContext) {
                classes = (DirContext) object;
            }
        } catch(NamingException e) {
            // Silent catch: it's valid that no /WEB-INF/classes collection
            // exists
        }

        if (classes != null) {

            File classRepository = null;

            String absoluteClassesPath =
                servletContext.getRealPath(classesPath);

            if (absoluteClassesPath != null) {

                classRepository = new File(absoluteClassesPath);

            } else {

                classRepository = new File(workDir, classesPath);
                classRepository.mkdirs();
                copyDir(classes, classRepository);

            }

            log(sm.getString("webappLoader.classDeploy", classesPath,
                             classRepository.getAbsolutePath()));


            // Adding the repository to the class loader
            classLoader.addRepository(classesPath + "/", classRepository);

        }

        // Setting up the JAR repository (/WEB-INF/lib), if it exists

        String libPath = "/WEB-INF/lib";

        classLoader.setJarPath(libPath);

        DirContext libDir = null;
        // Looking up directory /WEB-INF/lib in the context
        try {
            Object object = resources.lookup(libPath);
            if (object instanceof DirContext)
                libDir = (DirContext) object;
        } catch(NamingException e) {
            // Silent catch: it's valid that no /WEB-INF/lib collection
            // exists
        }

        if (libDir != null) {

            boolean copyJars = false;
            String absoluteLibPath = servletContext.getRealPath(libPath);

            File destDir = null;

            if (absoluteLibPath != null) {
                destDir = new File(absoluteLibPath);
            } else {
                copyJars = true;
                destDir = new File(workDir, libPath);
                destDir.mkdirs();
            }

            // Looking up directory /WEB-INF/lib in the context
            try {
                NamingEnumeration<Binding> enm = resources.listBindings(libPath);
                while (enm.hasMoreElements()) {

                    Binding binding = enm.nextElement();
                    String filename = libPath + "/" + binding.getName();
                    if (!filename.endsWith(".jar"))
                        continue;

                    // Copy JAR in the work directory, always (the JAR file
                    // would get locked otherwise, which would make it
                    // impossible to update it or remove it at runtime)
                    File destFile = new File(destDir, binding.getName());

                    log(sm.getString("webappLoader.jarDeploy", filename,
                                     destFile.getAbsolutePath()));

                    Resource jarResource = (Resource) binding.getObject();
                    if (copyJars) {
                        if (!copy(jarResource.streamContent(),
                                  new FileOutputStream(destFile)))
                            continue;
                    }

                    JarFile jarFile = new JarFile(destFile);
                    classLoader.addJar(filename, jarFile, destFile);

                }
            } catch (NamingException e) {
                // Silent catch: it's valid that no /WEB-INF/lib directory
                // exists
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    /**
     * Set the appropriate context attribute for our class path.  This
     * is required only because Jasper depends on it.
     */
    private void setClassPath() {

        // Validate our current state information
        if (!(container instanceof Context))
            return;
        ServletContext servletContext =
            ((Context) container).getServletContext();
        if (servletContext == null)
            return;

        StringBuffer classpath = new StringBuffer();

        // Assemble the class path information from our class loader chain
        ClassLoader loader = getClassLoader();
        int layers = 0;
        int n = 0;
        while ((layers < 3) && (loader != null)) {
            if (!(loader instanceof URLClassLoader))
                break;
            URL repositories[] =
                ((URLClassLoader) loader).getURLs();
            for (int i = 0; i < repositories.length; i++) {
                String repository = repositories[i].toString();
                if (repository.startsWith("file://"))
                    repository = repository.substring(7);
                else if (repository.startsWith("file:"))
                    repository = repository.substring(5);
                else if (repository.startsWith("jndi:"))
                    repository =
                        servletContext.getRealPath(repository.substring(5));
                else
                    continue;
                if (repository == null)
                    continue;
                if (n > 0)
                    classpath.append(File.pathSeparator);
                classpath.append(repository);
                n++;
            }
            loader = loader.getParent();
            layers++;
        }

        // Store the assembled class path as a servlet context attribute
        servletContext.setAttribute(Globals.CLASS_PATH_ATTR,
                                    classpath.toString());

    }


    /**
     * Copy directory.
     */
    private boolean copyDir(DirContext srcDir, File destDir) {

        try {

            NamingEnumeration<NameClassPair> enm = srcDir.list("");
            while (enm.hasMoreElements()) {
                NameClassPair ncPair =
                        enm.nextElement();
                String name = ncPair.getName();
                Object object = srcDir.lookup(name);
                File currentFile = new File(destDir, name);
                if (object instanceof Resource) {
                    InputStream is = ((Resource) object).streamContent();
                    OutputStream os = new FileOutputStream(currentFile);
                    if (!copy(is, os))
                        return false;
                } else if (object instanceof InputStream) {
                    OutputStream os = new FileOutputStream(currentFile);
                    if (!copy((InputStream) object, os))
                        return false;
                } else if (object instanceof DirContext) {
                    currentFile.mkdir();
                    copyDir((DirContext) object, currentFile);
                }
            }

        } catch (NamingException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        return true;

    }


    /**
     * Copy a file to the specified temp directory. This is required only
     * because Jasper depends on it.
     */
    private boolean copy(InputStream is, OutputStream os) {

        try {
            byte[] buf = new byte[4096];
            while (true) {
                int len = is.read(buf);
                if (len < 0)
                    break;
                os.write(buf, 0, len);
            }
            is.close();
            os.close();
        } catch (IOException e) {
            return false;
        }

        return true;

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
     * Start the background thread that will periodically check for
     * session timeouts.
     *
     * @exception IllegalStateException if we should not be starting
     *  a background thread now
     */
    private void threadStart() {

        // Has the background thread already been started?
        if (thread != null)
            return;

        // Validate our current state
        if (!reloadable)
            throw new IllegalStateException
                (sm.getString("webappLoader.notReloadable"));
        if (!(container instanceof Context))
            throw new IllegalStateException
                (sm.getString("webappLoader.notContext"));

        // Start the background thread
        if (debug >= 1)
            log(" Starting background thread");
        threadDone = false;
        threadName = "WebappLoader[" + container.getName() + "]";
        thread = new Thread(this, threadName);
        thread.setDaemon(true);
        thread.start();

    }


    /**
     * Stop the background thread that is periodically checking for
     * modified classes.
     */
    private void threadStop() {

        if (thread == null)
            return;

        if (debug >= 1)
            log(" Stopping background thread");
        threadDone = true;
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            ;
        }

        thread = null;

    }


    /**
     * Validate that the required optional packages for this application
     * are actually present.
     *
     * @exception LifecycleException if a required package is not available
     */
    private void validatePackages() throws LifecycleException {

        ClassLoader classLoader = getClassLoader();
        if (classLoader instanceof WebappClassLoader) {

            Extension available[] =
                ((WebappClassLoader) classLoader).findAvailable();
            Extension required[] =
                ((WebappClassLoader) classLoader).findRequired();
            if (debug >= 1)
                log("Optional Packages:  available=" +
                    available.length + ", required=" +
                    required.length);

            for (int i = 0; i < required.length; i++) {
                if (debug >= 1)
                    log("Checking for required package " + required[i]);
                boolean found = false;
                for (int j = 0; j < available.length; j++) {
                    if (available[j].isCompatibleWith(required[i])) {
                        found = true;
                        break;
                    }
                }
                if (!found)
                    throw new LifecycleException
                        ("Missing optional package " + required[i]);
            }

        }

    }


    // ------------------------------------------------------ Background Thread


    /**
     * The background thread that checks for session timeouts and shutdown.
     */
    public void run() {

        if (debug >= 1)
            log("BACKGROUND THREAD Starting");

        // Loop until the termination semaphore is set
        while (!threadDone) {

            // Wait for our check interval
            threadSleep();

            if (!started)
                break;

            try {
                // Perform our modification check
                if (!classLoader.modified())
                    continue;
            } catch (Exception e) {
                log(sm.getString("webappLoader.failModifiedCheck"), e);
                continue;
            }

            // Handle a need for reloading
            notifyContext();
            break;

        }

        if (debug >= 1)
            log("BACKGROUND THREAD Stopping");

    }


    // -------------------------------------- WebappContextNotifier Inner Class


    /**
     * Private thread class to notify our associated Context that we have
     * recognized the need for a reload.
     */
    protected class WebappContextNotifier implements Runnable {


        /**
         * Perform the requested notification.
         */
        public void run() {

            ((Context) container).reload();

        }


    }


}
