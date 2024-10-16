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


package org.apache.catalina.connector;


import org.apache.catalina.Response;
import org.apache.catalina.util.StringManager;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * Convenience implementation of <b>ServletOutputStream</b> that works with
 * the standard ResponseBase implementation of <b>Response</b>.  If the content
 * length has been set on our associated Response, this implementation will
 * enforce not writing more than that many bytes on the underlying stream.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.7 $ $Date: 2004/08/26 21:30:19 $
 * @deprecated
 */

public class ResponseStream
        extends ServletOutputStream {


    // ----------------------------------------------------------- Constructors


    /**
     * The localized strings for this package.
     */
    protected static StringManager sm =
            StringManager.getManager(Constants.Package);


    // ----------------------------------------------------- Instance Variables
    /**
     * Has this stream been closed?
     */
    protected boolean closed = false;


    /**
     * Should we commit the response when we are flushed?
     */
    protected boolean commit = false;


    /**
     * The number of bytes which have already been written to this stream.
     */
    protected int count = 0;


    /**
     * The content length past which we will not write, or -1 if there is
     * no defined content length.
     */
    protected int length = -1;


    /**
     * The Response with which this input stream is associated.
     */
    protected Response response = null;
    /**
     * The underlying output stream to which we should write data.
     */
    protected OutputStream stream = null;
    /**
     * Has this response output been suspended?
     */
    protected boolean suspended = false;


    /**
     * Construct a servlet output stream associated with the specified Request.
     *
     * @param response The associated response
     */
    public ResponseStream(Response response) {

        super();
        closed = false;
        commit = false;
        count = 0;
        this.response = response;
        this.stream = response.getStream();
        this.suspended = response.isSuspended();

    }


    // ------------------------------------------------------------- Properties

    /**
     * [Package Private] Return the "commit response on flush" flag.
     */
    boolean getCommit() {

        return (this.commit);

    }


    /**
     * [Package Private] Set the "commit response on flush" flag.
     *
     * @param commit The new commit flag
     */
    void setCommit(boolean commit) {

        this.commit = commit;

    }

    /**
     * Suspended flag accessor.
     */
    boolean isSuspended() {

        return (this.suspended);

    }

    /**
     * Set the suspended flag.
     */
    void setSuspended(boolean suspended) {

        this.suspended = suspended;

    }


    // --------------------------------------------------------- Public Methods

    /**
     * Close this output stream, causing any buffered data to be flushed and
     * any further output data to throw an IOException.
     */
    public void close() throws IOException {

        if (suspended)
            throw new IOException
                    (sm.getString("responseStream.suspended"));

        if (closed)
            throw new IOException(sm.getString("responseStream.close.closed"));

        response.getResponse().flushBuffer();
        closed = true;

    }


    /**
     * Flush any buffered data for this output stream, which also causes the
     * response to be committed.
     */
    public void flush() throws IOException {

        if (suspended)
            throw new IOException
                    (sm.getString("responseStream.suspended"));

        if (closed)
            throw new IOException(sm.getString("responseStream.flush.closed"));

        if (commit)
            response.getResponse().flushBuffer();

    }


    /**
     * Write the specified byte to our output stream.
     *
     * @param b The byte to be written
     * @throws IOException if an input/output error occurs
     */
    public void write(int b) throws IOException {

        if (suspended)
            return;

        if (closed)
            throw new IOException(sm.getString("responseStream.write.closed"));

        if ((length > 0) && (count >= length))
            throw new IOException(sm.getString("responseStream.write.count"));

        ((ResponseBase) response).write(b);
        count++;

    }


    /**
     * Write <code>b.length</code> bytes from the specified byte array
     * to our output stream.
     *
     * @param b The byte array to be written
     * @throws IOException if an input/output error occurs
     */
    public void write(byte b[]) throws IOException {

        if (suspended)
            return;

        write(b, 0, b.length);

    }


    /**
     * Write <code>len</code> bytes from the specified byte array, starting
     * at the specified offset, to our output stream.
     *
     * @param b   The byte array containing the bytes to be written
     * @param off Zero-relative starting offset of the bytes to be written
     * @param len The number of bytes to be written
     * @throws IOException if an input/output error occurs
     */
    public void write(byte b[], int off, int len) throws IOException {

        if (suspended)
            return;

        if (closed)
            throw new IOException(sm.getString("responseStream.write.closed"));

        int actual = len;
        if ((length > 0) && ((count + len) >= length))
            actual = length - count;
        ((ResponseBase) response).write(b, off, actual);
        count += actual;
        if (actual < len)
            throw new IOException(sm.getString("responseStream.write.count"));

    }


    // -------------------------------------------------------- Package Methods


    /**
     * Has this response stream been closed?
     */
    boolean closed() {

        return (this.closed);

    }


    /**
     * Reset the count of bytes written to this stream to zero.
     */
    void reset() {

        count = 0;

    }


}
