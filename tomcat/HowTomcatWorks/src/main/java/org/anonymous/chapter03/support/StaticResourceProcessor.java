package org.anonymous.chapter03.support;

import org.anonymous.chapter03.connector.http.HttpRequest;
import org.anonymous.chapter03.connector.http.HttpResponse;

import java.io.IOException;

public class StaticResourceProcessor implements Processor {

    @Override
    public void process(HttpRequest request, HttpResponse response) {
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
