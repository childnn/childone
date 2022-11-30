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
package tomcat4.org.apache.naming;

/**
 * Naming enumeration implementation.
 *
 * @author Remy Maucherat
 * @version $Revision: 1.2 $ $Date: 2004/08/26 21:46:17 $
 */

import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import java.util.Iterator;

/**
 * Naming enumeration implementation.
 *
 * @author Remy Maucherat
 */
public class NamingContextEnumeration
        implements NamingEnumeration<NameClassPair> {


    // ----------------------------------------------------------- Constructors


    public NamingContextEnumeration(Iterator<NamingEntry> entries) {
        iterator = entries;
    }


    // -------------------------------------------------------------- Variables


    /**
     * Underlying enumeration.
     */
    protected final Iterator<NamingEntry> iterator;


    // --------------------------------------------------------- Public Methods


    /**
     * Retrieves the next element in the enumeration.
     */
    @Override
    public NameClassPair next()
            throws NamingException {
        return nextElement();
    }


    /**
     * Determines whether there are any more elements in the enumeration.
     */
    @Override
    public boolean hasMore()
            throws NamingException {
        return iterator.hasNext();
    }


    /**
     * Closes this enumeration.
     */
    @Override
    public void close()
            throws NamingException {
    }


    @Override
    public boolean hasMoreElements() {
        return iterator.hasNext();
    }


    @Override
    public NameClassPair nextElement() {
        NamingEntry entry = iterator.next();
        return new NameClassPair(entry.name, entry.value.getClass().getName());
    }


}

// tomcat 4
/*public class NamingContextEnumeration implements NamingEnumeration {


    // ----------------------------------------------------------- Constructors


    public NamingContextEnumeration(Vector entries) {
        enum = entries.elements();
    }


    public NamingContextEnumeration(Enumeration enum) {
        this.enum = enum;
    }


    // -------------------------------------------------------------- Variables


    *//**
     * Underlying enumeration.
     *//*
    protected Enumeration enum;


    // --------------------------------------------------------- Public Methods


    *//**
     * Retrieves the next element in the enumeration.
     *//*
    public Object next()
        throws NamingException {
        return nextElement();
    }


    *//**
     * Determines whether there are any more elements in the enumeration.
     *//*
    public boolean hasMore()
        throws NamingException {
        return enum.hasMoreElements();
    }


    *//**
     * Closes this enumeration.
     *//*
    public void close()
        throws NamingException {
    }


    public boolean hasMoreElements() {
        return enum.hasMoreElements();
    }


    public Object nextElement() {
        NamingEntry entry = (NamingEntry) enum.nextElement();
        return new NameClassPair(entry.name, entry.value.getClass().getName());
    }


}*/

