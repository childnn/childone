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


package tomcat4.org.apache.catalina.startup;


import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Logger;
import org.apache.catalina.util.StringManager;


/**
 * Startup event listener for a <b>Host</b> that configures Contexts (web
 * applications) for all defined "users" who have a web application in a
 * directory with the specified name in their home directories.  The context
 * path of each deployed application will be set to <code>~xxxxx</code>, where
 * xxxxx is the username of the owning user for that web application
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.4 $ $Date: 2004/08/26 21:41:41 $
 */

public final class UserConfig
    implements LifecycleListener {


    // ----------------------------------------------------- Instance Variables


    /**
     * The Java class name of the Context configuration class we should use.
     */
    private String configClass = "org.apache.catalina.startup.ContextConfig";


    /**
     * The Java class name of the Context implementation we should use.
     */
    private String contextClass = "org.apache.catalina.core.StandardContext";


    /**
     * The debugging detail level for this component.
     */
    private int debug = 999;


    /**
     * The directory name to be searched for within each user home directory.
     */
    private String directoryName = "public_html";


    /**
     * The base directory containing user home directories.
     */
    private String homeBase = null;


    /**
     * The Host we are associated with.
     */
    private Host host = null;


    /**
     * The string resources for this package.
     */
    private static final StringManager sm =
        StringManager.getManager(Constants.Package);


    /**
     * The Java class name of the user database class we should use.
     */
    private String userClass =
        "org.apache.catalina.startup.PasswdUserDatabase";


    // ------------------------------------------------------------- Properties


    /**
     * Return the Context configuration class name.
     */
    public String getConfigClass() {

        return (this.configClass);

    }


    /**
     * Set the Context configuration class name.
     *
     * @param configClass The new Context configuration class name.
     */
    public void setConfigClass(String configClass) {

        this.configClass = configClass;

    }


    /**
     * Return the Context implementation class name.
     */
    public String getContextClass() {

        return (this.contextClass);

    }


    /**
     * Set the Context implementation class name.
     *
     * @param contextClass The new Context implementation class name.
     */
    public void setContextClass(String contextClass) {

        this.contextClass = contextClass;

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
     * Return the directory name for user web applications.
     */
    public String getDirectoryName() {

        return (this.directoryName);

    }


    /**
     * Set the directory name for user web applications.
     *
     * @param directoryName The new directory name
     */
    public void setDirectoryName(String directoryName) {

        this.directoryName = directoryName;

    }


    /**
     * Return the base directory containing user home directories.
     */
    public String getHomeBase() {

        return (this.homeBase);

    }


    /**
     * Set the base directory containing user home directories.
     *
     * @param homeBase The new base directory
     */
    public void setHomeBase(String homeBase) {

        this.homeBase = homeBase;

    }


    /**
     * Return the user database class name for this component.
     */
    public String getUserClass() {

        return (this.userClass);

    }


    /**
     * Set the user database class name for this component.
     */
    public void setUserClass(String userClass) {

        this.userClass = userClass;

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Process the START event for an associated Host.
     *
     * @param event The lifecycle event that has occurred
     */
    public void lifecycleEvent(LifecycleEvent event) {

        // Identify the host we are associated with
        try {
            host = (Host) event.getLifecycle();
        } catch (ClassCastException e) {
            log(sm.getString("hostConfig.cce", event.getLifecycle()), e);
            return;
        }

        // Process the event that has occurred
        if (event.getType().equals(Lifecycle.START_EVENT))
            start();
        else if (event.getType().equals(Lifecycle.STOP_EVENT))
            stop();

    }


    // -------------------------------------------------------- Private Methods


    /**
     * Deploy a web application for any user who has a web application present
     * in a directory with a specified name within their home directory.
     */
    private void deploy() {

        if (debug >= 1)
            log(sm.getString("userConfig.deploying"));

        // Load the user database object for this host
        UserDatabase database = null;
        try {
            Class clazz = Class.forName(userClass);
            database = (UserDatabase) clazz.newInstance();
            database.setUserConfig(this);
        } catch (Exception e) {
            log(sm.getString("userConfig.database"), e);
            return;
        }

        // Deploy the web application (if any) for each defined user
        Enumeration users = database.getUsers();
        while (users.hasMoreElements()) {
            String user = (String) users.nextElement();
            String home = database.getHome(user);
            deploy(user, home);
        }

    }


    /**
     * Deploy a web application for the specified user if they have such an
     * application in the defined directory within their home directory.
     *
     * @param user Username owning the application to be deployed
     * @param home Home directory of this user
     */
    private void deploy(String user, String home) {

        // Does this user have a web application to be deployed?
        String contextPath = "/~" + user;
        if (host.findChild(contextPath) != null)
            return;
        File app = new File(home, directoryName);
        if (!app.exists() || !app.isDirectory())
            return;
        /*
        File dd = new File(app, "/WEB-INF/web.xml");
        if (!dd.exists() || !dd.isFile() || !dd.canRead())
            return;
        */
        log(sm.getString("userConfig.deploy", user));

        // Deploy the web application for this user
        try {
            Class clazz = Class.forName(contextClass);
            Context context =
              (Context) clazz.newInstance();
            context.setPath(contextPath);
            context.setDocBase(app.toString());
            if (context instanceof Lifecycle) {
                clazz = Class.forName(configClass);
                LifecycleListener listener =
                  (LifecycleListener) clazz.newInstance();
                ((Lifecycle) context).addLifecycleListener(listener);
            }
            host.addChild(context);
        } catch (Exception e) {
            log(sm.getString("userConfig.error", user), e);
        }

    }


    /**
     * Log a message on the Logger associated with our Host (if any)
     *
     * @param message Message to be logged
     */
    private void log(String message) {

        Logger logger = null;
        if (host != null)
            logger = host.getLogger();
        if (logger != null)
            logger.log("UserConfig[" + host.getName() + "]: " + message);
        else
            System.out.println("UserConfig[" + host.getName() + "]: "
                               + message);

    }


    /**
     * Log a message on the Logger associated with our Host (if any)
     *
     * @param message Message to be logged
     * @param throwable Associated exception
     */
    private void log(String message, Throwable throwable) {

        Logger logger = null;
        if (host != null)
            logger = host.getLogger();
        if (logger != null)
            logger.log("UserConfig[" + host.getName() + "] "
                       + message, throwable);
        else {
            System.out.println("UserConfig[" + host.getName() + "]: "
                               + message);
            System.out.println("" + throwable);
            throwable.printStackTrace(System.out);
        }

    }


    /**
     * Process a "start" event for this Host.
     */
    private void start() {

        if (debug > 0)
            log(sm.getString("userConfig.start"));

        deploy();

    }


    /**
     * Process a "stop" event for this Host.
     */
    private void stop() {

        if (debug > 0)
            log(sm.getString("userConfig.stop"));

    }


}
