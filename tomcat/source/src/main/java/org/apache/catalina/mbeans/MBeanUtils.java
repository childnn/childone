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

package org.apache.catalina.mbeans;


import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.deploy.ContextEnvironment;
import org.apache.catalina.deploy.ContextResource;
import org.apache.catalina.deploy.ContextResourceLink;
import org.apache.catalina.deploy.NamingResources;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.modeler.ManagedBean;
import org.apache.commons.modeler.Registry;

import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Public utility methods in support of the server side MBeans implementation.
 *
 * @author Craig R. McClanahan
 * @author Amy Roh
 * @version $Revision: 1.50 $ $Date: 2004/08/26 21:36:08 $
 */

public class MBeanUtils {


    // ------------------------------------------------------- Static Variables


    static boolean jsr77Names = false;
    /**
     * The set of exceptions to the normal rules used by
     * <code>createManagedBean()</code>.  The first element of each pair
     * is a class name, and the second element is the managed bean name.
     */
    private static String exceptions[][] = {
            {"org.apache.ajp.tomcat4.Ajp13Connector",
                    "Ajp13Connector"},
            {"org.apache.coyote.tomcat4.Ajp13Connector",
                    "CoyoteConnector"},
            {"org.apache.catalina.core.StandardDefaultContext",
                    "DefaultContext"},
            {"org.apache.catalina.connector.http10.HttpConnector",
                    "Http10Connector"},
            {"org.apache.catalina.connector.http.HttpConnector",
                    "Http11Connector"},
            {"org.apache.catalina.users.JDBCGroup",
                    "Group"},
            {"org.apache.catalina.users.JDBCRole",
                    "Role"},
            {"org.apache.catalina.users.JDBCUser",
                    "User"},
            {"org.apache.catalina.users.MemoryGroup",
                    "Group"},
            {"org.apache.catalina.users.MemoryRole",
                    "Role"},
            {"org.apache.catalina.users.MemoryUser",
                    "User"},
    };
    /**
     * The configuration information registry for our managed beans.
     */
    private static Registry registry = createRegistry();
    /**
     * The <code>MBeanServer</code> for this application.
     */
    private static MBeanServer mserver = createServer();

    // --------------------------------------------------------- Static Methods

    /**
     * Translates a string into x-www-form-urlencoded format
     *
     * @param t string to be encoded
     * @return encoded string
     */
    private static final String encodeStr(String t) {

        return URLEncoder.encode(t);

    }


