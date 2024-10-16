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


package org.apache.catalina.deploy;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Hashtable;


/**
 * Holds and manages the naming resources defined in the J2EE Enterprise
 * Naming Context and their associated JNDI context.
 *
 * @author Remy Maucherat
 * @version $Revision: 1.11 $ $Date: 2004/08/26 21:33:24 $
 */

public final class NamingResources {


    // ----------------------------------------------------------- Constructors


    /**
     * The property change support for this component.
     */
    protected PropertyChangeSupport support = new PropertyChangeSupport(this);


    // ----------------------------------------------------- Instance Variables
    /**
     * Associated container object.
     */
    private Object container = null;


    /**
     * List of naming entries, keyed by name. The value is the entry type, as
     * declared by the user.
     */
    private Hashtable entries = new Hashtable();


    /**
     * The EJB resource references for this web application, keyed by name.
     */
    private HashMap ejbs = new HashMap();


    /**
     * The environment entries for this web application, keyed by name.
     */
    private HashMap envs = new HashMap();


    /**
     * The local  EJB resource references for this web application, keyed by
     * name.
     */
    private HashMap localEjbs = new HashMap();


    /**
     * The resource environment references for this web application,
     * keyed by name.
     */
    private HashMap resourceEnvRefs = new HashMap();


    /**
     * The resource references for this web application, keyed by name.
     */
    private HashMap resources = new HashMap();


    /**
     * The resource links for this web application, keyed by name.
     */
    private HashMap resourceLinks = new HashMap();


    /**
     * The resource parameters for this web application, keyed by name.
     */
    private HashMap resourceParams = new HashMap();


    /**
     * Create a new NamingResources instance.
     */
    public NamingResources() {
    }


    // ------------------------------------------------------------- Properties

    /**
     * Get the container with which the naming resources are associated.
     */
    public Object getContainer() {
        return container;
    }


    /**
     * Set the container with which the naming resources are associated.
     */
    public void setContainer(Object container) {
        this.container = container;
    }


    /**
     * Add an EJB resource reference for this web application.
     *
     * @param ejb New EJB resource reference
     */
    public void addEjb(ContextEjb ejb) {

        if (entries.containsKey(ejb.getName())) {
            return;
        } else {
            entries.put(ejb.getName(), ejb.getType());
        }

        synchronized (ejbs) {
            ejb.setNamingResources(this);
            ejbs.put(ejb.getName(), ejb);
        }
        support.firePropertyChange("ejb", null, ejb);

    }


    /**
     * Add an environment entry for this web application.
     *
     * @param environment New environment entry
     */
    public void addEnvironment(ContextEnvironment environment) {

        if (entries.containsKey(environment.getName())) {
            return;
        } else {
            entries.put(environment.getName(), environment.getType());
        }

        synchronized (envs) {
            environment.setNamingResources(this);
            envs.put(environment.getName(), environment);
        }
        support.firePropertyChange("environment", null, environment);

    }


    /**
     * Add resource parameters for this web application.
     *
     * @param resourceParameters New resource parameters
     */
    public void addResourceParams(ResourceParams resourceParameters) {

        synchronized (resourceParams) {
            if (resourceParams.containsKey(resourceParameters.getName())) {
                return;
            }
            resourceParameters.setNamingResources(this);
            resourceParams.put(resourceParameters.getName(),
                    resourceParameters);
        }
        support.firePropertyChange("resourceParams", null, resourceParameters);

    }


