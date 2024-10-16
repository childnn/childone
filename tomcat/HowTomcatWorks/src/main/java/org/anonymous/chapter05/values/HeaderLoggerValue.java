package org.anonymous.chapter05.values;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/11/6 21:44
 */
public class HeaderLoggerValue implements Valve, Contained {

    private Container container;

    @Override
    public void invoke(Request request, Response response, ValveContext valveContext) throws IOException, ServletException {
        // pass this request on to the next value in our pipeline
        valveContext.invokeNext(request, response);

        System.out.println("Header Logger Value");
        ServletRequest req = request.getRequest();
        if (req instanceof HttpServletRequest) {
            HttpServletRequest hreq = (HttpServletRequest) req;
            Enumeration headerNames = hreq.getHeaderNames();

            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement().toString();
                String headerValue = hreq.getHeader(headerName);
                System.out.println(headerName + ": " + headerValue);
            }
        } else {
            System.out.println("Not an HTTP Request");
        }
        System.out.println("============================================");
    }

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

}
