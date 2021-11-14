package com.example.bootactuator.filters.demo2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/8 17:23
 * 注解注入
 */
@WebFilter(urlPatterns = "/*", filterName = "filter2",
    dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.INCLUDE,
            // Async support must be enabled on a servlet and for all filters involved in async request processing.
            DispatcherType.ASYNC})
public class Filter2 implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LocalDateTime start = LocalDateTime.now();
        System.err.println(Filter2.class + "--- Start Time: " + start);
        chain.doFilter(request, response);
        System.err.println(Filter2.class + "--- End Time: " + Duration.between(start, LocalDateTime.now()).toMillis());
    }
}
