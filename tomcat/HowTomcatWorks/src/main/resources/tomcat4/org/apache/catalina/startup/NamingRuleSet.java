/*
 * Copyright 1999-2001,2004 The Apache Software Foundation.
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


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Loader;
import org.apache.catalina.Wrapper;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.Rule;
import org.apache.commons.digester.RuleSetBase;
import org.xml.sax.Attributes;


/**
 * <p><strong>RuleSet</strong> for processing the JNDI Enterprise Naming
 * Context resource declaration elements.</p>
 *
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 * @version $Revision: 1.4 $ $Date: 2004/08/26 21:41:41 $
 */

public class NamingRuleSet extends RuleSetBase {


    // ----------------------------------------------------- Instance Variables


    /**
     * The matching pattern prefix to use for recognizing our elements.
     */
    protected String prefix = null;


    // ------------------------------------------------------------ Constructor


    /**
     * Construct an instance of this <code>RuleSet</code> with the default
     * matching pattern prefix.
     */
    public NamingRuleSet() {

        this("");

    }


    /**
     * Construct an instance of this <code>RuleSet</code> with the specified
     * matching pattern prefix.
     *
     * @param prefix Prefix for matching pattern rules (including the
     *  trailing slash character)
     */
    public NamingRuleSet(String prefix) {

        super();
        this.namespaceURI = null;
        this.prefix = prefix;

    }


    // --------------------------------------------------------- Public Methods


    /**
     * <p>Add the set of Rule instances defined in this RuleSet to the
     * specified <code>Digester</code> instance, associating them with
     * our namespace URI (if any).  This method should only be called
     * by a Digester instance.</p>
     *
     * @param digester Digester instance to which the new Rule instances
     *  should be added.
     */
    public void addRuleInstances(Digester digester) {

        digester.addObjectCreate(prefix + "Ejb",
                                 "org.apache.catalina.deploy.ContextEjb");
        digester.addSetProperties(prefix + "Ejb");
        digester.addSetNext(prefix + "Ejb",
                            "addEjb",
                            "org.apache.catalina.deploy.ContextEjb");

        digester.addObjectCreate(prefix + "Environment",
                                 "org.apache.catalina.deploy.ContextEnvironment");
        digester.addSetProperties(prefix + "Environment");
        digester.addSetNext(prefix + "Environment",
                            "addEnvironment",
                            "org.apache.catalina.deploy.ContextEnvironment");

        digester.addObjectCreate(prefix + "LocalEjb",
                                 "org.apache.catalina.deploy.ContextLocalEjb");
        digester.addSetProperties(prefix + "LocalEjb");
        digester.addSetNext(prefix + "LocalEjb",
                            "addLocalEjb",
                            "org.apache.catalina.deploy.ContextLocalEjb");

        digester.addObjectCreate(prefix + "Resource",
                                 "org.apache.catalina.deploy.ContextResource");
        digester.addSetProperties(prefix + "Resource");
        digester.addSetNext(prefix + "Resource",
                            "addResource",
                            "org.apache.catalina.deploy.ContextResource");

        digester.addCallMethod(prefix + "ResourceEnvRef",
                               "addResourceEnvRef", 2);
        digester.addCallParam(prefix + "ResourceEnvRef/name", 0);
        digester.addCallParam(prefix + "ResourceEnvRef/type", 1);

        digester.addObjectCreate(prefix + "ResourceParams",
                                 "org.apache.catalina.deploy.ResourceParams");
        digester.addSetProperties(prefix + "ResourceParams");
        digester.addSetNext(prefix + "ResourceParams",
                            "addResourceParams",
                            "org.apache.catalina.deploy.ResourceParams");

        digester.addCallMethod(prefix + "ResourceParams/parameter",
                               "addParameter", 2);
        digester.addCallParam(prefix + "ResourceParams/parameter/name", 0);
        digester.addCallParam(prefix + "ResourceParams/parameter/value", 1);

    }


}