    /**
     * Create and return the name of the <code>ManagedBean</code> that
     * corresponds to this Catalina component.
     *
     * @param component The component for which to create a name
     */
    public static String createManagedName(Object component) {

        // Deal with exceptions to the standard rule
        String className = component.getClass().getName();
        for (int i = 0; i < exceptions.length; i++) {
            if (className.equals(exceptions[i][0])) {
                return (exceptions[i][1]);
            }
        }

        // Perform the standard transformation
        int period = className.lastIndexOf('.');
        if (period >= 0)
            className = className.substring(period + 1);
        return (className);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>Connector</code> object.
     *
     * @param connector The Connector to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(Connector connector)
            throws Exception {

        String mname = createManagedName(connector);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(connector);
        ObjectName oname = createObjectName(domain, connector);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>Context</code> object.
     *
     * @param context The Context to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(Context context)
            throws Exception {

        String mname = createManagedName(context);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(context);
        ObjectName oname = createObjectName(domain, context);
        mserver.registerMBean(mbean, oname);
        if (jsr77Names) {
            oname = createObjectName77(domain, context);
            mserver.registerMBean(mbean, oname);
        }
        return (mbean);
    }

    public static ModelMBean createMBean(Wrapper wrapper)
            throws Exception {

        String mname = createManagedName(wrapper);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(wrapper);
        ObjectName oname = createObjectName(domain, wrapper);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>ContextEnvironment</code> object.
     *
     * @param environment The ContextEnvironment to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(ContextEnvironment environment)
            throws Exception {

        String mname = createManagedName(environment);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(environment);
        ObjectName oname = createObjectName(domain, environment);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>ContextResource</code> object.
     *
     * @param resource The ContextResource to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(ContextResource resource)
            throws Exception {

        String mname = createManagedName(resource);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(resource);
        ObjectName oname = createObjectName(domain, resource);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>ContextResourceLink</code> object.
     *
     * @param resourceLink The ContextResourceLink to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(ContextResourceLink resourceLink)
            throws Exception {

        String mname = createManagedName(resourceLink);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(resourceLink);
        ObjectName oname = createObjectName(domain, resourceLink);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>DefaultContext</code> object.
     *
     * @param context The DefaultContext to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(DefaultContext context)
            throws Exception {

        String mname = createManagedName(context);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(context);
        ObjectName oname = createObjectName(domain, context);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>Engine</code> object.
     *
     * @param engine The Engine to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(Engine engine)
            throws Exception {

        String mname = createManagedName(engine);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(engine);
        ObjectName oname = createObjectName(domain, engine);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>Group</code> object.
     *
     * @param group The Group to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(Group group)
            throws Exception {

        String mname = createManagedName(group);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(group);
        ObjectName oname = createObjectName(domain, group);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>Host</code> object.
     *
     * @param host The Host to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(Host host)
            throws Exception {

        String mname = createManagedName(host);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(host);
        ObjectName oname = createObjectName(domain, host);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>Loader</code> object.
     *
     * @param loader The Loader to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(Loader loader)
            throws Exception {

        String mname = createManagedName(loader);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(loader);
        ObjectName oname = createObjectName(domain, loader);
        mserver.registerMBean(mbean, oname);
        return (mbean);
    }

    /**
     * Create, register, and return an MBean for this
     * <code>Logger</code> object.
     *
     * @param logger The Logger to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(Logger logger)
            throws Exception {

        String mname = createManagedName(logger);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(logger);
        ObjectName oname = createObjectName(domain, logger);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>Manager</code> object.
     *
     * @param manager The Manager to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(Manager manager)
            throws Exception {

        String mname = createManagedName(manager);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(manager);
        ObjectName oname = createObjectName(domain, manager);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>MBeanFactory</code> object.
     *
     * @param factory The MBeanFactory to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(MBeanFactory factory)
            throws Exception {

        String mname = createManagedName(factory);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(factory);
        ObjectName oname = createObjectName(domain, factory);
        if (mserver.isRegistered(oname)) {
            mserver.unregisterMBean(oname);
        }
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>NamingResources</code> object.
     *
     * @param resource The NamingResources to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(NamingResources resource)
            throws Exception {

        String mname = createManagedName(resource);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(resource);
        ObjectName oname = createObjectName(domain, resource);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>Realm</code> object.
     *
     * @param realm The Realm to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(Realm realm)
            throws Exception {

        String mname = createManagedName(realm);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(realm);
        ObjectName oname = createObjectName(domain, realm);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>Role</code> object.
     *
     * @param role The Role to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(Role role)
            throws Exception {

        String mname = createManagedName(role);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(role);
        ObjectName oname = createObjectName(domain, role);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>Server</code> object.
     *
     * @param server The Server to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(Server server)
            throws Exception {

        String mname = createManagedName(server);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(server);
        ObjectName oname = createObjectName(domain, server);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>Service</code> object.
     *
     * @param service The Service to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(Service service)
            throws Exception {

        String mname = createManagedName(service);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(service);
        ObjectName oname = createObjectName(domain, service);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>User</code> object.
     *
     * @param user The User to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(User user)
            throws Exception {

        String mname = createManagedName(user);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(user);
        ObjectName oname = createObjectName(domain, user);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>UserDatabase</code> object.
     *
     * @param userDatabase The UserDatabase to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(UserDatabase userDatabase)
            throws Exception {

        String mname = createManagedName(userDatabase);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(userDatabase);
        ObjectName oname = createObjectName(domain, userDatabase);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }


    /**
     * Create, register, and return an MBean for this
     * <code>Valve</code> object.
     *
     * @param valve The Valve to be managed
     * @throws Exception if an MBean cannot be created or registered
     */
    public static ModelMBean createMBean(Valve valve)
            throws Exception {

        String mname = createManagedName(valve);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            Exception e = new Exception("ManagedBean is not found with " + mname);
            throw new MBeanException(e);
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ModelMBean mbean = managed.createMBean(valve);
        ObjectName oname = createObjectName(domain, valve);
        mserver.registerMBean(mbean, oname);
        return (mbean);

    }

    /**
     * Create an <code>ObjectName</code> for this
     * <code>Connector</code> object.
     *
     * @param domain    Domain in which this name is to be created
     * @param connector The Connector to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              Connector connector)
            throws MalformedObjectNameException {

        ObjectName name = null;
        if (connector instanceof HttpConnector) {
            HttpConnector httpConnector = (HttpConnector) connector;
            Service service = httpConnector.getService();
            String serviceName = null;
            if (service != null)
                serviceName = service.getName();
            name = new ObjectName(domain + ":type=Connector" +
                    ",service=" + serviceName +
                    ",port=" + httpConnector.getPort() +
                    ",address=" + httpConnector.getAddress());
            return (name);
        } else if (connector instanceof org.apache.catalina.connector.http10.HttpConnector) {
            org.apache.catalina.connector.http10.HttpConnector httpConnector =
                    (org.apache.catalina.connector.http10.HttpConnector) connector;
            Service service = httpConnector.getService();
            String serviceName = null;
            if (service != null)
                serviceName = service.getName();
            name = new ObjectName(domain + ":type=Connector" +
                    ",service=" + serviceName +
                    ",port=" + httpConnector.getPort() +
                    ",address=" + httpConnector.getAddress());
            return (name);
        } else if ("org.apache.ajp.tomcat4.Ajp13Connector".equals
                (connector.getClass().getName())) {
            try {
                String address = (String)
                        PropertyUtils.getSimpleProperty(connector, "address");
                Integer port = (Integer)
                        PropertyUtils.getSimpleProperty(connector, "port");
                Service service = connector.getService();
                String serviceName = null;
                if (service != null)
                    serviceName = service.getName();
                name = new ObjectName(domain + ":type=Connector" +
                        ",service=" + serviceName +
                        ",port=" + port +
                        ",address=" + address);
                return (name);
            } catch (Exception e) {
                throw new MalformedObjectNameException
                        ("Cannot create object name for " + connector + e);
            }
        } else if ("org.apache.coyote.tomcat4.CoyoteConnector".equals
                (connector.getClass().getName())) {
            try {
                String address = (String)
                        PropertyUtils.getSimpleProperty(connector, "address");
                Integer port = (Integer)
                        PropertyUtils.getSimpleProperty(connector, "port");
                Service service = connector.getService();
                String serviceName = null;
                if (service != null)
                    serviceName = service.getName();
                name = new ObjectName(domain + ":type=Connector" +
                        ",service=" + serviceName +
                        ",port=" + port +
                        ",address=" + address);
                return (name);
            } catch (Exception e) {
                throw new MalformedObjectNameException
                        ("Cannot create object name for " + connector + e);
            }
        } else {
            throw new MalformedObjectNameException
                    ("Cannot create object name for " + connector);
        }

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Context</code> object.
     *
     * @param domain  Domain in which this name is to be created
     * @param context The Context to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              Context context)
            throws MalformedObjectNameException {

        ObjectName name = null;
        Host host = (Host) context.getParent();
        Service service = ((Engine) host.getParent()).getService();
        String path = context.getPath();
        if (path.length() < 1)
            path = "/";
        name = new ObjectName(domain + ":type=Context,path=" +
                path + ",host=" +
                host.getName() + ",service=" +
                service.getName());
        return (name);

    }

    public static ObjectName createObjectName77(String domain,
                                                Context context)
            throws MalformedObjectNameException {

        ObjectName name = null;
        Host host = (Host) context.getParent();
        Service service = ((Engine) host.getParent()).getService();
        String path = context.getPath();
        if (path.length() < 1)
            path = "/";
        String localName = "//" +
                ((host.getName() == null) ? "DEFAULT" : host.getName()) + path;
        name = new ObjectName(domain + ":j2eeType=WebModule,name=" +
                localName + ",J2EEApplication=none,J2EEServer=none");
        return (name);
    }

    /**
     * Create an <code>ObjectName</code> for this
     * <code>Context</code> object.
     *
     * @param domain  Domain in which this name is to be created
     * @param context The Context to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              Wrapper wrapper)
            throws MalformedObjectNameException {

        ObjectName name = null;
        Context context = (Context) wrapper.getParent();
        Host host = (Host) context.getParent();
        Service service = ((Engine) host.getParent()).getService();
        String sname = wrapper.getJspFile();
        if (sname == null) {
            sname = wrapper.getName();
        }
        String path = context.getPath();
        if (path.length() < 1)
            path = "/";
        String hostName = host.getName();
        String webMod = "//" + ((hostName == null) ? "DEFAULT" : hostName) + path;
        name = new ObjectName(domain + ":j2eeType=Servlet,name=" +
                sname + ",WebModule=" +
                webMod + ",J2EEApplication=none,J2EEServer=none");

        return (name);

    }

    /**
     * Create an <code>ObjectName</code> for this
     * <code>Service</code> object.
     *
     * @param domain  Domain in which this name is to be created
     * @param context The ContextEnvironment to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              ContextEnvironment environment)
            throws MalformedObjectNameException {

        ObjectName name = null;
        Object container =
                environment.getNamingResources().getContainer();
        if (container instanceof Server) {
            name = new ObjectName(domain + ":type=Environment" +
                    ",resourcetype=Global,name=" + environment.getName());
        } else if (container instanceof Context) {
            String path = ((Context) container).getPath();
            if (path.length() < 1)
                path = "/";
            Host host = (Host) ((Context) container).getParent();
            Engine engine = (Engine) host.getParent();
            Service service = engine.getService();
            name = new ObjectName(domain + ":type=Environment" +
                    ",resourcetype=Context,path=" + path +
                    ",host=" + host.getName() +
                    ",service=" + service.getName() +
                    ",name=" + environment.getName());
        } else if (container instanceof DefaultContext) {
            container = ((DefaultContext) container).getParent();
            if (container instanceof Host) {
                Host host = (Host) container;
                Service service = ((Engine) host.getParent()).getService();
                name = new ObjectName(domain + ":type=Environment" +
                        ",resourcetype=HostDefaultContext,host=" + host.getName() +
                        ",service=" + service.getName() +
                        ",name=" + environment.getName());
            } else if (container instanceof Engine) {
                Engine engine = (Engine) container;
                Service service = engine.getService();
                name = new ObjectName(domain + ":type=Environment" +
                        ",resourcetype=ServiceDefaultContext,service=" +
                        service.getName() + ",name=" + environment.getName());
            }
        }

        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>ContextResource</code> object.
     *
     * @param domain   Domain in which this name is to be created
     * @param resource The ContextResource to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              ContextResource resource)
            throws MalformedObjectNameException {

        ObjectName name = null;
        String encodedResourceName = encodeStr(resource.getName());
        Object container =
                resource.getNamingResources().getContainer();
        if (container instanceof Server) {
            name = new ObjectName(domain + ":type=Resource" +
                    ",resourcetype=Global,class=" + resource.getType() +
                    ",name=" + encodedResourceName);
        } else if (container instanceof Context) {
            String path = ((Context) container).getPath();
            if (path.length() < 1)
                path = "/";
            Host host = (Host) ((Context) container).getParent();
            Engine engine = (Engine) host.getParent();
            Service service = engine.getService();
            name = new ObjectName(domain + ":type=Resource" +
                    ",resourcetype=Context,path=" + path +
                    ",host=" + host.getName() +
                    ",service=" + service.getName() +
                    ",class=" + resource.getType() +
                    ",name=" + encodedResourceName);
        } else if (container instanceof DefaultContext) {
            container = ((DefaultContext) container).getParent();
            if (container instanceof Host) {
                Host host = (Host) container;
                Service service = ((Engine) host.getParent()).getService();
                name = new ObjectName(domain + ":type=Resource" +
                        ",resourcetype=HostDefaultContext,host=" + host.getName() +
                        ",service=" + service.getName() +
                        ",class=" + resource.getType() +
                        ",name=" + encodedResourceName);
            } else if (container instanceof Engine) {
                Engine engine = (Engine) container;
                Service service = engine.getService();
                name = new ObjectName(domain + ":type=Resource" +
                        ",resourcetype=ServiceDefaultContext,service=" + service.getName() +
                        ",class=" + resource.getType() +
                        ",name=" + encodedResourceName);
            }
        }

        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>ContextResourceLink</code> object.
     *
     * @param domain       Domain in which this name is to be created
     * @param resourceLink The ContextResourceLink to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              ContextResourceLink resourceLink)
            throws MalformedObjectNameException {

        ObjectName name = null;
        String encodedResourceLinkName = encodeStr(resourceLink.getName());
        Object container =
                resourceLink.getNamingResources().getContainer();
        if (container instanceof Server) {
            name = new ObjectName(domain + ":type=ResourceLink" +
                    ",resourcetype=Global" +
                    ",name=" + encodedResourceLinkName);
        } else if (container instanceof Context) {
            String path = ((Context) container).getPath();
            if (path.length() < 1)
                path = "/";
            Host host = (Host) ((Context) container).getParent();
            Engine engine = (Engine) host.getParent();
            Service service = engine.getService();
            name = new ObjectName(domain + ":type=ResourceLink" +
                    ",resourcetype=Context,path=" + path +
                    ",host=" + host.getName() +
                    ",service=" + service.getName() +
                    ",name=" + encodedResourceLinkName);
        } else if (container instanceof DefaultContext) {
            container = ((DefaultContext) container).getParent();
            if (container instanceof Host) {
                Host host = (Host) container;
                Service service = ((Engine) host.getParent()).getService();
                name = new ObjectName(domain + ":type=ResourceLink" +
                        ",resourcetype=HostDefaultContext,host=" + host.getName() +
                        ",service=" + service.getName() +
                        ",name=" + encodedResourceLinkName);
            } else if (container instanceof Engine) {
                Engine engine = (Engine) container;
                Service service = engine.getService();
                name = new ObjectName(domain + ":type=ResourceLink" +
                        ",resourcetype=ServiceDefaultContext,service=" + service.getName() +
                        ",name=" + encodedResourceLinkName);
            }
        }

        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>DefaultContext</code> object.
     *
     * @param domain  Domain in which this name is to be created
     * @param context The DefaultContext to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              DefaultContext context)
            throws MalformedObjectNameException {

        ObjectName name = null;
        Container container = context.getParent();
        if (container instanceof Host) {
            Host host = (Host) container;
            Service service = ((Engine) host.getParent()).getService();
            name = new ObjectName(domain + ":type=DefaultContext,host=" +
                    host.getName() + ",service=" +
                    service.getName());
        } else if (container instanceof Engine) {
            Engine engine = (Engine) container;
            Service service = engine.getService();
            name = new ObjectName(domain + ":type=DefaultContext,service=" +
                    service.getName());
        }

        return (name);

    }

    /**
     * Create an <code>ObjectName</code> for this
     * <code>Engine</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param engine The Engine to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              Engine engine)
            throws MalformedObjectNameException {

        ObjectName name = null;
        name = new ObjectName(domain + ":type=Engine,service=" +
                engine.getService().getName());
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Group</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param group  The Group to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              Group group)
            throws MalformedObjectNameException {

        ObjectName name = null;
        name = new ObjectName(domain + ":type=Group,groupname=" +
                group.getGroupname() + ",database=" +
                group.getUserDatabase().getId());
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Host</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param host   The Host to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              Host host)
            throws MalformedObjectNameException {

        ObjectName name = null;
        Engine engine = (Engine) host.getParent();
        Service service = engine.getService();
        name = new ObjectName(domain + ":type=Host,host=" +
                host.getName() + ",service=" +
                service.getName());
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Loader</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param loader The Loader to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              Loader loader)
            throws MalformedObjectNameException {

        ObjectName name = null;
        Container container = loader.getContainer();

        if (container instanceof Engine) {
            Service service = ((Engine) container).getService();
            name = new ObjectName(domain + ":type=Loader,service=" +
                    service.getName());
        } else if (container instanceof Host) {
            Engine engine = (Engine) container.getParent();
            Service service = engine.getService();
            name = new ObjectName(domain + ":type=Loader,host=" +
                    container.getName() + ",service=" +
                    service.getName());
        } else if (container instanceof Context) {
            String path = ((Context) container).getPath();
            if (path.length() < 1) {
                path = "/";
            }
            Host host = (Host) container.getParent();
            Engine engine = (Engine) host.getParent();
            Service service = engine.getService();
            name = new ObjectName(domain + ":type=Loader,path=" + path +
                    ",host=" + host.getName() + ",service=" +
                    service.getName());
        } else if (container == null) {
            DefaultContext defaultContext = loader.getDefaultContext();
            if (defaultContext != null) {
                Container parent = defaultContext.getParent();
                if (parent instanceof Engine) {
                    Service service = ((Engine) parent).getService();
                    name = new ObjectName(domain + ":type=DefaultLoader,service=" +
                            service.getName());
                } else if (parent instanceof Host) {
                    Engine engine = (Engine) parent.getParent();
                    Service service = engine.getService();
                    name = new ObjectName(domain + ":type=DefaultLoader,host=" +
                            parent.getName() + ",service=" + service.getName());
                }
            }
        }

        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Logger</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param logger The Logger to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              Logger logger)
            throws MalformedObjectNameException {

        ObjectName name = null;
        Container container = logger.getContainer();

        if (container instanceof Engine) {
            Service service = ((Engine) container).getService();
            name = new ObjectName(domain + ":type=Logger,service=" +
                    service.getName());
        } else if (container instanceof Host) {
            Engine engine = (Engine) container.getParent();
            Service service = engine.getService();
            name = new ObjectName(domain + ":type=Logger,host=" +
                    container.getName() + ",service=" +
                    service.getName());
        } else if (container instanceof Context) {
            String path = ((Context) container).getPath();
            if (path.length() < 1) {
                path = "/";
            }
            Host host = (Host) container.getParent();
            Engine engine = (Engine) host.getParent();
            Service service = engine.getService();
            name = new ObjectName(domain + ":type=Logger,path=" + path +
                    ",host=" + host.getName() + ",service=" +
                    service.getName());
        }

        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Manager</code> object.
     *
     * @param domain  Domain in which this name is to be created
     * @param manager The Manager to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              Manager manager)
            throws MalformedObjectNameException {

        ObjectName name = null;
        Container container = manager.getContainer();

        if (container instanceof Engine) {
            Service service = ((Engine) container).getService();
            name = new ObjectName(domain + ":type=Manager,service=" +
                    service.getName());
        } else if (container instanceof Host) {
            Engine engine = (Engine) container.getParent();
            Service service = engine.getService();
            name = new ObjectName(domain + ":type=Manager,host=" +
                    container.getName() + ",service=" +
                    service.getName());
        } else if (container instanceof Context) {
            String path = ((Context) container).getPath();
            if (path.length() < 1) {
                path = "/";
            }
            Host host = (Host) container.getParent();
            Engine engine = (Engine) host.getParent();
            Service service = engine.getService();
            name = new ObjectName(domain + ":type=Manager,path=" + path +
                    ",host=" + host.getName() + ",service=" +
                    service.getName());
        } else if (container == null) {
            DefaultContext defaultContext = manager.getDefaultContext();
            if (defaultContext != null) {
                Container parent = defaultContext.getParent();
                if (parent instanceof Engine) {
                    Service service = ((Engine) parent).getService();
                    name = new ObjectName(domain + ":type=DefaultManager,service=" +
                            service.getName());
                } else if (parent instanceof Host) {
                    Engine engine = (Engine) parent.getParent();
                    Service service = engine.getService();
                    name = new ObjectName(domain + ":type=DefaultManager,host=" +
                            parent.getName() + ",service=" + service.getName());
                }
            }
        }

        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Server</code> object.
     *
     * @param domain    Domain in which this name is to be created
     * @param resources The NamingResources to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              NamingResources resources)
            throws MalformedObjectNameException {

        ObjectName name = null;
        Object container = resources.getContainer();
        if (container instanceof Server) {
            name = new ObjectName(domain + ":type=NamingResources" +
                    ",resourcetype=Global");
        } else if (container instanceof Context) {
            String path = ((Context) container).getPath();
            if (path.length() < 1)
                path = "/";
            Host host = (Host) ((Context) container).getParent();
            Engine engine = (Engine) host.getParent();
            Service service = engine.getService();
            name = new ObjectName(domain + ":type=NamingResources" +
                    ",resourcetype=Context,path=" + path +
                    ",host=" + host.getName() +
                    ",service=" + service.getName());
        } else if (container instanceof DefaultContext) {
            container = ((DefaultContext) container).getParent();
            if (container instanceof Host) {
                Host host = (Host) container;
                Service service = ((Engine) host.getParent()).getService();
                name = new ObjectName(domain + ":type=NamingResources" +
                        ",resourcetype=HostDefaultContext,host=" + host.getName() +
                        ",service=" + service.getName());
            } else if (container instanceof Engine) {
                Engine engine = (Engine) container;
                Service service = engine.getService();
                name = new ObjectName(domain + ":type=NamingResources" +
                        ",resourcetype=ServiceDefaultContext" +
                        ",service=" + service.getName());
            }
        }

        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>MBeanFactory</code> object.
     *
     * @param domain  Domain in which this name is to be created
     * @param factory The MBeanFactory to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              MBeanFactory factory)
            throws MalformedObjectNameException {

        ObjectName name = new ObjectName(domain + ":type=MBeanFactory");

        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Realm</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param realm  The Realm to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              Realm realm)
            throws MalformedObjectNameException {

        ObjectName name = null;
        Container container = realm.getContainer();

        if (container instanceof Engine) {
            Service service = ((Engine) container).getService();
            name = new ObjectName(domain + ":type=Realm,service=" +
                    service.getName());
        } else if (container instanceof Host) {
            Engine engine = (Engine) container.getParent();
            Service service = engine.getService();
            name = new ObjectName(domain + ":type=Realm,host=" +
                    container.getName() + ",service=" +
                    service.getName());
        } else if (container instanceof Context) {
            String path = ((Context) container).getPath();
            if (path.length() < 1) {
                path = "/";
            }
            Host host = (Host) container.getParent();
            Engine engine = (Engine) host.getParent();
            Service service = engine.getService();
            name = new ObjectName(domain + ":type=Realm,path=" + path +
                    ",host=" + host.getName() + ",service=" +
                    service.getName());
        }

        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Role</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param role   The Role to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              Role role)
            throws MalformedObjectNameException {

        ObjectName name = null;
        name = new ObjectName(domain + ":type=Role,rolename=" +
                role.getRolename() + ",database=" +
                role.getUserDatabase().getId());
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Server</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param server The Server to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              Server server)
            throws MalformedObjectNameException {

        ObjectName name = null;
        name = new ObjectName(domain + ":type=Server");
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Service</code> object.
     *
     * @param domain  Domain in which this name is to be created
     * @param service The Service to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              Service service)
            throws MalformedObjectNameException {

        ObjectName name = null;
        name = new ObjectName(domain + ":type=Service,name=" +
                service.getName());
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>User</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param user   The User to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              User user)
            throws MalformedObjectNameException {

        ObjectName name = null;
        name = new ObjectName(domain + ":type=User,username=" +
                user.getUsername() + ",database=" +
                user.getUserDatabase().getId());
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>UserDatabase</code> object.
     *
     * @param domain       Domain in which this name is to be created
     * @param userDatabase The UserDatabase to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              UserDatabase userDatabase)
            throws MalformedObjectNameException {

        ObjectName name = null;
        name = new ObjectName(domain + ":type=UserDatabase,database=" +
                userDatabase.getId());
        return (name);

    }


    /**
     * Create an <code>ObjectName</code> for this
     * <code>Valve</code> object.
     *
     * @param domain Domain in which this name is to be created
     * @param valve  The Valve to be named
     * @throws MalformedObjectNameException if a name cannot be created
     */
    public static ObjectName createObjectName(String domain,
                                              Valve valve)
            throws MalformedObjectNameException {

        ObjectName name = null;
        Container container = null;
        if (valve instanceof Contained) {
            container = ((Contained) valve).getContainer();
        }
        if (container == null) {
            throw new MalformedObjectNameException(
                    "Cannot create mbean for non-contained valve " +
                            valve);
        }

        if (container instanceof Engine) {
            Service service = ((Engine) container).getService();
            name = new ObjectName(domain + ":type=Valve,sequence=" +
                    valve.hashCode() + ",service=" +
                    service.getName());
        } else if (container instanceof Host) {
            Service service = ((Engine) container.getParent()).getService();
            name = new ObjectName(domain + ":type=Valve,sequence=" +
                    valve.hashCode() + ",host=" +
                    container.getName() + ",service=" +
                    service.getName());
        } else if (container instanceof Context) {
            String path = ((Context) container).getPath();
            if (path.length() < 1) {
                path = "/";
            }
            Host host = (Host) container.getParent();
            Service service = ((Engine) host.getParent()).getService();
            name = new ObjectName(domain + ":type=Valve,sequence=" +
                    valve.hashCode() + ",path=" +
                    path + ",host=" +
                    host.getName() + ",service=" +
                    service.getName());
        }

        return (name);

    }

    /**
     * Create and configure (if necessary) and return the registry of
     * managed object descriptions.
     */
    public synchronized static Registry createRegistry() {

        if (registry == null) {
            try {
                URL url = ServerLifecycleListener.class.getResource
                        ("/org/apache/catalina/mbeans/mbeans-descriptors.xml");
                InputStream stream = url.openStream();
                //                Registry.setDebug(1);
                Registry.loadRegistry(stream);
                stream.close();
                registry = Registry.getRegistry();
            } catch (Throwable t) {
                t.printStackTrace(System.out);
                System.exit(1);
            }
        }
        return (registry);

    }


    /**
     * Load an MBean descriptor resource.
     */
    public synchronized static void loadMBeanDescriptors(String resource) {

        try {
            URL url = ServerLifecycleListener.class.getResource(resource);
            if (url != null) {
                InputStream stream = url.openStream();
                Registry.loadRegistry(stream);
                stream.close();
            } else {
                // XXX: i18n
                System.out.println("MBean descriptors not found:" + resource);
            }
        } catch (Throwable t) {
            t.printStackTrace(System.out);
        }

    }


    /**
     * Create and configure (if necessary) and return the
     * <code>MBeanServer</code> with which we will be
     * registering our <code>ModelMBean</code> implementations.
     */
    public synchronized static MBeanServer createServer() {

        if (mserver == null) {
            try {
                //Trace.parseTraceProperties();
                //mserver = MBeanServerFactory.createMBeanServer();
                mserver = Registry.getServer();
            } catch (Throwable t) {
                t.printStackTrace(System.out);
                System.exit(1);
            }
        }
        return (mserver);

    }


    /**
     * Deregister the MBean for this
     * <code>Connector</code> object.
     *
     * @param connector The Connector to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(Connector connector, Service service)
            throws Exception {

        connector.setService(service);
        String mname = createManagedName(connector);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, connector);
        connector.setService(null);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Context</code> object.
     *
     * @param context The Context to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(Context context)
            throws Exception {

        String mname = createManagedName(context);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, context);
        mserver.unregisterMBean(oname);
        if (jsr77Names) {
            oname = createObjectName77(domain, context);
            mserver.unregisterMBean(oname);
        }
    }


    /**
     * Deregister the MBean for this
     * <code>ContextEnvironment</code> object.
     *
     * @param environment The ContextEnvironment to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(ContextEnvironment environment)
            throws Exception {

        String mname = createManagedName(environment);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, environment);
        mserver.unregisterMBean(oname);

    }

    /**
     * Deregister the MBean for this
     * <code>Wrapper</code> object.
     *
     * @param wrapper The Wrapper to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(Wrapper wrapper)
            throws Exception {

        String mname = createManagedName(wrapper);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, wrapper);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>ContextResource</code> object.
     *
     * @param resource The ContextResource to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(ContextResource resource)
            throws Exception {

        String mname = createManagedName(resource);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, resource);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>ContextResourceLink</code> object.
     *
     * @param resourceLink The ContextResourceLink to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(ContextResourceLink resourceLink)
            throws Exception {

        String mname = createManagedName(resourceLink);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, resourceLink);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>DefaultContext</code> object.
     *
     * @param context The DefaultContext to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(DefaultContext context)
            throws Exception {

        String mname = createManagedName(context);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, context);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Engine</code> object.
     *
     * @param engine The Engine to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(Engine engine)
            throws Exception {

        String mname = createManagedName(engine);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, engine);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Group</code> object.
     *
     * @param group The Group to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(Group group)
            throws Exception {

        String mname = createManagedName(group);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, group);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Host</code> object.
     *
     * @param host The Host to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(Host host)
            throws Exception {

        String mname = createManagedName(host);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, host);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Loader</code> object.
     *
     * @param loader The Loader to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(Loader loader)
            throws Exception {

        String mname = createManagedName(loader);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, loader);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Logger</code> object.
     *
     * @param logger The Logger to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(Logger logger)
            throws Exception {

        String mname = createManagedName(logger);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, logger);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Manager</code> object.
     *
     * @param manager The Manager to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(Manager manager)
            throws Exception {

        String mname = createManagedName(manager);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, manager);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>NamingResources</code> object.
     *
     * @param resources The NamingResources to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(NamingResources resources)
            throws Exception {

        String mname = createManagedName(resources);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, resources);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Realm</code> object.
     *
     * @param realm The Realm to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(Realm realm)
            throws Exception {

        String mname = createManagedName(realm);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, realm);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Role</code> object.
     *
     * @param role The Role to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(Role role)
            throws Exception {

        String mname = createManagedName(role);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, role);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Server</code> object.
     *
     * @param server The Server to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(Server server)
            throws Exception {

        String mname = createManagedName(server);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, server);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Service</code> object.
     *
     * @param service The Service to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(Service service)
            throws Exception {

        String mname = createManagedName(service);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, service);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>User</code> object.
     *
     * @param user The User to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(User user)
            throws Exception {

        String mname = createManagedName(user);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, user);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>UserDatabase</code> object.
     *
     * @param userDatabase The UserDatabase to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(UserDatabase userDatabase)
            throws Exception {

        String mname = createManagedName(userDatabase);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, userDatabase);
        mserver.unregisterMBean(oname);

    }


    /**
     * Deregister the MBean for this
     * <code>Valve</code> object.
     *
     * @param valve The Valve to be managed
     * @throws Exception if an MBean cannot be deregistered
     */
    public static void destroyMBean(Valve valve, Container container)
            throws Exception {

        ((Contained) valve).setContainer(container);
        String mname = createManagedName(valve);
        ManagedBean managed = registry.findManagedBean(mname);
        if (managed == null) {
            return;
        }
        String domain = managed.getDomain();
        if (domain == null)
            domain = mserver.getDefaultDomain();
        ObjectName oname = createObjectName(domain, valve);
        try {
            ((Contained) valve).setContainer(null);
        } catch (Throwable t) {
            ;
        }
        mserver.unregisterMBean(oname);

    }

}
