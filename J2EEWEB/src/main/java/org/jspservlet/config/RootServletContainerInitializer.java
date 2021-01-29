package org.jspservlet.config;

import org.anonymous.web.listener.MyServletContextListener;
import org.jspservlet.web.servlets.DynamicServlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.HandlesTypes;
import java.util.HashMap;
import java.util.Set;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/10/31 18:24
 * Servlet 3.0 之后 Java-code-based(无 web.xml)形式的 web 应用
 * 将 {@link ServletContainerInitializer} 的实现类放在 /META-INF/services/javax.servlet.ServletContainerInitializer
 * 文件中, 文件名就是 javax.servlet.ServletContainerInitializer.
 */
@HandlesTypes({InitUrl.class})
public class RootServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext ctx) throws ServletException {
        setEncoding(ctx);

        initParams(ctx);

        /*  listeners */
        registerListeners(ctx);

        /* filters */
        registerFilters(ctx);

        /* servlets */
        registerServlet(ctx);


        ctx.setInitParameter("urlWithDB", "jdbc:mysql://localhost:3306/bookDB");
        set.forEach(c -> {
            try {
                ((InitUrl) c.newInstance()).onStartup(ctx.getInitParameter("url"));
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    // 所有 servlet 共享的参数
    private void initParams(ServletContext ctx) {
        ctx.setInitParameter("driverClass", "com.mysql.cj.jdbc.Driver");
        ctx.setInitParameter("username", "root");
        ctx.setInitParameter("password", "root");
    }

    // 非 web.xml, 非注解形式注册 servlet
    private void registerServlet(ServletContext ctx) {
        ServletRegistration.Dynamic dynamicServlet = ctx.addServlet("dynamicServlet", DynamicServlet.class);
        dynamicServlet.setLoadOnStartup(1);
        dynamicServlet.addMapping("/dynamic");
        dynamicServlet.setAsyncSupported(true);
        //dynamicServlet.setRunAsRole();
        dynamicServlet.setInitParameters(new HashMap<String, String>() {{
            put("k1", "测试");
        }});
    }

    private void setEncoding(ServletContext ctx) {
        // servlet-4.0/tomcat-9
        //ctx.setRequestCharacterEncoding(StandardCharsets.UTF_8.name());
        //ctx.setResponseCharacterEncoding("");
    }

    private void registerFilters(ServletContext ctx) {

    }

    private void registerListeners(ServletContext ctx) {
        ctx.addListener(MyServletContextListener.class);
    }
}
