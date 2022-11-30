package org.anonymous.chapter05.core;

import org.apache.catalina.*;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/11/6 22:10
 */
public class SimplePipeline implements Pipeline {
    public SimplePipeline(Container c) {

    }

    @Override
    public Valve getBasic() {
        return null;
    }

    @Override
    public void setBasic(Valve valve) {

    }

    @Override
    public void addValve(Valve valve) {

    }

    @Override
    public Valve[] getValves() {
        return new Valve[0];
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        // Invoke the first Valve in this pipeline for this request
        (new SimplePipelineValveContext()).invokeNext(request, response);
    }

    @Override
    public void removeValve(Valve valve) {

    }


    protected class SimplePipelineValveContext
            implements ValveContext {


        // ------------------------------------------------- Instance Variables


        protected int stage = 0;


        // --------------------------------------------------------- Properties


        /**
         * Return descriptive information about this ValveContext
         * implementation.
         */
        public String getInfo() {
            return "";
        }


        // ----------------------------------------------------- Public Methods


        /**
         * Cause the <code>invoke()</code> method of the next Valve that is
         * part of the Pipeline currently being processed (if any) to be
         * executed, passing on the specified request and response objects
         * plus this <code>ValveContext</code> instance.  Exceptions thrown by
         * a subsequently executed Valve (or a Filter or Servlet at the
         * application level) will be passed on to our caller.
         * <p>
         * If there are no more Valves to be executed, an appropriate
         * ServletException will be thrown by this ValveContext.
         *
         * @param request  The request currently being processed
         * @param response The response currently being created
         * @throws IOException      if thrown by a subsequent Valve, Filter, or
         *                          Servlet
         * @throws ServletException if thrown by a subsequent Valve, Filter,
         *                          or Servlet
         * @throws ServletException if there are no further Valves
         *                          configured in the Pipeline currently being processed
         */
        public void invokeNext(Request request, Response response)
                throws IOException, ServletException {

            int subscript = stage;
            stage = stage + 1;

            // Invoke the requested Valve for the current request thread
            /*if (subscript < valves.length) {
                valves[subscript].invoke(request, response, this);
            } else if ((subscript == valves.length) && (basic != null)) {
                basic.invoke(request, response, this);
            } else {
                throw new ServletException
                        (sm.getString("standardPipeline.noValve"));
            }*/

        }


    }

}
