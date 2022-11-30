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


package org.apache.naming.factory;

import org.apache.naming.ResourceEnvRef;

import javax.naming.*;
import javax.naming.spi.ObjectFactory;
import java.util.Hashtable;

/**
 * Object factory for Resources env.
 *
 * @author Remy Maucherat
 * @version $Revision: 1.3 $ $Date: 2004/08/26 21:44:37 $
 */

public class ResourceEnvFactory
        implements ObjectFactory {


    // ----------------------------------------------------------- Constructors


    // -------------------------------------------------------------- Constants


    // ----------------------------------------------------- Instance Variables


    // --------------------------------------------------------- Public Methods


    // -------------------------------------------------- ObjectFactory Methods


    /**
     * Crete a new Resource env instance.
     *
     * @param obj The reference object describing the DataSource
     */
    public Object getObjectInstance(Object obj, Name name, Context nameCtx,
                                    Hashtable environment)
            throws Exception {

        if (obj instanceof ResourceEnvRef) {
            Reference ref = (Reference) obj;
            ObjectFactory factory = null;
            RefAddr factoryRefAddr = ref.get(Constants.FACTORY);
            if (factoryRefAddr != null) {
                // Using the specified factory
                String factoryClassName =
                        factoryRefAddr.getContent().toString();
                // Loading factory
                ClassLoader tcl =
                        Thread.currentThread().getContextClassLoader();
                Class factoryClass = null;
                if (tcl != null) {
                    try {
                        factoryClass = tcl.loadClass(factoryClassName);
                    } catch (ClassNotFoundException e) {
                    }
                } else {
                    try {
                        factoryClass = Class.forName(factoryClassName);
                    } catch (ClassNotFoundException e) {
                    }
                }
                if (factoryClass != null) {
                    try {
                        factory = (ObjectFactory) factoryClass.newInstance();
                    } catch (Throwable t) {
                    }
                }
            }
            // Note: No defaults here
            if (factory != null) {
                return factory.getObjectInstance
                        (obj, name, nameCtx, environment);
            } else {
                throw new NamingException
                        ("Cannot create resource instance");
            }
        }

        return null;

    }


}

