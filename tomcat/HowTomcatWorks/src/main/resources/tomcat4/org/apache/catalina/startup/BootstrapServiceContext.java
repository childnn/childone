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


import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonController;


/**
 * Context for the BootstrapService.
 *
 * @author Jean-Frederic Clere
 * @version $Revision: 1.2 $ $Date: 2004/08/26 21:41:12 $
 */

public class BootstrapServiceContext implements DaemonContext {
    DaemonController controller = null;
    String[] args = null;

    public DaemonController getController() {
        return controller;
    }
    public void setController(DaemonController controller) {
        this.controller = controller;
    }
    public String[] getArguments() {
        return args;
    }
    public void setArguments(String[] args) {
        this.args = args;
    }
}