    /**
     * Add a local EJB resource reference for this web application.
     *
     * @param ejb New EJB resource reference
     */
    public void addLocalEjb(ContextLocalEjb ejb) {

        if (entries.containsKey(ejb.getName())) {
            return;
        } else {
            entries.put(ejb.getName(), ejb.getType());
        }

        synchronized (localEjbs) {
            ejb.setNamingResources(this);
            localEjbs.put(ejb.getName(), ejb);
        }
        support.firePropertyChange("localEjb", null, ejb);

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
     * Add a resource reference for this web application.
     *
     * @param resource New resource reference
     */
    public void addResource(ContextResource resource) {

        if (entries.containsKey(resource.getName())) {
            return;
        } else {
            entries.put(resource.getName(), resource.getType());
        }

        synchronized (resources) {
            resource.setNamingResources(this);
            resources.put(resource.getName(), resource);
        }
        support.firePropertyChange("resource", null, resource);

    }


    /**
     * Add a resource environment reference for this web application.
     *
     * @param name The resource environment reference name
     * @param type The resource environment reference type
     */
    public void addResourceEnvRef(String name, String type) {

        if (entries.containsKey(name)) {
            return;
        } else {
            entries.put(name, type);
        }

        synchronized (resourceEnvRefs) {
            resourceEnvRefs.put(name, type);
        }
        support.firePropertyChange("resourceEnvRef", null,
                name + ":" + type);

    }


    /**
     * Add a resource link for this web application.
     *
     * @param resource New resource link
     */
    public void addResourceLink(ContextResourceLink resourceLink) {

        if (entries.containsKey(resourceLink.getName())) {
            return;
        } else {
            Object value = resourceLink.getType();
            if (value == null) {
                value = "";
            }
            entries.put(resourceLink.getName(), value);
        }

        synchronized (resourceLinks) {
            resourceLink.setNamingResources(this);
            resourceLinks.put(resourceLink.getName(), resourceLink);
        }
        support.firePropertyChange("resourceLink", null, resourceLink);

    }


    /**
     * Return the EJB resource reference with the specified name, if any;
     * otherwise, return <code>null</code>.
     *
     * @param name Name of the desired EJB resource reference
     */
    public ContextEjb findEjb(String name) {

        synchronized (ejbs) {
            return ((ContextEjb) ejbs.get(name));
        }

    }


    /**
     * Return the defined EJB resource references for this application.
     * If there are none, a zero-length array is returned.
     */
    public ContextEjb[] findEjbs() {

        synchronized (ejbs) {
            ContextEjb results[] = new ContextEjb[ejbs.size()];
            return ((ContextEjb[]) ejbs.values().toArray(results));
        }

    }


    /**
     * Return the environment entry with the specified name, if any;
     * otherwise, return <code>null</code>.
     *
     * @param name Name of the desired environment entry
     */
    public ContextEnvironment findEnvironment(String name) {

        synchronized (envs) {
            return ((ContextEnvironment) envs.get(name));
        }

    }


    /**
     * Return the set of defined environment entries for this web
     * application.  If none have been defined, a zero-length array
     * is returned.
     */
    public ContextEnvironment[] findEnvironments() {

        synchronized (envs) {
            ContextEnvironment results[] = new ContextEnvironment[envs.size()];
            return ((ContextEnvironment[]) envs.values().toArray(results));
        }

    }


    /**
     * Return the local EJB resource reference with the specified name, if any;
     * otherwise, return <code>null</code>.
     *
     * @param name Name of the desired EJB resource reference
     */
    public ContextLocalEjb findLocalEjb(String name) {

        synchronized (localEjbs) {
            return ((ContextLocalEjb) localEjbs.get(name));
        }

    }


    /**
     * Return the defined local EJB resource references for this application.
     * If there are none, a zero-length array is returned.
     */
    public ContextLocalEjb[] findLocalEjbs() {

        synchronized (localEjbs) {
            ContextLocalEjb results[] = new ContextLocalEjb[localEjbs.size()];
            return ((ContextLocalEjb[]) localEjbs.values().toArray(results));
        }

    }


    /**
     * Return the resource reference with the specified name, if any;
     * otherwise return <code>null</code>.
     *
     * @param name Name of the desired resource reference
     */
    public ContextResource findResource(String name) {

        synchronized (resources) {
            return ((ContextResource) resources.get(name));
        }

    }


    /**
     * Return the resource link with the specified name, if any;
     * otherwise return <code>null</code>.
     *
     * @param name Name of the desired resource link
     */
    public ContextResourceLink findResourceLink(String name) {

        synchronized (resourceLinks) {
            return ((ContextResourceLink) resourceLinks.get(name));
        }

    }


    /**
     * Return the defined resource links for this application.  If
     * none have been defined, a zero-length array is returned.
     */
    public ContextResourceLink[] findResourceLinks() {

        synchronized (resourceLinks) {
            ContextResourceLink results[] =
                    new ContextResourceLink[resourceLinks.size()];
            return ((ContextResourceLink[]) resourceLinks.values()
                    .toArray(results));
        }

    }


    /**
     * Return the defined resource references for this application.  If
     * none have been defined, a zero-length array is returned.
     */
    public ContextResource[] findResources() {

        synchronized (resources) {
            ContextResource results[] = new ContextResource[resources.size()];
            return ((ContextResource[]) resources.values().toArray(results));
        }

    }


    /**
     * Return the resource environment reference type for the specified
     * name, if any; otherwise return <code>null</code>.
     *
     * @param name Name of the desired resource environment reference
     */
    public String findResourceEnvRef(String name) {

        synchronized (resourceEnvRefs) {
            return ((String) resourceEnvRefs.get(name));
        }

    }


    /**
     * Return the set of resource environment reference names for this
     * web application.  If none have been specified, a zero-length
     * array is returned.
     */
    public String[] findResourceEnvRefs() {

        synchronized (resourceEnvRefs) {
            String results[] = new String[resourceEnvRefs.size()];
            return ((String[]) resourceEnvRefs.keySet().toArray(results));
        }

    }


    /**
     * Return the resource parameters with the specified name, if any;
     * otherwise return <code>null</code>.
     *
     * @param name Name of the desired resource parameters
     */
    public ResourceParams findResourceParams(String name) {

        synchronized (resourceParams) {
            return ((ResourceParams) resourceParams.get(name));
        }

    }


    /**
     * Return the resource parameters with the specified name, if any;
     * otherwise return <code>null</code>.
     *
     * @param name Name of the desired resource parameters
     */
    public ResourceParams[] findResourceParams() {

        synchronized (resourceParams) {
            ResourceParams results[] =
                    new ResourceParams[resourceParams.size()];
            return ((ResourceParams[]) resourceParams.values()
                    .toArray(results));
        }

    }


    /**
     * Return true if the name specified already exists.
     */
    public boolean exists(String name) {

        return (entries.containsKey(name));

    }


    /**
     * Remove any EJB resource reference with the specified name.
     *
     * @param name Name of the EJB resource reference to remove
     */
    public void removeEjb(String name) {

        entries.remove(name);

        ContextEjb ejb = null;
        synchronized (ejbs) {
            ejb = (ContextEjb) ejbs.remove(name);
        }
        if (ejb != null) {
            support.firePropertyChange("ejb", ejb, null);
            ejb.setNamingResources(null);
        }

    }


    /**
     * Remove any environment entry with the specified name.
     *
     * @param name Name of the environment entry to remove
     */
    public void removeEnvironment(String name) {

        entries.remove(name);

        ContextEnvironment environment = null;
        synchronized (envs) {
            environment = (ContextEnvironment) envs.remove(name);
        }
        if (environment != null) {
            support.firePropertyChange("environment", environment, null);
            environment.setNamingResources(null);
        }

    }


    /**
     * Remove any local EJB resource reference with the specified name.
     *
     * @param name Name of the EJB resource reference to remove
     */
    public void removeLocalEjb(String name) {

        entries.remove(name);

        ContextLocalEjb localEjb = null;
        synchronized (localEjbs) {
            localEjb = (ContextLocalEjb) ejbs.remove(name);
        }
        if (localEjb != null) {
            support.firePropertyChange("localEjb", localEjb, null);
            localEjb.setNamingResources(null);
        }

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
     * Remove any resource reference with the specified name.
     *
     * @param name Name of the resource reference to remove
     */
    public void removeResource(String name) {

        entries.remove(name);

        ContextResource resource = null;
        synchronized (resources) {
            resource = (ContextResource) resources.remove(name);
        }
        if (resource != null) {
            support.firePropertyChange("resource", resource, null);
            resource.setNamingResources(null);
        }

    }


    /**
     * Remove any resource environment reference with the specified name.
     *
     * @param name Name of the resource environment reference to remove
     */
    public void removeResourceEnvRef(String name) {

        entries.remove(name);

        String type = null;
        synchronized (resourceEnvRefs) {
            type = (String) resourceEnvRefs.remove(name);
        }
        if (type != null) {
            support.firePropertyChange("resourceEnvRef",
                    name + ":" + type, null);
        }

    }


    /**
     * Remove any resource link with the specified name.
     *
     * @param name Name of the resource link to remove
     */
    public void removeResourceLink(String name) {

        entries.remove(name);

        ContextResourceLink resourceLink = null;
        synchronized (resourceLinks) {
            resourceLink = (ContextResourceLink) resourceLinks.remove(name);
        }
        if (resourceLink != null) {
            support.firePropertyChange("resourceLink", resourceLink, null);
            resourceLink.setNamingResources(null);
        }

    }


    /**
     * Remove any resource parameters with the specified name.
     *
     * @param name Name of the resource parameters to remove
     */
    public void removeResourceParams(String name) {

        ResourceParams resourceParameters = null;
        synchronized (resourceParams) {
            resourceParameters = (ResourceParams) resourceParams.remove(name);
        }
        if (resourceParameters != null) {
            support.firePropertyChange("resourceParams", resourceParameters,
                    null);
            resourceParameters.setNamingResources(null);
        }

    }


}
