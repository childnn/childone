package webroot;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * /servlet/PrimitiveServlet
 */
public class PrimitiveServlet implements Servlet {

    public void init(ServletConfig config) throws ServletException {
        System.out.println("init");
    }

    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        System.out.println("from service");
        // org.anonymous.chapter02.Response.getWriter
        PrintWriter out = response.getWriter();
        // java.io.PrintWriter.newLine
        out.println("Hello. Roses are red.");
        out.print("Violets are blue."); // 此行并不会返回到浏览器(客户端), 此处不会 flush
    }

    public void destroy() {
        System.out.println("destroy");
    }

    public String getServletInfo() {
        return null;
    }

    public ServletConfig getServletConfig() {
        return null;
    }

}
