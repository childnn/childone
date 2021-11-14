package webservice.server;

import webservice.server.impl.HelloWS;

import javax.xml.ws.Endpoint;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/10/18 16:57
 */
public class WSServer  {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:4321/ws", new HelloWS());
    }

}
