package org.anonymous.chapter03.support;

import org.anonymous.chapter03.connector.http.HttpRequest;
import org.anonymous.chapter03.connector.http.HttpResponse;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2020/10/17 20:58
 */
public interface Processor {
    void process(HttpRequest req, HttpResponse resp);
}
