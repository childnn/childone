package org.anonymous.chapter02;

import org.anonymous.Constants;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

public class Response implements ServletResponse {

    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;
    PrintWriter writer;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    /* This method is used to serve a static page */
    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            /* request.getUri has been replaced by request.getRequestURI */
            File file = new File(Constants.WEB_ROOT, request.getUri());
            fis = new FileInputStream(file);
      /*
         HTTP Response = Status-Line
           *(( general-header | response-header | entity-header ) CRLF)
           CRLF
           [ message-body ]
         Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
      */
            int ch;
            while ((ch = fis.read(bytes, 0, BUFFER_SIZE)) != -1) {
                output.write(bytes, 0, ch);
            }
        } catch (FileNotFoundException e) {
            String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: 23\r\n" +
                    "\r\n" +
                    "<h1>File Not Found</h1>";
            output.write(errorMessage.getBytes());
        } finally {
            if (fis != null)
                fis.close();
        }
    }


    /**
     * implementation of ServletResponse
     */
    public void flushBuffer() throws IOException {
    }

    public int getBufferSize() {
        return 0;
    }

    public void setBufferSize(int size) {
    }

    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String charset) {

    }

    @Override
    public String getContentType() {
        return null;
    }

    public void setContentType(String type) {
    }

    public Locale getLocale() {
        return null;
    }

    public void setLocale(Locale locale) {
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    public PrintWriter getWriter() throws IOException {
        // autoflush is true, println() will flush,
        // but print() will not.
        // java.io.PrintWriter.newLine
        writer = new PrintWriter(output, true);
        return writer;
    }

    public boolean isCommitted() {
        return false;
    }

    public void reset() {
    }

    public void resetBuffer() {
    }

    public void setContentLength(int length) {
    }

}