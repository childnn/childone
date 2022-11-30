package org.anonymous.chapter03.connector.http;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Extends InputStream to be more efficient reading lines during HTTP
 * header processing.
 *
 * @author <a href="mailto:remm@apache.org">Remy Maucherat</a>
 * @deprecated {@link #readRequestLine(HttpRequestLine)}
 * 返回一个 HTTP 请求中第 1 行的内容, 即包含 URO, 请求方法和 HTTP 版本信息的内容
 * 由于从套接字的输入流中处理字节流是指读取从第 1 个字节到最后 1 个字节(无法从后向前读取)
 * 的内容, 因此 {@link #readRequestLine(HttpRequestLine)}
 * 必须在 {@link #readHeader(HttpHeader)} 之前调用
 * 每次调用 {@link #readHeader(HttpHeader)} 方法都会返回一个 名/实 对, 所以应重复调用
 * {@link #readHeader(HttpHeader)} 方法, 直到读取了所有的请求头信息.
 * {@link #readRequestLine(HttpRequestLine)} 方法的返回值是一个 {@link HttpRequestLine} 实例
 * {@link #readHeader(HttpHeader)} 方法的返回值是一个 {@link HttpHeader}
 */
public class SocketInputStream extends InputStream {


    // -------------------------------------------------------------- Constants


    /**
     * CR.
     */
    private static final byte CR = (byte) '\r';


    /**
     * LF.
     */
    private static final byte LF = (byte) '\n';


    /**
     * SP.
     */
    private static final byte SP = (byte) ' ';


    /**
     * HT.
     */
    private static final byte HT = (byte) '\t';


    /**
     * COLON.
     */
    private static final byte COLON = (byte) ':';


    /**
     * Lower case offset.
     */
    private static final int LC_OFFSET = 'A' - 'a';
    /**
     * The string manager for this package.
     */
    protected static StringManager sm =
            StringManager.getManager(StringManager.class.getPackage().getName());
    /**
     * Internal buffer.
     */
    protected byte[] buf;
    /**
     * Last valid byte.
     */
    protected int count;
    /**
     * Position in the buffer.
     */
    protected int pos;


    // ----------------------------------------------------------- Constructors
    /**
     * Underlying input stream.
     */
    protected InputStream is;


    // -------------------------------------------------------------- Variables


    /**
     * Construct a servlet input stream associated with the specified socket
     * input.
     *
     * @param is         socket input stream
     * @param bufferSize size of the internal buffer
     */
    public SocketInputStream(InputStream is, int bufferSize) {
        this.is = is;
        buf = new byte[bufferSize];

    }


    // ----------------------------------------------------- Instance Variables


    // --------------------------------------------------------- Public Methods

    /**
     * Read the request line, and copies it to the given buffer. This
     * function is meant to be used during the HTTP request header parsing.
     * Do NOT attempt to read the request body using it.
     *
     * @param requestLine Request line object
     * @throws IOException If an exception occurs during the underlying socket
     *                     read operations, or if the given buffer is not big enough to accomodate
     *                     the whole line.
     */
    public void readRequestLine(HttpRequestLine requestLine) throws IOException {

        // Recycling check
        if (requestLine.getMethodEnd() != 0)
            requestLine.recycle();

        // Checking for a blank line
        int chr;
        do { // Skipping CR or LF
            try {
                chr = read();
            } catch (IOException e) {
                chr = -1;
            }
        } while ((chr == CR) || (chr == LF));
        if (chr == -1)
            throw new EOFException(sm.getString("requestStream.readline.error"));
        pos--;

        // Reading the method name
        int maxRead = requestLine.getMethod().length;
        int readStart = pos;
        int readCount = 0;

        boolean space = false;

        while (!space) {
            // if the buffer is full, extend it
            if (readCount >= maxRead) {
                if ((2 * maxRead) <= HttpRequestLine.MAX_METHOD_SIZE) {
                    char[] newBuffer = new char[2 * maxRead];
                    System.arraycopy(requestLine.getMethod(), 0, newBuffer, 0,
                            maxRead);
                    requestLine.setMethod(newBuffer);
                    maxRead = requestLine.getMethod().length;
                } else {
                    throw new IOException
                            (sm.getString("requestStream.readline.toolong"));
                }
            }
            // We're at the end of the internal buffer
            if (pos >= count) {
                int val = read();
                if (val == -1) {
                    throw new IOException
                            (sm.getString("requestStream.readline.error"));
                }
                pos = 0;
                readStart = 0;
            }
            if (buf[pos] == SP) {
                space = true;
            }
            requestLine.getMethod()[readCount] = (char) buf[pos];
            readCount++;
            pos++;
        }

        requestLine.setMethodEnd(readCount - 1);

        // Reading URI

        maxRead = requestLine.getUri().length;
        readStart = pos;
        readCount = 0;

        space = false;

        boolean eol = false;

        while (!space) {
            // if the buffer is full, extend it
            if (readCount >= maxRead) {
                if ((2 * maxRead) <= HttpRequestLine.MAX_URI_SIZE) {
                    char[] newBuffer = new char[2 * maxRead];
                    System.arraycopy(requestLine.getUri(), 0, newBuffer, 0,
                            maxRead);
                    requestLine.setUri(newBuffer);
                    maxRead = requestLine.getUri().length;
                } else {
                    throw new IOException
                            (sm.getString("requestStream.readline.toolong"));
                }
            }
            // We're at the end of the internal buffer
            if (pos >= count) {
                int val = read();
                if (val == -1)
                    throw new IOException
                            (sm.getString("requestStream.readline.error"));
                pos = 0;
                readStart = 0;
            }
            if (buf[pos] == SP) {
                space = true;
            } else if ((buf[pos] == CR) || (buf[pos] == LF)) {
                // HTTP/0.9 style request
                eol = true;
                space = true;
            }
            requestLine.getUri()[readCount] = (char) buf[pos];
            readCount++;
            pos++;
        }

        requestLine.setUriEnd(readCount - 1);

        // Reading protocol

        maxRead = requestLine.getProtocol().length;
        readStart = pos;
        readCount = 0;

        while (!eol) {
            // if the buffer is full, extend it
            if (readCount >= maxRead) {
                if ((2 * maxRead) <= HttpRequestLine.MAX_PROTOCOL_SIZE) {
                    char[] newBuffer = new char[2 * maxRead];
                    System.arraycopy(requestLine.getProtocol(), 0, newBuffer, 0,
                            maxRead);
                    requestLine.setProtocol(newBuffer);
                    maxRead = requestLine.getProtocol().length;
                } else {
                    throw new IOException(sm.getString("requestStream.readline.toolong"));
                }
            }
            // We're at the end of the internal buffer
            if (pos >= count) {
                // Copying part (or all) of the internal buffer to the line
                // buffer
                int val = read();
                if (val == -1)
                    throw new IOException(sm.getString("requestStream.readline.error"));
                pos = 0;
                readStart = 0;
            }
            if (buf[pos] == CR) {
                // Skip CR.
            } else if (buf[pos] == LF) {
                eol = true;
            } else {
                requestLine.getProtocol()[readCount] = (char) buf[pos];
                readCount++;
            }
            pos++;
        }

        requestLine.setProtocolEnd(readCount);
    }


    /**
     * Read a header, and copies it to the given buffer. This
     * function is meant to be used during the HTTP request header parsing.
     * Do NOT attempt to read the request body using it.
     *
     * @param header Request line object
     * @throws IOException If an exception occurs during the underlying socket
     *                     read operations, or if the given buffer is not big enough to accomodate
     *                     the whole line.
     */
    public void readHeader(HttpHeader header) throws IOException {

        // Recycling check
        if (header.getNameEnd() != 0)
            header.recycle();

        // Checking for a blank line
        int chr = read();
        if ((chr == CR) || (chr == LF)) { // Skipping CR
            if (chr == CR)
                read(); // Skipping LF
            header.setNameEnd(0);
            header.setValueEnd(0);
            return;
        } else {
            pos--;
        }

        // Reading the header name

        char[] name = header.getName();
        int maxRead = name.length;
        int readStart = pos;
        int readCount = 0;

        boolean colon = false;

        while (!colon) {
            // if the buffer is full, extend it
            if (readCount >= maxRead) {
                if ((2 * maxRead) <= HttpHeader.MAX_NAME_SIZE) {
                    char[] newBuffer = new char[2 * maxRead];
                    System.arraycopy(name, 0, newBuffer, 0, maxRead);
                    header.setName(newBuffer);
                    maxRead = name.length;
                } else {
                    throw new IOException(sm.getString("requestStream.readline.toolong"));
                }
            }
            // We're at the end of the internal buffer
            if (pos >= count) {
                int val = read();
                if (val == -1) {
                    throw new IOException(sm.getString("requestStream.readline.error"));
                }
                pos = 0;
                readStart = 0;
            }
            if (buf[pos] == COLON) {
                colon = true;
            }
            char val = (char) buf[pos];
            if ((val >= 'A') && (val <= 'Z')) {
                val = (char) (val - LC_OFFSET);
            }
            name[readCount] = val;
            readCount++;
            pos++;
        }

        header.setNameEnd(readCount - 1);

        // Reading the header value (which can be spanned over multiple lines)
        char[] value = header.getValue();
        maxRead = value.length;
        readStart = pos;
        readCount = 0;

        int crPos = -2;

        boolean eol = false;
        boolean validLine = true;

        while (validLine) {

            boolean space = true;

            // Skipping spaces
            // Note : Only leading white spaces are removed. Trailing white
            // spaces are not.
            while (space) {
                // We're at the end of the internal buffer
                if (pos >= count) {
                    // Copying part (or all) of the internal buffer to the line
                    // buffer
                    int val = read();
                    if (val == -1)
                        throw new IOException
                                (sm.getString("requestStream.readline.error"));
                    pos = 0;
                    readStart = 0;
                }
                if ((buf[pos] == SP) || (buf[pos] == HT)) {
                    pos++;
                } else {
                    space = false;
                }
            }

            while (!eol) {
                // if the buffer is full, extend it
                if (readCount >= maxRead) {
                    if ((2 * maxRead) <= HttpHeader.MAX_VALUE_SIZE) {
                        char[] newBuffer = new char[2 * maxRead];
                        System.arraycopy(value, 0, newBuffer, 0, maxRead);
                        header.setValue(newBuffer);
                        maxRead = value.length;
                    } else {
                        throw new IOException(sm.getString("requestStream.readline.toolong"));
                    }
                }
                // We're at the end of the internal buffer
                if (pos >= count) {
                    // Copying part (or all) of the internal buffer to the line
                    // buffer
                    int val = read();
                    if (val == -1)
                        throw new IOException(sm.getString("requestStream.readline.error"));
                    pos = 0;
                    readStart = 0;
                }
                if (buf[pos] == CR) {
                    // skip
                } else if (buf[pos] == LF) {
                    eol = true;
                } else {
                    // FIXME : Check if binary conversion is working fine
                    int ch = buf[pos] & 0xff;
                    header.getValue()[readCount] = (char) ch;
                    readCount++;
                }
                pos++;
            }

            int nextChr = read();

            if ((nextChr != SP) && (nextChr != HT)) {
                pos--;
                validLine = false;
            } else {
                eol = false;
                // if the buffer is full, extend it
                if (readCount >= maxRead) {
                    if ((2 * maxRead) <= HttpHeader.MAX_VALUE_SIZE) {
                        char[] newBuffer = new char[2 * maxRead];
                        System.arraycopy(header.getValue(), 0, newBuffer, 0, maxRead);
                        header.setValue(newBuffer);
                        maxRead = header.getValue().length;
                    } else {
                        throw new IOException(sm.getString("requestStream.readline.toolong"));
                    }
                }
                header.getValue()[readCount] = ' ';
                readCount++;
            }

        }

        header.setValueEnd(readCount);
    }


    /**
     * Read byte.
     */
    public int read() throws IOException {
        if (pos >= count) {
            fill();
            if (pos >= count)
                return -1;
        }
        return buf[pos++] & 0xff;
    }

    /**
     * Returns the number of bytes that can be read from this input
     * stream without blocking.
     */
    public int available() throws IOException {
        return (count - pos) + is.available();
    }


    /**
     * Close the input stream.
     */
    public void close() throws IOException {
        if (is == null)
            return;
        is.close();
        is = null;
        buf = null;
    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Fill the internal buffer using data from the undelying input stream.
     */
    protected void fill() throws IOException {
        pos = 0;
        count = 0;
        int nRead = is.read(buf, 0, buf.length);
        if (nRead > 0) {
            count = nRead;
        }
    }


}
