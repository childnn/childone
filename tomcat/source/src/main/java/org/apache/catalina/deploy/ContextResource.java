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
 * Representation of a resource reference for a web application, as
 * represented in a <code>&lt;resource-ref&gt;</code> element in the
 * deployment descriptor.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.7 $ $Date: 2004/08/26 21:33:24 $
 */

public final class ContextResource {


    // ------------------------------------------------------------- Properties


    /**
     * The NamingResources with which we are associated (if any).
     */
    protected NamingResources resources = null;
    /**
     * The authorization requirement for this resource
     * (<code>Application</code> or <code>Container</code>).
     */
    private String auth = null;
    /**
     * The description of this resource.
     */
    private String description = null;
    /**
     * The name of this resource.
     */
    private String name = null;
    /**
     * The sharing scope of this resource factory (<code>Shareable</code>
     * or <code>Unshareable</code>).
     */
    private String scope = "Shareable";
    /**
     * The type of this resource.
     */
    private String type = null;

    public String getAuth() {
        return (this.auth);
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getDescription() {
        return (this.description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScope() {
        return (this.scope);
    }

    public void setScope(String scope) {
        this.scope = scope;
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

        StringBuffer sb = new StringBuffer("ContextResource[");
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
        if (auth != null) {
            sb.append(", auth=");
            sb.append(auth);
        }
        if (scope != null) {
            sb.append(", scope=");
            sb.append(scope);
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
