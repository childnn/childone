package com.example.cache.scopedproxy;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Date;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2023/7/18
 */
@SessionScope
@Component
public class SessionScopeService implements SessionScopeServiceIfc {

    private final Date date = new Date();

    @Override
    public void session() {
        System.out.println("date = " + date);
    }
}
