package org.anonymous.chapter05.values;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/11/6 21:52
 */
public class ClientIPLoggerValue implements Valve, Contained {
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
        // pass this req on to the next value in our pipeline
        valveContext.invokeNext(request, response);
        System.out.println("Client IP Logger Value");
        ServletRequest req = request.getRequest();
        System.out.println(req.getRemoteAddr());
        System.out.println("----------------------------------------------------");
    }
}
