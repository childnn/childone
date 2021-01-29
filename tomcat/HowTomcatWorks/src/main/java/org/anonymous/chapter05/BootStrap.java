package org.anonymous.chapter05;

import org.anonymous.chapter05.core.SimpleLoader;
import org.anonymous.chapter05.core.SimpleWrapper;
import org.anonymous.chapter05.values.ClientIPLoggerValue;
import org.anonymous.chapter05.values.HeaderLoggerValue;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Loader;
import org.apache.catalina.Valve;
import org.apache.catalina.connector.http.HttpConnector;

import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/11/6 21:51
 */
public class BootStrap {
    public static void main(String[] args) throws IOException, LifecycleException {
        HttpConnector connector = new HttpConnector();
        SimpleWrapper wrapper = new SimpleWrapper();
        wrapper.setServletClass("ModernServlet");
        Loader loader = new SimpleLoader();
        Valve va1 = new HeaderLoggerValue();
        Valve va2 = new ClientIPLoggerValue();

        wrapper.setLoader(loader);
        wrapper.addValve(va1);
        wrapper.addValve(va2);

        connector.setContainer(wrapper);

        connector.initialize();
        connector.start();

        // make the app wait until we press a key.
        System.in.read();
    }
}
