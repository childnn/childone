package org.anonymous.chapter02;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

public class ResponseFacade implements ServletResponse {

    private final ServletResponse response;

    public ResponseFacade(Response response) {
        this.response = response;
    }

    public void flushBuffer() throws IOException {
        response.flushBuffer();
    }

    public int getBufferSize() {
        return response.getBufferSize();
    }

    public void setBufferSize(int size) {
        response.setBufferSize(size);
    }

    public String getCharacterEncoding() {
        return response.getCharacterEncoding();
    }

    @Override
    public void setCharacterEncoding(String charset) {
        response.setCharacterEncoding(charset);
    }

    @Override
    public String getContentType() {
        return null;
    }

    public void setContentType(String type) {
        response.setContentType(type);
    }

    public Locale getLocale() {
        return response.getLocale();
    }

    public void setLocale(Locale locale) {
        response.setLocale(locale);
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return response.getOutputStream();
    }

    public PrintWriter getWriter() throws IOException {
        return response.getWriter();
    }

    public boolean isCommitted() {
        return response.isCommitted();
    }

    public void reset() {
        response.reset();
    }

    public void resetBuffer() {
        response.resetBuffer();
    }

    public void setContentLength(int length) {
        response.setContentLength(length);
    }

}