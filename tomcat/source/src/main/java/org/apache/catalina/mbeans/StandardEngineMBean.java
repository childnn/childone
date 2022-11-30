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


import org.apache.commons.modeler.BaseModelMBean;

import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.RuntimeOperationsException;


/**
 * <p>A <strong>ModelMBean</strong> implementation for the
 * <code>org.apache.catalina.core.StandardEngine</code> component.</p>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.9 $ $Date: 2004/08/26 21:36:09 $
 */

public class StandardEngineMBean extends BaseModelMBean {

    /**
     * The <code>MBeanServer</code> for this application.
     */
    private static MBeanServer mserver = MBeanUtils.createServer();

    // ----------------------------------------------------------- Constructors


    /**
     * Construct a <code>ModelMBean</code> with default
     * <code>ModelMBeanInfo</code> information.
     *
     * @throws MBeanException             if the initializer of an object
     *                                    throws an exception
     * @throws RuntimeOperationsException if an IllegalArgumentException
     *                                    occurs
     */
    public StandardEngineMBean()
            throws MBeanException, RuntimeOperationsException {

        super();

    }


    // ------------------------------------------------------------- Attributes


    // ------------------------------------------------------------- Operations


}
