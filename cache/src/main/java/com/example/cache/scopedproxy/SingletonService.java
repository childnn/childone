package com.example.cache.scopedproxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2023/7/18
 */
@Component
public class SingletonService {

    @Autowired
    private SessionScopeServiceIfc sessionScopeService;

    public void test() {
        sessionScopeService.session();
    }

}
