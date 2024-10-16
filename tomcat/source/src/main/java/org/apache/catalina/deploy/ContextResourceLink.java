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
 * Representation of a resource link for a web application, as
 * represented in a <code>&lt;ResourceLink&gt;</code> element in the
 * server configuration file.
 *
 * @author Remy Maucherat
 * @version $Revision: 1.4 $ $Date: 2004/08/26 21:33:24 $
 */

public final class ContextResourceLink {


    // ------------------------------------------------------------- Properties


    /**
     * The NamingResources with which we are associated (if any).
     */
    protected NamingResources resources = null;
    /**
     * The name of this resource.
     */
    private String name = null;
    /**
     * The type of this resource.
     */
    private String type = null;
    /**
     * The global name of this resource.
     */
    private String global = null;

    public String getName() {
        return (this.name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return (this.type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGlobal() {
        return (this.global);
    }


    // --------------------------------------------------------- Public Methods

    public void setGlobal(String global) {
        this.global = global;
    }


    // -------------------------------------------------------- Package Methods

    /**
     * Return a String representation of this object.
     */
    public String toString() {

        StringBuffer sb = new StringBuffer("ContextResourceLink[");
        sb.append("name=");
        sb.append(name);
        if (type != null) {
            sb.append(", type=");
            sb.append(type);
        }
        if (global != null) {
            sb.append(", global=");
            sb.append(global);
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
