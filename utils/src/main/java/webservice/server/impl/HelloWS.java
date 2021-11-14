package webservice.server.impl;

import webservice.server.HelloWorld;

import javax.jws.WebService;

/**
 * ~~ Talk is cheap. Show me the code. ~~ :-)
 *
 * @author MiaoOne
 * @since 2021/10/18 16:56
 */
@WebService(endpointInterface = "webservice.server.HelloWorld"/* serviceName = "hello",*/
        , targetNamespace = "http://server.webservice.impl/") // 必须以 / 结尾
public class HelloWS implements HelloWorld {
    @Override
    public String hello(String name) {
        // Collections.emptyList().get(1);
        System.out.println("name = " + name);
        return "DSFSDASDFDASFAD";
    }
}
