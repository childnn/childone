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


package tomcat4.org.apache.naming.resources;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Naming enumeration implementation.
 *
 * @author Remy Maucherat
 * @version $Revision: 1.2 $ $Date: 2004/08/26 21:45:38 $
 */

public class RecyclableNamingEnumeration 
    implements NamingEnumeration {


    // ----------------------------------------------------------- Constructors


    public RecyclableNamingEnumeration(Vector entries) {
        this.entries = entries;
        recycle();
    }


    // -------------------------------------------------------------- Variables


    /**
     * Entries.
     */
    protected Vector entries;


    /**
     * Underlying enumeration.
     */
    protected Enumeration enm;


    // --------------------------------------------------------- Public Methods


    /**
     * Retrieves the next element in the enumeration.
     */
    public Object next()
        throws NamingException {
        return nextElement();
    }


    /**
     * Determines whether there are any more elements in the enumeration.
     */
    public boolean hasMore()
        throws NamingException {
        return enm.hasMoreElements();
    }


    /**
     * Closes this enumeration.
     */
    public void close()
        throws NamingException {
    }


    public boolean hasMoreElements() {
        return enm.hasMoreElements();
    }


    public Object nextElement() {
        return enm.nextElement();
    }


    // -------------------------------------------------------- Package Methods


    /**
     * Recycle.
     */
    void recycle() {
        enm = entries.elements();
    }


}

