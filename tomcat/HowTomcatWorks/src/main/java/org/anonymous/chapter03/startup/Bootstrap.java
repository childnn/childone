package org.anonymous.chapter03.startup;

import org.anonymous.chapter03.connector.http.HttpConnector;

/**
 * 启动类
 */
public final class Bootstrap {

    public static void main(String[] args) {
        HttpConnector connector = new HttpConnector();
        connector.start();
    }

}