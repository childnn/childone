package org.anonymous.chapter05.core;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/11/6 21:55
 */
public class SimpleWrapperValue implements Valve, Contained {

    private Container container;

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException {
        SimpleWrapper wrapper = (SimpleWrapper) getContainer();
        ServletRequest req = request.getRequest();
        ServletResponse resp = response.getResponse();
        Servlet servlet;
        HttpServletRequest sreq = null;
        if (req instanceof HttpServletRequest) {
            sreq = (HttpServletRequest) req;
        }
        HttpServletResponse sresp = null;
        if (resp instanceof HttpServletResponse) {
            sresp = (HttpServletResponse) resp;
        }
        // allocate a servlet instance to process this request
        servlet = wrapper.allocate();
        if (sreq != null && sresp != null) {
            servlet.service(sreq, sresp);
        } else {
            servlet.service(req, resp);
        }
    }
}
