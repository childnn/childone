package com.example.bootactuator.filters.demo1;

import javax.servlet.*;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/1/7 14:55
 */
public class Filter1 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LocalDateTime start = LocalDateTime.now();
        System.out.println(Filter1.class + "--- Start Time: " + start);
        chain.doFilter(request, response);
        System.out.println(Filter1.class + "--- End Time: " + Duration.between(start, LocalDateTime.now()).toMillis());
    }

}
