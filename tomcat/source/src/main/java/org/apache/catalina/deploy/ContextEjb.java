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


/**
 * Representation of an EJB resource reference for a web application, as
 * represented in a <code>&lt;ejb-ref&gt;</code> element in the
 * deployment descriptor.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.8 $ $Date: 2004/08/26 21:33:24 $
 */

public final class ContextEjb {


    // ------------------------------------------------------------- Properties


    /**
     * The NamingResources with which we are associated (if any).
     */
    protected NamingResources resources = null;
    /**
     * The description of this EJB.
     */
    private String description = null;
    /**
     * The name of the EJB home implementation class.
     */
    private String home = null;
    /**
     * The link to a J2EE EJB definition.
     */
    private String link = null;
    /**
     * The name of this EJB.
     */
    private String name = null;
    /**
     * The name of the EJB remote implementation class.
     */
    private String remote = null;
    /**
     * The name of the EJB bean implementation class.
     */
    private String type = null;

    public String getDescription() {
        return (this.description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHome() {
        return (this.home);
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getLink() {
        return (this.link);
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemote() {
        return (this.remote);
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public String getType() {
        return (this.type);
    }


    // --------------------------------------------------------- Public Methods

    public void setType(String type) {
        this.type = type;
    }


    // -------------------------------------------------------- Package Methods

    /**
     * Return a String representation of this object.
     */
    public String toString() {

        StringBuffer sb = new StringBuffer("ContextEjb[");
        sb.append("name=");
        sb.append(name);
        if (description != null) {
            sb.append(", description=");
            sb.append(description);
        }
        if (type != null) {
            sb.append(", type=");
            sb.append(type);
        }
        if (home != null) {
            sb.append(", home=");
            sb.append(home);
        }
        if (remote != null) {
            sb.append(", remote=");
            sb.append(remote);
        }
        if (link != null) {
            sb.append(", link=");
            sb.append(link);
        }
        sb.append("]");
        return (sb.toString());

    }

    public NamingResources getNamingResources() {
        return (this.resources);
    }

    void setNamingResources(NamingResources resources) {
        this.resources = resources;
    }


}
