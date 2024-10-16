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


package org.apache.catalina.connector.http;


/**
 * HTTP request line enum type.
 *
 * @author Remy Maucherat
 * @version $Revision: 1.7 $ $Date: 2004/08/26 21:28:57 $
 * @deprecated
 */

final class HttpRequestLine {


    // -------------------------------------------------------------- Constants


    public static final int INITIAL_METHOD_SIZE = 8;
    public static final int INITIAL_URI_SIZE = 64;
    public static final int INITIAL_PROTOCOL_SIZE = 8;
    public static final int MAX_METHOD_SIZE = 1024;
    public static final int MAX_URI_SIZE = 32768;
    public static final int MAX_PROTOCOL_SIZE = 1024;


    // ----------------------------------------------------------- Constructors
    public char[] method;
    public int methodEnd;


    // ----------------------------------------------------- Instance Variables
    public char[] uri;
    public int uriEnd;
    public char[] protocol;
    public int protocolEnd;
    public HttpRequestLine() {

        this(new char[INITIAL_METHOD_SIZE], 0, new char[INITIAL_URI_SIZE], 0,
                new char[INITIAL_PROTOCOL_SIZE], 0);

    }
    public HttpRequestLine(char[] method, int methodEnd,
                           char[] uri, int uriEnd,
                           char[] protocol, int protocolEnd) {

        this.method = method;
        this.methodEnd = methodEnd;
        this.uri = uri;
        this.uriEnd = uriEnd;
        this.protocol = protocol;
        this.protocolEnd = protocolEnd;

    }


    // ------------------------------------------------------------- Properties


    // --------------------------------------------------------- Public Methods

    /**
     * Release all object references, and initialize instance variables, in
     * preparation for reuse of this object.
     */
    public void recycle() {

        methodEnd = 0;
        uriEnd = 0;
        protocolEnd = 0;

    }


    /**
     * Test if the uri includes the given char array.
     */
    public int indexOf(char[] buf) {
        return indexOf(buf, buf.length);
    }


    /**
     * Test if the value of the header includes the given char array.
     */
    public int indexOf(char[] buf, int end) {
        char firstChar = buf[0];
        int pos = 0;
        while (pos < uriEnd) {
            pos = indexOf(firstChar, pos);
            if (pos == -1)
                return -1;
            if ((uriEnd - pos) < end)
                return -1;
            for (int i = 0; i < end; i++) {
                if (uri[i + pos] != buf[i])
                    break;
                if (i == (end - 1))
                    return pos;
            }
            pos++;
        }
        return -1;
    }


    /**
     * Test if the value of the header includes the given string.
     */
    public int indexOf(String str) {
        return indexOf(str.toCharArray(), str.length());
    }


    /**
     * Returns the index of a character in the value.
     */
    public int indexOf(char c, int start) {
        for (int i = start; i < uriEnd; i++) {
            if (uri[i] == c)
                return i;
        }
        return -1;
    }


    // --------------------------------------------------------- Object Methods


    public int hashCode() {
        // FIXME
        return 0;
    }


    public boolean equals(Object obj) {
        return false;
    }


}
